/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : future

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2017-11-16 11:15:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for future_money_project
-- ----------------------------
DROP TABLE IF EXISTS `future_money_project`;
CREATE TABLE `future_money_project` (
  `id` varchar(255) NOT NULL,
  `project_name` varchar(255) NOT NULL,
  `project_price` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `delete_time` varchar(255) default NULL,
  `create_time` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`,`project_price`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
