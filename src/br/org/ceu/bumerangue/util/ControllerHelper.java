package br.org.ceu.bumerangue.util;

/**
 * Agregador de helpers para o Controller
 * 
 */
public class ControllerHelper{

	private RequestParamUtils requestParamUtils;
	private TokenUtils tokenUtils;

	public RequestParamUtils getRequestParamUtils() {
		return requestParamUtils;
	}

	public void setRequestParamUtils(RequestParamUtils requestParamUtils) {
		this.requestParamUtils = requestParamUtils;
	}

	public TokenUtils getTokenUtils() {
		return tokenUtils;
	}

	public void setTokenUtils(TokenUtils tokenUtils) {
		this.tokenUtils = tokenUtils;
	}

}
