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

/* */
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
  place_id MEDIUMINT NOT NULL,
  place_name VARCHAR(64) NOT NULL,
  place_description VARCHAR(128),
  PRIMARY KEY (pk_place_id),
  FOREIGN KEY (pk_user_id) REFERENCES user(pk_user_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS device (
  pk_device_id int NOT NULL AUTO_INCREMENT,
  pk_user_id int NOT NULL,
  place_id MEDIUMINT NOT NULL,
  device_id MEDIUMINT NOT NULL,
  device_name VARCHAR(64) NOT NULL,
  device_description VARCHAR(128),
  device_state int DEFAULT 0,
  device_value int DEFAULT 0,
  PRIMARY KEY (pk_device_id),
  FOREIGN KEY (pk_user_id) REFERENCES user(pk_user_id)
) ENGINE=InnoDB;

INSERT INTO user (username, password, email)
VALUES ('comiot', '123456', 'comiotproject@gmail.com');

SELECT @user_pk := pk_user_id FROM user WHERE username = 'comiot';

INSERT INTO connection (pk_user_id, host, port, username, password, root_topic)
VALUES (@user_pk, 'mqtt.dioty.co', '1883', 'comiotproject@gmail.com', 'fbe4629f', '/comiotproject@gmail.com/');

INSERT INTO place (pk_user_id, place_id, place_name, place_description)
VALUES (@user_pk, '1', 'House', 'This is a description.');
INSERT INTO place (pk_user_id, place_id, place_name, place_description)
VALUES (@user_pk, '2', 'Backyard', 'This is a description.');

INSERT INTO device (pk_user_id, place_id, device_id, device_name, device_description)
VALUES (@user_pk, '1', '5', 'Generic', 'Generic device');
INSERT INTO device (pk_user_id, place_id, device_id, device_name, device_description)
VALUES (@user_pk, '1', '9', 'Raspberry', 'Raspberry device');
UPDATE place SET place_id = '1' where pk_place_id = 1;





INSERT INTO user (username, password, email)
VALUES ('b', 'b', 'comiotproject@gmail.com');

INSERT INTO user (username, password, email)
VALUES ('comiot', 'a', 'a@gmail.com');

INSERT INTO user (username, password, email)
VALUES ('hola', 'b', 'c@gmail.com');

SELECT pk_user_id FROM user;
DELETE FROM user where pk_user_id = 4;
SELECT * FROM user;
SELECT @user_pk := pk_user_id FROM user WHERE username = 'comiot';
SELECT * FROM user where ((username = 'comiot' or email = 'comiotproject@gmail.com' ) and (pk_user_id != 1));

UPDATE user SET username = 'b', password = 'b', email = 'b@b.com' where pk_user_id = 6;


SELECT * FROM connection;
SELECT * FROM connection WHERE pk_user_id = @user_pk;
DELETE FROM connection where pk_user_id = 6;


SELECT * FROM place WHERE pk_user_id = @user_pk;
SELECT * FROM device WHERE pk_user_id = @user_pk;

SELECT * FROM device WHERE pk_user_id = @user_pk;
SET @place_id = 8;
SELECT * FROM place WHERE pk_user_id = @user_pk AND place_id = @place_id;
SET @device_id = 5;
SELECT * FROM device WHERE pk_user_id = @user_pk AND place_id = @place_id AND device_id = @device_id;
