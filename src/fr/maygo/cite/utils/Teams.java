package fr.maygo.cite.utils;

public enum Teams {
	
	LION("Lion","§4Lion ","§4"),
	REQUIN("Requin","§3Requin ","§3"),
	AIGLE("Aigle","§6Aigle ","§6"),
	INDEFINIE("Indéfini","§8Indéfini ","§8");
	
	String name;
	String displayName;
	String color;
	
	Teams(String name,String displayName,String color){
		this.name = name;
		this.displayName = displayName;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getColor() {
		return color;
	}
	
	public static Teams getTeam(String string) {
		for(Teams ranks : Teams.values()) {
			if(string.equalsIgnoreCase(ranks.getName())) {
				return ranks;
			}
		}
		return Teams.INDEFINIE;
	}
	
}
