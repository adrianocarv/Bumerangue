package br.org.ceu.bumerangue.dao;

import java.io.Serializable;
import java.util.List;

import br.org.ceu.bumerangue.entity.Emprestimo;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.criterias.PesquisaHistoricoLivroCulturalCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaHistoricoVideoCriteria;

public interface EmprestimoDao extends BaseDao {
 
	public Emprestimo get(Serializable id);
	public List<Emprestimo> findReferenciasUsuario(Usuario usuario);
	public List<Emprestimo> findEmprestimos(PesquisaHistoricoVideoCriteria pesquisaHistoricoVideoCriteria);
	public List<Emprestimo> findEmprestimos(PesquisaHistoricoLivroCulturalCriteria pesquisaHistoricoLivroCulturalCriteria);
}
 
