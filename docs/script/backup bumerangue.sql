/*
MySQL Backup
Source Server Version: 5.0.24
Source Database: bumerangue
Date: 30/8/2006 21:09:12
*/


-- ----------------------------
--  Table structure for `dominio`
-- ----------------------------
DROP TABLE IF EXISTS `dominio`;
CREATE TABLE `dominio` (
  `ID` varchar(255) NOT NULL,
  `CODIGO` int(11) default NULL,
  `DESCRICAO` varchar(255) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `elemento_dominio`
-- ----------------------------
DROP TABLE IF EXISTS `elemento_dominio`;
CREATE TABLE `elemento_dominio` (
  `ID` varchar(255) NOT NULL,
  `DESCRICAO` varchar(255) default NULL,
  `ID_DOMINIO` varchar(255) default NULL,
  `ELEMENT_POSITION` int(11) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK9651B9E1528B0E73` (`ID_DOMINIO`),
  CONSTRAINT `FK9651B9E1528B0E73` FOREIGN KEY (`ID_DOMINIO`) REFERENCES `dominio` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `emprestimo`
-- ----------------------------
DROP TABLE IF EXISTS `emprestimo`;
CREATE TABLE `emprestimo` (
  `ID` varchar(255) NOT NULL,
  `DATA_RESERVA` datetime default NULL,
  `OBSERVACAO_RESERVA` varchar(255) default NULL,
  `DATA_EMPRESTIMO` datetime default NULL,
  `OBSERVACAO_EMPRESTIMO` varchar(255) default NULL,
  `DATA_DEVOLUCAO` datetime default NULL,
  `OBSERVACAO_DEVOLUCAO` varchar(255) default NULL,
  `PUBLICO_VIDEO` varchar(255) default NULL,
  `NUMERO_ASSISTENTES_VIDEO` int(11) default NULL,
  `ID_OBJETO_BUMERANGUE` varchar(255) default NULL,
  `ID_USUARIO_REALIZOU_RESERVA` varchar(255) default NULL,
  `ID_USUARIO_EMPRESTIMO` varchar(255) default NULL,
  `ID_USUARIO_REALIZOU_EMPRESTIMO` varchar(255) default NULL,
  `ID_USUARIO_REALIZOU_DEVOLUCAO` varchar(255) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK52F52DAF8D583DA4` (`ID_OBJETO_BUMERANGUE`),
  KEY `FK52F52DAF2A89D72F` (`ID_USUARIO_EMPRESTIMO`),
  KEY `FK52F52DAF8723B124` (`ID_USUARIO_REALIZOU_DEVOLUCAO`),
  KEY `FK52F52DAFDF51452F` (`ID_USUARIO_REALIZOU_EMPRESTIMO`),
  KEY `FK52F52DAF31435DAE` (`ID_USUARIO_REALIZOU_RESERVA`),
  CONSTRAINT `FK52F52DAF31435DAE` FOREIGN KEY (`ID_USUARIO_REALIZOU_RESERVA`) REFERENCES `usuario` (`ID`),
  CONSTRAINT `FK52F52DAF2A89D72F` FOREIGN KEY (`ID_USUARIO_EMPRESTIMO`) REFERENCES `usuario` (`ID`),
  CONSTRAINT `FK52F52DAF8723B124` FOREIGN KEY (`ID_USUARIO_REALIZOU_DEVOLUCAO`) REFERENCES `usuario` (`ID`),
  CONSTRAINT `FK52F52DAF8D583DA4` FOREIGN KEY (`ID_OBJETO_BUMERANGUE`) REFERENCES `objeto_bumerangue` (`ID`),
  CONSTRAINT `FK52F52DAFDF51452F` FOREIGN KEY (`ID_USUARIO_REALIZOU_EMPRESTIMO`) REFERENCES `usuario` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `objeto_bumerangue`
-- ----------------------------
DROP TABLE IF EXISTS `objeto_bumerangue`;
CREATE TABLE `objeto_bumerangue` (
  `ID` varchar(255) NOT NULL,
  `TIPO_OBJETO_BUMERANGUE` varchar(255) NOT NULL,
  `TITULO` varchar(255) default NULL,
  `FORA_USO` decimal(1,0) default NULL,
  `ID_EMPRESTIMO_ATUAL` varchar(255) default NULL,
  `CODIGO` varchar(255) default NULL,
  `CATEGORIA` varchar(255) default NULL,
  `CODIGO_CAIXA` varchar(255) default NULL,
  `LOCALIDADE` varchar(255) default NULL,
  `DATA` datetime default NULL,
  `LEGENDADO` decimal(1,0) default NULL,
  `DUBLADO` decimal(1,0) default NULL,
  `PUBLICO` varchar(255) default NULL,
  `DURACAO_MINUTOS` int(11) default NULL,
  `MIDIA` varchar(255) default NULL,
  `PALAVRAS_CHAVES` varchar(255) default NULL,
  `OBSERVACOES` varchar(255) default NULL,
  `OBSERVACOES_GERAIS` varchar(255) default NULL,
  `LOCALIZACAO_PI` varchar(255) default NULL,
  `LOCALIZACAO_FISICA` varchar(255) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FKFFFA65994C68209` (`OBSERVACOES`),
  KEY `FKFFFA65993B49037` (`MIDIA`),
  KEY `FKFFFA6599447EBF3` (`ID_EMPRESTIMO_ATUAL`),
  KEY `FKFFFA659EAD8300A` (`CATEGORIA`),
  CONSTRAINT `FKFFFA65993B49037` FOREIGN KEY (`MIDIA`) REFERENCES `elemento_dominio` (`ID`),
  CONSTRAINT `FKFFFA6599447EBF3` FOREIGN KEY (`ID_EMPRESTIMO_ATUAL`) REFERENCES `emprestimo` (`ID`),
  CONSTRAINT `FKFFFA65994C68209` FOREIGN KEY (`OBSERVACOES`) REFERENCES `elemento_dominio` (`ID`),
  CONSTRAINT `FKFFFA659EAD8300A` FOREIGN KEY (`CATEGORIA`) REFERENCES `elemento_dominio` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `permissao`
-- ----------------------------
DROP TABLE IF EXISTS `permissao`;
CREATE TABLE `permissao` (
  `ID` varchar(255) NOT NULL,
  `NOME` varchar(255) default NULL,
  `DESCRICAO` varchar(255) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `NOME` (`NOME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `permissao_usuario`
-- ----------------------------
DROP TABLE IF EXISTS `permissao_usuario`;
CREATE TABLE `permissao_usuario` (
  `ID_USUARIO` varchar(255) NOT NULL,
  `ID_PERMISSAO` varchar(255) NOT NULL,
  KEY `FKF8B36836C2F59CA7` (`ID_PERMISSAO`),
  KEY `FKF8B3683667A00235` (`ID_USUARIO`),
  CONSTRAINT `FKF8B3683667A00235` FOREIGN KEY (`ID_USUARIO`) REFERENCES `usuario` (`ID`),
  CONSTRAINT `FKF8B36836C2F59CA7` FOREIGN KEY (`ID_PERMISSAO`) REFERENCES `permissao` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `usuario`
-- ----------------------------
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `ID` varchar(255) NOT NULL,
  `TIPO_USUARIO` varchar(255) NOT NULL,
  `NOME` varchar(255) default NULL,
  `NOME_COMPLETO` varchar(255) default NULL,
  `SENHA` varchar(255) default NULL,
  `EMAIL` varchar(255) default NULL,
  `TELEFONE` varchar(255) default NULL,
  `ATIVO` decimal(1,0) default NULL,
  `BLOQUEADO` decimal(1,0) default NULL,
  `NUMERO_TENTATIVAS_ERRADAS` int(11) default NULL,
  `ADMIN` decimal(1,0) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  View definition for `exportar_video_view`
-- ----------------------------
DROP VIEW IF EXISTS `exportar_video_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `exportar_video_view` AS select `v`.`CODIGO` AS `codigo`,`ed1`.`DESCRICAO` AS `categoria_d`,`v`.`CODIGO_CAIXA` AS `codigo_caixa`,date_format(`v`.`DATA`,_utf8'.%d/%m/%Y.') AS `data_d`,`v`.`PUBLICO` AS `publico`,`v`.`OBSERVACOES` AS `observacoes`,`v`.`TITULO` AS `titulo`,`v`.`DURACAO_MINUTOS` AS `duracao_minutos`,NULL AS `copia`,(case `v`.`LEGENDADO` when 1 then _utf8'Sim' else _utf8'Não' end) AS `legendado_d`,(case `v`.`DUBLADO` when 1 then _utf8'Sim' else _utf8'Não' end) AS `dublado_d`,`v`.`LOCALIDADE` AS `localidade`,`v`.`PALAVRAS_CHAVES` AS `palavras_chaves`,`v`.`LOCALIZACAO_PI` AS `localizacao_pi`,`ed2`.`DESCRICAO` AS `midia_d`,`v`.`OBSERVACOES_GERAIS` AS `observacoes_gerais`,`v`.`LOCALIZACAO_FISICA` AS `localizacao_fisica`,(case `v`.`FORA_USO` when 1 then _utf8'Sim' else _utf8'Não' end) AS `fora_uso_d` from ((`objeto_bumerangue` `v` join `elemento_dominio` `ed1`) join `elemento_dominio` `ed2`) where ((`ed1`.`ID` = `v`.`CATEGORIA`) and (`ed2`.`ID` = `v`.`MIDIA`) and (`v`.`TIPO_OBJETO_BUMERANGUE` = _latin1'VD')) order by `v`.`CATEGORIA`,`v`.`DATA`,`v`.`CODIGO`;

-- ----------------------------
--  View definition for `permissao_usuario_view`
-- ----------------------------
DROP VIEW IF EXISTS `permissao_usuario_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `permissao_usuario_view` AS select `u`.`NOME` AS `nome`,`p`.`NOME` AS `nome_permissao` from ((`permissao_usuario` `pu` join `usuario` `u`) join `permissao` `p`) where ((`pu`.`ID_USUARIO` = `u`.`ID`) and (`pu`.`ID_PERMISSAO` = `p`.`ID`));

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `dominio` VALUES ('1','1','VIDEO_OBSERVACOES');
INSERT INTO `dominio` VALUES ('2','2','VIDEO_CATEGORIA');
INSERT INTO `dominio` VALUES ('3','3','VIDEO_MIDIA');
INSERT INTO `elemento_dominio` VALUES ('1','Só para membros da Obra','1','1');
INSERT INTO `elemento_dominio` VALUES ('10','Só para sacerdotes','1','10');
INSERT INTO `elemento_dominio` VALUES ('11','Numerários mais velhos e Diretores','1','11');
INSERT INTO `elemento_dominio` VALUES ('12','São Miguel e São Gabriel (fundamentalmente zeladores)','1','12');
INSERT INTO `elemento_dominio` VALUES ('13','Para Conselhos locais e pessoas mais velhas','1','13');
INSERT INTO `elemento_dominio` VALUES ('14','Membros da Obra e rapazes de São Rafael','1','14');
INSERT INTO `elemento_dominio` VALUES ('15','Pessoas em contato com o labor apostólico','1','15');
INSERT INTO `elemento_dominio` VALUES ('16','Membros da Obra e rapazes de São Rafael encaixados','1','16');
INSERT INTO `elemento_dominio` VALUES ('17','Para o labor de São Gabriel e Cooperadores apitáveis','1','17');
INSERT INTO `elemento_dominio` VALUES ('19','Geral','2','1');
INSERT INTO `elemento_dominio` VALUES ('2','Só para membros da Obra e Cooperadores encaixados','1','2');
INSERT INTO `elemento_dominio` VALUES ('20','Brasil','2','2');
INSERT INTO `elemento_dominio` VALUES ('21','Documentário','2','3');
INSERT INTO `elemento_dominio` VALUES ('22','Ordenação','2','4');
INSERT INTO `elemento_dominio` VALUES ('23','UNIV','2','5');
INSERT INTO `elemento_dominio` VALUES ('24','VHS','3','1');
INSERT INTO `elemento_dominio` VALUES ('25','DVD','3','2');
INSERT INTO `elemento_dominio` VALUES ('3','Só para pessoas com Oblação e Supernumerários com Fidelidade','1','3');
INSERT INTO `elemento_dominio` VALUES ('4','Recomendado para o labor de São Gabriel','1','4');
INSERT INTO `elemento_dominio` VALUES ('5','Para pessoas doentes','1','5');
INSERT INTO `elemento_dominio` VALUES ('6','Recomendado para o labor de São Rafael','1','6');
INSERT INTO `elemento_dominio` VALUES ('7','Recomendado para o labor de São Gabriel','1','7');
INSERT INTO `elemento_dominio` VALUES ('8','Recomendado para o labor da Sociedade Sacerdotal','1','8');
INSERT INTO `elemento_dominio` VALUES ('9','Recomendado para São Gabriel  e universitários','1','9');
INSERT INTO `objeto_bumerangue` VALUES ('1','VD','Con el Padre en Aralar','0',NULL,'00001','19','Num. 55','Pamplona','1972-10-04 00:00:00','0','0','São Miguel','22','24','PARAR DE GRAVAR','1','','2MC1 pág. 20-25',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('10','VD','Con el Padre en Gaztelueta','0',NULL,'00010','19','Num. 33','Bilbao','1972-10-12 00:00:00','0','0','Geral','33','24','',NULL,'','2MC1 pág. 113-121',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('100','VD','Con el Padre en Altoclaro','0',NULL,'00093','19','A-208','Caracas','1975-02-14 00:00:00','0','0','Geral','54','24','ABORTO\r\nAMOR AO PAPA\r\nATENCAO A FAMIL\r\nCONFISSAO\r\nDIALOGO\r\nFAMILIA NUMEROS\r\nFEMINILIDADE\r\nHEBREU\r\nHEBREU BARBAS\r\nJUDEU\r\nPROFESSOR UNIVERSITÁRIO\r\nSÃO JOSÉ\r\nSOBRE LIVROS',NULL,'','CA3 pág. 108-132',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('101','VD','Con el Padre en Altoclaro','0',NULL,'00653','19','A-208','Caracas','1975-02-14 00:00:00','1','0','Geral','54','24','ABORTO\r\nAMOR AO PAPA\r\nATENCAO A FAMIL\r\nCONFISSAO\r\nDIALOGO\r\nFAMILIA NUMEROS\r\nFEMINILIDADE\r\nHEBREU\r\nHEBREU BARBAS\r\nJUDEU\r\nPROFESSOR UNIVERSITÁRIO\r\nSÃO JOSÉ\r\nSOBRE LIVROS',NULL,'','CA3 pág. 108-132',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('102','VD','Con el Padre en Ciudad Vieja','0',NULL,'00094','19','A-210','Guatemala','1975-02-18 00:00:00','0','0','Sociedade Sacerdotal','50','24','',NULL,'','CA3 pág. 252-261',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('103','VD','Con el Padre en Ciudad Vieja','0',NULL,'00095','19','A-211','Guatemala','1975-02-19 00:00:00','0','0','São Miguel','48','24','CONVERSA FRATERNA\r\nGRAVATAS\r\nDEVOCÃO A SÃO JOSÉ\r\nSANTIFICACAO\r\nSANTO D.ALVARO\r\nTRABALHO SR\r\nTRIPTICO\r\nVERSOS','1','','CA3 pág. 274-290',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('104','VD','Con el Padre en Tajamar','0',NULL,'00096','19','Num. 65','Madrid','1976-06-14 00:00:00','0','0','Geral','62','24','COMECOS DA OBRA\r\nCOMO CONHECEU\r\nGUADALUPE 26-V\r\nNOSSO PADRE\r\nO NOSSO PADE',NULL,'','cn 1.976 pág. 904-915',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('105','VD','Con el Padre en Tajamar','0',NULL,'00097','19','Num. 66','Madrid','1976-06-16 00:00:00','0','0','Geral','70','24','',NULL,'','cn 1.976 pág. 918-926',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('106','VD','Con el Padre en Tajamar','0',NULL,'00647','19','Num. 67','Madrid','1976-06-17 00:00:00','0','0','Geral','82','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('107','VD','Con el Padre en Tajamar','0',NULL,'00098','19','Num. 68','Madrid','1976-06-18 00:00:00','0','0','Geral','69','24','',NULL,'','cn 1.976 pág. 949-965',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('108','VD','Con el Padre en Tajamar','0',NULL,'00099','19','Num. 69','Madrid','1976-06-19 00:00:00','0','0','Geral','73','24','',NULL,'','cn 1.976 pág. 968-983',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('109','VD','Semana Santa en Roma','0',NULL,'00100','19','R-04/77','Roma','1977-04-07 00:00:00','0','0','Varões','68','24','',NULL,'','cn 1.977 pág. 530-538',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('11','VD','Con el Padre en Islabe','0',NULL,'00011','19','Num. 35','Bilbao','1972-10-12 00:00:00','0','0','Geral','22','24','',NULL,'','2MC1 pág. 125-127',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('110','VD','Con el Padre en Schweidt','0',NULL,'00101','19','2/78','','1978-11-05 00:00:00','0','0','Varões','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('111','VD','Con el Padre en Netherhall House','0',NULL,'00102','19','Brit-2/80','Londres','1980-08-16 00:00:00','0','0','São Miguel','72','24','','1','','cn 1.980 pág. 1169-1179',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('112','VD','Con el Padre en Netherhall House','0',NULL,'00103','19','Brit-2/80','Londres','1980-08-16 00:00:00','0','0','São Miguel','72','24','',NULL,'','cn 1.980 pág. 1169-1179',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('113','VD','Con el Padre en University College House','0',NULL,'00104','19','Brit-3/80','Londres','1980-08-17 00:00:00','0','0','Geral','55','24','',NULL,'','cn 1.980 pág. 1180-1195',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('114','VD','Con el Padre en University College House','0',NULL,'00105','19','Brit-3/80','Londres','1980-08-18 00:00:00','0','0','Geral','60','24','',NULL,'','cn 1.980 pág. 1180-1195',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('115','VD','Con el Padre en Siddington','0',NULL,'00106','19','Brit-4/80','Manchester','1980-08-24 00:00:00','0','0','Geral','55','24','',NULL,'','cn 1.980 pág. 1204-1217',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('116','VD','Con el Padre en Tajamar','0',NULL,'00107','19','H-01/82','Madrid','1982-04-14 00:00:00','0','0','São Miguel','59','24','',NULL,'','cn 1.982 pág. 533-551',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('117','VD','Con el Padre en Brafa','0',NULL,'00108','19','H-03/82','Barcelona','1982-04-17 00:00:00','0','0','São Miguel','29','24','',NULL,'','cn 1.982 pág. 558-574',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('118','VD','Con el Padre en La Univ. Panamericana','0',NULL,'00109','19','5/83','México DF','1983-05-07 00:00:00','0','0','Varões','50','24','',NULL,'','CP83 pág. 92-102',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('119','VD','Con el Padre en Montefalco','0',NULL,'00110','19','8/83','México','1983-05-10 00:00:00','0','0','Geral','67','24','',NULL,'','CP83 pág. 148-154',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('12','VD','Con el Padre en Tajamar','0',NULL,'00012','19','Num. 30','Madrid','1972-10-15 00:00:00','0','0','Geral','37','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('120','VD','Con el Padre en Monterrey','0',NULL,'00111','19','10/83','Monterrey','1983-05-13 00:00:00','0','0','Geral','78','24','',NULL,'','CP83. 189-201',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('121','VD','Con el Padre en Guadalajara','0',NULL,'00112','19','13/83','Guadalajara','1983-05-16 00:00:00','0','0','Geral','72','24','',NULL,'','CP83 pág. 233-241',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('122','VD','Con el Padre en Jaltepec','0',NULL,'00113','19','14/83','Guadalajara','1983-05-17 00:00:00','0','0','Varões, São Gabriel','75','24','',NULL,'','CP83 pág. 272-274',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('123','VD','Con el Padre en Toshi','0',NULL,'00114','19','15/83','México','1983-05-19 00:00:00','0','0','Geral','75','24','',NULL,'','CP83 pág. 257-265',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('124','VD','Con el Padre en Toshi','0',NULL,'00115','19','18/83','México','1983-05-19 00:00:00','0','0','Sociedade Sacerdotal','86','24','','8','','CP83 pág. 278-300',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('125','VD','Con el Padre en El Ipade','0',NULL,'00116','19','16/83','México DF','1983-05-21 00:00:00','0','0','Varões, Grupos Promotores','51','24','','2','','CP83 pág. 266-274',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('126','VD','Una mañana con el Padre (Ipade)','0',NULL,'00117','19','17/83','México DF','1983-05-22 00:00:00','0','0','Geral','66','24','',NULL,'','CP83 pág. 308-313',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('127','VD','Con el Padre en los Cerros','0',NULL,'00118','19','Col-4/83','Bogotá','1983-05-26 00:00:00','0','0','São Miguel','59','24','','3','','CP83 pág. 494-531',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('128','VD','Con el Padre en los Cerros','0',NULL,'00119','19','20/83','Bogotá','1983-05-27 00:00:00','0','0','Geral','66','24','',NULL,'','CP83 pág. 494-532',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('129','VD','Con el Padre en los Cerros','0',NULL,'00120','19','24/83','Bogotá','1983-05-29 00:00:00','0','0','Varões','70','24','',NULL,'','CP83 pág. 456-468',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('13','VD','Con el Padre en Prado','0',NULL,'00013','19','Num. 32','Madrid','1972-10-18 00:00:00','0','0','Geral','35','24','',NULL,'','2MC1 pág. 144-169',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('130','VD','Con el Padre en los Cerros','0',NULL,'00121','19','26/83','Bogotá','1983-05-30 00:00:00','0','0','Sociedade Sacerdotal','56','24','',NULL,'','CP83 pág. 469-474',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('131','VD','Con el Padre en los Cerros','0',NULL,'00122','19','27/83','Bogotá','1983-05-31 00:00:00','0','0','Geral','67','24','','4','','CP83 pág. 494-532',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('132','VD','Con el Padre en Uraba','0',NULL,'00123','19','28/83','Bogotá','1983-05-31 00:00:00','0','0','Varões','60','24','','1','','CP83 pág. 542-548',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('133','VD','Con el Padre en Crawford','0',NULL,'00124','19','33/83','New York','1983-06-04 00:00:00','0','0','São Miguel','78','24','','1','','CP83 pág. 584-594',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('134','VD','Con el Padre en Hunter College','0',NULL,'00125','19','34/83','New York','1983-06-05 00:00:00','0','0','Geral','83','24','',NULL,'','CP83 pág. 595-606',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('135','VD','Con el Padre en Colonia','0',NULL,'00126','19','37/83','Colônia','1983-08-27 00:00:00','0','0','Geral','61','24','',NULL,'','cn 1.983 pág. 928-934',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('136','VD','Con el Padre en Retamar','0',NULL,'00127','19','H-01/83','Madrid','1983-09-09 00:00:00','0','0','Geral','89','24','',NULL,'','cn 1.983 pág. 1065-1078',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('137','VD','Con el Padre en Cavabianca','0',NULL,'00128','19','R-01/85','Roma','1985-04-01 00:00:00','0','0','São Rafael','61','24','',NULL,'','ob 1.985 pág. 130-157',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('138','VD','Con el Padre en Cavabianca','0',NULL,'00129','19','R-03/85','Roma','1985-04-03 00:00:00','0','0','São Rafael','72','24','',NULL,'','ob 1.985 pág. 130-157',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('139','VD','Con el Padre en Cavabianca','0',NULL,'00130','19','R-05/85','Roma','1985-04-06 00:00:00','0','0','São Miguel','71','24','','1','','cn 1.985 pág. 353-359',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('14','VD','Con el Padre en Tajamar','0',NULL,'00014','19','Num. 31','Madrid','1972-10-21 00:00:00','0','0','Geral','47','24','MUITA PERGUNTA\r\nRESPOSTA BREVE\r\nVIDA FAMILIAR','1','','2MC1 pág. 169-175',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('140','VD','Con el Padre en Netherhall House','0',NULL,'00131','19','Brit-1/85','Londres','1985-11-09 00:00:00','0','0','São Miguel','67','24','','3','','cn 1.985 pág. 1406-1409',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('141','VD','Con el Padre en Cavabianca','0',NULL,'00132','19','R-03/86','Roma','1986-03-26 00:00:00','0','0','São Rafael','60','24','',NULL,'','ob 1.986 pág. 144-161',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('142','VD','Con el Padre en Cavabianca','0',NULL,'00133','19','R-01/86','Roma','1986-03-29 00:00:00','0','0','São Miguel','75','24','','1','','cn 1.986 pág. 404-419',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('143','VD','Una mañana con el Padre','0',NULL,'00134','19','H-01/86','Madrid','1986-11-30 00:00:00','0','0','São Miguel','61','24','',NULL,'','cn 1.987 pág. 37-41',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('144','VD','Una mañana con el Padre','0',NULL,'00135','19','H-03/86','Madrid','1986-12-02 00:00:00','0','0','Geral','59','24','','3','','cn 1.987 pág. 43-52',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('145','VD','Una tarde con el Padre','0',NULL,'00136','19','H-04/86','Espanha','1986-12-02 00:00:00','0','0','Geral','70','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('146','VD','Com o Padre no Planalto','0',NULL,'00137','19','L-01/86','Lisboa','1986-12-07 00:00:00','0','0','Geral','65','24','',NULL,'','cn 1.987 pág. 59-63',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('147','VD','Com o Padre no Lumiar','0',NULL,'00138','19','L-02/86','Lisboa','1986-12-08 00:00:00','0','0','São Miguel','65','24','',NULL,'','cn 1.987 pág. 63-65',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('148','VD','Com o Padre no Planalto','0',NULL,'00139','19','L-03/86','','1986-12-08 00:00:00','0','0','Geral','72','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('149','VD','Com o Padre no Planalto','0',NULL,'00140','19','L-03/86','Lisboa','1986-12-08 00:00:00','0','0','Geral','72','24','',NULL,'','cn 1.987 pág. 65-67',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('15','VD','Con el Padre en Tajamar','0',NULL,'00015','19','Num. 01','Madrid','1972-10-22 00:00:00','0','0','Geral','46','24','',NULL,'','2MC1 pág. 130-169',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('150','VD','Con el Padre en Singapur','0',NULL,'00141','19','PI-01/87','Singapura','1987-01-11 00:00:00','0','0','Geral','68','24','',NULL,'','CP87 pág. 24-33',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('151','VD','Con el Padre en Findlay','0',NULL,'00142','19','Aut-1/87','Sydney','1987-01-14 00:00:00','0','0','São Miguel','55','24','ALVARO\r\nAUSTRALIA\r\nDOM DA VOCACAO\r\nLANTERNA VERMEL\r\nMIL NOVECENTOS E OITENTA E SETE\r\nNUMERARIOS\r\nOVELHAS\r\nRESPEIT. HUMANO','1','','CP87 pág. 50-54',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('152','VD','Con el Padre en Warrane','0',NULL,'00143','19','Aut-3/87','Sydney','1987-01-16 00:00:00','0','0','Sociedade Sacerdotal','64','24','','1','','CP87 pág. 61-68',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('153','VD','Con el Padre en Warrane','0',NULL,'00144','19','Aut-5/87','Sydney','1987-01-17 00:00:00','0','0','Varões, São Gabriel','53','24','','1','','CP87 pág. 70-83',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('154','VD','Con el Padre en Warrane','0',NULL,'00145','19','Aut-6/87','Sydney','1987-01-17 00:00:00','0','0','São Rafael','55','24','ALCOOLISMO\r\nCOMO ANIMAL\r\nCOMPROMISSO\r\nINFERIORIDADE\r\nNOIVA\r\nSINAIS DA VOCAÇÃO\r\nacidente de moto',NULL,'','CP87 pág. 77-83',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('155','VD','Con el Padre en Australia','0',NULL,'00146','19','1/87','Sydney','1987-01-18 00:00:00','0','0','Geral','76','24','BÊNÇÃO DO PAPA\r\nFAMILIA SANGUE\r\nINDIA CONVERSA\r\nJUDIA\r\nMÉDICO DE CAMINHOS DIVINOS\r\nSEGREDO\r\nUnidade de Vida',NULL,'','CP87 pág. 84-93',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('156','VD','Con el Padre en Westburne','0',NULL,'00147','19','Aut-8/87','Sydney','1987-01-19 00:00:00','0','0','São Rafael','49','24','','6','','CP87 pág. 102-104',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('157','VD','Con el Padre en PICC','0',NULL,'00148','19','PI-06/87','Manilla','1987-01-25 00:00:00','0','0','Geral','77','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('158','VD','Con el Padre en Manilla-PICC','0',NULL,'00149','19','PI-07/87','Manilla','1987-01-25 00:00:00','0','0','Geral','58','24','',NULL,'','CP87 pág. 169-186',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('159','VD','Con el Padre en el Center for Research and Communication','0',NULL,'00150','19','PI-08/87','Manilla','1987-01-26 00:00:00','0','0','Sociedade Sacerdotal','73','24','',NULL,'','CP87 pág. 145-152',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('16','VD','Con el Padre en Tajamar','0',NULL,'00016','19','Num. 06-B','Madrid','1972-10-24 00:00:00','0','0','São Rafael','35','24','RAPAZ QUE CHORA\r\nBEIJO NA FOTO\r\nDEIXAR A NOIVA\r\nRESPOSTAS CURTAS',NULL,'','2MC1 pág. 144-169',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('160','VD','Con el Padre en PICC','0',NULL,'00151','19','PI-09/87','Manilla','1987-01-27 00:00:00','0','0','Geral','65','24','',NULL,'','CP87 pág. 196-199',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('161','VD','Con el Padre en Cebú','0',NULL,'00152','19','PI-10/87','Cebú','1987-01-28 00:00:00','0','0','Sociedade Sacerdotal','77','24','','8','','CP87 pág. 205-209',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('162','VD','Con el Padre en Tanglaw','0',NULL,'00153','19','PI-11/87','Manilla','1987-01-30 00:00:00','0','0','Grupos Promotores','71','24','',NULL,'','CP87 pág. 250-257',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('163','VD','Con el Padre en Manilla-PICC','0',NULL,'00154','19','PI-13/87','Manilla','1987-01-31 00:00:00','0','0','São Miguel','67','24','BENDITA SEJA\r\nCARINHAS\r\nESCARAVELHO',NULL,'','CP87 pág. 259-268',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('164','VD','Con el Padre en Manilla-PICC','0',NULL,'00155','19','4-87','Manilla','1987-02-01 00:00:00','0','0','Geral','83','24','',NULL,'','CP87 pág. 156-192',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('165','VD','Con el Padre en Manilla-PICC','0',NULL,'00156','19','4-87','Manilla','1987-02-01 00:00:00','0','0','Geral','83','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('166','VD','Con el Padre en Hong-Kong','0',NULL,'00157','19','5/87','Hong-Kong','1987-02-03 00:00:00','0','0','Geral','66','24','',NULL,'','CP87 pág. 303-313',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('167','VD','Con el Padre en Taipe','0',NULL,'00158','19','PI-16/87','Taipei','1987-02-07 00:00:00','0','0','Geral','65','24','',NULL,'','CP87 pág. 342-350',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('168','VD','Con el Padre en Ashiya (Yamamura Salon)','0',NULL,'00159','19','J-02/87','Ashiya','1987-02-14 00:00:00','0','0','Geral','61','24','',NULL,'','CP87 pág. 398-406',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('169','VD','Con el Padre en Colegia Mihara','0',NULL,'00160','19','J-03/87','Nagasaki','1987-02-15 00:00:00','0','0','Geral','65','24','',NULL,'','CP87 pág. 410-422',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('17','VD','Con el Padre en Senara','0',NULL,'00017','19','Num. 20','Madrid','1972-10-26 00:00:00','0','0','Geral','23','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('170','VD','Con el Padre en Seido Cultural Center','0',NULL,'00161','19','J-04/87','Ashiya','1987-02-18 00:00:00','0','0','São Miguel','67','24','ALVARO K.\r\nAMIZADE\r\nNORTE/SUL\r\nORACAO NPE',NULL,'','CP87 pág. 448-453',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('171','VD','Con el Padre en Seido Cultural Center','0',NULL,'00162','19','J-09/87','Ashiya','1987-02-22 00:00:00','0','0','Varões','59','24','',NULL,'','CP87 pág. 473-480',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('172','VD','Con el Padre en Cavabianca','0',NULL,'00163','19','R-02/87','Roma','1987-04-15 00:00:00','0','0','São Rafael','71','24','','15','','cn 1.987 pág. 387-397',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('173','VD','Una Tarde con El Padre','0',NULL,'00164','19','H-02/87','Pamplona','1987-08-05 00:00:00','0','0','Varões','60','24','','1','','cn 1.987 pág. 918-926',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('174','VD','Con el Padre en Aldebaran','0',NULL,'00165','19','H-03/87','Valladolid','1987-08-26 00:00:00','0','0','Geral','78','24','','2','','ob 1.987 pág. 429-444',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('175','VD','Con el Padre en Guilarte','0',NULL,'00166','19','Port-02/88','San Juan de Puerto Rico','1988-01-20 00:00:00','0','0','São Miguel','50','24','',NULL,'','CP88 pág. 33-38',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('176','VD','Con el Padre en Guilarte','0',NULL,'00167','19','Port-04/88','San Juan de Puerto Rico','1988-01-23 00:00:00','0','0','São Rafael','66','24','','15','','CP88 pág. 33-38',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('177','VD','Con el Padre en Ponce','0',NULL,'00168','19','1/88','Puerto rico','1988-01-24 00:00:00','0','0','Geral','60','24','',NULL,'','CP88 pág. 48-57',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('178','VD','Con el Padre en Oakton Study Center','0',NULL,'00169','19','Tx-02/88','Houston','1988-01-27 00:00:00','0','0','São Miguel','68','24','',NULL,'','CP88 pág. 178-187',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('179','VD','Con el Padre en Tilden','0',NULL,'00170','19','Cal-2/88','Los Angeles','1988-02-03 00:00:00','0','0','São Rafael','59','24','','1','','CP88 pág. 267-275',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('18','VD','Con el Padre en Tajamar','0',NULL,'00018','19','Num. 43','Madrid','1972-10-26 00:00:00','0','0','Sociedade Sacerdotal','41','24','',NULL,'','2MC1 pág. 129-169',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('180','VD','Con el Padre en San Francisco','0',NULL,'00171','19','6/88','San Francisco','1988-02-07 00:00:00','0','0','Geral','63','24','',NULL,'','CP88 pág. 332-341',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('181','VD','Con el Padre en Shellbourne','0',NULL,'00172','19','Eu-6/88','Chicago','1988-02-13 00:00:00','0','0','São Rafael','66','24','GIBSON\r\nLINGUA ESPANHOL\r\nNUM. 25\r\nQUATORZE ANOS','6','','CP88 pág. 418-430',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('182','VD','Con el Padre en Chicago','0',NULL,'00173','19','7/88','Chicago','1988-02-14 00:00:00','0','0','Geral','72','24','',NULL,'','CP88 pág. 434-443',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('183','VD','Con el Padre en Northview University Center','0',NULL,'00174','19','Eu-7/88','Chicago','1988-02-15 00:00:00','0','0','Sociedade Sacerdotal','66','24','','8','','CP88 pág. 451-459',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('184','VD','Con el Padre en Tenley Study Center','0',NULL,'00175','19','Eu-9/88','Washington','1988-02-18 00:00:00','0','0','Geral','61','24','','4','','CP88 pág. 494-496',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('185','VD','Con el Padre en Crawford','0',NULL,'00176','19','Eu-10/88','New York','1988-02-20 00:00:00','0','0','São Rafael','67','24','','6','','CP88 pág. 511-518',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('186','VD','Con el Padre en Lehman College','0',NULL,'00177','19','9/88','New York','1988-02-21 00:00:00','0','0','Geral','58','24','',NULL,'','CP88 pág. 519-526',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('187','VD','Con el Padre en Crawford (Nueva York)','0',NULL,'00178','19','Eu-12/88','Estados Unidos','1988-02-22 00:00:00','0','0','Sociedade Sacerdotal','59','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('188','VD','Con el Padre en Chestnut Hill','0',NULL,'00179','19','Eu-14/88','Boston','1988-02-24 00:00:00','0','0','Geral, Grupos Promotores','70','24','','7','','CP88 pág. 538-542',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('189','VD','Con el Padre en Elmbrook','0',NULL,'00180','19','Eu-15/88','Boston','1988-02-25 00:00:00','0','0','São Rafael','62','24','',NULL,'','CP88 pág. 543-551',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('19','VD','Con el Padre en Tajamar','0',NULL,'00019','19','Num. 29','Madrid','1972-10-28 00:00:00','0','0','Geral','35','24','',NULL,'','2MC1 pág. 199-206',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('190','VD','Con el Padre en Tour des Pins','0',NULL,'00181','19','C-1/88','Montreal','1988-02-28 00:00:00','0','0','Geral','65','24','',NULL,'','CP88 pág. 576-579',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('191','VD','Con el Padre en Tour des Pins','0',NULL,'00662','19','C-2/88','Montreal','1988-02-29 00:00:00','0','0','Sociedade Sacerdotal','77','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('192','VD','Con el Padre en Quebec','0',NULL,'00182','19','C-4/88','Quebéc','1988-03-03 00:00:00','0','0','Geral','75','24','',NULL,'','CP88 pág. 617-622',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('193','VD','Con el Padre en Ullerston','0',NULL,'00183','19','C-6/88','Toronto','1988-03-05 00:00:00','0','0','São Rafael','59','24','',NULL,'','CP88 pág. 637-642',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('194','VD','Con el Padre en Toronto','0',NULL,'00184','19','C-8/88','Toronto','1988-03-06 00:00:00','0','0','Geral','80','24','','4','','CP88 pág. 645-650',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('195','VD','Con el Padre en Pittsburgh','0',NULL,'00185','19','Eu-16/88','Pittsburg','1988-03-07 00:00:00','0','0','Geral','71','24','',NULL,'','CP88 pág. 663-668',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('196','VD','Con el Padre en Pamplona','0',NULL,'00186','19','H-01/88','Pamplona','1988-07-27 00:00:00','0','0','Geral','75','24','',NULL,'','cn 1.988 pág. 868-875',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('197','VD','Con el Padre en Valmayor','0',NULL,'00187','19','H-02/88','Gijón','1988-08-22 00:00:00','0','0','Geral','62','24','','1','','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('198','VD','Con el Padre en Couvrelles','0',NULL,'00188','19','Gal-2/88','Braine','1988-08-28 00:00:00','0','0','São Miguel','48','24','DIRETOR JOVEM',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('199','VD','Con el Padre en Paris','0',NULL,'00189','19','Gal-3/88','Paris','1988-08-29 00:00:00','0','0','Geral','65','24','',NULL,'','cn 1.988 pág. 876-885',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('2','VD','Con el Padre en Aralar','0',NULL,'00002','19','Num. 40','Pamplona','1972-10-06 00:00:00','0','0','Sociedade Sacerdotal','30','24','','1','','2MC1 pág. 20-29',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('20','VD','Con el Padre en Tajamar','0',NULL,'00020','19','Num. 29','Madrid','1972-10-28 00:00:00','1','0','Geral','35','24','',NULL,'','2MC1 pág. 199-206',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('200','VD','Con el Padre en Tajamar','0',NULL,'00190','19','H-01/89','Espanha','1989-01-16 00:00:00','0','0','São Miguel','64','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('201','VD','Tertulia con el Padre en Pamplona','0',NULL,'00191','19','H-06/89','Pamplona','1989-01-22 00:00:00','0','0','Geral','75','24','',NULL,'','cn 1.989 pág. 165-173',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('202','VD','Com o Padre no Lumiar','0',NULL,'00192','19','L-01/89','Lisboa','1989-01-24 00:00:00','0','0','São Miguel','57','24','AMO-TE (PAPA)\r\nAPUNTES INTIMOS\r\nHUGO AZEVEDO\r\nIRMA LUCIA\r\nMACAO\r\nMIGUEL FARIAS',NULL,'','cn 1.989 pág. 266-268',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('203','VD','Com o Padre no Planalto','0',NULL,'00193','19','L-02/89','Lisboa','1989-01-26 00:00:00','0','0','Geral','70','24','','4','','cn 1.989 pág. 272-278',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('204','VD','Con el Padre en Cavabianca','0',NULL,'00194','19','R-02/89','Roma','1989-03-22 00:00:00','0','0','São Rafael','67','24','','15','','cn 1.989 pág. 400-410',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('205','VD','Con el Padre en Nairobi','0',NULL,'00195','19','K-02/89','Nairobi','1989-04-04 00:00:00','0','0','Geral','72','24','PACIENCIA\r\nPLANEJ. FAMILIA\r\nUNIDADE VIDA\r\nVOC. DOS FILHOS',NULL,'','cn 1.989 pág. 34-52',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('206','VD','Con el Padre en Strathmore College','0',NULL,'00196','19','K-03/89','Kinshasa','1989-04-05 00:00:00','0','0','São Rafael','39','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('207','VD','Con el Padre en Strathmore College','0',NULL,'00197','19','K-06/89','Nairobi','1989-04-07 00:00:00','0','0','São Rafael','52','24','',NULL,'','CP89 pág. 71-79',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('208','VD','Con el Padre en Nairobi','0',NULL,'00198','19','K-07/89','Nairobi','1989-04-08 00:00:00','0','0','Geral','65','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('209','VD','Colegio Viaro','0',NULL,'00200','19','H-09/89','Barcelona','1989-05-20 00:00:00','0','0','Geral','75','24','',NULL,'','cn 1.989 pág. 621-625',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('21','VD','Con el Padre en Tajamar','0',NULL,'00021','19','Num. 28','Madrid','1972-10-29 00:00:00','0','0','Geral','43','24','SAUDACAO AFRICA',NULL,'','2MC1 pág. 220-227',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('210','VD','Con el Padre en Kinshasa','0',NULL,'00201','19','Z-02/89','Kinshasa','1989-08-24 00:00:00','0','0','Geral','58','24','',NULL,'','cn 1.989 pág. 165-184',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('211','VD','Con el Padre en Loango (Kinshasa)','0',NULL,'00202','19','Z-04/89','Kinshasa','1989-08-25 00:00:00','0','0','São Rafael','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('212','VD','Con el Padre en Zaire','0',NULL,'00203','19','Z-05/89','Kinshasa','1989-08-26 00:00:00','0','0','Geral','62','24','AJUDA ECONOMICA',NULL,'','cn 1.989 pág. 194-197',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('213','VD','Con el Padre en Abidjan','0',NULL,'00204','19','Ceb-2/89','Costa do Marfim','1989-10-15 00:00:00','0','0','Geral','72','24','',NULL,'','cn 1.989 pág. 260-272',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('214','VD','Con el Padre en el Instituto Goethe','0',NULL,'00205','19','Ceb-6/89','Costa do Marfim','1989-10-18 00:00:00','0','0','São Rafael','65','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('215','VD','Con el Padre en Comerce House','0',NULL,'00206','19','Nig-2/89','Lagos','1989-11-11 00:00:00','0','0','São Rafael','61','24','',NULL,'','cn 1.989 pág. 337-350',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('216','VD','Con el Padre en Ibadán','0',NULL,'00207','19','Nig-10/89','Ibadán','1989-11-15 00:00:00','0','0','Geral','52','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('217','VD','Con el Padre en Iroto','0',NULL,'00208','19','Nig-11/89','Iroto','1989-11-16 00:00:00','0','0','Geral','72','24','',NULL,'','cn 1.989 pág. 374-383',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('218','VD','Con el Padre en Enugu','0',NULL,'00209','19','Nig-12/89','Enugu','1989-11-17 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('219','VD','Con el Padre en Lagos','0',NULL,'00210','19','Nig-13/89','Lagos','1989-11-19 00:00:00','0','0','Geral','58','24','',NULL,'','cn 1.989 pág. 463-474',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('22','VD','La huella de un Santo, Andalucía','0',NULL,'00022','19','xx','Andalucía','1972-11-01 00:00:00','0','0','xx','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('220','VD','Con el Padre en Cavabianca','0',NULL,'00211','19','R-05/91','Roma','1991-03-30 00:00:00','0','0','São Miguel','46','24','FRIO\r\nJAVI? COMO ERA?\r\nLOS COLADOS\r\nROLIM',NULL,'','cn 1.991 pág. 360-369',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('221','VD','Con el Padre en Viena','0',NULL,'00213','19','A-1/93','Viena','1993-10-03 00:00:00','0','0','Geral','48','24','',NULL,'','cn 1.993 pág. 1045-1051',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('222','VD','Con el Padre en Zürick','0',NULL,'00214','19','Hel-01/93','Zürich','1993-10-07 00:00:00','0','0','Geral','56','24','',NULL,'','cn 1.993 pág. 1054-1067',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('223','VD','Con el Padre en Colonia','0',NULL,'00215','19','G-1/93','Colônia','1993-10-30 00:00:00','0','0','Geral','60','24','',NULL,'','cn 1.993 pág. 1133-1144',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('224','VD','Con el Padre en Pozoalbero','0',NULL,'00216','19','H-02/93','Jerez de la F.','1993-11-19 00:00:00','0','0','São Miguel','67','24','AGRADECIMENTOS\r\nCOROINHAS\r\nEMPRESARIAL\r\nEMPRESAS\r\nJESUS ARELLANO\r\nMANOLO\r\nPESSOAS +VELHAS\r\nSEVILLANAS',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('225','VD','Con el Padre en Pozoalbero','0',NULL,'00217','19','H-03/93','Jerez de la F.','1993-11-20 00:00:00','0','0','Geral','65','24','',NULL,'','ob 1.994 pág. 70-81',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('226','VD','Con el Padre en el Colegio Retamar','0',NULL,'00218','19','H-05/93','Madrid','1993-11-24 00:00:00','0','0','Geral','70','24','APO. CONFISSAO\r\nCEM MIL PESSOAS\r\nGANCHO\r\nRESPEITO HUM.\r\nVOCACAO JOVEM',NULL,'','ob 1.994 pág. 82-93',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('227','VD','Con el Padre en Belén','0',NULL,'00219','19','Iers-3/94','Belén(Israel)','1994-03-19 00:00:00','0','0','Geral','45','24','',NULL,'','ob 1.994 pág. 203-206',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('228','VD','Con el Padre em el Cielo','0',NULL,'00220','19','xx','xx','1994-03-23 00:00:00','0','0','xx','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('229','VD','Con el Padre en Cavabianca','0',NULL,'00221','19','R-05/94','Roma','1994-04-01 00:00:00','0','0','São Rafael','75','24','CAVABIANCA\r\nFUNERAL DE DOM ÁLVARO\r\nTERRA SANTA',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('23','VD','Con el Padre en Pozoalbero','0',NULL,'00023','19','Num. 44','Jerez de la F.','1972-11-07 00:00:00','0','0','Sociedade Sacerdotal','44','24','',NULL,'','2MC1 pág. 379-382',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('230','VD','Tertulia con el Padre','0',NULL,'00222','19','H-02/94','Madrid','1994-07-09 00:00:00','0','0','São Miguel','52','24','CARECA\r\nESTOY HARTO\r\nPAPAS',NULL,'','cn 1.994 pág. 950-955',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('231','VD','Con el Padre en Pamplona','0',NULL,'00223','19','H-04/94','Pamplona','1994-08-20 00:00:00','0','0','Geral','70','24','AVO E NETOS\r\nAVO/NETOS\r\nISMAEL S. BELLA\r\nOBRA NOSSA MAO',NULL,'','cn 1.994 pág. 955-961',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('232','VD','Con el Padre en Lumiar','0',NULL,'00224','19','L-01/94','Lisboa','1994-08-25 00:00:00','0','0','São Miguel','50','24','',NULL,'','cn 1.994 pág. 965-968',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('233','VD','Tertulia con el Padre','0',NULL,'00225','19','L-03/94','Fátima','1994-08-26 00:00:00','0','0','Geral','63','24','AMOR VALE AMOR\r\nANO DA FAMILIA\r\nARQUIFELIZES\r\nCINCO MIL VOCACOES\r\nCINCO MIL VOCACOES\r\nCOLEGIOS\r\nCOLEGIOS FOMENT\r\nDOM ALBERTO\r\nFAM. NUMEROSA\r\nFATIMA\r\nIMAGEM FATIMA\r\nMUITO AMIGOS\r\nOITENTA MIL MEMBROS\r\nOITO ALEMÃES\r\nPORTUGUESAS\r\nRAPARIGAS',NULL,'','cn 1.994 pág. 967-972',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('234','VD','Tertulia con el Padre en Fluntern','0',NULL,'00226','19','Hel-02/94','Zürich','1994-08-28 00:00:00','0','0','São Miguel','62','24','DOENTE\r\nEXPANSAO\r\nFILIAÇÃO DIVINA\r\nFILIACAO/FRATERNIDADE\r\nFRATERNIDADE\r\nGRACA PROPORCIO\r\nONOMASTICO\r\nPROFESSOR/ALUNO\r\nSUICA E IGUAL\r\nTEMPOS N. PADRE\r\nUNIDADE OBRA',NULL,'','cn 1.994 pág. 973-981',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('235','VD','Con el Padre en Cleraun','0',NULL,'00227','19','Hiber-1/94','Dublín','1994-08-30 00:00:00','0','0','São Miguel','60','24','',NULL,'','cn 1.994 pág. 1042-1043',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('236','VD','Tertulia con el Padre','0',NULL,'00228','19','Hiber-3/94','Dublín','1994-08-31 00:00:00','0','0','Geral','67','24','ABORTO\r\nBEIJO NO BEBÊ\r\nCASAIS JOVENS\r\nDUBLIN\r\nIGREJA2\r\nSALA DO ORGAO\r\nTRADUCAO','9','','cn 1.994 pág. 1039-1049',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('237','VD','Con el Padre en Orme Court','0',NULL,'00229','19','Brit-1/94','Londres','1994-09-02 00:00:00','0','0','São Miguel','59','24','',NULL,'','cn 1.994 pág. 1050-1058',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('238','VD','Con el Padre en Ashwell House','0',NULL,'00230','19','Brit-3/94','Londres','1994-09-03 00:00:00','0','0','Geral','66','24','',NULL,'','cn 1.994 pág. 1050-1058',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('239','VD','Con el Padre en Garnelles','0',NULL,'00231','19','Gal-1/94','Paris','1994-09-04 00:00:00','0','0','São Miguel','63','24','',NULL,'','cn 1.994 pág. 1059-1067',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('24','VD','Con el Padre en Guadaira','0',NULL,'00024','19','Num. 04-A','Sevilla','1972-11-08 00:00:00','0','0','São Rafael','35','24','HABLANDO EN PLA\r\nMORTIFIC ESTUDO\r\nMUITAS PERGUNT.\r\nVAMOS A LA PLAT',NULL,'','2MC1 pág. 371-436',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('240','VD','Con el Padre en Garnelles','0',NULL,'00232','19','Gal-2/94','Paris','1994-09-05 00:00:00','0','0','São Rafael','33','24','',NULL,'','cn 1.994 pág. 1062-1064',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('241','VD','Tertulia con el Padre','0',NULL,'00233','19','Gal-3/94','Paris','1994-09-05 00:00:00','0','0','Geral','64','24','AMOR HUMANO\r\nCERDENHA\r\nCOMER APRESSADO\r\nCORSEGA\r\nFIDELIDADE\r\nHOTEL MERIDIEN\r\nMATRIMONIO\r\nRESTAURANTE\r\nTERTULIA\r\nTORRE EYFEL\r\nVIUVO CORSEGA',NULL,'','cn 1.994 pág. 1059-1067',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('242','VD','Año Internacional da la Familia en Roma','0',NULL,'00234','19','R-10/94','Roma','1994-10-10 00:00:00','0','0','Geral','70','24','',NULL,'','ob 1.994 pág. 570-577',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('243','VD','Con el Padre en Belgica','0',NULL,'00235','19','Bel-1/94','Bruxelas','1994-10-30 00:00:00','0','0','Geral','73','24','',NULL,'','cn 1.994 pág. 1231-1243',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('244','VD','Tertulia con el Padre','0',NULL,'00236','19','N-01/94','Armsterdan','1994-11-02 00:00:00','0','0','Geral','67','24','ALUNO HISTORIA\r\nCONVERSAO\r\nFINADOS\r\nHOLANDA\r\nHOLANDES DE COR\r\nJUDEU-MACOM\r\nMAOS DO PADRE\r\nMAOS SANTAS\r\nMOINHO DE VENTO\r\nNAO-CATOLICOS\r\nORQUESTRA\r\nSEGREDO OBRA\r\nSEGREDOS\r\nSEGREDOS OD',NULL,'','cn 1.994 pág. 1244-1254',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('245','VD','Tertulia con el Padre','0',NULL,'00237','19','G-1/94','Colônia','1994-11-20 00:00:00','0','0','Geral','69','24','ALEMANHA\r\nANIVERSARIO\r\nAPROV. TEMPO\r\nCOLONIA\r\nCONFESSIONARIOS\r\nDEFEITO MICROF.\r\nDIVORCIADOS\r\nDIVORCIO\r\nELISABETH\r\nHUMILDADE\r\nMICROFONE\r\nMORTE AVO\r\nOVELHA NEGRA\r\nPAX\r\nPE FISHER\r\nPRECONCEITO\r\nVOC.SAC NPE\r\nYAUYOS',NULL,'','cn 1.994 pág. 1267-1275',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('246','VD','Tertulia con el Padre. La Lloma','0',NULL,'00238','19','H-02/95','Valencia','1995-01-31 00:00:00','0','0','São Miguel','63','24','',NULL,'','cn 1.995 pág. 157-160',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('247','VD','Con el Padre en el Vedat','0',NULL,'00239','19','H-06/95','Valencia','1995-02-02 00:00:00','0','0','Geral','69','24','BENCAO BRASIL\r\nCORO\r\nELOGIO AO PADRE\r\nS. JOSE',NULL,'','cn 1.995 pág. 156-170',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('248','VD','Tertulia con el Padre. Cavabianca','0',NULL,'00240','19','R-02/95','Roma','1995-04-12 00:00:00','0','0','São Rafael','66','24','ARMÊNIA\r\nFUTEBOL',NULL,'','ob 1.995 pág. 151-165',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('249','VD','Con el Padre en Cavabianca','0',NULL,'00241','19','R-05/95','Roma','1995-04-15 00:00:00','0','0','São Miguel','69','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('25','VD','Con el Padre en Pozoalbero','0',NULL,'00025','19','Num. 06-C','Jerez de la F.','1972-11-09 00:00:00','0','0','São Rafael','36','24','',NULL,'','2MC1 pág. 371',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('250','VD','Tertulia con el Padre. Helsinki (Finlandia)','0',NULL,'00242','19','Esep-1/95','Helsinki','1995-04-22 00:00:00','0','0','Geral','59','24','',NULL,'','cn 1.995 pág. 432-436',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('251','VD','Con el Padre en Estocolmo','0',NULL,'00243','19','Esep-2/95','Estocolmo','1995-04-25 00:00:00','0','0','Geral','66','24','',NULL,'','cn 1.995 pág. 438-443',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('252','VD','Una Tradicion de Piedad','0',NULL,'00244','19','Esep-3/95','Estocolmo','1995-04-26 00:00:00','0','0','São Miguel','45','24','',NULL,'','cn 1.995 pág. 437-438',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('253','VD','Con el Padre en Lumiar','0',NULL,'00245','19','L-02/95','Lisboa','1995-05-30 00:00:00','0','0','São Miguel','46','24','CARINHO PAPA\r\nCONSELHO LOCAL\r\nDIFICULT. DIARI\r\nDOM ÁLVARO\r\nFILHO PRODIGO',NULL,'','cn 1.995 pág. 556-558',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('254','VD','Con el Padre en Planalto','0',NULL,'00246','19','L-03/95','Lisboa','1995-05-31 00:00:00','0','0','Geral','45','24','CR. MONGOLOIDE',NULL,'','cn 1.995 pág. 554-563',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('255','VD','Con el Padre en La Estila','0',NULL,'00247','19','H-07/95','Santiago de Compostela','1995-06-02 00:00:00','0','0','São Miguel','52','24','',NULL,'','cn 1.995 pág. 566-569',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('256','VD','Con el Padre en Galicia','0',NULL,'00248','19','H-09/95','La Coruña','1995-06-03 00:00:00','0','0','Geral','69','24','',NULL,'','cn 1.995 pág. 564-576',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('257','VD','Con el Padre en Galicia','0',NULL,'00249','19','H-09/95','La Coruña','1995-06-03 00:00:00','0','0','Geral','69','24','',NULL,'','cn 1.995 pág. 564-576',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('258','VD','Tertulia con el Padre. Santiago de Compostela','0',NULL,'00250','19','H-11/95','Santiago de Compostela','1995-06-04 00:00:00','0','0','Varões, São Gabriel','62','24','',NULL,'','cn 1.995 pág. 564-576',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('259','VD','Tertulia con el Padre','0',NULL,'00251','19','H-12/95','Santiago de Compostela','1995-06-04 00:00:00','0','0','São Rafael','58','24','PROTAGONISTA',NULL,'','cn 1.995 pág. 564-576',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('26','VD','Con el Padre en Pozoalbero','0',NULL,'00026','19','Num. 25','Jerez de la F.','1972-11-11 00:00:00','0','0','Geral','38','24','EFA\r\nMISERICORDIA\r\nPARABOLAS\r\nPAROQUIA\r\nTRANSUBSTANC.\r\n1 viúvo e 3 viúvas',NULL,'','2MC1 pág. 415-425',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('260','VD','El Padre en Montreal','0',NULL,'00252','19','C-1/95','Montreal','1995-08-20 00:00:00','0','0','Geral','62','24','',NULL,'','cn 1.995 pág. 839-841 y 848-850',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('261','VD','Tertulia con el Padre en World Trade Center en Mexico','0',NULL,'00253','19','M-01/95','México DF','1995-08-28 00:00:00','0','0','Geral','76','24','',NULL,'','cn 1.995 pág. 946-951',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('262','VD','Tertulia con el Padre. IPADE','0',NULL,'00254','19','M-03/95','México DF','1995-08-30 00:00:00','0','0','São Miguel','60','24','',NULL,'','cn 1.995 pág. 951-955',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('263','VD','Tertulia en el IPADE','0',NULL,'00255','19','M-06/95','México DF','1995-09-01 00:00:00','0','0','Varões, São Gabriel','63','24','',NULL,'','cn 1.995 pág. 958-960',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('264','VD','Congresso Internacional de la Familia. Tertulia con el Padre','0',NULL,'00256','19','R-08/95','Roma','1995-11-26 00:00:00','0','0','Geral','48','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('265','VD','Tertulia con el Padre. Nairobi','0',NULL,'00257','19','K-01/95','Nairobi','1995-12-17 00:00:00','0','0','Geral','53','24','A CÉU ABERTO\r\nCABRA\r\nCACHO DE BANANA\r\nCANTORIA\r\nESPANADOR\r\nPROVERBIO',NULL,'','cn 1.996 pág. 53-59',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('266','VD','Tertulia en Tigoni y Dedicacion de Altar en Satima','0',NULL,'00258','19','K-08/95','Nairobi','1995-12-20 00:00:00','0','0','São Miguel','49','24','',NULL,'','cn 1.996 pág. 63-64',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('267','VD','Tres Dias con el Padre en Zaire','0',NULL,'00259','19','Z-01/96','Zaire','1996-02-01 00:00:00','0','0','Varões','12','24','ALEGRIA\r\nPALMAS E GRITOS\r\nmúsica\r\ndança',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('268','VD','Tertulia con el Padre. Cavabianca','0',NULL,'00260','19','R-03/96','Roma','1996-04-03 00:00:00','0','0','São Rafael','65','24','',NULL,'','ob 1.996 pág. 264-265',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('269','VD','Con el Padre en Ravenahl Study Center','0',NULL,'00261','19','Sea-02/96','Singapura','1996-04-11 00:00:00','0','0','São Rafael','37','24','',NULL,'','cn 1.996 pág. 591-593',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('27','VD','Con el Padre en Pozoalbero','0',NULL,'00027','19','Num. 25','Jerez de la F.','1972-11-11 00:00:00','1','0','Geral','41','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('270','VD','Con el Padre en Singapur','0',NULL,'00262','19','Sea-03/96','Singapura','1996-04-12 00:00:00','0','0','Geral','51','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('271','VD','Tertulia con el Padre','0',NULL,'00263','19','Sea-04/96','Singapura','1996-04-13 00:00:00','0','0','Varões, São Gabriel','37','24','',NULL,'','cn 1.996 pág. 594-595',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('272','VD','Con el Padre en Granite Study Center','0',NULL,'00264','19','Sea-06/96','Hong-Kong','1996-04-15 00:00:00','0','0','São Rafael','23','24','',NULL,'','cn 1.996 pág. 608-609',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('273','VD','Tertulia con el Padre','0',NULL,'00265','19','Sea-08/96','Hong-Kong','1996-04-16 00:00:00','0','0','Geral','65','24','CANCER\r\nCORRENTITAS\r\nENG. EM CANTAO\r\nFUSAO NUCLEAR\r\nLEI NATURAL\r\nMARGARETH\r\nMULHER MANDA\r\nMULHERE MUNDANA',NULL,'','cn 1.996 pág. 602-605',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('274','VD','Tertulia con el Padre','0',NULL,'00266','19','Sea-11/96','Macao','1996-04-17 00:00:00','0','0','Geral','52','24','',NULL,'','cn 1.996 pág. 606-609',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('275','VD','Una Tarde en Granite Study Center','0',NULL,'00267','19','Sea-10/96','Hong-Kong','1996-04-18 00:00:00','0','0','Varões, São Gabriel','30','24','',NULL,'','cn 1.996 pág. 612',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('276','VD','Tertulia con el Padre en Albayzin','0',NULL,'00268','19','H-03/96','Granada','1996-05-11 00:00:00','0','0','Varões, São Gabriel','58','24','',NULL,'','cn 1.996 pág. 684-687',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('277','VD','Con el Padre en el Colegio Mulhacen','0',NULL,'00269','19','H-04/96','Granada','1996-05-12 00:00:00','0','0','Geral','75','24','',NULL,'','cn 1.996 pág. 687-689',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('278','VD','Tertulia con el Padre','0',NULL,'00270','19','H-05/96','Granada','1996-05-12 00:00:00','0','0','São Rafael','58','24','CRACÓVIA',NULL,'','cn 1.996 pág. 682-693',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('279','VD','Con el Padre en Granada','0',NULL,'00271','19','H-06/96','Granada','1996-05-13 00:00:00','0','0','São Miguel','57','24','CRISE DOS 40\r\nGRANA\r\nMIL E DUZENTOS ALUNOS\r\nNOVECENTOS E NOVENTA E NOVE PONTOS DE  CAMINHO\r\nPAI C/ CANCER\r\nPOLITICO\r\nPROFESSOR\r\nQUARENTA ANOS\r\nSI SENOR',NULL,'','cn 1.996 pág. 691-693',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('28','VD','Con el Padre en Pozoalbero','0',NULL,'00028','19','Num. 26','Jerez de la F.','1972-11-11 00:00:00','0','0','Geral','41','24','',NULL,'','2MC1 pág. 415-425',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('280','VD','Ordenacion de Presbíteros','0',NULL,'00272','19','R-06/96','Roma','1996-06-09 00:00:00','0','0','Geral','40','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('281','VD','Tertulia con el Padre','0',NULL,'00273','19','E-2/96','Quito','1996-08-03 00:00:00','0','0','Varões, São Gabriel','55','24','',NULL,'','cn 1.996 pág. 856-858',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('282','VD','Con el Padre en Intisana','0',NULL,'00274','19','E-3/96','Quito','1996-08-04 00:00:00','0','0','Geral','69','24','',NULL,'','cn 1.996 pág. 858-861',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('283','VD','Con el Padre en Ilaloma','0',NULL,'00275','19','E-4/96','Quito','1996-08-04 00:00:00','0','0','São Rafael','53','24','',NULL,'','cn 1.996 pág. 861-863',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('284','VD','Con el Padre en Esteros','0',NULL,'00276','19','E-11/96','Guayaquil','1996-08-08 00:00:00','0','0','Grupos Promotores','53','24','',NULL,'','cn 1.996 pág. 871-873',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('285','VD','El Padre en Cañete','0',NULL,'00277','19','P-1/96','Peru','1996-08-09 00:00:00','0','0','Geral','63','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('286','VD','Tertulia con el Padre','0',NULL,'00278','19','P-2/96','Peru','1996-08-10 00:00:00','0','0','Varões, São Gabriel','61','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('287','VD','Con el Padre en Alpamayo','0',NULL,'00279','19','P-3/96','Peru','1996-08-10 00:00:00','0','0','geral','64','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('288','VD','Con el Padre en Alpamayo','0',NULL,'00280','19','P-10/96','Peru','1996-08-11 00:00:00','0','0','Geral','63','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('289','VD','Con el Padre en Alpamayo','0',NULL,'00281','19','P-11/96','Peru','1996-08-11 00:00:00','0','0','São Rafael','51','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('29','VD','Con el Padre en Pozoalbero','0',NULL,'00661','19','Num. 25','Jerez de la F.','1972-11-11 00:00:00','1','0','Geral','41','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('290','VD','Tertulia con el Padre','0',NULL,'00282','19','P-12/96','Peru','1996-08-13 00:00:00','0','0','Sociedade Sacerdotal','36','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('291','VD','Tertulia con el Padre','0',NULL,'00283','19','P-8/96','Peru','1996-08-14 00:00:00','0','0','Geral','67','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('292','VD','Con el Padre en los Andes','0',NULL,'00284','19','P-9/96','Peru','1996-08-16 00:00:00','0','0','Sociedade Sacerdotal','51','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('293','VD','Tertulia con el Padre','0',NULL,'00285','19','J-1/96','Japão','1996-12-16 00:00:00','0','0','São Miguel','48','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('294','VD','Terrulia con el Padre en Seido','0',NULL,'00286','19','J-4/96','Japão','1996-12-17 00:00:00','0','0','Varões, São Gabriel','32','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('295','VD','Tertulia con el Padre','0',NULL,'00287','19','J-6/96','Japão','1996-12-19 00:00:00','0','0','Geral','52','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('296','VD','Con el Padre en Amity Hall','0',NULL,'00288','19','J-9/96','Japão','1996-12-21 00:00:00','0','0','Geral','59','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('297','VD','Con el Padre en Cavabianca','0',NULL,'00289','19','R-02/97','Roma','1997-03-26 00:00:00','0','0','São Rafael','51','24','',NULL,'','ob 1.997 pág. 244-254',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('298','VD','Con el Padre en Cavabianca','0',NULL,'00290','19','R-04/97','Roma','1997-03-29 00:00:00','0','0','São Miguel','52','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('299','VD','Costa do Marfil (2-6/4/1997)','0',NULL,'00291','19','CEB-9/97','Costa do Marfim','1997-04-01 00:00:00','0','0','Geral','17','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('3','VD','Acto Acadêmico','0',NULL,'00003','19','Num. 08','Pamplona','1972-10-07 00:00:00','0','0','Geral','32','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('30','VD','Con el Padre en Pozoalbero','0',NULL,'00029','19','Num. 23','Jerez de la F.','1972-11-12 00:00:00','0','0','Geral','39','24','',NULL,'','2MC1 pág. 415-425',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('300','VD','Sicilia (6-9/4/1997)','0',NULL,'00292','19','I-8/96','Itália','1997-04-01 00:00:00','0','0','Geral','20','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('301','VD','Tertulia con el Padre','0',NULL,'00293','19','CEB-7/96','Costa do Marfim','1997-04-05 00:00:00','0','0','Geral','65','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('302','VD','Argentina, Bolivia, Paraguai','0',NULL,'00294','19','xx','Argentina, Bolivia, Paraguai','1997-08-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('303','VD','Argentina, Bolivia, Paraguai','0',NULL,'00295','19','xx','Argentina, Bolivia, Paraguai','1997-08-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('304','VD','El Padre en Uruguay','0',NULL,'00657','19','xx','Uruguai','1997-08-01 00:00:00','0','0','Geral','23','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('305','VD','Con el Padre en CUDES','0',NULL,'00296','19','ARG-1/97','Argentina','1997-08-03 00:00:00','0','0','São Miguel','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('306','VD','Con el Padre en Rosario','0',NULL,'00297','19','ARG-4/97','Argentina','1997-08-05 00:00:00','0','0','Geral','67','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('307','VD','Con el Padre en la Universid Austral','0',NULL,'00298','19','ARG-6/97','Argentina','1997-08-07 00:00:00','0','0','Varões, São Gabriel','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('308','VD','Con el Padre en CUDES','0',NULL,'00299','19','ARG-7/97','Argentina','1997-08-07 00:00:00','0','0','Geral','65','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('309','VD','En el Centro Costa Salquero','0',NULL,'00300','19','ARG-8/97','Argentina','1997-08-07 00:00:00','0','0','Geral','66','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('31','VD','Con el Padre en Pozoalbero','0',NULL,'00030','19','Num. 27','Jerez de la F.','1972-11-12 00:00:00','0','0','Geral','37','24','NOSSO PADRE GRIPADO',NULL,'','2MC1 pág. 415-425',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('310','VD','Tertulia con el Padre en el Centro Costa Salquero','0',NULL,'00301','19','ARG-10/97','Argentina','1997-08-08 00:00:00','0','0','Geral','69','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('311','VD','Con el Padre en le Universidad Austral','0',NULL,'00302','19','ARG-9/97','Argentina','1997-08-08 00:00:00','0','0','Grupos Promotores','65','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('312','VD','Con el Padre en el Colegio Los Molinos','0',NULL,'00303','19','ARG-12/97','Argentina','1997-08-09 00:00:00','0','0','São Rafael','61','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('313','VD','Con el Padre en Cordoba','0',NULL,'00304','19','ARG-13/97','Argentina','1997-08-10 00:00:00','0','0','Geral','59','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('314','VD','Con el Padre en Mendoza','0',NULL,'00305','19','ARG-15/97','Argentina','1997-08-12 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('315','VD','Con el Padre en la Universidad Austral','0',NULL,'00306','19','ARG-16/97','Argentina','1997-08-13 00:00:00','0','0','Varões, São Gabriel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('316','VD','Con el Padre en Rio Abajo','0',NULL,'00307','19','BOL-2/97','Bolivia','1997-08-15 00:00:00','0','0','Varões, São Gabriel','42','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('317','VD','Tertulia con el Padre','0',NULL,'00308','19','PAR-1/97','Paraguai','1997-08-17 00:00:00','0','0','Geral','65','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('318','VD','Con el Padre en Puntarrieles','0',NULL,'00309','19','PAR-3/97','Paraguai','1997-08-17 00:00:00','0','0','Varões, São Gabriel','49','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('319','VD','Con el Padre en el Colegio Los Pilares','0',NULL,'00310','19','U-1/97','Uruguai','1997-08-19 00:00:00','0','0','Geral','52','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('32','VD','Con el Padre en la Lloma','0',NULL,'00031','19','Num. 45','Valencia','1972-11-14 00:00:00','0','0','Sociedade Sacerdotal','42','24','',NULL,'','2MC2 pág. 465-475',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('320','VD','Con el Padre en Montevideo','0',NULL,'00311','19','U-3/97','Montevideo','1997-08-19 00:00:00','0','0','São Rafael','55','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('321','VD','Con el Padre en Montevideo','0',NULL,'00312','19','U-4/97','Montevideo','1997-08-20 00:00:00','0','0','Varões, São Gabriel','58','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('322','VD','Con el Padre en Montevideo','0',NULL,'00313','19','U-6/97','Montevideo','1997-08-20 00:00:00','0','0','Geral','74','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('323','VD','Con el Padre en Montevideo','0',NULL,'00315','19','U-7/97','Montevideo','1997-08-21 00:00:00','0','0','São Miguel','33','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('324','VD','Tertulia con el Padre','0',NULL,'00316','19','CH-2/97','Chile','1997-08-23 00:00:00','0','0','Geral','74','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('325','VD','Con el Padre en Alborada','0',NULL,'00317','19','CH-3/97','Chile','1997-08-23 00:00:00','0','0','Geral','55','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('326','VD','Con el Padre en el Colegio Cordillera','0',NULL,'00318','19','CH-4/97','Chile','1997-08-24 00:00:00','0','0','Varões, São Gabriel','62','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('327','VD','Tertulia con el Padre en Alborada','0',NULL,'00319','19','CH-6/97','Chile','1997-08-25 00:00:00','0','0','Sociedade Sacerdotal','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('328','VD','Con el Padre en el Colegio Cordillera','0',NULL,'00320','19','CH-7/97','Chile','1997-08-25 00:00:00','0','0','São Rafael','65','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('329','VD','Tertulia con el Padre, Colegio Albamar','0',NULL,'00321','19','CH-9/97','Chile','1997-08-27 00:00:00','0','0','Geral','68','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('33','VD','Con el Padre en la Lloma','0',NULL,'00032','19','Num. 58','Valencia','1972-11-14 00:00:00','0','0','São Miguel','47','24','DIFICIL ENTEND.\r\nGRAVATA\r\nHUELLAS\r\nINDICE\r\nLUTA ASCETICA\r\nPROBL. IGREJA\r\nSão José\r\nsomos o permanente\r\npreocupação com a Igreja','1','','2MC2 pág. 457-464',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('330','VD','El Padre en Concepción','0',NULL,'00322','19','CH-11/97','Chile','1997-08-29 00:00:00','0','0','Geral','69','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('331','VD','En el Colegio de Tabancura','0',NULL,'00314','19','CH-12/97','Chile','1997-08-30 00:00:00','0','0','Geral','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('332','VD','El Padre en las Garzas','0',NULL,'00323','19','CH-14/97','Chile','1997-08-31 00:00:00','0','0','Geral','55','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('333','VD','El Padre en Chile','0',NULL,'00324','19','xx','Chile','1997-09-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('334','VD','Con el Padre en CUDES','0',NULL,'00325','19','ARG-18/97','Argentina','1997-09-03 00:00:00','0','0','São Miguel','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('335','VD','Con el Padre en Tajamar','0',NULL,'00326','19','H-5/97','Espanha','1997-09-08 00:00:00','0','0','São Miguel','61','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('336','VD','Con el Padre en Tajamar','0',NULL,'00327','19','H-6/97','Espanha','1997-09-09 00:00:00','0','0','Varões, São Gabriel','64','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('337','VD','Con el Padre en Irabia','0',NULL,'00328','19','H-5/98','Espanha','1998-02-01 00:00:00','0','0','São Rafael','45','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('338','VD','El Padre en Bilbao y Pamplona','0',NULL,'00329','19','xx','Espanha','1998-02-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('339','VD','Con el Padre en Gaztelueta','0',NULL,'00330','19','H-8/98','Espanha','1998-02-03 00:00:00','0','0','São Miguel','62','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('34','VD','Con el Padre en Guadalaviar','0',NULL,'00033','19','Num. 59','Valencia','1972-11-17 00:00:00','0','0','Geral','45','24','FILHOS DIFERENTES\r\nIRMA LUCIA2\r\nMAUS SACERDOTES\r\nPAPA MARTIR\r\nRAPIDO\r\nRESPOSTAS CURTA\r\nVALENCIA',NULL,'','2MC2 pág. 511-520',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('340','VD','Con el Padre en Gaztelueta','0',NULL,'00331','19','H-9/98','Espanha','1998-02-03 00:00:00','0','0','Varões, São Gabriel','46','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('341','VD','Con el Padre en Camerún','0',NULL,'00332','19','CAM-1/98','Camarões','1998-02-07 00:00:00','0','0','Geral','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('342','VD','Con el Padre en Cavabianca','0',NULL,'00333','19','R-2/98','Roma','1998-04-08 00:00:00','0','0','São Rafael','55','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('343','VD','Con el Padre en Cavabianca','0',NULL,'00334','19','R-4/98','Roma','1998-04-11 00:00:00','0','0','São Miguel','61','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('344','VD','Con el Padre en Santo Domingo','0',NULL,'00335','19','DLR-2/98','R. Dominicana','1998-04-16 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('345','VD','Con el Padre en Vallenuevo','0',NULL,'00336','19','DLR-4/98','R. Dominicana','1998-04-17 00:00:00','0','0','Varões, São Gabriel','40','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('346','VD','Con el Padre en Vallenuevo','0',NULL,'00337','19','DLR-6/98','R. Dominicana','1998-04-18 00:00:00','0','0','São Rafael','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('347','VD','Con el Padre en Paloblanco','0',NULL,'00338','19','PORT-1/98','Porto Rico','1998-04-19 00:00:00','0','0','São Miguel','51','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('348','VD','Con el Padre en Puerto Rico','0',NULL,'00339','19','PORT-2/98','Porto Rico','1998-04-19 00:00:00','0','0','geral','68','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('349','VD','Con el Padre en Paloblanco','0',NULL,'00340','19','PORT-6/98','Porto Rico','1998-04-21 00:00:00','0','0','Varões, São Gabriel','67','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('35','VD','Con el Padre en Guadalaviar','0',NULL,'00658','19','Num. 59','Valencia','1972-11-17 00:00:00','1','0','Geral','45','24','FILHOS DIFERENTES\r\nIRMA LUCIA2\r\nMAUS SACERDOTES\r\nPAPA MARTIR\r\nRAPIDO\r\nRESPOSTAS CURTA\r\nVALENCIA',NULL,'','2MC2 pág. 511-520',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('350','VD','Con el Padre en Paloblanco','0',NULL,'00341','19','PORT-7/98','Porto Rico','1998-04-21 00:00:00','0','0','São Rafael','50','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('351','VD','Con el Padre en Guajiles','0',NULL,'00342','19','PORT-8/98','Porto Rico','1998-04-22 00:00:00','0','0','Sociedade Sacerdotal','65','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('352','VD','Con el Padre en Florida','0',NULL,'00343','19','EU-1/98','Estados Unidos','1998-04-24 00:00:00','0','0','Geral','69','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('353','VD','El Padre en Australia y Nueva Zelanda','0',NULL,'00344','19','xx','Australia e Nova Zelândia','1998-08-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('354','VD','El Padre en Australia y Nueva Zelanda','0',NULL,'00345','19','xx','Australia e Nova Zelândia','1998-08-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('355','VD','El Padre en Filipinas','0',NULL,'00346','19','xx','Filipinas','1998-08-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('356','VD','Con el Padre en Kenthurst','0',NULL,'00347','19','AUT-3/98','Australia e Nova Zelândia','1998-08-02 00:00:00','0','0','São Miguel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('357','VD','Con el Padre en Melbourne','0',NULL,'00348','19','AUT-5/98','Australia','1998-08-04 00:00:00','0','0','Geral','55','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('358','VD','Con el Padre en Auckland (Nueva Zelanda)','0',NULL,'00349','19','AUT-6/98','Nova Zelândia','1998-08-05 00:00:00','0','0','Geral','58','24','',NULL,'','cn 1.998 pág. 832-833',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('359','VD','Con el Padre en Kenthurst','0',NULL,'00350','19','AUT-8/98','Austrália e Nova Zelândia','1998-08-07 00:00:00','0','0','Sociedade Sacerdotal','46','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('36','VD','Con el Padre en la Lloma','0',NULL,'00034','19','Num. 06-D','Valencia','1972-11-18 00:00:00','0','0','São Rafael','34','24','',NULL,'','2MC2 pág. 448',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('360','VD','Con el Padre en Tangara','0',NULL,'00351','19','AUT-10/98','Austrália e Nova Zelândia','1998-08-08 00:00:00','0','0','Varões, São Gabriel','55','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('361','VD','Con el Padre en Sydney','0',NULL,'00352','19','AUT-11/98','Austrália e Nova Zelândia','1998-08-09 00:00:00','0','0','Geral','63','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('362','VD','Con el Padre en la Universidad de Asia y el Pacífico','0',NULL,'00353','19','PL-1/98','Filipinas','1998-08-12 00:00:00','0','0','Geral','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('363','VD','Con el Padre en Manila','0',NULL,'00354','19','PL-2/98','Filipinas','1998-08-12 00:00:00','0','0','Geral','66','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('364','VD','Con el Padre en la Universidad de Asia y el Pacífico','0',NULL,'00355','19','PL-3/98','Filipinas','1998-08-13 00:00:00','0','0','Sociedade Sacerdotal','56','24','Batina, Missa, Oração, Pe. Glorioso',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('365','VD','Con el Padre en el Teatro Aguinaldo','0',NULL,'00356','19','PL-6/98','Filipinas','1998-08-14 00:00:00','0','0','Varões, São Gabriel','66','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('366','VD','Con el Padre en PICC','0',NULL,'00357','19','PL-8/98','Filipinas','1998-08-15 00:00:00','0','0','São Rafael','64','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('367','VD','Con el Padre en Manila','0',NULL,'00358','19','PL-10/98','Filipinas','1998-08-16 00:00:00','0','0','Geral','73','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('368','VD','Con el Padre en Sugbu','0',NULL,'00359','19','PL-11/98','Filipinas','1998-08-17 00:00:00','0','0','Sociedade Sacerdotal','49','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('369','VD','Con el Padre en Cebú','0',NULL,'00360','19','PL-12/98','Filipinas','1998-08-17 00:00:00','0','0','Geral','63','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('37','VD','Con el Padre en Guadalaviar','0',NULL,'00035','19','Num. 24','Valencia','1972-11-19 00:00:00','0','0','Geral','42','24','CONCILIO\r\nIGREJA',NULL,'','2MC2 pág. 511-520',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('370','VD','Con el Padre en Ilo Ilo','0',NULL,'00361','19','PL-13/98','Filipinas','1998-08-18 00:00:00','0','0','Geral','49','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('371','VD','Con el Padre en la Universidad de Asia y el Pacífico','0',NULL,'00362','19','PL-14/98','Filipinas','1998-08-19 00:00:00','0','0','São Miguel','61','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('372','VD','El Padre en Nigeria','0',NULL,'00363','19','xx','Nigéria','1999-01-04 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('373','VD','Con el Padre en Jaltepec','0',NULL,'00364','19','M-1/99','México','1999-01-29 00:00:00','0','0','São Miguel','54','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('374','VD','Con el Padre en Jaltepec','0',NULL,'00365','19','M-2/99','México','1999-01-29 00:00:00','0','0','Sociedade Sacerdotal','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('375','VD','Con el Padre en la Calerilla','0',NULL,'00366','19','M-5/99','México','1999-01-30 00:00:00','0','0','Varões, São Gabriel','61','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('376','VD','Con el Padre en Cavabianca','0',NULL,'00367','19','R-2/99','Roma','1999-03-31 00:00:00','0','0','São Rafael','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('377','VD','Con el Padre en Cavabianca','0',NULL,'00368','19','R-4/99','Roma','1999-04-03 00:00:00','0','0','São Miguel','59','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('378','VD','Con el Padre en Banker s Hall','0',NULL,'00369','19','NIG-1/99','Nigéria','1999-04-07 00:00:00','0','0','São Rafael','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('379','VD','Con el Padre en Ibadán','0',NULL,'00370','19','NIG-4/99','Nigéria','1999-04-09 00:00:00','0','0','Geral','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('38','VD','Con el Padre en la Lloma','0',NULL,'00036','19','Num. 38','Valencia','1972-11-19 00:00:00','0','0','Geral','51','24','CASAIS DE S\r\nNPE A VONTADE\r\nNPE FALA RAPIDO\r\nS ANTIGOS',NULL,'','2MC2 pág. 476-485',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('380','VD','Con el Padre en Enugu','0',NULL,'00371','19','NIG-5/99','Nigéria','1999-04-10 00:00:00','0','0','Geral','51','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('381','VD','Con el Padre en LBS','0',NULL,'00372','19','NIG-6/99','Nigéria','1999-04-11 00:00:00','0','0','Geral','46','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('382','VD','Con el Padre en Lagos','0',NULL,'00373','19','NIG-7/99','Nigéria','1999-04-11 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('383','VD','Con el Padre en LBS','0',NULL,'00374','19','NIG-8/99','Nigéria','1999-04-12 00:00:00','0','0','Varões, São Gabriel','38','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('384','VD','Con el Padre en LBS','0',NULL,'00375','19','NIG-9/99','Nigéria','1999-04-12 00:00:00','0','0','Geral','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('385','VD','Tertulia con el Padre','0',NULL,'00376','19','H-2/99','Espanha','1999-07-04 00:00:00','0','0','Geral','49','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('386','VD','Con el Padre en La Estila','0',NULL,'00377','19','H-4/99','Espanha','1999-08-31 00:00:00','0','0','Varões','55','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('387','VD','Con el Padre en Torreciudad','0',NULL,'00378','19','H-5/99','Espanha','1999-09-04 00:00:00','0','0','Geral','43','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('388','VD','Con el Padre en Tajamar','0',NULL,'00379','19','H-7/99','Espanha','1999-09-07 00:00:00','0','0','São Miguel','65','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('389','VD','El Padre en Costa Rica, Nicaragua y Panamá','0',NULL,'00007','19','xx','Costa Rica, Nicaragua y Panamá','2000-01-01 00:00:00','0','0','Geral','26','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('39','VD','Con el Padre en Viaro','0',NULL,'00037','19','Num. 21','Barcelona','1972-11-21 00:00:00','0','0','Geral','30','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('390','VD','Con el Padre en Miravalles','0',NULL,'00380','19','ACME-2/00','Costa Rica','2000-01-12 00:00:00','0','0','Varões, São Gabriel','51','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('391','VD','Con el Padre en Miravalles','0',NULL,'00381','19','ACME-4/00','Costa Rica','2000-01-13 00:00:00','0','0','São Miguel','45','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('392','VD','Con el Padre en Managua','0',NULL,'00382','19','ACME-5/00','Costa Rica','2000-01-14 00:00:00','0','0','Geral','66','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('393','VD','Con el Padre en Panamá','0',NULL,'00383','19','ACME-6/00','Panamá','2000-01-16 00:00:00','0','0','Geral','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('394','VD','Con el Padre en Miravalles','0',NULL,'00384','19','ACME-8/00','Costa Rica','2000-01-18 00:00:00','0','0','Geral','66','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('395','VD','Con el Padre en Miravalles','0',NULL,'00385','19','ACME-9/00','Costa Rica','2000-01-19 00:00:00','0','0','São Rafael','44','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('396','VD','Con el Padre en Balanya','0',NULL,'00386','19','ACSE-3/00','Guatemala','2000-01-20 00:00:00','0','0','São Miguel','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('397','VD','Con el Padre en Balanya','0',NULL,'00387','19','ACSE-6/00','Guatemala','2000-01-21 00:00:00','0','0','Sociedade Sacerdotal','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('398','VD','Con el Padre en Guatemala','0',NULL,'00388','19','ACSE-7/00','Guatemala','2000-01-21 00:00:00','0','0','Varões, São Gabriel','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('399','VD','Con el Padre en el Colegio El Roble','0',NULL,'00389','19','ACSE-8/00','Guatemala','2000-01-22 00:00:00','0','0','geral','64','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('4','VD','Con el Padre en Irábia','0',NULL,'00004','19','Num. 22','Pamplona','1972-10-07 00:00:00','0','0','Geral','29','24','',NULL,'','2MC1 pág. 30-38',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('40','VD','Con el Padre en Viaro','0',NULL,'00038','19','Num. 21','Barcelona','1972-11-21 00:00:00','1','0','Geral','30','24','pergunta sobre a humildade; colégio viaró; pais, professores, preceptores; virtude principal de um professor; virtude principal de um aluno',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('400','VD','Con el Padre en Kinal','0',NULL,'00390','19','ACSE-11/00','Guatemala','2000-01-23 00:00:00','0','0','geral','54','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('401','VD','Con el Padre en la Universidad del ISTMO','0',NULL,'00391','19','ACSE-12/00','Guatemala','2000-01-23 00:00:00','0','0','São Rafael','44','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('402','VD','Con el Padre en la Lomita','0',NULL,'00392','19','SALV-3/00','El Salvador','2000-01-24 00:00:00','0','0','São Rafael','48','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('403','VD','Con el Padre en la Lomita','0',NULL,'00393','19','SALV-2/00','El Salvador','2000-01-24 00:00:00','0','0','Varões, São Gabriel','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('404','VD','Con el Padre en Lamatepec','0',NULL,'00394','19','SALV-4/00','El Salvador','2000-01-24 00:00:00','0','0','Geral','61','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('405','VD','Con el Padre en la Lomita','0',NULL,'00395','19','SALV-7/00','El Salvador','2000-01-25 00:00:00','0','0','São Miguel','32','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('406','VD','Con el Padre en Montecillos','0',NULL,'00396','19','HON-1/00','Honduras','2000-01-25 00:00:00','0','0','São Miguel','28','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('407','VD','Con el Padre en Antares','0',NULL,'00397','19','HON-5/00','Honduras','2000-01-26 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('408','VD','Con el Padre en Montecillos','0',NULL,'00398','19','HON-6/00','Honduras','2000-01-27 00:00:00','0','0','Varões, São Gabriel','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('409','VD','Con el Padre en Montecillos','0',NULL,'00399','19','HON-7/00','Honduras','2000-01-27 00:00:00','0','0','São Rafael','42','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('41','VD','Con el Padre en Brafa','0',NULL,'00039','19','Num. 10','Barcelona','1972-11-22 00:00:00','0','0','Geral','48','24','COMO SE QUEREM\r\nPARAFUSOS\r\nVOCACAO',NULL,'','2MC2 pág. 535-560',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('410','VD','Tertulia con el Padre','0',NULL,'00400','19','R-2/00','Roma','2000-03-29 00:00:00','0','0','Geral','34','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('411','VD','Con el Padre en Cavabianca','0',NULL,'00401','19','R-4/00','Roma','2000-04-19 00:00:00','0','0','São Rafael','50','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('412','VD','Con el Padre en Cavabianca','0',NULL,'00402','19','R-6/00','Roma','2000-04-22 00:00:00','0','0','São Miguel','54','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('413','VD','Con el Padre en Garnelles','0',NULL,'00403','19','GAL-1/00','Paris','2000-04-24 00:00:00','0','0','Varões, São Gabriel','54','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('414','VD','Con el Padre en Garnelles','0',NULL,'00404','19','GAL-4/00','Paris','2000-04-25 00:00:00','0','0','São Miguel','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('415','VD','Con el Padre en el Líbano','0',NULL,'00405','19','LI-1/00','Líbano','2000-04-29 00:00:00','0','0','Geral','55','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('416','VD','Con el Padre en Lisboa','0',NULL,'00406','19','L-1/00','Portugal','2000-05-14 00:00:00','0','0','Varões','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('417','VD','Con el Padre en Pamplona','0',NULL,'00407','19','H-06/00','Espanha','2000-07-02 00:00:00','0','0','Varões, São Gabriel','42','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('418','VD','Tertulia con el Padre','0',NULL,'00408','19','H-1/00','Espanha','2000-07-07 00:00:00','0','0','Geral','42','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('419','VD','Tertulia con el Padre','0',NULL,'00409','19','H-2/00','Espanha','2000-07-08 00:00:00','0','0','São Rafael','33','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('42','VD','Con el Padre en Castelldaura','0',NULL,'00040','19','Num. 46','Barcelona','1972-11-22 00:00:00','0','0','Sociedade Sacerdotal','39','24','','3','','2MC2 pág. 560-561',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('420','VD','Con el Padre en el CUDES','0',NULL,'00410','19','ARG-5/00','Argentina','2000-08-02 00:00:00','0','0','São Miguel','49','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('421','VD','Con el Padre en la Universidad Austral','0',NULL,'00411','19','ARG-3/00','Argentina','2000-08-30 00:00:00','0','0','Varões, São Gabriel','61','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('422','VD','Con el Padre en el CUDES','0',NULL,'00412','19','ARG-4/00','Argentina','2000-08-31 00:00:00','0','0','São Miguel','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('423','VD','El Padre con las familias de los ordenandos','0',NULL,'00413','19','R-8/00','Roma','2000-09-10 00:00:00','0','0','Geral','39','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('424','VD','Jubileo de las familias. Con el Padre en Cavabianca','0',NULL,'00414','19','R-9/00','Roma','2000-10-14 00:00:00','0','0','Geral','48','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('425','VD','Venezuela','0',NULL,'00415','19','xx','Venezuela e Trinidad-Tobago','2001-01-08 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('426','VD','Con el Padre en el Ipade','0',NULL,'00416','19','M-1/01','México','2001-04-03 00:00:00','0','0','São Miguel, São Gabriel','54','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('427','VD','Con el Padre en Cavabianca','0',NULL,'00417','19','R-02/01','Roma','2001-04-11 00:00:00','0','0','São Rafael','52','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('428','VD','Con el Padre en Cavabianca','0',NULL,'00418','19','R-4/01','Roma','2001-04-14 00:00:00','0','0','São Miguel','52','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('429','VD','Tertulia con el Padrea','0',NULL,'00419','19','L-1/01','Portugal','2001-07-21 00:00:00','0','0','Geral','63','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('43','VD','Con el Padre en Brafa','0',NULL,'00041','19','Num. 03','Barcelona','1972-11-23 00:00:00','0','0','Geral','50','24','CHIQUI\r\nDOM JMH GARNICA',NULL,'','2MC2 pág. 535-560',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('430','VD','Con el Padre en Adarve','0',NULL,'00420','19','COL-2/01','Colômbia','2001-07-28 00:00:00','0','0','São Miguel','33','24','Não mais velhos, fidelidade, a Obra em nossas mãos, vida em família',NULL,'parcialmente com falhas','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('431','VD','Con el Padre en Cañaverales','0',NULL,'00421','19','COL-3/01','Colômbia','2001-07-30 00:00:00','0','0','Varões, São Gabriel','36','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('432','VD','Con el Padre en Cali','0',NULL,'00422','19','COL-4/01','Colômbia','2001-07-30 00:00:00','0','0','Geral','62','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('433','VD','Con el Padre en Medellín','0',NULL,'00423','19','COL-8/01','Colômbia','2001-08-01 00:00:00','0','0','Geral','64','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('434','VD','Con el Padre en el Gimnasio Los Cerros','0',NULL,'00424','19','COL-12/01','Colômbia','2001-08-02 00:00:00','0','0','São Rafael','42','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('435','VD','Universidad de la Sabama','0',NULL,'00425','19','COL-13/01','Colômbia','2001-08-03 00:00:00','0','0','Geral','25','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('436','VD','Con el Padre en los Cerros','0',NULL,'00426','19','COL-16/01','Colômbia','2001-08-03 00:00:00','0','0','Varões, São Gabriel','49','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('437','VD','Con el Padre en los Cerros','0',NULL,'00427','19','COL-17/01','Colômbia','2001-08-04 00:00:00','0','0','São Miguel','42','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('438','VD','Con el Padre en la Universidad de la Sabana','0',NULL,'00428','19','COL-18/01','Colômbia','2001-08-04 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('439','VD','Con el Padre en Maracaibo','0',NULL,'00429','19','V-2/01','Venezuela e Trinidad-Tobago','2001-08-06 00:00:00','0','0','Geral','61','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('44','VD','Con el Padre en Moncloa','0',NULL,'00042','19','Num. 04-C','Madrid','1972-11-24 00:00:00','0','0','São Rafael','41','24','',NULL,'','2MC1 pág. 128-220',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('440','VD','Con el Padre en Araya','0',NULL,'00430','19','V-3/01','Venezuela e Trinidad-Tobago','2001-08-07 00:00:00','0','0','Sociedade Sacerdotal','49','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('441','VD','Con el Padre en la Residencia Univ. Monteávila','0',NULL,'00431','19','V-7/01','Venezuela e Trinidad-Tobago','2001-08-09 00:00:00','0','0','São Rafael','46','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('442','VD','Con el Padre en Los Arcos','0',NULL,'00432','19','V-09/01','Venezuela e Trinidad-Tobago','2001-08-10 00:00:00','0','0','Varões, São Gabriel','54','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('443','VD','Con el Padre en el Liceo Los Arcos','0',NULL,'00433','19','V-11/01','Venezuela e Trinidad-Tobago','2001-08-11 00:00:00','0','0','Geral','67','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('444','VD','Con el Padre en Araya','0',NULL,'00434','19','V-12/01','Venezuela e Trinidad-Tobago','2001-08-11 00:00:00','0','0','São Miguel','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('445','VD','Con el Padre en Torreciudad','0',NULL,'00435','19','H-05/01','Espanha','2001-09-01 00:00:00','0','0','São Rafael','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('446','VD','Con el Padre en Torreciudad','0',NULL,'00436','19','H-08/01','Espanha','2001-09-02 00:00:00','0','0','Geral','64','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('447','VD','El Padre con las familias de los ordenandos','0',NULL,'00437','19','R-7/01','Roma','2001-10-07 00:00:00','0','0','Geral','44','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('448','VD','Con el Padre en Torreciudad','0',NULL,'00438','19','H-9/01','Espanha','2001-12-08 00:00:00','0','0','São Rafael','50','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('449','VD','Tertulia con el Padre','0',NULL,'00439','19','R-01/02','Roma','2002-01-09 00:00:00','0','0','São Miguel','36','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('45','VD','Con el Padre en Bell-Lloc','0',NULL,'00043','19','Num. 19','Gerona','1972-11-24 00:00:00','0','0','Geral','45','24','AMERICANO WASHI\r\nAMIGO AUSTRIACO\r\nBELL-LOC\r\nEDUCAÇÃO DOS FILHOS\r\nEDUCAR FILHOS\r\nFAMIL. NUMEROSA\r\nLIBERDADE\r\nLIBERDD CONDIC.\r\nOBEDIENCIA\r\nSITUACAO IGREJA\r\nVIENENSE',NULL,'','2MC2 pág. 565-572',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('450','VD','Tertulia con el Padre. Auditorio Santa Cecilia','0',NULL,'00440','19','R-02/02','Roma','2002-01-12 00:00:00','0','0','Geral','58','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('451','VD','Con el Padre en Tabladilla','0',NULL,'00441','19','H-1/02','Espanha','2002-04-06 00:00:00','0','0','Geral','63','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('452','VD','Con el Padre en Sevilla','0',NULL,'00442','19','H-2/02','Espanha','2002-04-06 00:00:00','0','0','Geral','62','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('453','VD','Con el Padre en Sevilla','0',NULL,'00443','19','H-3/02','Espanha','2002-04-06 00:00:00','0','0','Geral','63','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('454','VD','Con el Padre en Altair (Sevilla)','0',NULL,'00444','19','H-4/02','Espanha','2002-04-07 00:00:00','0','0','São Miguel','59','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('455','VD','Con el Padre en Torreciudad','0',NULL,'00445','19','H-7/02','Espanha','2002-08-30 00:00:00','0','0','São Rafael','26','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('456','VD','Con el Padre en Torreciudad','0',NULL,'00446','19','H-11/02','Espanha','2002-09-01 00:00:00','0','0','Geral','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('457','VD','Con el Padre en los días de la Canonización (Voluntários)','0',NULL,'00447','19','R-08/02','Roma','2002-10-04 00:00:00','0','0','São Rafael','32','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('458','VD','Con el Padre en el Colegio Mulhacén (Granada)','0',NULL,'00448','19','H-12/02','Espanha','2002-11-16 00:00:00','0','0','Varões, São Gabriel','55','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('459','VD','Con el Padre en el Colegio Mulhacén (Granada)','0',NULL,'00449','19','H-13/02','Espanha','2002-11-16 00:00:00','0','0','São Miguel','49','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('46','VD','Una tarde con el Padre (Brafa)','0',NULL,'00044','19','Num. 07','Barcelona','1972-11-25 00:00:00','0','0','São Rafael','52','24','CONTRARIEDADE\r\nDEVOÇÃO À VIRGEM\r\nESTUDO\r\nEUCARISTIA\r\nGAROTO 14 ANOS\r\nGUARDA DA VISTA\r\nJOGRAL\r\nMOLEZA\r\nMORTIF. ESTUDO\r\nOLHAR E VER\r\nOLIMPIADA\r\nPERSEVERANCA\r\nPUREZA\r\nSACRIFICIO\r\nSALTO COM VARA\r\nSITUAÇÃO DOS SACERDOTES\r\nTIA CARMEM\r\nTIA CAROLINA\r\nVIRTUDES',NULL,'','2MC2 pág. 530-561',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('460','VD','Mons. Javier Echevarría en Pamplona y Logroño','0',NULL,'00450','19','PL-1/03','Espanha','2003-01-16 00:00:00','0','0','Geral','25','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('461','VD','Con el Padre en la Universidad de Navarra','0',NULL,'00451','19','H-03/03','Pamplona','2003-01-18 00:00:00','0','0','Geral','51','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('462','VD','Con el Padre en Cavabianca','0',NULL,'00452','19','R-02/03','Roma','2003-04-16 00:00:00','0','0','São Rafael','46','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('463','VD','Con el Padre en Cavabianca','0',NULL,'00453','19','R-04/03','Roma','2003-04-19 00:00:00','0','0','São Miguel','52','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('464','VD','Con el Padre en Johannesburgo','0',NULL,'00454','19','Afm-1/03','Johannesburgo','2003-05-10 00:00:00','0','0','Geral','52','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('465','VD','Tertulia con las Familias de los Ordenandos','0',NULL,'00455','19','R-06/03','Roma','2003-06-01 00:00:00','0','0','Geral','36','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('466','VD','Con el Padre en Oporto','0',NULL,'00456','19','L-01/03','Portugal','2003-07-26 00:00:00','0','0','Geral','64','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('467','VD','Con el Padre en Torreciudad','0',NULL,'00457','19','H-05/03','El Grado(Huesca)','2003-08-30 00:00:00','0','0','São Rafael','46','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('468','VD','Tertúlia com el Padre. Torreciudad','0',NULL,'00458','19','H-08/03','El Grado(Huesca)','2003-08-31 00:00:00','0','0','Geral','37','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('469','VD','Con el Padre en Montevideo','0',NULL,'00663','19','U-3/03','Montevideo','2003-09-17 00:00:00','0','0','Varões','44','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('47','VD','Una tarde con el Padre (Brafa)','0',NULL,'00644','19','Num. 07','Barcelona','1972-11-25 00:00:00','1','0','São Rafael','52','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('470','VD','Con el Padre en la Universidad de los Andes','0',NULL,'00459','19','Ch-1/03','Chile','2003-09-25 00:00:00','0','0','Varões','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('471','VD','Con el Padre en la Universidad Austral','0',NULL,'00460','19','Arg-3/03','Buenos Aires','2003-09-30 00:00:00','0','0','Varões','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('472','VD','Con el Padre en Scheidt','0',NULL,'00461','19','N-02/78','Holanda','2003-11-05 00:00:00','0','0','Varões','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('473','VD','Con el Padre en UCANCA','0',NULL,'00199','19','H-1/04','Sta. Cruz de Tenerife','2004-02-05 00:00:00','0','0','Varões, São Gabriel','40','24','DVD',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('474','VD','Con el Padre en Tenerife','0',NULL,'00462','19','H-3/04','Sta. Cruz de Tenerife','2004-02-06 00:00:00','0','0','Geral','60','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('475','VD','Con el Padre en AIRAGA','0',NULL,'00463','19','H-4/04','Las Palmas de Gran Canaria','2004-02-07 00:00:00','0','0','Varões, São Gabriel','44','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('476','VD','Con el Padre en Guaydil','0',NULL,'00464','19','H-6/04','Las Palmas de Gran Canaria','2004-02-08 00:00:00','0','0','Geral','53','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('477','VD','Con el Padre en VILNIUS','0',NULL,'00656','19','BAL-1/04','VILNIUS','2004-07-27 00:00:00','0','0','Geral','48','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('478','VD','Con el Padre en Tallin','0',NULL,'00465','19','BAL-3/04','Tallin','2004-07-30 00:00:00','0','0','Geral','46','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('479','VD','Com el Padre em Oporto','0',NULL,'00466','19','L-2/04','Portugal','2004-08-21 00:00:00','0','0','Varões','58','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('48','VD','Con el Padre em Brafa','0',NULL,'00045','19','Num. 02','Barcelona','1972-11-26 00:00:00','0','0','Geral','60','24','PRIMEIRA AUXILIAR AUSTRÍACA\r\nCIGANO ANDALUZ\r\nCIRURGIAO\r\nCRUZES\r\nFARMACÊUTICA\r\nCONFUSÃO GALES/ESCÓCIA\r\nFILHA CEGA\r\nJUAN ANTONIO CREMADES\r\nMUITOS FILHOS\r\nTORRECIUDAD\r\nVENEZUELANA',NULL,'','2MC2 pág. 535-560',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('480','VD','Con el Padre en Zagreb','0',NULL,'00664','19','Cro-1/04','Croacia','2004-08-30 00:00:00','0','0','Geral','60','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('481','VD','Tertulia con el Padre','0',NULL,'00467','19','H-10/04','Espanha','2004-09-04 00:00:00','0','0','Geral','41','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('482','VD','Con el Padre en Viaró','0',NULL,'00468','19','H-15/04','Espanha','2004-09-18 00:00:00','0','0','Geral','58','25','mulçumana russa; quis fazer o bem, mas vocês não deixaram; artista; sentido da dor',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('483','VD','Con el Padre en Brafa','0',NULL,'00469','19','H-16/04','Espanha','2004-09-19 00:00:00','0','0','São Miguel, São Gabriel','53','25','perguntas sobre \"planos legais\" de um adjunto, sobre a pobreza de um professor do IESE',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('484','VD','Con el Padre en Cavabianca','0',NULL,'00470','19','R-4/04','Roma','2004-10-04 00:00:00','0','0','São Miguel','51','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('485','VD','Con el Padre en Praga','0',NULL,'00665','19','A-1/05','Praga','2005-02-05 00:00:00','0','0','Geral','47','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('486','VD','Con el Padre en Budapest','0',NULL,'00471','19','A-2/05','Hungria','2005-02-06 00:00:00','0','0','Geral','50','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('487','VD','Con el Padre en Munich ','0',NULL,'00666','19','G-1/05','Munich','2005-02-25 00:00:00','0','0','Geral','56','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('488','VD','Con el Padre en El Rincón','0',NULL,'00667','19','H-4/05','Tordesillas','2005-03-03 00:00:00','0','0','Sociedade Sacerdotal','48','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('489','VD','Con el Padre en Peñalba','0',NULL,'00668','19','H-7/05','Valladolid','2005-03-03 00:00:00','0','0','Varões, São Gabriel','43','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('49','VD','Con el Padre em Brafa','0',NULL,'00046','19','Num. 02','Barcelona','1972-11-26 00:00:00','1','0','Geral','60','24','PRIMEIRA AUXILIAR AUSTRÍACA\r\nCIGANO ANDALUZ\r\nCIRURGIAO\r\nCRUZES\r\nFARMACÊUTICA\r\nCONFUSÃO GALES/ESCÓCIA\r\nFILHA CEGA\r\nJUAN ANTONIO CREMADES\r\nMUITOS FILHOS\r\nTORRECIUDAD\r\nVENEZUELANA',NULL,'','2MC2 pág. 535-560',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('490','VD','Con el Padre en Cavabianca','0',NULL,'00669','19','R-4/05','Roma','2005-03-26 00:00:00','0','0','Varões, São Miguel','49','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('491','VD','Con las familias de los Ordenandos','0',NULL,'00670','19','R-6/05','Roma','2005-05-22 00:00:00','0','0','Geral','52','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('492','VD','Con el Padre en Varsovia','0',NULL,'00660','19','Pol-6/05','Varsovia','2005-08-28 00:00:00','0','0','Geral','67','25','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('493','VD','Com o Padre em São Paulo','0',NULL,'00472','20','BR-02/74','São Paulo','1974-05-23 00:00:00','0','0','São Miguel','40','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('494','VD','Com o Padre em São Paulo','0',NULL,'00473','20','BR-02/74','São Paulo','1974-05-23 00:00:00','0','0','São Miguel','40','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('495','VD','Com o Padre em São Paulo','0',NULL,'00474','20','BR-02/74','São Paulo','1974-05-23 00:00:00','0','0','São Miguel','40','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('496','VD','Com o Padre em São Paulo','0',NULL,'00475','20','BR-02/74','São Paulo','1974-05-23 00:00:00','0','0','São Miguel','40','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('497','VD','Com o Padre em São Paulo','0',NULL,'00476','20','BR-02/74','São Paulo','1974-05-23 00:00:00','0','0','São Miguel','40','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('498','VD','Com o Padre no Auditório do CE','0',NULL,'00477','20','BR-07/74','São Paulo','1974-05-25 00:00:00','0','0','Varões, São Gabriel','62','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('499','VD','Com o Padre no Auditório do CE','0',NULL,'00478','20','BR-07/74','São Paulo','1974-05-25 00:00:00','0','0','Varões, São Gabriel','62','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('5','VD','Con el Padre en Irábia','0',NULL,'00654','19','Num. 22','Pamplona','1972-10-07 00:00:00','1','0','Geral','29','24','',NULL,'','2MC1 pág. 30-38',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('50','VD','Con el Padre en el IESE','0',NULL,'00047','19','Num. 12','Barcelona','1972-11-27 00:00:00','0','0','Geral','46','24','',NULL,'','2MC2 pág. 604-616',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('500','VD','Com o Padre no Auditório do CE','0',NULL,'00479','20','BR-07/74','São Paulo','1974-05-25 00:00:00','0','0','Varões, São Gabriel','62','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('501','VD','Com o Padre no Auditório do CE','0',NULL,'00480','20','BR-07/74','São Paulo','1974-05-25 00:00:00','0','0','Varões, São Gabriel','62','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('502','VD','Com o Padre no Auditório do CE','0',NULL,'00481','20','BR-07/74','São Paulo','1974-05-25 00:00:00','0','0','Varões, São Gabriel','62','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('503','VD','O Padre com estudantes','0',NULL,'00482','20','BR-09/74','São Paulo','1974-05-26 00:00:00','0','0','São Rafael','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('504','VD','O Padre com estudantes','0',NULL,'00483','20','BR-09/74','São Paulo','1974-05-26 00:00:00','0','0','São Rafael','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('505','VD','O Padre com estudantes','0',NULL,'00484','20','BR-09/74','São Paulo','1974-05-26 00:00:00','0','0','São Rafael','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('506','VD','O Padre com estudantes','0',NULL,'00485','20','BR-09/74','São Paulo','1974-05-26 00:00:00','0','0','São Rafael','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('507','VD','Com o Padre no Auditório do CE','0',NULL,'00487','20','BR-10/74','São Paulo','1974-05-27 00:00:00','0','0','Varões, São Gabriel','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('508','VD','Com o Padre no Auditório do CE','0',NULL,'00488','20','BR-10/74','São Paulo','1974-05-27 00:00:00','0','0','Varões, São Gabriel','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('509','VD','Com o Padre no Auditório do CE','0',NULL,'00489','20','BR-10/74','São Paulo','1974-05-27 00:00:00','0','0','Varões, São Gabriel','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('51','VD','Con el Padre en el IESE','0',NULL,'00655','19','Num. 12','Barcelona','1972-11-27 00:00:00','1','0','Geral','46','24','',NULL,'','2MC2 pág. 604-616',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('510','VD','Com o Padre no Auditório do CE','0',NULL,'00490','20','BR-10/74','São Paulo','1974-05-27 00:00:00','0','0','Varões, São Gabriel','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('511','VD','Com o Padre no Auditório do CE','0',NULL,'00491','20','BR-10/74','São Paulo','1974-05-27 00:00:00','0','0','Varões, São Gabriel','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('512','VD','Com o Padre no Auditório do CE','0',NULL,'00492','20','BR-10/74','São Paulo','1974-05-27 00:00:00','0','0','Varões, São Gabriel','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('513','VD','Com o Padre em São Paulo','0',NULL,'00493','20','BR-12/74','São Paulo','1974-05-30 00:00:00','0','0','Varões, São Gabriel','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('514','VD','Com o Padre em São Paulo','0',NULL,'00494','20','BR-12/74','São Paulo','1974-05-30 00:00:00','0','0','São Rafael','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('515','VD','Com o Padre em São Paulo','0',NULL,'00495','20','BR-12/74','São Paulo','1974-05-30 00:00:00','0','0','São Rafael','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('516','VD','Com o Padre em São Paulo','0',NULL,'00496','20','BR-12/74','São Paulo','1974-05-30 00:00:00','0','0','São Rafael','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('517','VD','Com o Padre em São Paulo','0',NULL,'00651','20','BR-12/74','São Paulo','1974-05-30 00:00:00','0','0','São Rafael','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('518','VD','Com o Padre no Sesi','0',NULL,'00497','20','BR-15/74','São Paulo','1974-06-02 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('519','VD','Com o Padre no Sesi','0',NULL,'00498','20','BR-15/74','São Paulo','1974-06-02 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('52','VD','Con el Padre en Castelldaura','0',NULL,'00048','19','Num. 39','Barcelona','1972-11-28 00:00:00','0','0','Geral','56','24','','2','','2MC2 pág. 617-627',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('520','VD','Com o Padre no Sesi','0',NULL,'00499','20','BR-15/74','São Paulo','1974-06-02 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('521','VD','Com o Padre no Sesi','0',NULL,'00500','20','BR-15/74','São Paulo','1974-06-02 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('522','VD','Centro de Extensão Universitária','0',NULL,'00501','20','BR-01/96','São Paulo','1996-08-18 00:00:00','0','0','Geral','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('523','VD','Centro de Extensão Universitária','0',NULL,'00502','20','BR-01/96','São Paulo','1996-08-18 00:00:00','0','0','Geral','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('524','VD','Centro de Extensão Universitária','0',NULL,'00503','20','BR-01/96','São Paulo','1996-08-18 00:00:00','0','0','Geral','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('525','VD','Centro de Extensão Universitária','0',NULL,'00504','20','BR-01/96','São Paulo','1996-08-18 00:00:00','0','0','Geral','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('526','VD','Centro de Extensão Universitária','0',NULL,'00505','20','BR-01/96','São Paulo','1996-08-18 00:00:00','0','0','Geral','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('527','VD','Centro de Extensão Universitária','0',NULL,'00506','20','BR-02/96','São Paulo','1996-08-19 00:00:00','0','0','Varões, São Gabriel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('528','VD','Centro de Extensão Universitária','0',NULL,'00507','20','BR-02/96','São Paulo','1996-08-19 00:00:00','0','0','Varões, São Gabriel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('529','VD','Centro de Extensão Universitária','0',NULL,'00508','20','BR-02/96','São Paulo','1996-08-19 00:00:00','0','0','Varões, São Gabriel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('53','VD','Con el Padre en Aralar','0',NULL,'00050','19','Num. 60-61','Pamplona','1974-05-07 00:00:00','0','0','São Miguel','24','24','luta e covardia\r\nalemães\r\nUniversidade de Navarra\r\nSacredotes/serviço\r\nA tertúlia termina no meio','1','','cn 1.974 pág. 1071-1075',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('530','VD','Centro de Extensão Universitária','0',NULL,'00509','20','BR-02/96','São Paulo','1996-08-19 00:00:00','0','0','Varões, São Gabriel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('531','VD','Centro de Extensão Universitária','0',NULL,'00510','20','BR-02/96','São Paulo','1996-08-19 00:00:00','0','0','Varões, São Gabriel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('532','VD','Centro de Extensão Universitária','0',NULL,'00511','20','BR-02/96','São Paulo','1996-08-19 00:00:00','0','0','Varões, São Gabriel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('533','VD','Centro Universitário da Tijuca','0',NULL,'00512','20','BR-03/96','Rio de Janeiro','1996-08-21 00:00:00','0','0','São Miguel','41','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('534','VD','Centro Universitário da Tijuca','0',NULL,'00513','20','BR-03/96','Rio de Janeiro','1996-08-21 00:00:00','0','0','São Miguel','41','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('535','VD','Centro Universitário da Tijuca','0',NULL,'00514','20','BR-03/96','Rio de Janeiro','1996-08-21 00:00:00','0','0','São Miguel','41','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('536','VD','Centro Universitário da Tijuca','0',NULL,'00515','20','BR-03/96','Rio de Janeiro','1996-08-21 00:00:00','0','0','São Miguel','41','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('537','VD','Centro Universitário da Tijuca','0',NULL,'00516','20','BR-03/96','Rio de Janeiro','1996-08-21 00:00:00','0','0','São Miguel','41','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('538','VD','Centro Universitário da Tijuca','0',NULL,'00517','20','BR-03/96','Rio de Janeiro','1996-08-21 00:00:00','0','0','São Miguel','41','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('539','VD','Clube Ginástico Português','0',NULL,'00518','20','BR-04/96','Rio de Janeiro','1996-08-21 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('54','VD','Con el Padre en Belagua','0',NULL,'00051','19','Num. 63','Pamplona','1974-05-10 00:00:00','0','0','Geral','37','24','CALOR\r\nDOR TESOURO\r\nENFERMEIRA\r\nENFERMEIRAS\r\nINDECENCIA\r\nLEJEUNE\r\nUNIVERSIDADE',NULL,'','cn 1.974 pág. 1091-1096',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('540','VD','Clube Ginástico Português','0',NULL,'00519','20','BR-04/96','Rio de Janeiro','1996-08-21 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('541','VD','Clube Ginástico Português','0',NULL,'00520','20','BR-04/96','Rio de Janeiro','1996-08-21 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('542','VD','Clube Ginástico Português','0',NULL,'00521','20','BR-04/96','Rio de Janeiro','1996-08-21 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('543','VD','Clube Ginástico Português','0',NULL,'00522','20','BR-04/96','Rio de Janeiro','1996-08-21 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('544','VD','Clube Ginástico Português','0',NULL,'00523','20','BR-04/96','Rio de Janeiro','1996-08-21 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('545','VD','Centro Educacional da Pedreira','0',NULL,'00524','20','BR-05/96','São Paulo','1996-08-22 00:00:00','0','0','São Rafael','38','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('546','VD','Centro Educacional da Pedreira','0',NULL,'00525','20','BR-05/96','São Paulo','1996-08-22 00:00:00','0','0','São Rafael','38','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('547','VD','Centro Educacional da Pedreira','0',NULL,'00526','20','BR-05/96','São Paulo','1996-08-22 00:00:00','0','0','São Rafael','38','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('548','VD','Centro Educacional da Pedreira','0',NULL,'00527','20','BR-05/96','São Paulo','1996-08-22 00:00:00','0','0','São Rafael','38','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('549','VD','Centro Educacional da Pedreira','0',NULL,'00528','20','BR-05/96','São Paulo','1996-08-22 00:00:00','0','0','São Rafael','38','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('55','VD','Com o Padre no Anhembi','0',NULL,'00049','19','A-030','Brasil','1974-06-01 00:00:00','0','0','Geral','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('550','VD','Polideportivo de Pompéia','0',NULL,'00531','20','BR-06/96','São Paulo','1996-08-24 00:00:00','0','0','São Rafael','54','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('551','VD','Polideportivo de Pompéia','0',NULL,'00532','20','BR-06/96','São Paulo','1996-08-24 00:00:00','0','0','São Rafael','54','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('552','VD','Polideportivo de Pompéia','0',NULL,'00533','20','BR-06/96','São Paulo','1996-08-24 00:00:00','0','0','São Rafael','54','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('553','VD','Tertulia con el Padre','0',NULL,'00534','20','BR-06/96','São Paulo','1996-08-24 00:00:00','0','0','São Rafael','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('554','VD','Ginásio do Ibirapuera','0',NULL,'00529','20','BR-07/96','São Paulo','1996-08-25 00:00:00','0','0','São Rafael','80','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('555','VD','Ginásio do Ibirapuera','0',NULL,'00530','20','BR-07/96','São Paulo','1996-08-25 00:00:00','0','0','São Rafael','80','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('556','VD','Ginásio do Ibirapuera','0',NULL,'00535','20','BR-07/96','São Paulo','1996-08-25 00:00:00','0','0','Geral','80','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('557','VD','Ginásio do Ibirapuera','0',NULL,'00536','20','BR-07/96','São Paulo','1996-08-25 00:00:00','0','0','Geral','80','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('558','VD','Ginásio do Ibirapuera','0',NULL,'00537','20','BR-07/96','São Paulo','1996-08-25 00:00:00','0','0','Geral','80','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('559','VD','Ginásio do Ibirapuera','0',NULL,'00538','20','BR-07/96','São Paulo','1996-08-25 00:00:00','0','0','Geral','80','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('56','VD','La huella de un Santo, Argentina','0',NULL,'00052','19','xx','Argentina','1974-06-01 00:00:00','0','0','xx','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('560','VD','Ginásio do Ibirapuera','0',NULL,'00539','20','BR-07/96','São Paulo','1996-08-25 00:00:00','0','0','Geral','80','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('561','VD','Ginásio do Ibirapuera','0',NULL,'00659','20','BR-07/96','São Paulo','1996-08-25 00:00:00','0','0','Geral','80','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('562','VD','Meidtación y Misa del Padre, Centro de Estudos','0',NULL,'00540','20','BR-08/96','São Paulo','1996-08-26 00:00:00','0','0','São Miguel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('563','VD','Meidtación y Misa del Padre, Centro de Estudos','0',NULL,'00541','20','BR-08/96','São Paulo','1996-08-26 00:00:00','0','0','São Miguel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('564','VD','Meidtación y Misa del Padre, Centro de Estudos','0',NULL,'00542','20','BR-08/96','São Paulo','1996-08-26 00:00:00','0','0','São Miguel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('565','VD','Meidtación y Misa del Padre, Centro de Estudos','0',NULL,'00543','20','BR-08/96','São Paulo','1996-08-26 00:00:00','0','0','São Miguel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('566','VD','Meidtación y Misa del Padre, Centro de Estudos','0',NULL,'00544','20','BR-08/96','São Paulo','1996-08-26 00:00:00','0','0','São Miguel','56','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('567','VD','Tertulia con sacerdotes','0',NULL,'00545','20','BR-09/96','São Paulo','1996-08-26 00:00:00','0','0','Sociedade Sacerdotal','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('568','VD','Tertulia con sacerdotes','0',NULL,'00546','20','BR-09/96','São Paulo','1996-08-26 00:00:00','0','0','Sociedade Sacerdotal','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('569','VD','Tertulia con sacerdotes','0',NULL,'00547','20','BR-09/96','São Paulo','1996-08-26 00:00:00','0','0','Sociedade Sacerdotal','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('57','VD','Con el Padre en Sao Paulo','0',NULL,'00053','19','B-17/74','São Paulo','1974-06-05 00:00:00','0','0','Varões','34','24','',NULL,'','CA1 pág. 302-307',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('570','VD','Tertulia con sacerdotes','0',NULL,'00548','20','BR-09/96','São Paulo','1996-08-26 00:00:00','0','0','Sociedade Sacerdotal','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('571','VD','Tertulia con sacerdotes','0',NULL,'00549','20','BR-09/96','São Paulo','1996-08-26 00:00:00','0','0','Sociedade Sacerdotal','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('572','VD','Tertulia con sacerdotes','0',NULL,'00550','20','BR-09/96','São Paulo','1996-08-26 00:00:00','0','0','Sociedade Sacerdotal','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('573','VD','Tertulia con sacerdotes','0',NULL,'00551','20','BR-09/96','São Paulo','1996-08-26 00:00:00','0','0','Sociedade Sacerdotal','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('574','VD','Polideportivo de Pompéia','0',NULL,'00552','20','BR-10/96','São Paulo','1996-08-27 00:00:00','0','0','Varões','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('575','VD','Polideportivo de Pompéia','0',NULL,'00553','20','BR-10/96','São Paulo','1996-08-27 00:00:00','0','0','Varões','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('576','VD','Polideportivo de Pompéia','0',NULL,'00554','20','BR-10/96','São Paulo','1996-08-27 00:00:00','0','0','Varões','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('577','VD','Polideportivo de Pompéia','0',NULL,'00555','20','BR-10/96','São Paulo','1996-08-27 00:00:00','0','0','Varões','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('578','VD','Polideportivo de Pompéia','0',NULL,'00556','20','BR-10/96','São Paulo','1996-08-27 00:00:00','0','0','Varões','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('579','VD','Polideportivo de Pompéia','0',NULL,'00557','20','BR-10/96','São Paulo','1996-08-27 00:00:00','0','0','Varões','57','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('58','VD','Con el Padre en el Col. De Escribanos','0',NULL,'00054','19','A-038','Buenos Aires','1974-06-14 00:00:00','0','0','São Rafael','33','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('580','VD','Con el Padre en el Centro de Estudios','0',NULL,'00558','20','BR-11/96','São Paulo','1996-08-28 00:00:00','0','0','São Miguel','26','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('581','VD','Con el Padre en el Centro de Estudios','0',NULL,'00559','20','BR-11/96','São Paulo','1996-08-28 00:00:00','0','0','São Miguel','26','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('582','VD','Con el Padre en el Centro de Estudios','0',NULL,'00560','20','BR-11/96','São Paulo','1996-08-28 00:00:00','0','0','São Miguel','26','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('583','VD','Con el Padre en el Centro de Estudios','0',NULL,'00561','20','BR-11/96','São Paulo','1996-08-28 00:00:00','0','0','São Miguel','26','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('584','VD','Con el Padre en el Centro de Estudios','0',NULL,'00562','20','BR-11/96','São Paulo','1996-08-28 00:00:00','0','0','São Miguel','26','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('585','VD','Con el Padre en el Centro de Estudios','0',NULL,'00563','20','BR-11/96','São Paulo','1996-08-28 00:00:00','0','0','São Miguel','26','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('586','VD','Acto Académico','0',NULL,'00564','21','DOC','Espanha','1976-12-06 00:00:00','0','0','Geral','51','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('587','VD','Constituicion \"UT SIT\"','0',NULL,'00565','21','DOC','xx','1983-03-19 00:00:00','0','0','Geral','106','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('588','VD','Él me escucha - relato de una madre','0',NULL,'00566','21','DOC','xx','1989-01-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('589','VD','Entrevista al Padre en la Clinica Universitaria','0',NULL,'00567','21','DOC','xx','1989-01-20 00:00:00','0','0','Geral','21','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('59','VD','Con el Padre en el Col. De Escribanos','0',NULL,'00055','19','A-038','Buenos Aires','1974-06-14 00:00:00','1','0','São Rafael','33','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('590','VD','Acto Académico','0',NULL,'00568','21','DOC','xx','1989-01-21 00:00:00','0','0','Geral','60','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('591','VD','Entre Nosotros, Mons Javier Echevarria (Espanha)','0',NULL,'00569','21','DOC','Espanha','1990-09-22 00:00:00','0','0','Geral','33','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('592','VD','Huellas en la nieve','0',NULL,'00570','21','DOC','xx','1992-01-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('593','VD','Huellas en la nieve','0',NULL,'00571','21','DOC','xx','1992-01-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('594','VD','Santidad en el medio del mundo - Alvaro de Portillo requerda al Fundador','0',NULL,'00572','21','DOC','xx','1992-01-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('595','VD','Jornadas de la Beatificacion III - ceremonias Eucarísticas','0',NULL,'00573','21','DOC','xx','1992-01-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('596','VD','Jornadas de la Beatificacion III - ceremonias Eucarísticas','0',NULL,'00574','21','DOC','xx','1992-01-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('597','VD','Un regalo de Dios','0',NULL,'00575','21','DOC','xx','1992-01-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('598','VD','Un regalo de Dios','0',NULL,'00576','21','DOC','xx','1992-01-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('599','VD','Un regalo de Dios','0',NULL,'00577','21','DOC','xx','1992-01-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('6','VD','Con el Padre en Belagua','0',NULL,'00005','19','Num. 09','Pamplona','1972-10-08 00:00:00','0','0','Geral','27','24','',NULL,'','2MC1 pág. 51-53',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('60','VD','En el Palacio de Congresos General San Martin','0',NULL,'00056','19','A-018','Buenos Aires','1974-06-15 00:00:00','0','0','São Rafael','63','24','AMAR A VIRGEM\r\nBRASIL./PARAGUA\r\nCRITICA LIVROS\r\nFILHO UNICO\r\nJARDINEIRO\r\nMAIS LEITURAS\r\nPARAGUAI\r\nPRIMER. CONFISS\r\nSOU DE ROSARIO\r\nVENDEDORA LIVRO',NULL,'','CA1 pág. 475-483',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('600','VD','Un regalo de Dios','0',NULL,'00578','21','DOC','xx','1992-01-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('601','VD','Uma dádiva de Deus','0',NULL,'00579','21','DOC','xx','1992-01-01 00:00:00','0','0','Geral','53','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('602','VD','Beatificación - Reportaje general','0',NULL,'00580','21','DOC','xx','1992-01-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('603','VD','Entrevista a Mons. Alvaro del Portillo','0',NULL,'00584','21','DOC','Roma','1992-03-20 00:00:00','0','0','Geral','15','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('604','VD','17 mayo 1992','0',NULL,'00581','21','DOC','Roma','1992-05-17 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('605','VD','18 mayo 1992','0',NULL,'00582','21','DOC','Roma','1992-05-18 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('606','VD','21 mayo 1992','0',NULL,'00583','21','DOC','Roma','1992-05-21 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('607','VD','Homenaje al Beato Josemaría, Barbastro','0',NULL,'00212','21','H-1/92','Barbastro','1992-09-03 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('608','VD','Entre Nosotros, Mons Javier Echevarria (Espanha)','0',NULL,'00585','21','DOC','Espanha','1992-10-22 00:00:00','0','0','Geral','34','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('609','VD','V Jornada Mariana de la Familia','0',NULL,'00586','21','DOC','Espanha','1993-10-16 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('61','VD','En el Palacio de Congresos General San Martin','0',NULL,'00645','19','A-018','Buenos Aires','1974-06-15 00:00:00','1','0','São Rafael','63','24','AMAR A VIRGEM',NULL,'','CA1 pág. 475-483',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('610','VD','V Jornada Mariana de la Familia','0',NULL,'00587','21','DOC','Espanha','1993-10-16 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('611','VD','Elección del Padre','0',NULL,'00588','21','DOC','Roma','1994-04-24 00:00:00','0','0','São Miguel','35','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('612','VD','Guadalupe 1970-1995, XXV Aniversario','0',NULL,'00589','21','DOC','México','1995-01-01 00:00:00','0','0','Geral','29','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('613','VD','In memoriam de Mons. Alvaro del Portillo','0',NULL,'00590','21','DOC','Roma','1995-01-28 00:00:00','0','0','Geral','46','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('614','VD','Dedicación Solemne de la Paroquia del Bt. Josemaria','0',NULL,'00591','21','R-1/96','Roma','1996-03-10 00:00:00','0','0','Geral','35','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('615','VD','Honoris Causa 98 - resumen general','0',NULL,'00592','21','DOC','Espanha','1998-01-01 00:00:00','0','0','Geral','27','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('616','VD','XI Jornada Mariana de la Familia','0',NULL,'00593','21','DOC','Espanha','1999-04-09 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('617','VD','El Padre en la Universidad del ISTMO - acto académico','0',NULL,'00594','21','DOC','Panamá','2000-01-21 00:00:00','0','0','Geral','23','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('618','VD','Sembradores de paz y de alegria','0',NULL,'00595','21','DOC','xx','2001-01-01 00:00:00','0','0','Geral','49','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('619','VD','Centenario Josemaría Escrivá','0',NULL,'00596','21','DOC','Espanha','2002-01-01 00:00:00','0','0','Geral','6','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('62','VD','En el Palacio de Congresos General San Martin','0',NULL,'00057','19','A-002','Buenos Aires','1974-06-16 00:00:00','0','0','Geral','53','24','BRASIL./PARAGUA',NULL,'','CA1 pág. 496-512',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('620','VD','The Grandeur of Ordinary Life','0',NULL,'00597','21','DOC','Estados Unidos','2002-01-01 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('621','VD','O Santo do Ordinário','0',NULL,'00598','21','DOC','xx','2002-01-01 00:00:00','0','0','Geral','23','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('622','VD','O Santo do Ordinário','0',NULL,'00599','21','DOC','xx','2002-01-01 00:00:00','0','0','Geral','23','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('623','VD','O Santo do Ordinário','0',NULL,'00600','21','DOC','xx','2002-01-01 00:00:00','0','0','Geral','23','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('624','VD','Centenario: la vocación cristiana a la luz de las enseñanzas del Beato Josemaría','0',NULL,'00603','21','DOC','Roma','2002-01-01 00:00:00','0','0','Geral','99','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('625','VD','Centenario: la vocación cristiana a la luz de las enseñanzas del Beato Josemaría','0',NULL,'00604','21','DOC','Roma','2002-01-01 00:00:00','0','0','Geral','102','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('626','VD','Centenario: la vocación cristiana a la luz de las enseñanzas del Beato Josemaría','0',NULL,'00605','21','DOC','Roma','2002-01-01 00:00:00','0','0','Geral','72','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('627','VD','6 de octubre. Canonización de San Josemaría','0',NULL,'00601','21','DOC','Roma','2002-10-06 00:00:00','0','0','Geral','47','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('628','VD','7 de octubre: Jornada de acción de gracias','0',NULL,'00602','21','DOC','Roma','2002-10-07 00:00:00','0','0','Geral','61','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('629','VD','Acto Académico en la Universidad Austral','0',NULL,'00606','21','DOC','Argentina','2003-09-29 00:00:00','0','0','Geral','27','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('63','VD','En el Palacio de Congresos General San Martin','0',NULL,'00058','19','A-002','Buenos Aires','1974-06-16 00:00:00','0','0','Geral','53','24','CRITICA LIVROS',NULL,'','CA1 pág. 496-512',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('630','VD','Abancay - una diócesis en el techo del mundo','0',NULL,'00607','21','DOC','Peru',NULL,'0','0','Geral','17','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('631','VD','La aventura de la vida diária','0',NULL,'00608','21','DOC','xx',NULL,'0','0','Geral','40','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('632','VD','La aventura de la vida diária','0',NULL,'00609','21','DOC','xx',NULL,'0','0','Geral','40','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('633','VD','Founder of Opus Dei - a profile of his life and work','0',NULL,'00610','21','DOC','Estados Unidos',NULL,'0','0','Geral','20','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('634','VD','Los caminos divinos de la tierra','0',NULL,'00611','21','DOC','xx',NULL,'0','0','Geral','36','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('635','VD','Los caminos divinos de la tierra','0',NULL,'00612','21','DOC','xx',NULL,'0','0','Geral','36','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('636','VD','Los caminos divinos de la tierra','0',NULL,'00613','21','DOC','xx',NULL,'0','0','Geral','36','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('637','VD','Los caminos divinos de la tierra','0',NULL,'00614','21','DOC','xx',NULL,'0','0','Geral','36','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('638','VD','Los caminos divinos de la tierra','0',NULL,'00615','21','DOC','xx',NULL,'0','0','Geral','36','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('639','VD','É questão de Fé','0',NULL,'00616','21','DOC','xx',NULL,'0','0','Geral','30','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('64','VD','En el Palacio de Congresos General San Martin','0',NULL,'00059','19','A-002','Buenos Aires','1974-06-16 00:00:00','1','0','Geral','53','24','FILHO UNICO',NULL,'','CA1 pág. 496-512',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('640','VD','Es cuestión de fe','0',NULL,'00617','21','DOC','xx',NULL,'0','0','Geral','30','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('641','VD','Kinai - en un bairro de Ciudad de Guatemala','0',NULL,'00618','21','DOC','Guatemala',NULL,'0','0','Geral','18','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('642','VD','Le llaman Padre en cinco continentes','0',NULL,'00619','21','DOC','xx',NULL,'0','0','Geral','50','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('643','VD','Le llaman Padre en cinco continentes','0',NULL,'00620','21','DOC','xx',NULL,'0','0','Geral','50','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('644','VD','Le llaman Padre en cinco continentes','0',NULL,'00621','21','DOC','xx',NULL,'0','0','Geral','50','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('645','VD','Parroquia del Beato Josemaría','0',NULL,'00622','21','DOC','Roma',NULL,'0','0','Geral','8','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('646','VD','Santidade no meio do mundo - Mons. Álvaro del Portillo recorda o Fundador','0',NULL,'00623','21','DOC','xx',NULL,'0','0','Geral','30','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('647','VD','Torreciudad - Santuario Mariano','0',NULL,'00624','21','DOC','Espanha',NULL,'0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('648','VD','Trabajo de Dios','0',NULL,'00625','21','DOC','xx',NULL,'0','0','Geral','23','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('649','VD','Trabajo de Dios','0',NULL,'00626','21','DOC','xx',NULL,'0','0','Geral','23','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('65','VD','Con el Padre en La Chacra','0',NULL,'00060','19','A-039','Buenos Aires','1974-06-17 00:00:00','0','0','Sociedade Sacerdotal','36','24','JARDINEIRO',NULL,'','CA1 pág. 515-538 y/o 612-620',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('650','VD','Los caminos divinos de la tierra','0',NULL,'00648','21','DOC','xx',NULL,'0','0','Geral','36','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('651','VD','Ordenación de Presbíteros en Torreciudad','0',NULL,'00627','22','ORD','Espanha','1991-01-01 00:00:00','0','0','Geral','45','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('652','VD','Ordenacion Episcopal del Padre','0',NULL,'00628','22','ORD','Roma','1991-01-06 00:00:00','0','0','Geral','72','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('653','VD','Ordenacion Episcopal del Padre','0',NULL,'00629','22','ORD','Roma','1991-01-06 00:00:00','0','0','Geral','72','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('654','VD','San Eugenio Santa Misa (Ordenacion Episcopal del Padre)','0',NULL,'00630','22','ORD','Roma','1991-01-07 00:00:00','0','0','Geral','63','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('655','VD','Ordenación de Presbíteros en Torreciudad','0',NULL,'00631','22','ORD','Espanha','1992-06-09 00:00:00','0','0','Geral','48','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('656','VD','Ordenación de Presbíteros en Torreciudad','0',NULL,'00632','22','ORD','Espanha','1993-05-09 00:00:00','0','0','Geral','88','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('657','VD','Ordenación de Presbíteros en Roma','0',NULL,'00633','22','ORD','Roma','1994-09-15 00:00:00','0','0','Geral','55','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('658','VD','Ordenacion Episcopal del Padre','0',NULL,'00634','22','ORD','Roma','1995-01-06 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('659','VD','Ordenación de Presbíteros en San Eugenio','0',NULL,'00635','22','ORD','Roma','1995-09-15 00:00:00','0','0','Geral','45','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('66','VD','Con el Padre en el Colegio Escribanos','0',NULL,'00061','19','A-004','Buenos Aires','1974-06-18 00:00:00','0','0','Geral','49','24','MAIS LEITURAS',NULL,'','CA1 pág. 542-552',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('660','VD','Missa dos 70 anos do Opus Dei','0',NULL,'00649','22','ORD','Brasil','1998-10-03 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('661','VD','Missa dos 70 anos do Opus Dei','0',NULL,'00650','22','ORD','Brasil','1998-10-03 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('662','VD','Missa de São Josemaria - Catedral da Sé','0',NULL,'00486','22','ORD','Brasil','2003-06-28 00:00:00','0','0','Geral','0','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('663','VD','Ordenación de Presbíteros en Nuestra Señora de Luján','0',NULL,'00636','22','ORD','Argentina','2003-09-13 00:00:00','0','0','Geral','33','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('664','VD','Audiencia con el Papa. Univ 86','0',NULL,'00637','23','UNIV','Roma','1986-01-01 00:00:00','0','0','Geral','52','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('665','VD','Audiencia con el Papa. Univ 87','0',NULL,'00638','23','UNIV','Roma','1987-01-01 00:00:00','0','0','Geral','50','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('666','VD','Audiencia con el Papa. Univ 89','0',NULL,'00639','23','UNIV','Roma','1989-01-01 00:00:00','0','0','Geral','35','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('667','VD','Audiencia con el Papa. Univ 90','0',NULL,'00640','23','UNIV','Roma','1990-01-01 00:00:00','0','0','Geral','39','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('668','VD','Audiencia con el Papa. Univ 91','0',NULL,'00641','23','UNIV','Roma','1991-01-01 00:00:00','0','0','Geral','50','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('669','VD','Congresos Universitarios Internacionales. UNIV','0',NULL,'00642','23','UNIV','Roma','1994-01-01 00:00:00','0','0','Geral','18','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('67','VD','Con el Padre en el Colegio Escribanos','0',NULL,'00062','19','A-004','Buenos Aires','1974-06-18 00:00:00','0','0','Geral','49','24','PARAGUAI',NULL,'','CA1 pág. 542-552',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('670','VD','25 años con el Papa','0',NULL,'00643','23','UNIV','Roma',NULL,'0','0','Geral','25','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('68','VD','Con el Padre en el Colegio Escribanos','0',NULL,'00063','19','A-001','Buenos Aires','1974-06-21 00:00:00','0','0','Geral','49','24','PRIMER. CONFISS',NULL,'','CA1 pág. 542-552',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('69','VD','Con el Padre en el Colegio Escribanos','0',NULL,'00064','19','A-001','Buenos Aires','1974-06-21 00:00:00','1','0','Geral','55','24','SOU DE ROSARIO',NULL,'','CA1 pág. 554-564',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('7','VD','Con el Padre en Belagua','0',NULL,'00006','19','Num. 09','Pamplona','1972-10-08 00:00:00','1','0','Geral','27','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('70','VD','Con el Padre en el Teatro Coliseo','0',NULL,'00065','19','A-019','Buenos Aires','1974-06-23 00:00:00','0','0','Geral','63','24','VENDEDORA LIVRO',NULL,'','CA1 pág. 660-680',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('71','VD','Con el Padre en el Teatro Coliseo','0',NULL,'00066','19','A-019','Buenos Aires','1974-06-23 00:00:00','0','0','Geral','63','24','CADELA\r\nCALICE\r\nCASAIS\r\nIOIÔ\r\nLABOR DE São Gabriel\r\nNAO E BOM P/ SR\r\nPERG. DOMESTICA\r\nUNIFORME\r\nVIUVA',NULL,'','CA1 pág. 660-680',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('72','VD','Con el Padre en el Teatro Coliseo','0',NULL,'00067','19','A-019','Buenos Aires','1974-06-23 00:00:00','1','0','Geral','63','24','CADELA\r\nCALICE\r\nCASAIS\r\nIOIÔ\r\nLABOR DE São Gabriel\r\nNAO E BOM P/ SR\r\nPERG. DOMESTICA\r\nUNIFORME\r\nVIUVA',NULL,'','CA1 pág. 660-680',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('73','VD','Con el Padre en el Teatro Coliseo','0',NULL,'00068','19','A-014','Buenos Aires','1974-06-26 00:00:00','0','0','Geral','64','24','COMUNHAO SANTOS',NULL,'','CA1 pág. 684-700',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('74','VD','Con el Padre en el Teatro Coliseo','0',NULL,'00646','19','A-014','Buenos Aires','1974-06-26 00:00:00','0','0','Geral','64','24','BANCA DE JORNAL',NULL,'','CA1 pág. 684-700',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('75','VD','Una Tarde en Alameda','0',NULL,'00069','19','A-006','Santiago/Chile','1974-06-29 00:00:00','0','0','São Rafael','53','24','CRIAR-SE NA RUA',NULL,'','CA2 pág. 40-51',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('76','VD','Una Tarde con el Padre en Alameda','0',NULL,'00070','19','A-006','Santiago/Chile','1974-06-29 00:00:00','0','0','São Rafael','53','24','HOMEM DA RUA',NULL,'','CA2 pág. 40-51',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('77','VD','Una Tarde en Alameda','0',NULL,'00071','19','A-022','Santiago/Chile','1974-07-01 00:00:00','0','0','Geral','35','24','MORTE MARIDO','1','','CA2 pág. 68-85',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('78','VD','En el Colegio de Tabancura','0',NULL,'00072','19','A-005','Santiago/Chile','1974-07-02 00:00:00','0','0','Geral','57','24','MULHER INVALIDA',NULL,'','CA2 pág. 146-214',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('79','VD','En el Colegio de Tabancura','0',NULL,'00652','19','A-005','Santiago/Chile','1974-07-02 00:00:00','1','0','Geral','57','24','MULHER INVALIDA',NULL,'','CA2 pág. 146-214',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('8','VD','Con el Padre en Belágua','0',NULL,'00008','19','Num. 11','Pamplona','1972-10-09 00:00:00','0','0','Geral','33','24','',NULL,'','2MC1 pág. 83-89',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('80','VD','Con el Padre en Alameda','0',NULL,'00073','19','A-023','Santiago/Chile','1974-07-04 00:00:00','0','0','Geral','67','24','MUSICAS INGLESA',NULL,'','CA2 pág. 68-85 y 104',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('81','VD','Con el Padre en Alameda','0',NULL,'00074','19','A-023','Santiago/Chile','1974-07-04 00:00:00','1','0','Geral','67','24','NPE COMOVIDO',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('82','VD','Una Tarde en Tabancura','0',NULL,'00075','19','A-024','Santiago/Chile','1974-07-05 00:00:00','0','0','Geral','56','24','PAPA BEATIFICAC',NULL,'','CA2 pág. 105 y 146-214',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('83','VD','Una Tarde en Tabancura','0',NULL,'00076','19','A-024','Santiago/Chile','1974-07-05 00:00:00','0','0','Geral','56','24','PARALITICA',NULL,'','CA2 pág. 105 y 146-214',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('84','VD','Con el Padre en Tabancura','0',NULL,'00077','19','A-010','Santiago/Chile','1974-07-06 00:00:00','0','0','Geral','49','24','PARANA',NULL,'','CA2 pág. 146-214',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('85','VD','Con el Padre en Tabancura','0',NULL,'00078','19','A-037','Santiago/Chile','1974-07-07 00:00:00','0','0','Geral','48','24','RIO PARANA',NULL,'','CA2 pág. 146-214',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('86','VD','Con el Padre en Valle-Grande','0',NULL,'00079','19','A-009','Lima','1974-07-13 00:00:00','0','0','Geral','63','24','UNIV 93',NULL,'','CA2 pág. 281-307',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('87','VD','Con el Padre en Valle-Grande','0',NULL,'00080','19','A-009','Lima','1974-07-13 00:00:00','1','0','Geral','63','24','VIUVA ROSARIO',NULL,'','CA2 pág. 281-307',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('88','VD','Con el Padre en Miralba','0',NULL,'00081','19','A-012','','1974-07-14 00:00:00','0','0','Geral','64','24','VOLTAR/FICAR',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('89','VD','Con el Padre en Miralba','0',NULL,'00082','19','A-027','Lima','1974-07-25 00:00:00','0','0','Geral','48','24','','1','','CA2 pág. 360-374',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('9','VD','Con el Padre en Gaztelueta','0',NULL,'00009','19','Num. 06-A','Bilbao','1972-10-11 00:00:00','0','0','São Rafael','22','24','',NULL,'','2MC1 pág. 113-121',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('90','VD','Con el Padre en Miralba','0',NULL,'00083','19','A-027','Lima','1974-07-25 00:00:00','1','0','Geral','48','24','','1','','CA2 pág. 360-374',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('91','VD','Con el Padre en Laboleda','0',NULL,'00084','19','A-016','Lima','1974-07-26 00:00:00','0','0','Sociedade Sacerdotal','66','24','AR LIVRE\r\nAVO CHOROU\r\nCOF NPE\r\nNPE COM FRIO',NULL,'','CA2 pág. 395-410',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('92','VD','Con el Padre en Laboleda','0',NULL,'00085','19','A-013','Lima','1974-07-29 00:00:00','0','0','Geral','62','24','FELIPE/ETIOPE\r\nJOAO, LEITEIRO\r\nMARXISMO\r\nNAX\r\nNOVELA ROSA\r\nPIURA','1','','CA2 pág. 417-430 y 435',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('93','VD','Con el Padre en Larboleda','0',NULL,'00086','19','A-029','Lima','1974-07-30 00:00:00','0','0','São Rafael','50','24','AMIGOS DROGADOS\r\nBANHO NO RIO COM TODOS OS SABONETES\r\nCHINO\r\nCUSTODIO\r\nFEBRE\r\nLAVAR O NARIZ\r\nNARIZES\r\nPESCADORES\r\nSOU REBELDE',NULL,'','CA2 pág. 437-453',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('94','VD','Con el Padre en Ecuador','0',NULL,'00087','19','A-032','Quito','1974-08-13 00:00:00','0','0','São Miguel','46','24','CANCAO UNA ROSA\r\nDOENCA NPE\r\nNPE CANSADO\r\nNPE SENTADO\r\nPESSOAS NO CHAO\r\nSOMOS POUCOS\r\nSOROCHE\r\nUNA ROSA ME DIS','1','','CA2 pág. 500-515',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('95','VD','Con el Padre en Altoclaro','0',NULL,'00088','19','A-025','Caracas','1974-08-29 00:00:00','0','0','São Miguel','47','24','AGD DIABETICO\r\nENTREGA\r\nFLOJERA\r\nPINICO','1','','CA2 pág. 608-625',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('96','VD','Con el Padre en Altoclaro','0',NULL,'00089','19','A-011','Caracas','1974-08-30 00:00:00','0','0','Geral','41','24','',NULL,'','',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('97','VD','Con el Padre en Altoclaro','0',NULL,'00090','19','A-204','Caracas','1975-02-10 00:00:00','0','0','Geral','54','24','FRACASSO',NULL,'','CA3 pág. 158-168',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('98','VD','Con el Padre en Altoclaro','0',NULL,'00091','19','A-205','Caracas','1975-02-11 00:00:00','0','0','Geral','50','24','ALEGRIA DO NOSSO PADRE\r\nBATISMO\r\nDEFICIENTE\r\nFILHA RETARDADA\r\nFILHO PARALITICO\r\nGRANDALHAO PALC\r\nJOVEM NO PALCO\r\nMARACAIBO\r\nMODESTIA\r\nMULHER VERDE\r\nNOIVADO\r\nPUDOR\r\nPUXAR DA LINGUA\r\nRECEM-NASCIDO\r\nSANTA PUREZA\r\nSUICO',NULL,'','CA3 pág. 85-99',NULL);
INSERT INTO `objeto_bumerangue` VALUES ('99','VD','Con el Padre en Altoclaro','0',NULL,'00092','19','A-206','Caracas','1975-02-12 00:00:00','0','0','São Miguel','52','24','ANÉIS\r\nAVO CHINA\r\nBENCAO D.ALVARO\r\nDIA ENSOLARADO\r\nDOM JAVIER/TERCO\r\nINICIO LABOR SR\r\nMOTOCICLETA\r\nMOTOCROSS\r\nOCULOS ESCUROS\r\nRAPAZ & MOTO\r\nTERCO NPE\r\nTRABALHO DE SR','1','','CA3 pág. 149-150',NULL);
INSERT INTO `permissao` VALUES ('402880840d61395d010d6139b4c60001','ADMIN_VIDEO','Administração dos vídeos');
INSERT INTO `permissao` VALUES ('402880840d61395d010d6139b61e0002','BASICO_VIDEO','Usuário comum dos videos');
INSERT INTO `permissao` VALUES ('402880840d61395d010d6139b68b0003','ADMIN_LIVRO_CULTURAL','Administração dos livros culturais');
INSERT INTO `permissao` VALUES ('402880840d61395d010d6139b6d90004','BASICO_LIVRO_CULTURAL','Usuário comum dos livros culturais');
INSERT INTO `permissao_usuario` VALUES ('402880840d613b73010d613bc47c0001','402880840d61395d010d6139b4c60001');
INSERT INTO `permissao_usuario` VALUES ('402880840d613b73010d613bc6cd0002','402880840d61395d010d6139b4c60001');
INSERT INTO `usuario` VALUES ('402880840d613b73010d613bc47c0001','CT','AC','Adriano Carvalho','123456','adrianocarv@yahoo.com.br','3872-9855(casa) / 3862-1243(trabalho)','1','0','0','1');
INSERT INTO `usuario` VALUES ('402880840d613b73010d613bc6cd0002','CT','CA','Carlos de Andrea','123456','carlitos@gmail.com','3872-9855(casa)','1','0','0','1');
