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
                              `comment_counts` int(11) default null,
                              `create_date` datetime default null,
                              `summary` varchar(100) default null,
                              `title` varchar(64) default null,
                              `view_counts` int(11) default null,
                              `weight` int(11) not null,
                              `author_id` bigint(20) default null,
                              `body_id` bigint(20) default null,
                              `category_id` int(11) default null,
                              primary key (`id`),
                              key `FKndx2m69302cso79y66yxiju4h` (`author_id`),
                              key `FKrd11pjsmueckfrh9gs7bc6374` (`body_id`),
                              key `FKjrn3ua4xmiulp8raj7m9d2xk6` (`category_id`),
                              constraint `FKjrn3ua4xmiulp8raj7m9d2xk6` foreign key (`category_id`) references `me_category` (`id`),
                              constraint `FKndx2m69302cso79y66yxiju4h` foreign key (`author_id`) references `sys_user` (`id`),
                              constraint `FKrd11pjsmueckfrh9gs7bc6374` foreign key (`body_id`) references `me_article_body` (`id`)
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
                                  `tag_id` int(11) not null,
                                  key `FK2s65pu9coxh7w16s8jycih79w` (`tag_id`),
                                  key `FKsmysra6pt3ehcvts18q2h4409` (`article_id`),
                                  constraint `FK2s65pu9coxh7w16s8jycih79w` foreign key (`tag_id`) references `me_tag` (`id`),
                                  constraint `FKsmysra6pt3ehcvts18q2h4409` foreign key (`article_id`) references `me_article` (`id`)
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
                              primary key (`id`),
                              key `FKecq0fuo9k0lnmea6r01vfhiok` (`article_id`),
                              key `FKkvuyh6ih7dt1rfqhwsjomsa6i` (`author_id`),
                              key `FKaecafrcorkhyyp1luffinsfqs` (`parent_id`),
                              key `FK73dgr23lbs3ebex5qvqyku308` (`to_uid`),
                              constraint `FK73dgr23lbs3ebex5qvqyku308` foreign key (`to_uid`) references `sys_user` (`id`),
                              constraint `FKaecafrcorkhyyp1luffinsfqs` foreign key (`parent_id`) references `me_comment` (`id`),
                              constraint `FKecq0fuo9k0lnmea6r01vfhiok` foreign key (`article_id`) references `me_article` (`id`),
                              constraint `FKkvuyh6ih7dt1rfqhwsjomsa6i` foreign key (`author_id`) references `sys_user` (`id`)
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
                            primary key (`id`),
                            unique key `UK_awpog86ljqwb89aqa1c5gvdrd` (`account`),
                            unique key `UK_ahtq5ew3v0kt1n7hf1sgp7p8l` (`email`)
) engine=innodb auto_increment=16 default charset=utf8;

set foreign_key_checks = 1;
