/**
 * ValidatorRegras.java 
 * 07/04/2006
 * @author Adriano Carvalho
 */
package br.org.ceu.bumerangue.util;

import java.text.SimpleDateFormat;


//***************************************************************************************
/** Classe usada para validação de valores, geralmente utilizada na camada Controller */
//***************************************************************************************
public class ValidationRules {

	//------------------------------------------------------------------------------
	/** Verifica se o valor informado é diferente de null e diferente de branco. */
	//------------------------------------------------------------------------------
	public static boolean isInformed( String value ) {
		return (value == null) ? false : (value.trim().equals("")) ? false : true ;	
	}	

	//------------------------------------------------------------------------------
	/** Verifica se o valor tem a largura compreendida entre os valores min e max (extremidades).
	 *  Para desconsiderar a validação de qualquer uma dessas extremidades, use um valor negativo (-1) no parâmetro. */
	//------------------------------------------------------------------------------
	public static boolean isValidRange( String value, int min, int max ) {
	    if(min >= 0 && max >= 0){
	        if (value == null) return false;
	        return (value.length() >= min && value.length() <= max); 
	    }else if(min >= 0){
	        if (value == null) return false;
	        return (value.length() >= min); 
	    }else if(max >= 0){
	        if (value == null) return false;
	        return (value.length() <= max); 
	    }else{
	        return true;
	    }
	}	

	//------------------------------------------------------------------------------
	/** Verifica se o valor está compreendido entre os valores min e max (extremidades).
	 *  Para desconsiderar a validação de qualquer uma dessas extremidades, use um valor null no parâmetro. */
	//------------------------------------------------------------------------------
	public static boolean isValidInterval( String value, Double min, Double max ) {
	    if(!ValidationRules.isFloat(value)) return false;

		if(min != null && max != null){
	        return (Double.parseDouble(value) >= min.doubleValue() && Double.parseDouble(value) <= max.doubleValue()); 
	    }else if(min != null){
	        return (Double.parseDouble(value) >= min.doubleValue()); 
	    }else if(max != null){
	        return (Double.parseDouble(value) <= max.doubleValue()); 
	    }else{
	        return true;
	    }
	}	

	//------------------------------------------------------------------------------
	/** Verifica se o valor informado é um número inteiro. */
	//------------------------------------------------------------------------------
	public static boolean isInteger( String value ) {
		try {
			Long.parseLong(value) ;
			return true ;
		} catch( NumberFormatException e ) {
		    return false ;
		}			
	}	
	
	//------------------------------------------------------------------------------
	/** Verifica se o valor informado é um número decimal.  */
	//------------------------------------------------------------------------------
	public static boolean isFloat( String value ) {
		try {
			Double.parseDouble(value) ;
			return true ;
		} catch( NumberFormatException e ) {
			return false ;
		}			
	}

	//------------------------------------------------------------------------------
	/** Verifica se o valor informado é uma data no formato dd/MM/yyyy */
	//------------------------------------------------------------------------------
	public static boolean isDateDDMMYYYY( String value ) {
		try {
		    if(value == null || value.length() != 10) return false;
		    StringBuffer sb = new StringBuffer(value);
            if (new Integer(sb.substring(6)).intValue() < 1950) return false;
		    sb.deleteCharAt(2);
		    sb.deleteCharAt(4);
		    if(!isInteger(sb.toString())) return false;
		    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy") ;
			df.setLenient(false);
			df.parse( value );
			return true ;
		} catch( Exception e ) {
			return false ;
		}			
	}

	//------------------------------------------------------------------------------
	/** Verifica se o e-mail é valido */
	//------------------------------------------------------------------------------
	public static boolean isValidEmail(String value ) {
		boolean emailvalido = (value.indexOf("@") > 0) && (value.indexOf("@")+1 < (value.lastIndexOf(".")) && (value.lastIndexOf(".") < value.length()) );
		return emailvalido;
	}
}