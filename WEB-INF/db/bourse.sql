alter table ORDERPURSACHE
   drop constraint FK_ORDERPUR_REFERENCE_SOCIETY;

alter table ORDERPURSACHE
   drop constraint FK_ORDERPUR_REFERENCE_CLIENT;

alter table ORDERPURSACHE
   drop constraint FK_ORDERPUR_REFERENCE_BROKER;

alter table ORDERSALE
   drop constraint FK_ORDERSAL_REFERENCE_CLIENT;

alter table ORDERSALE
   drop constraint FK_ORDERSAL_REFERENCE_BROKER;

alter table ORDERSALE
   drop constraint FK_ORDERSAL_REFERENCE_SOCIETY;

alter table TITRE
   drop constraint FK_TITRE_REFERENCE_CLIENT;

alter table TITRE
   drop constraint FK_TITRE_REFERENCE_SOCIETY;

alter table TITREONSALE
   drop constraint FK_TITREONS_REFERENCE_TITRE;

alter table TRANSACTION
   drop constraint FK_TRANSACT_REFERENCE_BROKER;

alter table TRANSACTION
   drop constraint FK_TRANSACT_REFERENCE_CLIENT;

alter table TRANSACTION
   drop constraint FK_TRANSACT_REFERENCE_CLIENT;

drop table BROKER cascade constraints;

drop table CLIENT cascade constraints;

drop table ORDERPURSACHE cascade constraints;

drop table ORDERSALE cascade constraints;

drop table SOCIETY cascade constraints;

drop table TITRE cascade constraints;

drop table TITREONSALE cascade constraints;

drop table TRANSACTION cascade constraints;

/*==============================================================*/
/* Table: BROKER                                                */
/*==============================================================*/
create table BROKER 
(
   IDBROKER             VARCHAR2(30)         not null,
   BROKERNAME           VARCHAR2(30),
   RATE                 NUMBER(10,2),
   MONEY                NUMBER(10,2),
   NOTE                 INT,
   constraint PK_BROKER primary key (IDBROKER)
);

/*==============================================================*/
/* Table: CLIENT                                                */
/*==============================================================*/
create table CLIENT 
(
   IDCLIENT             VARCHAR2(30)         not null,
   CLIENTNAME           VARCHAR2(30),
   constraint PK_CLIENT primary key (IDCLIENT)
);

/*==============================================================*/
/* Table: ORDERPURSACHE                                         */
/*==============================================================*/
create table ORDERPURSACHE 
(
   IDORDERPURSACHE      VARCHAR2(30)         not null,
   DATESTART            TIMESTAMP,
   QUANTITY             INT,
   PRICE                NUMBER(10,2),
   IDBROKER             VARCHAR2(30),
   IDSOCIETY            VARCHAR2(30),
   IDCLIENT             VARCHAR2(30),
   STATES               INT,
   constraint PK_ORDERPURSACHE primary key (IDORDERPURSACHE)
);

/*==============================================================*/
/* Table: ORDERSALE                                             */
/*==============================================================*/
create table ORDERSALE 
(
   IDORDERSALE          VARCHAR2(30)         not null,
   DATESTART            TIMESTAMP,
   QUANTITY             INT,
   QUANTITYDISPO        INT,
   IDBROKER             VARCHAR2(30),
   IDSOCIETY            VARCHAR2(30),
   IDCLIENT             VARCHAR2(30),
   PRICE                NUMBER(10,2),
   STATES               INT,
   constraint PK_ORDERSALE primary key (IDORDERSALE)
);

/*==============================================================*/
/* Table: SOCIETY                                               */
/*==============================================================*/
create table SOCIETY 
(
   IDSOCIETY            VARCHAR2(30)         not null,
   SOCIETYNAME          VARCHAR2(30),
   TITRENUMBER          INT,
   constraint PK_SOCIETY primary key (IDSOCIETY)
);

/*==============================================================*/
/* Table: TITRE                                                 */
/*==============================================================*/
create table TITRE 
(
   IDTITRE              VARCHAR2(30)         not null,
   REFERENCE            VARCHAR2(30),
   IDSOCIETY            VARCHAR2(30),
   IDCLIENT             VARCHAR2(30),
   constraint PK_TITRE primary key (IDTITRE)
);

/*==============================================================*/
/* Table: TITREONSALE                                           */
/*==============================================================*/
create table TITREONSALE 
(
   IDTITREONSALE        VARCHAR2(30)         not null,
   IDTITRE              VARCHAR2(30),
   SALESTATES           VARCHAR2(30),
   IDORDERSALE          VARCHAR2(30),
   constraint PK_TITREONSALE primary key (IDTITREONSALE)
);

/*==============================================================*/
/* Table: TRANSACTION                                           */
/*==============================================================*/
create table TRANSACTION 
(
   IDTRANSACTION        VARCHAR2(30)         not null,
   IDORDERPURSACHE      VARCHAR2(30),
   PRICE                NUMBER(10,2),
   QUANTITY             INT,
   IDBROKER             VARCHAR2(30),
   IDLASTPROPRIO        VARCHAR2(30),
   IDNEWPROPRIO         VARCHAR2(30),
   DATETRANSACTION      TIMESTAMP,
   STATES               INT,
   constraint PK_TRANSACTION primary key (IDTRANSACTION)
);

alter table ORDERPURSACHE
   add constraint FK_ORDERPUR_REFERENCE_SOCIETY foreign key (IDSOCIETY)
      references SOCIETY (IDSOCIETY);

alter table ORDERPURSACHE
   add constraint FK_ORDERPUR_REFERENCE_CLIENT foreign key (IDCLIENT)
      references CLIENT (IDCLIENT);

alter table ORDERPURSACHE
   add constraint FK_ORDERPUR_REFERENCE_BROKER foreign key (IDBROKER)
      references BROKER (IDBROKER);

alter table ORDERSALE
   add constraint FK_ORDERSAL_REFERENCE_CLIENT foreign key (IDCLIENT)
      references CLIENT (IDCLIENT);

alter table ORDERSALE
   add constraint FK_ORDERSAL_REFERENCE_BROKER foreign key (IDBROKER)
      references BROKER (IDBROKER);

alter table ORDERSALE
   add constraint FK_ORDERSAL_REFERENCE_SOCIETY foreign key (IDSOCIETY)
      references SOCIETY (IDSOCIETY);

alter table TITRE
   add constraint FK_TITRE_REFERENCE_CLIENT foreign key (IDCLIENT)
      references CLIENT (IDCLIENT);

alter table TITRE
   add constraint FK_TITRE_REFERENCE_SOCIETY foreign key (IDSOCIETY)
      references SOCIETY (IDSOCIETY);

alter table TITREONSALE
   add constraint FK_TITREONS_REFERENCE_TITRE foreign key (IDTITRE)
      references TITRE (IDTITRE);

alter table TRANSACTION
   add constraint FK_TRANSACT_REFERENCE_BROKER foreign key (IDBROKER)
      references BROKER (IDBROKER);

alter table TRANSACTION
   add constraint FK_TRANSACT_REFERENCE_CLIENT foreign key (IDLASTPROPRIO)
      references CLIENT (IDCLIENT);

alter table TRANSACTION
   add constraint FK_TRANSACT_REFERENCE_CLIENT foreign key (IDNEWPROPRIO)
      references CLIENT (IDCLIENT);

--------------------------------Session alter---------------------
alter session set nls_timestamp_format="YYYY-MM-DD H24:MI:SS.ff";
---------------------------------------------------------

----------Broker
----sequence
drop sequence idBroker;
create sequence idBroker;
----data
insert into Broker  values('BR'||idBroker.nextval,'Broker A',25,120,5);
insert into Broker  values('BR'||idBroker.nextval,'Broker B',36,620,7);
insert into Broker  values('BR'||idBroker.nextval,'Broker C',26,620,6);
insert into Broker  values('BR'||idBroker.nextval,'Broker D',10,620,2);
insert into Broker  values('BR'||idBroker.nextval,'Broker E',56,620,15);
insert into Broker  values('BR'||idBroker.nextval,'Broker F',10,620,3);
------------Client
-----sequence
drop sequence IDCLIENT;
create sequence IDCLIENT;
-----data
insert into client values('CL'||IDCLIENT.nextval,'Client num '||IDCLIENT.currval);
insert into client values('CL'||IDCLIENT.nextval,'Client num '||IDCLIENT.currval);
insert into client values('CL'||IDCLIENT.nextval,'Client num '||IDCLIENT.currval);
insert into client values('CL'||IDCLIENT.nextval,'Client num '||IDCLIENT.currval);
insert into client values('CL'||IDCLIENT.nextval,'Client num '||IDCLIENT.currval);
insert into client values('CL'||IDCLIENT.nextval,'Client num '||IDCLIENT.currval);
insert into client values('CL'||IDCLIENT.nextval,'Client num '||IDCLIENT.currval);
insert into client values('CL'||IDCLIENT.nextval,'Client num '||IDCLIENT.currval);
-------------Society
----sequence
drop sequence idSociety;
create sequence idSociety;
------data
insert into Society values('SOC'||idSociety.nextval,'BMOI',4);
insert into Society values('SOC'||idSociety.nextval,'BOA',5);
insert into Society values('SOC'||idSociety.nextval,'EDBM',5);
insert into Society values('SOC'||idSociety.nextval,'Star',6);
insert into Society values('SOC'||idSociety.nextval,'Tiko',8);

-------------Titre
---sequence
drop sequence idTitre;
create sequence idTitre;
----data
insert into titre values('TR'||idTitre.nextval,'BMOI 001','SOC1','CL1');
insert into titre values('TR'||idTitre.nextval,'BMOI 002','SOC1','CL1');
insert into titre values('TR'||idTitre.nextval,'BMOI 003','SOC1','CL2');
insert into titre values('TR'||idTitre.nextval,'BMOI 004','SOC1','CL2');
insert into titre values('TR'||idTitre.nextval,'BOA 001','SOC2','CL2');
insert into titre values('TR'||idTitre.nextval,'BOA 002','SOC2','CL3');
insert into titre values('TR'||idTitre.nextval,'BOA 003','SOC2','CL3');
insert into titre values('TR'||idTitre.nextval,'BOA 004','SOC2','CL3');
insert into titre values('TR'||idTitre.nextval,'BOA 005','SOC2','CL4');
insert into titre values('TR'||idTitre.nextval,'EDBM 001','SOC3','CL4');
insert into titre values('TR'||idTitre.nextval,'EDBM 002','SOC3','CL4');
insert into titre values('TR'||idTitre.nextval,'EDBM 003','SOC3','CL4');
insert into titre values('TR'||idTitre.nextval,'EDBM 004','SOC3','CL5');
insert into titre values('TR'||idTitre.nextval,'EDBM 005','SOC3','CL6');
insert into titre values('TR'||idTitre.nextval,'STAR 001','SOC4','CL1');
insert into titre values('TR'||idTitre.nextval,'STAR 002','SOC4','CL3');
insert into titre values('TR'||idTitre.nextval,'STAR 003','SOC4','CL6');
insert into titre values('TR'||idTitre.nextval,'STAR 004','SOC4','CL7');
insert into titre values('TR'||idTitre.nextval,'STAR 005','SOC4','CL7');
insert into titre values('TR'||idTitre.nextval,'STAR 006','SOC4','CL1');
insert into titre values('TR'||idTitre.nextval,'Tiko 001','SOC5','CL8');
insert into titre values('TR'||idTitre.nextval,'Tiko 002','SOC5','CL8');
insert into titre values('TR'||idTitre.nextval,'Tiko 003','SOC5','CL8');
insert into titre values('TR'||idTitre.nextval,'Tiko 004','SOC5','CL8');
insert into titre values('TR'||idTitre.nextval,'Tiko 005','SOC5','CL8');
insert into titre values('TR'||idTitre.nextval,'Tiko 006','SOC5','CL4');
insert into titre values('TR'||idTitre.nextval,'Tiko 007','SOC5','CL8');
insert into titre values('TR'||idTitre.nextval,'Tiko 008','SOC5','CL2');

---------sequence 
drop sequence IDORDERPURSACHE;
create sequence IDORDERPURSACHE;

drop sequence IDORDERSALE;
create sequence IDORDERSALE;

drop sequence IDTRANSACTION;
create sequence IDTRANSACTION;

drop sequence IDTITREONSALE;
create sequence IDTITREONSALE;