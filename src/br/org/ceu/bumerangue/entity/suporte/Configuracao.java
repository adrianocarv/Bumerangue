package br.org.ceu.bumerangue.entity.suporte;

import br.org.ceu.bumerangue.entity.BaseEntity;

public class Configuracao extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	public static final String SUFIXO_DESCRICAO_CHAVE = ".descricao";
	public static final String SUFIXO_VALIDACAO_CHAVE = ".validacao";

	public static final int VALIDACAO_TEXTO = 1;
	public static final int VALIDACAO_NUMERO = 2;
	public static final int VALIDACAO_BOOLEAN = 3;
	public static final int VALIDACAO_E_MAIL = 4;
	public static final int VALIDACAO_NUMERO_POSITIVO = 5;

	public static final int VALIDACAO_GRUPO_REQUERIDO = 20;
	public static final int VALIDACAO_GRUPO_REQUER_BOOT = 40;

	//
	// Atributos persistentes
	//

	//
	// Atributos de auditoria
	//

	//
	// Outros Atributos
	//

	//
	// Contrutores
	//
    
	//
	// Métodos de negócio
	//

    //
	// Métodos acessores default
	//
}
