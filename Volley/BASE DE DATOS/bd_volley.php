CREATE DATABASE bd_volley (
	id int(100) NOT NULL AUTO_INCREMENT,
	username varchar(50) NOT NULL,
	password varchar(50) NOT NULL,
	email varchar(50) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);