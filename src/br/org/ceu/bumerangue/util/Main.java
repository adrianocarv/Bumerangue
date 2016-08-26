package br.org.ceu.bumerangue.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

	public static void main(String[] args) throws Exception {
		String str = getUrlSource("https://dl.dropboxusercontent.com/u/5702521/cce-literatura/imagem_objeto/AbrE-001.jpg");

		System.out.println(str);
	}

	private static String getUrlSource(String urlString) {

		try {
			URL u = new URL(urlString); 
			HttpURLConnection huc = (HttpURLConnection) u.openConnection();
			huc.setRequestMethod("GET");
			huc.connect();
			return huc.getResponseCode() + "";
		} catch (Exception e) {
			return null;
		}
	}

}
