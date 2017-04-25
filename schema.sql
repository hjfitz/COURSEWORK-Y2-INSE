CREATE DATABASE IF NOT EXISTS bustimes;
use bustimes;

CREATE TABLE Route(
  Route_ID int AUTO_INCREMENT,
  Route_Name varchar(60) NOT NULL,
  PRIMARY KEY(Route_ID)
);

INSERT INTO `Route` (`Route_ID`,`Route_Name`) VALUES (001,"University Bus 1");
INSERT INTO `Route` (`Route_ID`,`Route_Name`) VALUES (002,"University Bus 2");
INSERT INTO `Route` (`Route_ID`,`Route_Name`) VALUES (003,"Southdowns College Bus 1");

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

CREATE TABLE Stop(
  Stop_ID int AUTO_INCREMENT,
  Stop_Name varchar(60) NOT NULL,
  PRIMARY KEY(Stop_ID)
);

INSERT INTO `Stop` (`Stop_ID`,`Stop_Name`) VALUES (100,"Langstone Campus");
INSERT INTO `Stop` (`Stop_ID`,`Stop_Name`) VALUES (101,"Locks Way Road");
INSERT INTO `Stop` (`Stop_ID`,`Stop_Name`) VALUES (102,"LIDl");
INSERT INTO `Stop` (`Stop_ID`,`Stop_Name`) VALUES (103,"Fratton Station");
INSERT INTO `Stop` (`Stop_ID`,`Stop_Name`) VALUES (104,"CambrIDge Road");
INSERT INTO `Stop` (`Stop_ID`,`Stop_Name`) VALUES (105,"Winston Churchill Ave");

CREATE TABLE Arrival_Times(
  Arrival_ID int AUTO_INCREMENT,
  Arrival_Time datetime NOT NULL,
  Stop_ID int NOT NULL,
  PRIMARY KEY(Arrival_ID),
  FOREIGN KEY(Stop_ID) References Stop(Stop_ID)
);


INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 07:15:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 07:30:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 07:45:00', 103);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 08:00:00', 104);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 08:15:00', 105);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 08:30:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 08:45:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 09:00:00', 103);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 09:15:00', 104);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 09:30:00', 105);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 09:45:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 10:00:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 10:15:00', 103);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 10:30:00', 104);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 10:45:00', 105);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 11:00:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 11:15:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 11:30:00', 103);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 11:45:00', 104);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 12:00:00', 105);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 12:15:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 12:30:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 12:45:00', 103);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 13:00:00', 104);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 14:15:00', 105);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 14:30:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 14:45:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 15:00:00', 103);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 15:15:00', 104);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 15:30:00', 105);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 15:45:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 16:00:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 16:15:00', 103);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 16:30:00', 104);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 16:45:00', 105);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 17:00:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 17:15:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 17:30:00', 103);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 17:45:00', 104);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 18:00:00', 105);

CREATE TABLE Arrival_Stop(
  ArrivalRoute_ID int AUTO_INCREMENT,
  Route_ID int NOT NULL,
  Arrival_ID int NOT NULL,
  PRIMARY KEY(ArrivalRoute_ID),
  FOREIGN KEY(Route_ID) References Route(Route_ID),
  FOREIGN KEY(Arrival_ID) References Arrival_Times(Arrival_ID)
);

INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 6);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 5);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 4);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 3);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 1);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 5);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 4);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 2);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 1);

INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (301, '2017-02-17 07:30:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (302, '2017-02-17 08:30:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (303, '2017-02-17 09:30:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (304, '2017-02-17 10:30:00', 103);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (305, '2017-02-17 11:30:00', 103);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (306, '2017-02-17 12:30:00', 104);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (307, '2017-02-17 13:30:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (308, '2017-02-18 14:30:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (309, '2017-02-18 15:30:00', 104);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (310, '2017-02-18 16:30:00', 104);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (311, '2017-02-18 17:30:00', 104);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (312, '2017-02-18 18:30:00', 104);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (313, '2017-02-18 07:30:00', 101);

INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (314, '2017-02-20 07:00:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (315, '2017-02-20 08:00:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (316, '2017-02-20 09:00:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (317, '2017-02-20 10:00:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (318, '2017-02-20 21:00:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (319, '2017-02-20 22:00:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (320, '2017-02-20 23:00:00', 104);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (321, '2017-02-20 00:00:00', 104);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (322, '2017-02-20 05:00:00', 104);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (323, '2017-02-20 06:00:00', 104);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (324, '2017-02-20 07:00:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (325, '2017-02-20 08:00:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (326, '2017-02-20 07:00:00', 101);

INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (327, '2017-02-20 07:00:00', 103);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (328, '2017-02-20 08:00:00', 103);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (329, '2017-02-20 09:00:00', 103);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (330, '2017-02-20 10:00:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (331, '2017-02-20 11:00:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (332, '2017-02-20 12:00:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (333, '2017-02-20 13:00:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (334, '2017-02-20 14:00:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (335, '2017-02-20 15:00:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (336, '2017-02-20 16:00:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (337, '2017-02-20 17:00:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (338, '2017-02-20 18:00:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (339, '2017-02-20 07:00:00', 101);

INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (340, '2017-02-27 07:30:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (341, '2017-02-27 08:30:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (342, '2017-02-27 09:30:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (343, '2017-02-27 10:30:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (344, '2017-02-27 11:30:00', 101);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (345, '2017-02-27 12:30:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (346, '2017-02-27 13:30:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (347, '2017-02-27 14:30:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (348, '2017-02-27 15:30:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (349, '2017-02-27 16:30:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (350, '2017-02-27 17:30:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (351, '2017-02-27 18:30:00', 102);
INSERT INTO Arrival_Times (Arrival_ID, Arrival_Time, Stop_ID) VALUES (352, '2017-02-27 07:30:00', 101);

INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 340);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 341);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 342);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 343);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 344);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 345);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 346);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 347);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 348);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 349);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 301);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 302);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 302);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 303);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 304);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 305);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 306);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 307);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 308);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 309);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 310);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 311);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 312);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 313);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 314);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 315);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 316);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 317);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 318);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 319);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 320);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 321);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 322);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 323);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 324);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 325);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 326);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 327);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 328);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 329);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 330);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 331);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 332);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 333);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 334);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 335);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 336);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 337);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 338);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (3, 339);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 350);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (1, 351);
INSERT INTO Arrival_Stop (Route_ID, Arrival_ID) VALUES (2, 352);
