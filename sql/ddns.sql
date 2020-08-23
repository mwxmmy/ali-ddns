/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : ddns

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-08-23 20:24:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ali_config`
-- ----------------------------
DROP TABLE IF EXISTS `ali_config`;
CREATE TABLE `ali_config` (
  `access_key` varchar(255) DEFAULT NULL COMMENT '密钥key',
  `access_secret` varchar(255) DEFAULT NULL COMMENT '密钥'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='阿里云配置表';

-- ----------------------------
-- Table structure for `domain`
-- ----------------------------
DROP TABLE IF EXISTS `domain`;
CREATE TABLE `domain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recordid` varchar(255) DEFAULT NULL COMMENT '域名id',
  `domainrr` varchar(255) DEFAULT NULL COMMENT '主机记录',
  `domainname` varchar(255) DEFAULT NULL COMMENT '域名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='解析记录表';

