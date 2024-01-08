/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50721
 Source Host           : localhost
 Source Database       : blog

 Target Server Version : 50721
 File Encoding         : utf-8

 Date: 04/28/2018 09:14:32 AM
*/

/*SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));*/

show databases;
create database cbse_blog;
use cbse_blog;

set names utf8;
set foreign_key_checks = 0;

-- ----------------------------
--  Table structure for `me_article`
-- ----------------------------
drop table if exists `me_article`;
create table `me_article` (
                              `id` int(11) not null auto_increment,
                              `comment_counts` int(11) default 0,
                              `create_date` datetime default null,
                              `summary` varchar(100) default null,
                              `title` varchar(64) default null,
                              `view_counts` int(11) default 0,
                              `star_counts` int(11) default 0,
                              `weight` int(11) not null,
                              `author_id` bigint(20) default null,
                              `body_id` bigint(20) default null,
                              `category_id` int(11) default null,
                              primary key (`id`)
) engine=innodb auto_increment=25 default charset=utf8;

-- ----------------------------
--  Table structure for `me_article_body`
-- ----------------------------
drop table if exists `me_article_body`;
create table `me_article_body` (
                                   `id` bigint(20) not null auto_increment,
                                   `content` longtext,
                                   `content_html` longtext,
                                   primary key (`id`)
) engine=innodb auto_increment=38 default charset=utf8;

-- ----------------------------
--  Table structure for `me_article_tag`
-- ----------------------------
drop table if exists `me_article_tag`;
create table `me_article_tag` (
                                  `article_id` int(11) not null,
                                  `tag_id` int(11) not null
) engine=innodb default charset=utf8;

-- ----------------------------
--  Table structure for `me_category`
-- ----------------------------
drop table if exists `me_category`;
create table `me_category` (
                               `id` int(11) not null auto_increment,
                               `avatar` varchar(255) default null,
                               `categoryname` varchar(255) default null,
                               `description` varchar(255) default null,
                               primary key (`id`)
) engine=innodb auto_increment=6 default charset=utf8;

-- ----------------------------
--  Table structure for `me_comment`
-- ----------------------------
drop table if exists `me_comment`;
create table `me_comment` (
                              `id` int(11) not null auto_increment,
                              `content` varchar(255) default null,
                              `create_date` datetime default null,
                              `article_id` int(11) default null,
                              `author_id` bigint(20) default null,
                              `parent_id` int(11) default null,
                              `to_uid` bigint(20) default null,
                              `level` varchar(1) default null,
                              primary key (`id`)
) engine=innodb auto_increment=53 default charset=utf8;

-- ----------------------------
--  Table structure for `me_tag`
-- ----------------------------
drop table if exists `me_tag`;
create table `me_tag` (
                          `id` int(11) not null auto_increment,
                          `avatar` varchar(255) default null,
                          `tagname` varchar(255) default null,
                          primary key (`id`)
) engine=innodb auto_increment=9 default charset=utf8;

-- ----------------------------
--  Table structure for `sys_log`
-- ----------------------------
drop table if exists `sys_log`;
create table `sys_log` (
                           `id` int(11) not null auto_increment,
                           `create_date` datetime default null,
                           `ip` varchar(15) collate utf8_bin default null,
                           `method` varchar(100) collate utf8_bin default null,
                           `module` varchar(10) collate utf8_bin default null,
                           `nickname` varchar(10) collate utf8_bin default null,
                           `operation` varchar(25) collate utf8_bin default null,
                           `params` varchar(255) collate utf8_bin default null,
                           `time` bigint(20) default null,
                           `userid` bigint(20) default null,
                           primary key (`id`)
) engine=innodb auto_increment=2994 default charset=utf8 collate=utf8_bin;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
drop table if exists `sys_user`;
create table `sys_user` (
                            `id` bigint(20) not null auto_increment,
                            `account` varchar(64) default null,
                            `admin` bit(1) default null,
                            `avatar` varchar(255) default null,
                            `create_date` datetime default null,
                            `deleted` bit(1) default null,
                            `email` varchar(128) default null,
                            `last_login` datetime default null,
                            `mobile_phone_number` varchar(20) default null,
                            `nickname` varchar(255) default null,
                            `password` varchar(64) default null,
                            `salt` varchar(255) default null,
                            `status` varchar(255) default null,
                            `about_me_visible` bit(1) default null,
                            `about_me` varchar(255) default null,
                            primary key (`id`),
                            unique key `UK_awpog86ljqwb89aqa1c5gvdrd` (`account`)
) engine=innodb auto_increment=16 default charset=utf8;

drop table if exists `me_star`;
create table `me_star` (
                                  `user_id` int(11) not null,
                                  `article_id` int(11) not null
) engine=innodb default charset=utf8;

drop table if exists `me_notification`;
CREATE TABLE `me_notification` (
                                 `id` bigint(20) not null auto_increment,
                                 `read_status` bit(1) default null,
                                 `title` varchar(64) default null,
                                 `content` varchar(255) default null,
                                 `type` int(10) default null comment '0: @me notifications (comment,star,reply) 1: system notifications',
                                 `from_uid` bigint(20) default null,
                                 `create_date` datetime default null,
                                 primary key (`id`)
) engine=innodb default charset=utf8;

set foreign_key_checks = 1;
