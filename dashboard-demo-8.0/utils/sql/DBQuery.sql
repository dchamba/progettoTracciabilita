

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