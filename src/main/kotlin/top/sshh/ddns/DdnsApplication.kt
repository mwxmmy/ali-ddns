package top.sshh.ddns

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DdnsApplication

fun main(args: Array<String>) {
    runApplication<DdnsApplication>(*args)
}
