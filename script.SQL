/*************SCRIPT SQL pour le projet de base de données****************/ 
/* Pour créer la base de donner ouvrir sqlite 3 et tapez '.open database.db'
* puis tapez '.read script.sql'. La base de données est désormais créée 
* et le programme peut désormais se servir de la base de données pour fonctionner
*/

DROP TABLE UTILISATEUR;
DROP TABLE MARQUE;
DROP TABLE PRODUIT;
DROP TABLE PANIER;

CREATE TABLE UTILISATEUR (pseudo varchar(20),mdp varchar(20),PRIMARY KEY(pseudo));
CREATE TABLE MARQUE (nomMarque varchar(20),nationalite varchar(20),PRIMARY KEY(nomMarque));
CREATE TABLE PRODUIT (idProduit varchar(9),nomProduit varchar(20),nomMarque varchar(20),type varchar(20),couleur varchar(20),poids number(9),prix number(4,2),PRIMARY KEY(idProduit),foreign key(nomMarque) references MARQUE(nomMarque));
CREATE TABLE PANIER (ID INTEGER PRIMARY KEY,idProduit varchar(9),pseudo varchar(20),prix number(4,2),foreign key(pseudo) references UTILISATEUR(pseudo), foreign key(idProduit) references PRODUIT(idProduit));

INSERT INTO UTILISATEUR VALUES ('user1','user1');
INSERT INTO UTILISATEUR VALUES ('user2','user2');

INSERT INTO MARQUE VALUES ('Fender','America');
INSERT INTO MARQUE VALUES ('Gibson','America');
INSERT INTO MARQUE VALUES ('Yamaha','Japan');
INSERT INTO MARQUE VALUES ('Vigier','France');
INSERT INTO MARQUE VALUES ('Charvel','Mexico');        

INSERT INTO PRODUIT VALUES ('fend01','Postmodern journeyman','Fender','Stratocaster','Midnight Blue','3kg','3544');
INSERT INTO PRODUIT VALUES ('fend02','Strato vintage','Fender','Stratocaster','Sunburst','3.2kg','3400');
INSERT INTO PRODUIT VALUES ('fend03','Custom Shop','Fender','Telecaster','Midnight Blue','3.4kg','5200');
INSERT INTO PRODUIT VALUES ('fend04','American Special','Fender','Stratocaster','Sandblasted','2.9kg','3150');

INSERT INTO PRODUIT VALUES ('gibs01','Les Paul Studio','Gibson','LP','White Pearl','4.1kg','1200');
INSERT INTO PRODUIT VALUES ('gibs02','Les Paul Traditional','Gibson','LP','Sunburst','4.2kg','3300');
INSERT INTO PRODUIT VALUES ('gibs03','Les Paul Melody Maker','Gibson','LP','Midnight Purple','4.6kg','2900');
INSERT INTO PRODUIT VALUES ('gibs04','Les Paul Standard','Gibson','LP','Stripped Yellow','4kg','2695');

INSERT INTO PRODUIT VALUES ('yam01','Pacifica 112','Yamaha','Stratocaster','Black','4.1kg','400');
INSERT INTO PRODUIT VALUES ('yam02','Pacifica 612','Yamaha','Stratocaster','Red','4.2kg','1000');
INSERT INTO PRODUIT VALUES ('yam03','Pacifica 912','Yamaha','Stratocaster','Dark Blue','4.6kg','1500');
INSERT INTO PRODUIT VALUES ('yam04','Pacfica GPA','Yamaha','Stratocaster','','4kg','2695');

INSERT INTO PRODUIT VALUES ('vig01','Vigier Excalibur','Vigier','Stratocaster','Amber','2.9kg','3500');
INSERT INTO PRODUIT VALUES ('vig02','Vigier Exess Roger','Vigier','Stratocaster','Sunburst','2.2kg','3300');
INSERT INTO PRODUIT VALUES ('vig03','Vigier Expert Retro','Vigier','Stratocaster','Stonewashed Blue','3.6kg','2860');
INSERT INTO PRODUIT VALUES ('vig04','Vigier G.V Wood','Vigier','Stratocaster','White Alder ','2.4kg','3156');

INSERT INTO PRODUIT VALUES ('char01','San Dimas','Charvel','Superstratocaster','Red Alert','2.5kg','800');
INSERT INTO PRODUIT VALUES ('char02','So Cal HH1 ','Charvel','Superstratocaster','Sunburst Blue','2.2kg','950');
INSERT INTO PRODUIT VALUES ('char03','Super Stock','Charvel','Superstratocaster','Black Pearl','3.6kg','674');
INSERT INTO PRODUIT VALUES ('char04','Pro Mod','Charvel','Superstratocaster','Viper Green','2.4kg','723');
