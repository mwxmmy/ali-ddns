package top.sshh.ddns.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import top.sshh.ddns.entity.Domain
import top.sshh.ddns.mapper.DomainMapper
import top.sshh.ddns.service.DomainService

/**
 * @author 钟业海
 * @date 2020/8/23
 */
@Service
class DomainServiceImpl: ServiceImpl<DomainMapper, Domain>(),DomainService {
}