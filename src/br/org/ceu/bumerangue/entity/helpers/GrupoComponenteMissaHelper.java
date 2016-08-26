package br.org.ceu.bumerangue.entity.helpers;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import br.org.ceu.bumerangue.entity.GrupoComponenteMissa;
import br.org.ceu.bumerangue.entity.LiturgiaPalavra;
import br.org.ceu.bumerangue.entity.Oracao;
import br.org.ceu.bumerangue.util.ValidationRules;

public class GrupoComponenteMissaHelper {

	//singleton
	private static GrupoComponenteMissaHelper instance;
	private GrupoComponenteMissaHelper(){}
	public static GrupoComponenteMissaHelper getInstance(){
		if(instance == null)
			instance = new GrupoComponenteMissaHelper();
		return instance;
	}
	
	public boolean isChaveValida(GrupoComponenteMissa grupo){
		
		//verifica o tipo da chave
		if(!grupo.isChaveCalendarioAnoLetivo() &&
				!grupo.isChaveCalendarioLiturgico() &&
				!grupo.isChaveFestaMovel() &&
				!grupo.isChaveOracaoVotiva() 
				){
			grupo.setErrosValidacaoChave("A Chave deve começar com A, L" + (grupo instanceof LiturgiaPalavra ? " ou F." : ", F ou V."));
			return false;
		}

		//valida pelo tipo de chave
		if(this.isChaveCalendarioAnoLetivoValida(grupo) ||
				this.isChaveCalendarioLiturgicoValida(grupo) ||
				this.isChaveFestaMovelValida(grupo) ||
				this.isChaveOracaoVotivaValida(grupo)
		){
			//valida os campos complementares da chave
			if(!this.isCamposChaveValidos(grupo))
				return false;
			
			grupo.setErrosValidacaoChave(null);
			return true;
		}

		return false;
	}

	private boolean isChaveCalendarioAnoLetivoValida(GrupoComponenteMissa grupo){
		if(!grupo.isChaveCalendarioAnoLetivo())
			return false;
		
		String chave = grupo.getChave();
		String prefixo = "A Chave para ano letivo (A) deve ";
		String padrao = ", conforme o padrão: " + (grupo instanceof LiturgiaPalavra ? "Ammdd[O/L]" : "Ammdd[O/L][P/L]");
		
		//valida largura
		if(grupo instanceof LiturgiaPalavra && chave.length() != 6){
			grupo.setErrosValidacaoChave(prefixo+"ter a largura de 6 caracteres"+padrao);
			return false;
		}else if(grupo instanceof Oracao && chave.length() < 7){
			grupo.setErrosValidacaoChave(prefixo+"ter a largura pelo menos 7 caracteres"+padrao);
			return false;
		}
		
		//valida ddmm
		if(!isMMDDValido(chave.substring(1,5))){
			grupo.setErrosValidacaoChave(prefixo+"ter mmdd (mês e dia) válidos"+padrao);
			return false;
		}
		
		//valida [O/L]
		if(chave.toUpperCase().charAt(5) != 'O' && chave.toUpperCase().charAt(5) != 'L'){
			grupo.setErrosValidacaoChave(prefixo+"ter O ou L (Obrigatória ou Livre)"+padrao);
			return false;
		}
		
		//valida [P/L] para Ora��o
		if(grupo instanceof Oracao && chave.toUpperCase().charAt(6) != 'P' && chave.toUpperCase().charAt(6) != 'L'){
			grupo.setErrosValidacaoChave(prefixo+"terminar com P ou O (Própria ou Livre)"+padrao);
			return false;
		}
		return true;
	}

	private boolean isChaveCalendarioLiturgicoValida(GrupoComponenteMissa grupo){
		if(!grupo.isChaveCalendarioLiturgico())
			return false;
		
		String chave = grupo.getChave();
		String prefixo = "A Chave para calendário litúrgico (L) deve ";
		String padrao = ", conforme o padrão: " + (grupo instanceof LiturgiaPalavra ? "LSS[A/B/C/2/3/4/5/6/S][TC/TA/TN/TQ/TP]{[I/P]}" : "LSS[D/2/3/4/5/6/S][TC/TA/TN/TQ/TP]");
		
		//valida largura
		if(	chave.length() != 6 && chave.length() != 7){
			//verifica��o em rela��o ao fragmento opcional: {[I/P]}
			char d = chave.toUpperCase().charAt(3);
			if(chave.length() != 6 && d != 'A' && d != 'B' && d != 'C' ){
				grupo.setErrosValidacaoChave(prefixo+"ter a largura de 6 ou 7 caracteres"+padrao);
				return false;
			}
		}

		//verifica��o em rela��o ao fragmento opcional: {[I/P]}
		//O fragmento {[I/P]} n�o � utilizado com A, B ou C.
		char d = chave.toUpperCase().charAt(3);
		if(chave.length() == 7 && (d == 'A' || d == 'B' || d == 'C') ){
			grupo.setErrosValidacaoChave(prefixo+"ignorar o fragmento opcional {[I/P]}, quando for Domingo"+padrao);
			return false;
		}
		//O fragmento {[I/P]} s� � utilizado com TC.
		String tt = ","+chave.toUpperCase().substring(4,6)+",";
		if(chave.length() == 7 && !tt.equals(",TC,")){
			grupo.setErrosValidacaoChave(prefixo+"ignorar o fragmento opcional {[I/P]}, quando TT não for Tempo Comum"+padrao);
			return false;
		}

		//valida SS
		if(!Character.isDigit(chave.toUpperCase().charAt(1)) || !Character.isDigit(chave.toUpperCase().charAt(2))){
			grupo.setErrosValidacaoChave(prefixo+"ter SS (número da semana) válidos"+padrao);
			return false;
		}
		
		//valida D
		if(grupo instanceof LiturgiaPalavra){
			if(d != 'A' && d != 'B' && d != 'C' && !(d >= '2' && d <= '6') && d != 'S' ){
				grupo.setErrosValidacaoChave(prefixo+"ter A, B ou C (para domingo), ou 2, 3, 4, 5, 6 ou S (de 2  � � sábado)"+padrao);
				return false;
			}
		}else{
			if(d != 'D' && !(d >= '2' && d <= '6') && d != 'S' ){
				grupo.setErrosValidacaoChave(prefixo+"ter D (para domingo), ou 2, 3, 4, 5, 6 ou S (de 2� � sábado)"+padrao);
				return false;
			}
		}

		//valida TT
		if(",TC,TA,TN,TQ,TP,".indexOf(tt) == -1){
			grupo.setErrosValidacaoChave(prefixo+"ter TC, TA, TN, TQ ou TP (Tempo: Comum, Advento, Natal, Quaresma ou P�scoa)"+padrao);
			return false;
		}

		//valida I
		if(grupo instanceof LiturgiaPalavra && chave.length() == 7){
			char i = chave.toUpperCase().charAt(6);
			if(i != 'I' && i != 'P'){
				grupo.setErrosValidacaoChave(prefixo+"ter I valendo I ou P (ímpar ou par)"+padrao);
				return false;
			}
		}
		
		return true;
	}

	private boolean isChaveFestaMovelValida(GrupoComponenteMissa grupo){
		if(!grupo.isChaveFestaMovel())
			return false;

		return true;
	}
	
	private boolean isChaveOracaoVotivaValida(GrupoComponenteMissa grupo){
		if(!grupo.isChaveOracaoVotiva())
			return false;

		return true;
	}

	private boolean isCamposChaveValidos(GrupoComponenteMissa grupo){
		boolean isCampoChaveDescricaoChaveRequerido = grupo.isCampoChaveDescricaoChaveRequerido();
		boolean isCampoChaveFestaMovelRequerido = grupo.isCampoChaveFestaMovelRequerido();
		boolean isCampoChaveOracaoVotivaRequerido = grupo.isCampoChaveOracaoVotivaRequerido();

		boolean isDescricaoChaveInformado = !StringUtils.isBlank(grupo.getDescricaoChave());
		boolean isFestaMovelInformado = grupo.getFestaMovel() != null && !StringUtils.isBlank(grupo.getFestaMovel().getId());
		boolean isOracaoVotivaInformado = grupo instanceof LiturgiaPalavra ? false : ((Oracao)grupo).getOracaoVotiva() != null && ValidationRules.isInformed(((Oracao)grupo).getOracaoVotiva().getId());
		
		if( (isCampoChaveDescricaoChaveRequerido  && !isDescricaoChaveInformado) || (!isCampoChaveDescricaoChaveRequerido && isDescricaoChaveInformado) ){
			grupo.setErrosValidacaoChave("O campo 'Descrição da Chave' "+ (isCampoChaveDescricaoChaveRequerido ? "deve" : "não deve") +" ser informado.");
			return false;
		}

		if( (isCampoChaveFestaMovelRequerido  && !isFestaMovelInformado) || (!isCampoChaveFestaMovelRequerido && isFestaMovelInformado) ){
			grupo.setErrosValidacaoChave("O campo 'Festa Móvel' "+ (isCampoChaveFestaMovelRequerido ? "deve" : "não deve") +" ser informado.");
			return false;
		}

		if( (isCampoChaveOracaoVotivaRequerido  && !isOracaoVotivaInformado) || (!isCampoChaveOracaoVotivaRequerido && isOracaoVotivaInformado) ){
			grupo.setErrosValidacaoChave("O campo 'Oração Votiva' "+ (isCampoChaveOracaoVotivaRequerido ? "deve" : "não deve") +" ser informado.");
			return false;
		}

		return true;
	}
	
	private boolean isMMDDValido(String mmdd){
		if(StringUtils.isBlank(mmdd) || mmdd.length() != 4)
			return false;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
		dateFormat.setLenient(false);
		try{
			dateFormat.parse(mmdd+"2000");
		}catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		LiturgiaPalavra l = new LiturgiaPalavra();
		l.setChave("L11dtti");
		if(l.isChaveValida())
			System.out.println("Chave válida!!!");
		else
			System.out.println(l.getErrosValidacaoChave());
	}
}
