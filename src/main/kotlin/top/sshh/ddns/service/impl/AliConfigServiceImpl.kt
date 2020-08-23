package top.sshh.ddns.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import top.sshh.ddns.entity.AliConfig
import top.sshh.ddns.mapper.AliConfigMapper
import top.sshh.ddns.service.AliConfigService

/**
 * @author 钟业海
 * @date 2020/8/23
 */
@Service
class AliConfigServiceImpl: ServiceImpl<AliConfigMapper, AliConfig>(),AliConfigService {
}