package br.org.ceu.bumerangue.entity.suporte.deploy;

public enum VersionEnum {
	VERSAO("4.0.25"), DATA_VERSAO("26/03/2017");

	private String value;

	public String value() {
		return value;
	}

	VersionEnum(String value) {
		this.value = value;
	}
}