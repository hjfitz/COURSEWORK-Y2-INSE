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

INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 07:30:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 08:30:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 09:30:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 10:30:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 11:30:00', 101);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 12:30:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 13:30:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 14:30:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 15:30:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 16:30:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 17:30:00', 102);
INSERT INTO Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-15 18:30:00', 102);
insert into Arrival_Times (Arrival_Time, Stop_ID) VALUES ('2017-02-16 07:30:00', 101);



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
