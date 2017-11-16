/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : future

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2017-11-16 11:15:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for future_money_member
-- ----------------------------
DROP TABLE IF EXISTS `future_money_member`;
CREATE TABLE `future_money_member` (
  `id` varchar(255) NOT NULL,
  `true_name` varchar(255) NOT NULL,
  `college` varchar(255) NOT NULL,
  `major` varchar(255) NOT NULL,
  `sn` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `note` varchar(500) default NULL,
  `create_time` varchar(255) NOT NULL,
  `verify_time` varchar(255) default NULL,
  `delete_time` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of future_money_member
-- ----------------------------
INSERT INTO `future_money_member` VALUES ('756225104794353664', '魏霖涛', '物理与信息工程学院', '电子科学与技术', '111100727', '0ab44bd43d6b18fcd5cd928d6faab1b8', '1', '3', null, '', null, null);
INSERT INTO `future_money_member` VALUES ('756225104794353663', '管理员', '福州大学', '福州大学图书馆', '111111', '0ab44bd43d6b18fcd5cd928d6faab1b8', '1', '2', null, '', null, null);
