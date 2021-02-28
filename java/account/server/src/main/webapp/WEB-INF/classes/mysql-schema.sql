DROP TABLE IF EXISTS link;
CREATE TABLE link (
  id int(10) NOT NULL auto_increment,
  name varchar(16) NOT NULL,
  url varchar(128) NOT NULL,
  tags varchar(128) NOT NULL,
  createTime timestamp NOT NULL default CURRENT_TIMESTAMP,
  updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (id),
  UNIQUE KEY name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS category;
CREATE TABLE category (
  id int(10) NOT NULL auto_increment,
  name varchar(16) NOT NULL,
  createTime timestamp NOT NULL default CURRENT_TIMESTAMP,
  updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (id),
  UNIQUE KEY name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS linkcategory;
CREATE TABLE linkcategory (
  linkid int(10) NOT NULL ,
  categoryid int(10) NOT NULL,
  createTime timestamp NOT NULL default CURRENT_TIMESTAMP,
  updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (linkid, categoryid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS account;
CREATE TABLE IF NOT EXISTS `account` (
  `accountid` int(10) NOT NULL,
  `username` varchar(32) COLLATE latin1_general_ci NOT NULL,
  `password` varchar(32) COLLATE latin1_general_ci NOT NULL,
  `email` varchar(32) COLLATE latin1_general_ci NOT NULL,
  `siteurl` varchar(128) COLLATE latin1_general_ci NOT NULL,
  `isEncrypted` tinyint(1) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastmodifiedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;