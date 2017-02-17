use bustimes;

CREATE TABLE Popular (
	Popular_ID int AUTO_INCREMENT,
	Route_ID int NOT NULL,
	Route_Count int NOT NULL,
	PRIMARY KEY(Popular_ID),
	FOREIGN KEY(Route_ID) References Route(Route_ID)
);

INSERT INTO `Popular` (`Route_ID`, `Route_Count`) VALUES (1,0);
INSERT INTO `Popular` (`Route_ID`, `Route_Count`) VALUES (2,0);
INSERT INTO `Popular` (`Route_ID`, `Route_Count`) VALUES (3,0);
INSERT INTO `Popular` (`Route_ID`, `Route_Count`) VALUES (4,0);
