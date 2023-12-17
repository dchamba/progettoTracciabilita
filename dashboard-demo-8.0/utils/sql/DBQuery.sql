		
INSERT INTO `Permessi` (`idPermesso`) 
VALUES ('FASE_PROCESSO_PACKINGLISTOMR7e8'), 
('FASE_PROCESSO_PACKINGLISTOMR1e2'), 
('FASE_PROCESSO_PACKINGLISTOMR3e4'), 
('FASE_PROCESSO_PACKINGLISTOMR5e6');

-- Implementazione OMR	
INSERT INTO `Prodotti` (`idProdotto`, `codiceDatamatrix`, `codiceProdotto`, `descrizione`, `numeroDisegno`, `formatoDatamatrix`, `idAziendaRichiedente`, `attivo`, `eliminato`, `packingListPermesso`, `provatenutaaria`, `provatenutaelio`, `minValoreDurezza`) VALUES  
(59, 'OMR0001', 'OMR0001', 'LONGHERINA POST.LH 55929 M182', 'FA00ADY55929', '^00055929000917\d{5}[0-3]{1}\d{2}[2-3]{1}\d{1}', '1', '1', '0', 'FASE_PROCESSO_PACKINGLISTOMR1e2', '0', '0', '0'), 
(60, 'OMR0002', 'OMR0002', 'LONGHERINA POST.RH 55936 M182', 'FA00ADY55936', '^00055936000917\d{5}[0-3]{1}\d{2}[2-3]{1}\d{1}', '1', '1', '0', 'FASE_PROCESSO_PACKINGLISTOMR1e2', '0', '0', '0'), 
(61, 'OMR0003', 'OMR0003', 'LONGHERINA ANT. LH 55933 M182', 'FA00ADY55933', '^0005593300009174\d{5}[0-3]{1}\d{2}[2-3]{1}\d{1}', '1', '1', '0', 'FASE_PROCESSO_PACKINGLISTOMR3e4', '0', '0', '0'), 
(62, 'OMR0004', 'OMR0004', 'LONGHERINA ANT. RH 55942 M182', 'FA00ADY55942', '^0005594200009174\d{5}[0-3]{1}\d{2}[2-3]{1}\d{1}', '1', '1', '0', 'FASE_PROCESSO_PACKINGLISTOMR3e4', '0', '0', '0'), 
(63, 'OMR0005', 'OMR0005', 'RINFORZO MONTANTE CENTR. DX', '670176339', '^0670176339002742\d{5}[0-3]{1}\d{2}[2-3]{1}\d{1}', '1', '1', '0', 'FASE_PROCESSO_PACKINGLISTOMR5e6', '0', '0', '0'), 
(64, 'OMR0006', 'OMR0006', 'RINFORZO MONTANTE CENTR. SX', '670176340', '^0670176340002742\d{5}[0-3]{1}\d{2}[2-3]{1}\d{1}', '1', '1', '0', 'FASE_PROCESSO_PACKINGLISTOMR5e6', '0', '0', '0'), 
(65, 'OMR0007', 'OMR0007', 'RINFORZO SUP. MONTANTE CENTR.', '670176632', '^0670176632002742\d{5}[0-3]{1}\d{2}[2-3]{1}\d{1}', '1', '1', '0', 'FASE_PROCESSO_PACKINGLISTOMR7e8', '0', '0', '0'), 
(66, 'OMR0008', 'OMR0008', 'RINFORZO SUP. MONTANTE CENTR.', '670176633', '^0670176633002742\d{5}[0-3]{1}\d{2}[2-3]{1}\d{1}', '1', '1', '0', 'FASE_PROCESSO_PACKINGLISTOMR7e8', '0', '0', '0');

--  Tipo Imballi  OMR
INSERT INTO `TipoImballi` (`idProdotto`, `qtaPezziPerScatola`, `qtaScatolePerBancale`, `imballoStandard`, `eliminato`) VALUES
( 59, 40, 1, 1, 0),
( 60, 40, 1, 1, 0),
( 61, 40, 1, 1, 0),
( 62, 40, 1, 1, 0),
( 63, 40, 1, 1, 0),
( 64, 40, 1, 1, 0),
( 65, 200, 1, 1, 0),
( 66, 200, 1, 1, 0);

--  Etichette vuote OMR
INSERT INTO `EtichetteImballi` (`idEtichettaImballo`, `codiceEtichetta`, `numeroDDT`, `dataDDT`, `codiceEtichettaRiepilogativa`, `idTipoImballo`, `eliminato`)
VALUES ('-5', 'SENZA ETICHETTA OMR0001', NULL, NULL, 'SENZA ETICHETTA', '14', '0'),
('-6', 'SENZA ETICHETTA OMR0002', NULL, NULL, 'SENZA ETICHETTA', '15', '0'),
('-7', 'SENZA ETICHETTA OMR0003', NULL, NULL, 'SENZA ETICHETTA', '16', '0'),
('-8', 'SENZA ETICHETTA OMR0004', NULL, NULL, 'SENZA ETICHETTA', '17', '0'),
('-9', 'SENZA ETICHETTA OMR0005', NULL, NULL, 'SENZA ETICHETTA', '18', '0'),
('-10', 'SENZA ETICHETTA OMR0006', NULL, NULL, 'SENZA ETICHETTA', '19', '0'),
('-11', 'SENZA ETICHETTA OMR0007', NULL, NULL, 'SENZA ETICHETTA', '20', '0'),
('-12', 'SENZA ETICHETTA OMR0008', NULL, NULL, 'SENZA ETICHETTA', '21', '0');

-- TRUNCATE Etichette vuote FIA00504 e FIA00505

INSERT INTO `EtichetteImballi` (`idEtichettaImballo`, `codiceEtichetta`, `numeroDDT`, `dataDDT`, `codiceEtichettaRiepilogativa`, `idTipoImballo`, `eliminato`) VALUES
(-2, 'SENZA ETICHETTA', NULL, NULL, 'SENZA ETICHETTA', 7, 0),
(-1, 'SENZA ETICHETTA', NULL, NULL, 'SENZA ETICHETTA', 8, 0);

-- TRUNCATE FasiProcessoProdotto

INSERT INTO `FasiProcessoProdotto`(`idProdotto`, `idFaseProcesso`, `ordine`) VALUES
(37,3,1),
(37,4,2),
(37,5,3),
(37,6,4),
(37,7,5),
(37,8,6);

INSERT INTO `FasiProcessoProdotto`(`idProdotto`, `idFaseProcesso`, `ordine`) VALUES
(38,3,1),
(38,4,2),
(38,5,3),
(38,6,4),
(38,7,5),
(38,8,6);

INSERT INTO `FasiProcessoProdotto`(`idProdotto`, `idFaseProcesso`, `ordine`) VALUES
(39,3,1),
(39,4,2),
(39,5,3),
(39,6,4),
(39,7,5),
(39,8,6);