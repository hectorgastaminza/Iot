CREATE DATABASE comiot;

USE comiot;

CREATE TABLE IF NOT EXISTS user (
  pk_user_id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(64) NOT NULL,
  password VARCHAR(64) NOT NULL,
  email VARCHAR(128) NOT NULL,
  PRIMARY KEY (pk_user_id)
) ENGINE=InnoDB;
/* */
CREATE TABLE IF NOT EXISTS connection (
  pk_connection_id BIGINT NOT NULL AUTO_INCREMENT,
  pk_user_id BIGINT NOT NULL,
  host VARCHAR(128) NOT NULL,
  port SMALLINT NOT NULL,
  username VARCHAR(64) NOT NULL,
  password VARCHAR(64) NOT NULL,
  root_topic VARCHAR(128) NOT NULL,
  PRIMARY KEY (pk_connection_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS place (
  pk_place_id BIGINT NOT NULL AUTO_INCREMENT,
  pk_user_id BIGINT NOT NULL,
  place_id MEDIUMINT NOT NULL,
  place_name VARCHAR(64) NOT NULL,
  place_description VARCHAR(128),
  PRIMARY KEY (pk_place_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS device (
  pk_device_id BIGINT NOT NULL AUTO_INCREMENT,
  pk_user_id BIGINT NOT NULL,
  place_id MEDIUMINT NOT NULL,
  device_id MEDIUMINT NOT NULL,
  device_name VARCHAR(64) NOT NULL,
  device_description VARCHAR(128),
  PRIMARY KEY (pk_device_id)
) ENGINE=InnoDB;

INSERT INTO user (username, password, email)
VALUES ('comiot', '123456', 'comiotproject@gmail.com');

DELETE FROM user where email = 'hectorgastaminza@gmail.com';
SELECT * FROM user;
SELECT @user_pk := pk_user_id FROM user WHERE username = 'comiot';

INSERT INTO connection (pk_user_id, host, port, username, password, root_topic)
VALUES (@user_pk, 'mqtt.dioty.co', '1883', 'comiotproject@gmail.com', 'fbe4629f', '/comiotproject@gmail.com/');

SELECT * FROM connection WHERE pk_user_id = @user_pk;

INSERT INTO place (pk_user_id, place_id, place_name, place_description)
VALUES (@user_pk, '8', 'House', 'This is a description.');
INSERT INTO place (pk_user_id, place_id, place_name, place_description)
VALUES (@user_pk, '20', 'Backyard', 'This is a description.');

SELECT * FROM place WHERE pk_user_id = @user_pk;

INSERT INTO device (pk_user_id, place_id, device_id, device_name, device_description)
VALUES (@user_pk, '8', '5', 'Generic', 'Generic device');
INSERT INTO device (pk_user_id, place_id, device_id, device_name, device_description)
VALUES (@user_pk, '20', '9', 'Raspberry', 'Raspberry device');

SELECT * FROM place WHERE pk_user_id = @user_pk;
SET @place_id = 8;
SELECT * FROM place WHERE pk_user_id = @user_pk AND place_id = @place_id;
SET @device_id = 5;
SELECT * FROM device WHERE pk_user_id = @user_pk AND place_id = @place_id AND device_id = @device_id;
