/*
 Navicat Premium Data Transfer

 Source Server         : MySQL-5.7-168.3
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 192.168.168.3:3306
 Source Schema         : se-dev-file

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 14/08/2020 17:28:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file_system
-- ----------------------------
DROP TABLE IF EXISTS `file_system`;
CREATE TABLE `file_system`  (
  `file_id` bigint(20) NOT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `file_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `uploader_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `fileinfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_system
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
