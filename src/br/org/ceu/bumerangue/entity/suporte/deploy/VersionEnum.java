package br.org.ceu.bumerangue.entity.suporte.deploy;

public enum VersionEnum {
	VERSAO("4.0.26"), DATA_VERSAO("21/08/2017");

	private String value;

	public String value() {
		return value;
	}

	VersionEnum(String value) {
		this.value = value;
	}
}