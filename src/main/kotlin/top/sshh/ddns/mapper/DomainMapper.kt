package top.sshh.ddns.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import top.sshh.ddns.entity.Domain

/**
 * @author 钟业海
 * @date 2020/8/23
 */
@Mapper
interface DomainMapper: BaseMapper<Domain> {
}