package fr.maygo.cite.utils;

public enum Messages {
	
	NOT_PERM("�c�lErreur : �cVous n'avez pas la permission d'�x�cuter cette commande !"),
	PREFIX("�8[�6La cit� des rois�8] ");
	
	private String message;

	Messages(String msg){
		this.message = msg;
	}
	
	public String get() {
		return message;
	}
	
}
