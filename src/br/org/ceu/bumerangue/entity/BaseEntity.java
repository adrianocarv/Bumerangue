package br.org.ceu.bumerangue.entity;

import org.apache.commons.lang.builder.EqualsBuilder;


/**
 * Classe Base para todos os Entities do Sistema
 * Ps.: Entity/Value type se define nos relacinamentos entre objetos, e não no objeto em si.
 * 
 */
public class BaseEntity extends BaseObject {

	private static final long serialVersionUID = 1L;

	/* id */
	protected String id;
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	
	//
	// Constructors
	//
	
	public BaseEntity(){}
	
	public BaseEntity(String id){
		this.id = id;
	}

	/**
	 * equals default: comparação do identificador (ex.: pk) de um entity.
	 * Para comparar objetos antes de persisti-los (momento em que ainda não existe id), 
	 * recomenda-se implementar o equals usando 'business key' no lugar do identificador. 
	 * Exemplo: ver classe Permission.
	 * -> Artigo: Equals and HashCode (http://www.hibernate.org/109.html) 
	 */
	public boolean equals(Object o) {
		
		if(o == null) return false;
		if (this == o) return true;
		if (!(o instanceof BaseEntity)) return false;

		final BaseEntity that = (BaseEntity) o;

		return new EqualsBuilder().append(this.getId(), that.getId()).isEquals();
	}

	/**
	 * hashCode default: hashCode baseado no tipo da identidade (Long)
	 */
	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

}
