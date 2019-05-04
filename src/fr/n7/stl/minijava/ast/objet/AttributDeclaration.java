package fr.n7.stl.minijava.ast.objet;

import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.type.Type;

public class AttributDeclaration implements Declaration {
	
	private String name;
	private Type type;
	
	
	public AttributDeclaration(String _name, Type _type) {
		this.name = _name;
		this.type = _type;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Type getType() {
		return this.type;
	}
	
	@Override
	public String toString(){
		return this.type.toString() + " " + this.name + ";";
	}

}
