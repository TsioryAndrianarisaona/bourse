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
