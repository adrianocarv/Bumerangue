--Lançamentos em empréstimos sem objetos, deve retornar 0 (zero) registros.
SELECT * FROM BMG_EMPRESTIMO WHERE ID_OBJETO_BUMERANGUE IS NULL;

--Devolução sem ter tido empréstimo antes, deve retornar 0 (zero) registros.
SELECT * FROM BMG_EMPRESTIMO WHERE DATA_DEVOLUCAO IS NOT NULL AND DATA_EMPRESTIMO IS NULL


--Dia 16/05/2008 Corrigiu a inconsistência, após eu setar null no campo ID_EMPRESTIMO_ATUAL de todos os registros
update BMG_OBJETO_BUMERANGUE as O
set ID_EMPRESTIMO_ATUAL = ( SELECT E.ID FROM BMG_EMPRESTIMO as E WHERE DATA_DEVOLUCAO IS NULL and  O.ID = E.ID_OBJETO_BUMERANGUE )
