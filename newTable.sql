CREATE TABLE POPULAR (
	Popular_ID int AUTO_INCREMENT,
	Route_ID int NOT NULL,
	Route_Count int NOT NULL,
	PRIMARY KEY(Popular_ID),
	FOREIGN KEY(Route_ID) References Route(Route_ID)
);