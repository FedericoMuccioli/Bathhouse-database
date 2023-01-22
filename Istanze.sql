/*Aggiunta tipi clienti*/
INSERT INTO tipiClienti
VALUES (1, 'Residente', 'Cliente del luogo, che abita a poca distanza dal bagnino.')
;
INSERT INTO tipiClienti
VALUES (2, 'Turista', 'Cliente in vacanza che abita distante.')
;

/*Aggiunta tipi sedute*/
INSERT INTO tipiSedute
VALUES (1, 'Lettino', 'Lettino con schienale e parasole, possibilità di posizionare la seduta con tre inclinazioni.')
;
INSERT INTO tipiSedute
VALUES (2, 'Sedia', 'Comoda sedia.')
;
INSERT INTO tipiSedute
VALUES (3, 'Sdraio', 'Comoda sdraio con 3 inclinazioni.')
;

/*Aggiunta numeri ombrelloni*/
INSERT INTO ombrelloni
VALUES (1)
;
INSERT INTO ombrelloni
VALUES (2)
;

/*Aggiunta anno*/
INSERT INTO spiagge
VALUES (2022)
;

/*Registrazione bagnini*/
INSERT INTO bagnini
VALUES ('VBLVNC00H47H294X', 'Veronica', 'Vibali', 1, '2000-06-07', 'Viale Dardelli 18', '393940526394')
;

/*Registrazione clienti*/
INSERT INTO clienti
VALUES ('RSSFPP95E05H294J', 'Filippo', 'Rossini', 'FiloRos', '396553953587', 1)
;
INSERT INTO clienti
VALUES ('RSSVNT80A01F839K', 'Valentino', 'Rossi', 'Vale', '396553953587', 1)
;
INSERT INTO clienti (codiceFiscale, nome, cognome, nominativo, codiceTipoCliente)
VALUES ('VRDLCU80A41H501K', 'Lucia', 'Verdi', 'Lucy', 1)
;

/*Aggiunta postazioni ombrelloni*/
INSERT INTO PostazioniOmbrelloni
SELECT 2022, 1, '2022-06-01', '2022-09-01', 1, 1
WHERE 100 >= (SELECT COUNT(*)
			  FROM PostazioniOmbrelloni
			  WHERE anno = 2022)
;
INSERT INTO PostazioniOmbrelloni
SELECT 2022, 2, '2022-07-01', '2022-09-01', 1, 2
WHERE 100 >= (SELECT COUNT(*)
			  FROM PostazioniOmbrelloni
			  WHERE anno = 2022)
;

/*Aggiunta prenotazioni*/
INSERT INTO OmbrelloniConPrenotazione
VALUES (1, 2022, '2022-06-01', '2022-06-30', 120, 'RSSFPP95E05H294J', 1)
;
INSERT INTO Composizioni
VALUES (1, 2022, '2022-06-01', 1, 2)
;
INSERT INTO OmbrelloniConPrenotazione
VALUES (2, 2022, '2022-07-01', '2022-08-01', 120, 'RSSFPP95E05H294J', 1)
;
INSERT INTO Composizioni
VALUES (2, 2022, '2022-07-01', 1, 2)
;
INSERT INTO Composizioni
VALUES (2, 2022, '2022-07-01', 2, 2)
;
