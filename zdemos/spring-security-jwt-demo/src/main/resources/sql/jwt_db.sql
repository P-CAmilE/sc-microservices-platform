/*
 Navicat Premium Data Transfer

 Source Server         : MySQL-5.7-168.3
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 192.168.168.3:3306
 Source Schema         : jwt_db

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 12/08/2020 17:17:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for jwt_user
-- ----------------------------
DROP TABLE IF EXISTS `jwt_user`;
CREATE TABLE `jwt_user`  (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `authorities` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jwt_user
-- ----------------------------
INSERT INTO `jwt_user` VALUES (1, 'tree1', 'tree1', 'ROLE_ADMIN,ROLE_USER');
INSERT INTO `jwt_user` VALUES (2, 'tree2', 'tree2', 'ROLE_USER');
INSERT INTO `jwt_user` VALUES (3, 'tree3', 'tree3', 'ROLE_ADMIN');

SET FOREIGN_KEY_CHECKS = 1;
