package top.sshh.ddns.controller

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.alidns.model.v20150109.AddDomainRecordRequest
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest
import com.aliyuncs.exceptions.ClientException
import com.aliyuncs.profile.DefaultProfile
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import top.sshh.ddns.entity.Domain
import top.sshh.ddns.service.AliConfigService
import top.sshh.ddns.service.DomainService
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author 钟业海
 * @date 2020/8/23
 */
@Slf4j
@EnableScheduling
@Service
class ScheduledController {

    @Autowired
    lateinit var configService: AliConfigService

    @Autowired
    lateinit var domainService: DomainService

    var dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @Value("\${IPEchoUrl}")
    lateinit var IPEchoUrl: String

    val restTemplate: RestTemplate by lazy {
        var template = RestTemplate();
        var list = template.messageConverters
        for (messageConverter in list) {
            if (messageConverter is StringHttpMessageConverter) {
                messageConverter.defaultCharset = Charset.forName("utf-8")
                break
            }
        }
        return@lazy template
    }

    val client: DefaultAcsClient by lazy {
        var aliConfig = configService.getOne(QueryWrapper())
        var profile = DefaultProfile.getProfile("cn-hangzhou", aliConfig.accessKey, aliConfig.accessSecret)
        return@lazy DefaultAcsClient(profile)
    }


    @Scheduled(cron = "0/10 * * * * *")
    fun checkIp() {
        var ip = getIp()
        if (ip.isBlank()) {
            println(dateFormat.format(LocalDateTime.now())+"---------定时任务开始执行,当前ip为空:"+ip)
            return
        }
        println(dateFormat.format(LocalDateTime.now())+"---------定时任务开始执行,当前ip为:"+ip)
        var domainList = domainService.list()
        for (domain in domainList) {
            val domain = getDomain(domain)
            if (ip == domain.domainip) {
                continue
            } else {
                domain.domainip = ip
            }
            if (domain.domainname.isNullOrBlank() || domain.domainrr.isNullOrBlank()) {
                continue
            }
            if(domain.recordid.isNullOrBlank()){
                addDomain(domain)
            }else{
                try{
                    updateDomain(domain)
                }catch (e: ClientException){
                    when (e.errCode) {
                        "DomainRecordNotBelongToUser" -> {
                            addDomain(domain)
                        }
                        "DomainRecordDuplicate" -> {
                            domainService.updateById(domain)
                        }
                        else -> {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    fun getDomain(domain: Domain): Domain {
        var request = DescribeDomainRecordsRequest()
        request.domainName = domain.domainname
        request.searchMode = "EXACT"
        request.keyWord = domain.domainrr
        request.type = "A"
        var domainRecords = client.getAcsResponse(request).domainRecords
        return if (domainRecords.size > 0) {
            domain.recordid = domainRecords[0].recordId
            domain.domainip = domainRecords[0].value
            return domain
        } else return domain
    }

    fun addDomain(domain: Domain) {
        var request = AddDomainRecordRequest()
        request.domainName = domain.domainname
        request.rr = domain.domainrr
        request.type = "A"
        request.value = domain.domainip
        domain.recordid = client.getAcsResponse(request).recordId
        println(dateFormat.format(LocalDateTime.now())+"---------添加域名"+domain.domainrr+"."+domain.domainname+"解析地址为:"+domain.domainip)
        domainService.updateById(domain)
    }

    fun updateDomain(domain: Domain) {
        var request = UpdateDomainRecordRequest()
        request.rr = domain.domainrr
        request.recordId = domain.recordid
        request.type = "A"
        request.value = domain.domainip
        client.getAcsResponse(request)
        println(dateFormat.format(LocalDateTime.now())+"---------更新域名"+domain.domainrr+"."+domain.domainname+"解析地址为:"+domain.domainip)
        domainService.updateById(domain)
    }


    fun getIp(): String {
        var ip: String? = restTemplate.getForObject(
                IPEchoUrl,
                String::class.java
        )
        if (ip.isNullOrBlank()) {
            return ""
        }
        return ip.trim()
    }
}