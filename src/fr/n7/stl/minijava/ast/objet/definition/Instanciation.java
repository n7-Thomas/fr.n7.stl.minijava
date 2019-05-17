package fr.n7.stl.minijava.ast.objet.definition;

public class Instanciation {
	
	private String name;
	
	public Instanciation(String _name){
		this.name = _name;
	}
	
	@Override
	public String toString(){
		return "instanciation" + this.name;
	}

	public String getName() {
		return this.name;
	}
	
	
}
