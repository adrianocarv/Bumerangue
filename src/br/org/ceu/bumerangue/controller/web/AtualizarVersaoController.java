package br.org.ceu.bumerangue.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class AtualizarVersaoController extends MultiActionBaseController {
	
	public boolean isPertencenteModuloDisponivel(){
		return true;
	}

	public boolean isMetodoAutorizado(){
		return true;
	}

	public ModelAndView validateMulti(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	public ModelAndView novaVersaoDisponivel(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("apresentacao");
	}

	public ModelAndView getURLAtualizacao(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("apresentacao");
	}

}