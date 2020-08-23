package top.sshh.ddns.entity

import lombok.Data
import lombok.ToString

/**
 * @author 钟业海
 * @date 2020/8/23
 */
@Data
@ToString
class AliConfig {
    var accessKey: String ? = null
    var accessSecret: String ? = null
}