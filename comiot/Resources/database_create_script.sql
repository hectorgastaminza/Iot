SHOW ENGINE INNODB STATUS;

CREATE DATABASE comiot;

USE comiot;

CREATE TABLE IF NOT EXISTS user (
  pk_user_id int NOT NULL AUTO_INCREMENT,
  username VARCHAR(64) NOT NULL,
  password VARCHAR(64) NOT NULL,
  email VARCHAR(128) NOT NULL,
  PRIMARY KEY (pk_user_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS connection (
    pk_user_id int NOT NULL,
    pk_connection_id int NOT NULL AUTO_INCREMENT,
    host VARCHAR(128) NOT NULL,
    port SMALLINT NOT NULL,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL,
    root_topic VARCHAR(128) NOT NULL,
    PRIMARY KEY (pk_connection_id),
    FOREIGN KEY (pk_user_id) REFERENCES user(pk_user_id)
)  ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS place (
  pk_place_id int NOT NULL AUTO_INCREMENT,
  pk_user_id int NOT NULL,
  place_id MEDIUMINT NOT NULL DEFAULT 1,
  place_name VARCHAR(64) NOT NULL,
  place_description VARCHAR(128),
  PRIMARY KEY (pk_place_id),
  FOREIGN KEY (pk_user_id) REFERENCES user(pk_user_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS device (
  pk_device_id int NOT NULL AUTO_INCREMENT,
  pk_user_id int NOT NULL,
  place_id MEDIUMINT NOT NULL DEFAULT 1,
  device_id MEDIUMINT NOT NULL,
  device_name VARCHAR(64) NOT NULL,
  device_description VARCHAR(128),
  device_state int DEFAULT 0,
  device_value int DEFAULT 0,
  PRIMARY KEY (pk_device_id),
  FOREIGN KEY (pk_user_id) REFERENCES user(pk_user_id)
) ENGINE=InnoDB;
