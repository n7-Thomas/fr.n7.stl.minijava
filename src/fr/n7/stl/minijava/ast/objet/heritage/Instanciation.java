package fr.n7.stl.minijava.ast.objet.heritage;

import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;

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

	public boolean equalsTo(Instanciation instanciation) {
		return this.name.equals(instanciation.getName());
	}

	public boolean compatibleWith(Instanciation instanciation) {
		return this.equalsTo(instanciation);
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		// A REFAIRE SI PLUSIEURS INSTANCIATIONS
		return _scope.knows(this.name);		
	}
	
	
}
