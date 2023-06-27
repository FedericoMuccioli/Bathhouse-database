-- Database Section
-- ________________ 

create database StabilimentoBalneare2;
use StabilimentoBalneare2;


-- Tables Section
-- _____________ 


create table ComposizioniOrdini (
     idOrdine int not null,
     idProdotto int not null,
     quantita int not null,
     constraint ID_composizione_ordine_ID primary key (idOrdine, idProdotto));

create table Ordini (
     id int not null auto_increment,
     dataOrdine date not null,
     oraConsegna time not null,
     prezzo decimal(6,2) not null,
     stato int not null,
     idBarista int not null,
     numeroOmbrellone int not null,
     anno int not null,
     dataInizio date not null,
     constraint ID_ORDINE_ID primary key (id),
     CHECK (YEAR(dataOrdine) = anno));

create table Disponibilita (
     idProdotto int not null,
     idFasciaOraria int not null,
     constraint ID_DISPONIBILITA_ID primary key (idProdotto, idFasciaOraria));

create table Prodotti (
     id int not null auto_increment,
     nome varchar(20) not null,
     descrizione varchar(200) not null,
     idTipo int not null,
     prezzo decimal(6,2) not null,
     constraint ID_PRODOTTO_ID primary key (id),
     constraint SID_PRODOTTO_ID unique (nome));

create table TipiProdotto (
     id int not null auto_increment,
     tipo varchar(20) not null,
     descrizione varchar(200) not null,
     constraint ID_TIPO_PRODOTTO_ID primary key (id),
     constraint SID_TIPO_PRODOTTO_ID unique (tipo));

create table FasceOrarie (
     id int not null auto_increment,
     inizio time not null,
     fine time not null,
     constraint ID_FASCIA_ORARIA_ID primary key (id),
     constraint SID_FASCIA_ORARIA_ID unique (inizio, fine),
     CHECK (inizio < fine));

create table Baristi (
     codiceFiscale char(16) not null,
     nome varchar(20) not null,
     cognome varchar(20) not null,
     codiceUnivoco int not null auto_increment,
     dataDiNascita date not null,
     indirizzo varchar(50) not null,
     telefono varchar(12) not null,
     constraint ID_BARISTA_ID primary key (codiceUnivoco),
     constraint SID_BARISTA_ID unique (codiceFiscale));

create table Bagnini (
     codiceFiscale char(16) not null,
     nome varchar(20) not null,
     cognome varchar(20) not null,
     codiceUnivoco int not null auto_increment,
     dataDiNascita date not null,
     indirizzo varchar(50) not null,
     telefono varchar(12) not null,
     constraint ID_BAGNINO_ID primary key (codiceUnivoco),
     constraint SID_BAGNINO_ID unique (codiceFiscale));

create table Clienti (
     codiceFiscale char(16) not null,
     nome varchar(20) not null,
     cognome varchar(20) not null,
     telefono varchar(12),
     codiceTipoCliente int not null,
     constraint ID_CLIENTE_ID primary key (codiceFiscale));

create table Composizioni (
     numeroOmbrellone int not null,
     anno int not null,
     dataInizio date not null,
     codiceTipoSeduta int not null,
     quantita int not null,
     constraint ID_composizione_ID primary key (numeroOmbrellone, anno, dataInizio, codiceTipoSeduta));

create table OmbrelloniConPrenotazione (
     numeroOmbrellone int not null,
     anno int not null,
     dataInizio date not null,
     dataFine date not null,
     prezzo decimal(6,2) not null,
     codiceFiscaleCliente char(16) not null,
     codiceUnivocoBagnino int not null,
     constraint SID_OMBRELLONE_CON_PRENOTAZIONE_ID unique (numeroOmbrellone, anno, dataFine),
     constraint ID_OMBRELLONE_CON_PRENOTAZIONE_ID primary key (numeroOmbrellone, anno, dataInizio),
	CHECK (dataInizio <= dataFine),
     CHECK (YEAR(dataInizio) = anno),
     CHECK (YEAR(dataFine) = anno));

create table PostazioniOmbrelloni (
     anno int not null,
     numeroOmbrellone int not null,
     dataInizio date not null,
     dataFine date,
     fila int not null,
     colonna int not null,
     constraint ID_POSTAZIONE_OMBRELLONE_ID primary key (numeroOmbrellone, anno),
     unique (fila, colonna, anno),
     CHECK (fila BETWEEN 1 AND 10),
     CHECK (colonna BETWEEN 1 AND 10),
     CHECK (dataInizio <= dataFine),
     CHECK (YEAR(dataInizio) = anno),
     CHECK (YEAR(dataFine) = anno));

create table PostazioniSeduteRiva (
     anno int not null,
     numeroSeduta int not null,
     constraint ID_POSTAZIONE_SEDUTA_RIVA_ID primary key (anno, numeroSeduta),
	CHECK (numeroSeduta BETWEEN 1 AND 20));

create table SeduteConPrenotazioni (
     anno int not null,
     numeroSeduta int not null,
     dataInizio date not null,
     dataFine date not null,
     prezzo decimal(6,2) not null,
     codiceTipoSeduta int not null,
     codiceFiscaleCliente char(16) not null,
     codiceUnivocoBagnino int not null,
     constraint SID_SEDUTA_CON_PRENOTAZIONE_ID unique (anno, numeroSeduta, dataFine),
     constraint ID_SEDUTA_CON_PRENOTAZIONE_ID primary key (anno, numeroSeduta, dataInizio),
	CHECK (dataInizio <= dataFine),
     CHECK (YEAR(dataInizio) = anno),
     CHECK (YEAR(dataFine) = anno));

create table Spiagge (
     anno int not null,
     constraint ID_SPIAGGIA_ID primary key (anno));

create table TipiClienti (
     codiceTipoCliente int not null auto_increment,
     nome varchar(20) not null,
     descrizione varchar(200) not null,
     constraint ID_TIPO_CLIENTE_ID primary key (codiceTipoCliente));

create table TipiSedute (
     codiceTipoSeduta int not null auto_increment,
     nome varchar(20) not null,
     descrizione varchar(200) not null,
     constraint ID_TIPO_SEDUTA_ID primary key (codiceTipoSeduta));


-- Constraints Section
-- ___________________ 
alter table ComposizioniOrdini add constraint FKcom_prodotto_FK
     foreign key (idProdotto)
     references Prodotti (id);

alter table ComposizioniOrdini add constraint FKcom_post
     foreign key (idOrdine)
     references Ordini (id);

alter table Disponibilita add constraint FKprodotto_FK
     foreign key (idProdotto)
     references Prodotti (id);

alter table Disponibilita add constraint FKfascia_oraria_FK
     foreign key (idFasciaOraria)
     references FasceOrarie (id);

alter table Prodotti add constraint FKtipo_prodotto_FK
     foreign key (idTipo)
     references TipiProdotto (id);

alter table Clienti add constraint FKtipologia_cliente_FK
     foreign key (codiceTipoCliente)
     references TipiClienti (codiceTipoCliente);

alter table Composizioni add constraint FKcom_TIP_FK
     foreign key (codiceTipoSeduta)
     references TipiSedute (codiceTipoSeduta);

alter table Composizioni add constraint FKcom_OMB
     foreign key (numeroOmbrellone, anno, dataInizio)
     references OmbrelloniConPrenotazione (numeroOmbrellone, anno, dataInizio);

alter table OmbrelloniConPrenotazione add constraint FKrichiesta_ombrellone_FK
     foreign key (codiceFiscaleCliente)
     references Clienti (codiceFiscale);

alter table OmbrelloniConPrenotazione add constraint FKassegnazione_ombrellone_FK
     foreign key (codiceUnivocoBagnino)
     references Bagnini (codiceUnivoco);

alter table OmbrelloniConPrenotazione add constraint FKprenotazione_ombrellone
     foreign key (numeroOmbrellone, anno)
     references PostazioniOmbrelloni (numeroOmbrellone, anno);

alter table PostazioniOmbrelloni add constraint FKcontiene_ombrellone_FK
     foreign key (anno)
     references Spiagge (anno);

alter table PostazioniSeduteRiva add constraint FKcontiene_seduta_riva
     foreign key (anno)
     references Spiagge (anno);

alter table SeduteConPrenotazioni add constraint FKtipologia_FK
     foreign key (codiceTipoSeduta)
     references TipiSedute (codiceTipoSeduta);

alter table SeduteConPrenotazioni add constraint FKrichiesta_seduta_riva_FK
     foreign key (codiceFiscaleCliente)
     references Clienti (codiceFiscale);

alter table SeduteConPrenotazioni add constraint FKassegnazione_seduta_riva_FK
     foreign key (codiceUnivocoBagnino)
     references Bagnini (codiceUnivoco);

alter table SeduteConPrenotazioni add constraint FKprenotazione_seduta
     foreign key (anno, numeroSeduta)
     references PostazioniSeduteRiva (anno, numeroSeduta);
