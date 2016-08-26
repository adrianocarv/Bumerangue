DROP VIEW IF EXISTS `bmg_permissao_usuario_view`;
CREATE VIEW bmg_permissao_usuario_view AS
select u.nome, p.nome as nome_permissao
from bmg_permissao_usuario as pu, bmg_usuario as u, bmg_permissao as p
where pu.id_usuario = u.id
and pu.id_permissao = p.id;

DELETE FROM bmg_elemento_dominio;
DELETE FROM bmg_dominio;

insert into bmg_elemento_dominio values (1,'Obs 1', 1, 0);
insert into bmg_elemento_dominio values (2,'Obs 2', 1, 1);
insert into bmg_elemento_dominio values (3,'Obs 3', 1, 2);
insert into bmg_elemento_dominio values (4,'Obs 4', 1, 3);

insert into bmg_elemento_dominio values (19,'Cat 1', 2, 0);
insert into bmg_elemento_dominio values (20,'Cat 2', 2, 1);
insert into bmg_elemento_dominio values (21,'Cat 3', 2, 2);
insert into bmg_elemento_dominio values (22,'Cat 4', 2, 3);
insert into bmg_elemento_dominio values (23,'Cat 5', 2, 4);

insert into bmg_elemento_dominio values (24,'VHS', 3, 0);
insert into bmg_elemento_dominio values (25,'DVD', 3, 1);

insert into bmg_elemento_dominio values (30,'b�sico', 4, 0);
insert into bmg_elemento_dominio values (31,'avan�ado', 4, 1);
insert into bmg_elemento_dominio values (32,'admin', 4, 2);

insert into bmg_elemento_dominio values (33,'A��o', 5, 0);
insert into bmg_elemento_dominio values (34,'Anima��o', 5, 1);
insert into bmg_elemento_dominio values (35,'Aventura', 5, 2);
insert into bmg_elemento_dominio values (36,'Aventura / Fic��o Cient�fica', 5, 3);
insert into bmg_elemento_dominio values (37,'Aventura Infantil', 5, 4);
insert into bmg_elemento_dominio values (38,'Com�dia', 5, 5);
insert into bmg_elemento_dominio values (39,'Document�rio', 5, 6);
insert into bmg_elemento_dominio values (40,'Drama', 5, 7);
insert into bmg_elemento_dominio values (41,'Drama / Infantil', 5, 8);
insert into bmg_elemento_dominio values (42,'Drama / Musical', 5, 9);
insert into bmg_elemento_dominio values (43,'Drama / Terror', 5, 10);
insert into bmg_elemento_dominio values (44,'Drama Hist�rico', 5, 11);
insert into bmg_elemento_dominio values (45,'�pico', 5, 12);
insert into bmg_elemento_dominio values (46,'Fantasia G�tica', 5, 13);
insert into bmg_elemento_dominio values (47,'Faroeste', 5, 14);
insert into bmg_elemento_dominio values (48,'Fic��o', 5, 15);
insert into bmg_elemento_dominio values (49,'Fic��o Cient�fica', 5, 16);
insert into bmg_elemento_dominio values (50,'Guerra', 5, 17);
insert into bmg_elemento_dominio values (51,'Musical', 5, 18);
insert into bmg_elemento_dominio values (52,'�pera', 5, 19);
insert into bmg_elemento_dominio values (53,'Policial', 5, 20);
insert into bmg_elemento_dominio values (54,'Romance', 5, 21);
insert into bmg_elemento_dominio values (55,'Suspense', 5, 22);
insert into bmg_elemento_dominio values (56,'Western', 5, 23);

insert into bmg_elemento_dominio values (70,'Adulto', 6, 0);
insert into bmg_elemento_dominio values (71,'Jovem e Adulto', 6, 1);
insert into bmg_elemento_dominio values (72,'Todos', 6, 2);

insert into bmg_elemento_dominio values (75,'Portugu�s', 7, 0);
insert into bmg_elemento_dominio values (76,'Ingl�s', 7, 1);
insert into bmg_elemento_dominio values (77,'Franc�s', 7, 2);
