-- cria as views

DROP VIEW IF EXISTS `bmg_permissao_usuario_view`;
CREATE VIEW bmg_permissao_usuario_view AS
select u.nome, p.nome as nome_permissao
from bmg_permissao_usuario as pu, bmg_usuario as u, bmg_permissao as p
where pu.id_usuario = u.id
and pu.id_permissao = p.id;

DROP VIEW IF EXISTS `bmg_exportar_video_view`;
CREATE VIEW bmg_exportar_video_view AS
select
codigo,
ed1.descricao as categoria_d,
codigo_caixa,
date_format(v.data,'.%d/%m/%Y.') as data_d,
publico,
observacoes,
titulo,
duracao_minutos,
null as copia,
(case v.legendado WHEN 1 THEN 'Sim' ELSE 'Não' END) as legendado_d,
(case v.dublado WHEN 1 THEN 'Sim' ELSE 'Não' END) as dublado_d,
localidade,
palavras_chaves,
localizacao_pi,
ed2.descricao as midia_d,
observacoes_gerais,
localizacao_fisica,
(case v.fora_uso WHEN 1 THEN 'Sim' ELSE 'Não' END) as fora_uso_d
from bmg_objeto_bumerangue as v, bmg_elemento_dominio as ed1, bmg_elemento_dominio as ed2
where ed1.id = v.categoria
and ed2.id = v.midia
and v.tipo_objeto_bumerangue = 'VD'
order by categoria, data, codigo;

-- deleta todos os domínios

DELETE FROM bmg_elemento_dominio;
DELETE FROM bmg_dominio;

-- video

insert into bmg_dominio values (1,1, 'VIDEO_OBSERVACOES');
insert into bmg_elemento_dominio values (1,'Só para membros da Obra', 1, 1);
insert into bmg_elemento_dominio values (2,'Só para membros da Obra e Cooperadores encaixados', 1, 2);
insert into bmg_elemento_dominio values (3,'Só para pessoas com Oblação e Supernumerários com Fidelidade', 1, 3);
insert into bmg_elemento_dominio values (4,'Recomendado para o labor de São Gabriel', 1, 4);
insert into bmg_elemento_dominio values (5,'Para pessoas doentes', 1, 5);
insert into bmg_elemento_dominio values (6,'Recomendado para o labor de São Rafael', 1, 6);
insert into bmg_elemento_dominio values (7,'Recomendado para o labor de São Gabriel', 1, 7);
insert into bmg_elemento_dominio values (8,'Recomendado para o labor da Sociedade Sacerdotal', 1, 8);
insert into bmg_elemento_dominio values (9,'Recomendado para São Gabriel  e universitários', 1, 9);
insert into bmg_elemento_dominio values (10,'Só para sacerdotes', 1, 10);
insert into bmg_elemento_dominio values (11,'Numerários mais velhos e Diretores', 1, 11);
insert into bmg_elemento_dominio values (12,'São Miguel e São Gabriel (fundamentalmente zeladores)', 1, 12);
insert into bmg_elemento_dominio values (13,'Para Conselhos locais e pessoas mais velhas', 1, 13);
insert into bmg_elemento_dominio values (14,'Membros da Obra e rapazes de São Rafael', 1, 14);
insert into bmg_elemento_dominio values (15,'Pessoas em contato com o labor apostólico', 1, 15);
insert into bmg_elemento_dominio values (16,'Membros da Obra e rapazes de São Rafael encaixados', 1, 16);
insert into bmg_elemento_dominio values (17,'Para o labor de São Gabriel e Cooperadores apitáveis', 1, 17);

insert into bmg_dominio values (2,2, 'VIDEO_CATEGORIA');
insert into bmg_elemento_dominio values (19,'Geral', 2, 1);
insert into bmg_elemento_dominio values (20,'Brasil', 2, 2);
insert into bmg_elemento_dominio values (21,'Documentário', 2, 3);
insert into bmg_elemento_dominio values (22,'Ordenação', 2, 4);
insert into bmg_elemento_dominio values (23,'UNIV', 2, 5);

insert into bmg_dominio values (3,3, 'VIDEO_MIDIA');
insert into bmg_elemento_dominio values (24,'VHS', 3, 1);
insert into bmg_elemento_dominio values (25,'DVD', 3, 2);

insert into bmg_dominio values (4,4, 'TIPO_PERMISSAO');
insert into bmg_elemento_dominio values (30,'básico', 4, 0);
insert into bmg_elemento_dominio values (31,'avançado', 4, 1);
insert into bmg_elemento_dominio values (32,'admin', 4, 2);

-- filmeComercial

insert into bmg_dominio values (5,5, 'FILME_COMERCIAL_CATEGORIA');
insert into bmg_elemento_dominio values (33,'Ação', 5, 0);
insert into bmg_elemento_dominio values (34,'Animação', 5, 1);
insert into bmg_elemento_dominio values (35,'Aventura', 5, 2);
insert into bmg_elemento_dominio values (36,'Aventura / Ficção Científica', 5, 3);
insert into bmg_elemento_dominio values (37,'Aventura Infantil', 5, 4);
insert into bmg_elemento_dominio values (38,'Comédia', 5, 5);
insert into bmg_elemento_dominio values (39,'Documentário', 5, 6);
insert into bmg_elemento_dominio values (40,'Drama', 5, 7);
insert into bmg_elemento_dominio values (41,'Drama / Infantil', 5, 8);
insert into bmg_elemento_dominio values (42,'Drama / Musical', 5, 9);
insert into bmg_elemento_dominio values (43,'Drama / Terror', 5, 10);
insert into bmg_elemento_dominio values (44,'Drama Histórico', 5, 11);
insert into bmg_elemento_dominio values (45,'Épico', 5, 12);
insert into bmg_elemento_dominio values (46,'Fantasia Gótica', 5, 13);
insert into bmg_elemento_dominio values (47,'Faroeste', 5, 14);
insert into bmg_elemento_dominio values (48,'Ficção', 5, 15);
insert into bmg_elemento_dominio values (49,'Ficção Científica', 5, 16);
insert into bmg_elemento_dominio values (50,'Guerra', 5, 17);
insert into bmg_elemento_dominio values (51,'Musical', 5, 18);
insert into bmg_elemento_dominio values (52,'Ópera', 5, 19);
insert into bmg_elemento_dominio values (53,'Policial', 5, 20);
insert into bmg_elemento_dominio values (54,'Romance', 5, 21);
insert into bmg_elemento_dominio values (55,'Suspense', 5, 22);
insert into bmg_elemento_dominio values (56,'Western', 5, 23);


insert into bmg_dominio values (6,6, 'FILME_COMERCIAL_PUBLICO');
insert into bmg_elemento_dominio values (70,'Adulto', 6, 0);
insert into bmg_elemento_dominio values (71,'Jovem e Adulto', 6, 1);
insert into bmg_elemento_dominio values (72,'Todos', 6, 2);

insert into bmg_dominio values (7,7, 'LIVRO_CULTURAL_IDIOMA');
insert into bmg_elemento_dominio values (75,'Português', 7, 0);
insert into bmg_elemento_dominio values (76,'Inglês', 7, 1);
insert into bmg_elemento_dominio values (77,'Francês', 7, 2);
