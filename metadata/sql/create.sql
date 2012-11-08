#This is search CREATE SQL.
#@author Conan Zhang
#@date 2012-11-07

use search;

CREATE TABLE t_account(
    id INT PRIMARY KEY AUTO_INCREMENT,
    uid BIGINT NOT NULL UNIQUE,
    create_date TIMESTAMP NULL  DEFAULT now(),
    expireIn VARCHAR(16) NULL ,
    refresh VARCHAR(32) NULL ,
    state VARCHAR(32) NULL ,
    screen_name VARCHAR(32) NOT NULL UNIQUE,
    token VARCHAR(32) NOT NULL 
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_user_relate(
    id INT PRIMARY KEY AUTO_INCREMENT,
    uid BIGINT NOT NULL ,
    fansid BIGINT NOT NULL ,
    create_date TIMESTAMP NULL  DEFAULT now()
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX t_user_relate_IDX_0 on t_user_relate(uid,fansid);

CREATE TABLE t_user(
    id INT PRIMARY KEY AUTO_INCREMENT,
    uid BIGINT NOT NULL UNIQUE,
    screen_name VARCHAR(32) NOT NULL UNIQUE,
    name VARCHAR(32) NOT NULL UNIQUE,
    province INT NULL ,
    city INT NULL ,
    location VARCHAR(32) NULL ,
    description VARCHAR(128) NULL ,
    url VARCHAR(128) NULL ,
    profile_image_url VARCHAR(128) NULL ,
    domain VARCHAR(32) NULL ,
    gender VARCHAR(1) NULL  DEFAULT 'm',
    followers_count INT NOT NULL  DEFAULT 0,
    friends_count INT NOT NULL  DEFAULT 0,
    statuses_count INT NOT NULL  DEFAULT 0,
    favourites_count INT NOT NULL  DEFAULT 0,
    created_at DATETIME NOT NULL ,
    allow_all_act_msg VARCHAR(1) NULL  DEFAULT 'f',
    remark VARCHAR(32) NULL ,
    geo_enabled VARCHAR(1) NULL  DEFAULT 't',
    verified VARCHAR(1) NULL  DEFAULT 'f',
    allow_all_comment VARCHAR(1) NULL  DEFAULT 't',
    avatar_large VARCHAR(128) NULL ,
    verified_reason VARCHAR(32) NULL ,
    online_status INT NULL ,
    lang VARCHAR(8) NULL ,
    weihao VARCHAR(32) NULL ,
    verifiedType INT NULL ,
    create_date TIMESTAMP NULL  DEFAULT now()
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_provinces(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL ,
    pid INT NOT NULL ,
    cid INT NULL  DEFAULT 1000,
    longitude VARCHAR(16) NULL ,
    latitude VARCHAR(16) NULL 
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX t_provinces_IDX_0 on t_provinces(pid,cid);

CREATE TABLE t_user_sign(
    id INT PRIMARY KEY AUTO_INCREMENT,
    uid BIGINT NOT NULL ,
    area VARCHAR(32) NOT NULL ,
    reason VARCHAR(16) NOT NULL ,
    type VARCHAR(16) NOT NULL ,
    verified VARCHAR(1) NOT NULL  DEFAULT 'f',
    create_date TIMESTAMP NULL  DEFAULT now()
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX t_user_sign_IDX_0 on t_user_sign(uid,area);

CREATE TABLE t_load_frequence(
    id INT PRIMARY KEY AUTO_INCREMENT,
    uid BIGINT NOT NULL ,
    type VARCHAR(16) NOT NULL ,
    create_date TIMESTAMP NULL  DEFAULT now()
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX t_load_frequence_IDX_0 on t_load_frequence(uid,type);

