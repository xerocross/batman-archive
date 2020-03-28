CREATE TABLE APPLICATION_USER (
	ID INT AUTO_INCREMENT PRIMARY KEY,
	PASSWORD VARCHAR(250) NOT NULL,
	USERNAME VARCHAR(250) NOT NULL
);


CREATE TABLE ARCHIVE_MASTER_ELEMENT (
	ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	INITIAL_HASH VARCHAR(255),
	LATEST_HASH VARCHAR(255),
	USER_ID INTEGER NOT NULL,
	CONSTRAINT FK_ARCH_MASTER_URS FOREIGN KEY (USER_ID) REFERENCES
	 APPLICATION_USER (ID)
);

CREATE TABLE ARCHIVE_ELEMENT (
	ID INT AUTO_INCREMENT PRIMARY KEY,
	HASH VARCHAR(255),
	MASTER_ID INT NOT NULL,
	POST_DATE TIMESTAMP NOT NULL,
	PREVIOUS_HASH VARCHAR(255),
	TEXT_DATA TEXT,
	USER_ID INT NOT NULL,
	CONSTRAINT FK_ARCHIVE_ELT_USR
		FOREIGN KEY (USER_ID) REFERENCES APPLICATION_USER(ID),
	CONSTRAINT FK_ARCHIVE_ELT_MASTER_ELT
		FOREIGN KEY (MASTER_ID) REFERENCES ARCHIVE_MASTER_ELEMENT(ID)
);


CREATE TABLE TAG (
	ID INT AUTO_INCREMENT PRIMARY KEY,
	TEXT VARCHAR(500),
	USER_ID INT,
	CONSTRAINT FK_TAG_URS FOREIGN KEY (USER_ID) 
		REFERENCES APPLICATION_USER (ID)
);

CREATE TABLE ARCHIVE_ELEMENT_TAG (
	ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	ARCHIVE_ELEMENT_ID INTEGER NOT NULL,
	TAG_ID INTEGER NOT NULL,
	CONSTRAINT FK_ARCH_ELT_TAG_TAG_REF 
		FOREIGN KEY (TAG_ID) REFERENCES TAG (ID),
	CONSTRAINT FK_ARCH_ELT_TAG_ELT_REF 
		FOREIGN KEY (ARCHIVE_ELEMENT_ID) 
		REFERENCES ARCHIVE_ELEMENT (ID)
);

CREATE TABLE SPRING_SESSION  (
	PRIMARY_ID VARCHAR(200) PRIMARY KEY,
	SESSION_ID VARCHAR(200),
	CREATION_TIME INT,
	LAST_ACCESS_TIME INT,
	MAX_INACTIVE_INTERVAL INT,
	EXPIRY_TIME INT,
	PRINCIPAL_NAME VARCHAR(200)
);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(50) PRIMARY KEY,
	ATTRIBUTE_NAME VARCHAR(200),
	ATTRIBUTE_BYTES VARBINARY(2147483647)	
);