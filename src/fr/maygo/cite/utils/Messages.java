package fr.maygo.cite.utils;

public enum Messages {
	
	NOT_PERM("§c§lErreur : §cVous n'avez pas la permission d'éxécuter cette commande !"),
	PREFIX("§8[§6La cité des rois§8] ");
	
	private String message;

	Messages(String msg){
		this.message = msg;
	}
	
	public String get() {
		return message;
	}
	
}
