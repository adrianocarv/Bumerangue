package br.org.ceu.bumerangue.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.org.ceu.bumerangue.dao.EmprestimoDao;
import br.org.ceu.bumerangue.entity.Emprestimo;
import br.org.ceu.bumerangue.entity.Usuario;
import br.org.ceu.bumerangue.entity.criterias.PesquisaHistoricoLivroCulturalCriteria;
import br.org.ceu.bumerangue.entity.criterias.PesquisaHistoricoVideoCriteria;
import br.org.ceu.bumerangue.util.DateUtil;
import br.org.ceu.bumerangue.util.ValidationRules;

public class EmprestimoDaoHibernate extends BaseDaoHibernate implements EmprestimoDao {

	public Emprestimo get(Serializable id) {
    	return (Emprestimo)super.get(Emprestimo.class, id);
    }
 
	public List<Emprestimo> findReferenciasUsuario(Usuario usuario){
		String hql = "from Emprestimo as e";
		hql += " where e.usuarioRealizouReserva = ?";
		hql += " or e.usuarioEmprestimo = ?";
		hql += " or e.usuarioRealizouEmprestimo = ?";
		hql += " or e.usuarioRealizouDevolucao = ?";
		
		List<Emprestimo> emprestimos = super.find(hql,new Object[] {usuario, usuario, usuario, usuario});
		return emprestimos;
	}
	
	public List<Emprestimo> findEmprestimos(PesquisaHistoricoVideoCriteria criteria){
        String hql = "select e from Emprestimo as e, Video as v where e.objetoBumerangue.id = v.id";
        ArrayList args = new ArrayList();
        if(ValidationRules.isInformed(criteria.getCodigo())) hql += " and v.codigo = '"+criteria.getCodigo()+"'";
        if(ValidationRules.isInformed(criteria.getDataVideoIni())){ hql += " and v.data >= ?"; args.add(DateUtil.getDate(criteria.getDataVideoIni(),"dd/MM/yyyy"));}
        if(ValidationRules.isInformed(criteria.getDataVideoFim())){ hql += " and v.data <= ?"; args.add(DateUtil.getDate(criteria.getDataVideoFim(),"dd/MM/yyyy"));}
        if(ValidationRules.isInformed(criteria.getDataReservaIni())){ hql += " and e.dataReserva >= ?"; args.add(DateUtil.getDate(criteria.getDataReservaIni(),"dd/MM/yyyy"));}
        if(ValidationRules.isInformed(criteria.getDataReservaFim())){ hql += " and e.dataReserva <= ?"; args.add(DateUtil.getDate(criteria.getDataReservaFim(),"dd/MM/yyyy"));}
        if(ValidationRules.isInformed(criteria.getObservacoesReserva())) hql += " and upper(e.observacoesReserva) like '%"+criteria.getObservacoesReserva().toUpperCase()+"%'";
        if(ValidationRules.isInformed(criteria.getDataEmprestimoIni())){ hql += " and e.dataEmprestimo >= ?"; args.add(DateUtil.getDate(criteria.getDataEmprestimoIni(),"dd/MM/yyyy"));}
        if(ValidationRules.isInformed(criteria.getDataEmprestimoFim())){ hql += " and e.dataEmprestimo <= ?"; args.add(DateUtil.getDate(criteria.getDataEmprestimoFim(),"dd/MM/yyyy"));}
        if(ValidationRules.isInformed(criteria.getObservacoesEmprestimo())) hql += " and upper(e.observacoesEmprestimo) like '%"+criteria.getObservacoesEmprestimo().toUpperCase()+"%'";
        if(ValidationRules.isInformed(criteria.getDataDevolucaoIni())){ hql += " and e.dataDevolucao >= ?"; args.add(DateUtil.getDate(criteria.getDataDevolucaoIni(),"dd/MM/yyyy"));}
        if(ValidationRules.isInformed(criteria.getDataDevolucaoFim())){ hql += " and e.dataDevolucao <= ?"; args.add(DateUtil.getDate(criteria.getDataDevolucaoFim(),"dd/MM/yyyy"));}
        if(ValidationRules.isInformed(criteria.getObservacoesDevolucao())) hql += " and upper(e.observacoesDevolucao) like '%"+criteria.getObservacoesDevolucao().toUpperCase()+"%'";
        if(ValidationRules.isInformed(criteria.getUsuarioEmprestimo())) hql += " and e.usuarioEmprestimo.id = '"+criteria.getUsuarioEmprestimo()+"'";
        if(ValidationRules.isInformed(criteria.getSituacao())){
	  		//1 - Devolução
	      	if(criteria.getSituacao().equals("1")){
	      		 hql += " and e.dataDevolucao is not null";
	         	//2 - Reserva
	      	}else if(criteria.getSituacao().equals("2")){
	     		 hql += " and e.dataDevolucao is null";
	     		 hql += " and e.dataEmprestimo is null";
	     		 hql += " and e.dataReserva is not null";
	      	// 3 - Empréstimo
	      	}else if(criteria.getSituacao().equals("3")){
	    		 hql += " and e.dataDevolucao is null";
	     		 hql += " and e.dataEmprestimo is not null";
	      	}
        }

        hql += " order by e.dataReserva desc, e.dataEmprestimo desc, e.dataDevolucao desc, e.usuarioEmprestimo.nome, v.codigo";
      
        List<Emprestimo> emprestimos = super.find(hql,args.toArray());
        return emprestimos;
	}

	public List<Emprestimo> findEmprestimos(PesquisaHistoricoLivroCulturalCriteria criteria){
        String hql = "select e from Emprestimo as e, LivroCultural as l where e.objetoBumerangue.id = l.id";
        ArrayList<Object> args = new ArrayList<Object>();
        if(ValidationRules.isInformed(criteria.getCodigo())) hql += " and l.codigo = '"+criteria.getCodigo()+"'";
        
        if(ValidationRules.isInformed(criteria.getDataEmprestimoIni())){ hql += " and e.dataEmprestimo >= ?"; args.add(DateUtil.getDate(criteria.getDataEmprestimoIni(),"dd/MM/yyyy"));}
        if(ValidationRules.isInformed(criteria.getDataEmprestimoFim())){ hql += " and e.dataEmprestimo <= ?"; args.add(DateUtil.getDate(criteria.getDataEmprestimoFim(),"dd/MM/yyyy"));}
        if(ValidationRules.isInformed(criteria.getObservacoesEmprestimo())) hql += " and upper(e.observacoesEmprestimo) like '%"+criteria.getObservacoesEmprestimo().toUpperCase()+"%'";
        if(ValidationRules.isInformed(criteria.getDataDevolucaoIni())){ hql += " and e.dataDevolucao >= ?"; args.add(DateUtil.getDate(criteria.getDataDevolucaoIni(),"dd/MM/yyyy"));}
        if(ValidationRules.isInformed(criteria.getDataDevolucaoFim())){ hql += " and e.dataDevolucao <= ?"; args.add(DateUtil.getDate(criteria.getDataDevolucaoFim(),"dd/MM/yyyy"));}
        if(ValidationRules.isInformed(criteria.getObservacoesDevolucao())) hql += " and upper(e.observacoesDevolucao) like '%"+criteria.getObservacoesDevolucao().toUpperCase()+"%'";
        if(ValidationRules.isInformed(criteria.getUsuarioEmprestimo())) hql += " and e.usuarioEmprestimo.id = '"+criteria.getUsuarioEmprestimo()+"'";
        if(ValidationRules.isInformed(criteria.getSituacao())){
	  		//1 - Devolução
	      	if(criteria.getSituacao().equals("1")){
	      		 hql += " and e.dataDevolucao is not null";
	         	//2 - Reserva
	      	}else if(criteria.getSituacao().equals("2")){
	     		 hql += " and e.dataDevolucao is null";
	     		 hql += " and e.dataEmprestimo is null";
	     		 hql += " and e.dataReserva is not null";
	      	// 3 - Empréstimo
	      	}else if(criteria.getSituacao().equals("3")){
	    		 hql += " and e.dataDevolucao is null";
	     		 hql += " and e.dataEmprestimo is not null";
	      	}
        }

        hql += " order by e.dataReserva desc, e.dataEmprestimo desc, e.dataDevolucao desc, e.usuarioEmprestimo.nome, l.codigo";
      
        List<Emprestimo> emprestimos = super.find(hql,args.toArray());
        return emprestimos;
	}
	
}

 
