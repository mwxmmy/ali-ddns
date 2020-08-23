package top.sshh.ddns.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import lombok.Data
import lombok.ToString

/**
 * @author 钟业海
 * @date 2020/8/23
 */
@Data
@ToString
class Domain {
    @TableId
    var id: Int ? = null
    var recordid: String ? = null
    var domainrr: String ? = null
    var domainname: String ? = null

    @TableField(exist = false)
    var domainip: String ? = null
}