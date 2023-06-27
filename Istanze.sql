/*
-- Istanze
*/
INSERT INTO `TipiSedute` (`codiceTipoSeduta`,`nome`,`descrizione`) VALUES (1,'Lettino','Realizzato con materiali di alta qualità, il lettino offre un\'ampia superficie di riposo imbottita, che consente di distendersi comodamente e godere di una rilassante giornata sulla spiaggia.');
INSERT INTO `TipiSedute` (`codiceTipoSeduta`,`nome`,`descrizione`) VALUES (2,'Sedia','Le sdraio sono un\'opzione versatile e comoda per coloro che preferiscono un\'esperienza più informale sulla spiaggia.');
INSERT INTO `TipiSedute` (`codiceTipoSeduta`,`nome`,`descrizione`) VALUES (3,'Sdraio','Le sedie offrono un\'alternativa confortevole e pratica per i visitatori che desiderano sedersi in modo più eretto e mantenere una postura più formale.');

INSERT INTO `TipiProdotto` (`id`,`tipo`,`descrizione`) VALUES (1,'Bevanda','Rinfrescati con la nostra bevanda speciale, una deliziosa combinazione di ingredienti selezionati per soddisfare la tua sete.');
INSERT INTO `TipiProdotto` (`id`,`tipo`,`descrizione`) VALUES (2,'Snack','Delizia il tuo palato con il nostro snack irresistibile, una prelibatezza che ti regalerà un\'esplosione di sapori.');
INSERT INTO `TipiProdotto` (`id`,`tipo`,`descrizione`) VALUES (3,'Piatto',' Il nostro piatto delizioso è una vera festa per gli occhi e per il palato.');
INSERT INTO `TipiProdotto` (`id`,`tipo`,`descrizione`) VALUES (4,'Gelato','Goditi un momento di puro piacere con il nostro gelato artigianale.');

INSERT INTO `TipiClienti` (`codiceTipoCliente`,`nome`,`descrizione`) VALUES (1,'Turista','Si riferisce a una persona che visita una determinata destinazione per motivi di vacanza, esplorazione o svago.');
INSERT INTO `TipiClienti` (`codiceTipoCliente`,`nome`,`descrizione`) VALUES (2,'Residente','Si riferisce a una persona che vive stabilmente o ha una residenza nella destinazione in questione.');

INSERT INTO `FasceOrarie` (`id`,`inizio`,`fine`) VALUES (1,'07:00:00','10:00:00');
INSERT INTO `FasceOrarie` (`id`,`inizio`,`fine`) VALUES (2,'12:00:00','15:00:00');
INSERT INTO `FasceOrarie` (`id`,`inizio`,`fine`) VALUES (3,'19:00:00','21:00:00');