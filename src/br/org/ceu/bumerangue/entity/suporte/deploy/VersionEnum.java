package br.org.ceu.bumerangue.entity.suporte.deploy;

public enum VersionEnum {
	VERSAO("4.0.23"), DATA_VERSAO("27/11/2014");

	private String value;

	public String value() {
		return value;
	}

	VersionEnum(String value) {
		this.value = value;
	}
}