package fr.n7.stl.minijava.ast.objet;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.minijava.ast.type.Type;

public class Entete {
	
	private String name;
	
	private Type type;
	
	private List<ParameterDeclaration> parametres;
	
	public Entete(String _name, Type _type){
		this.name = _name;
		this.type = _type;
		this.parametres = new ArrayList<ParameterDeclaration>();
	}
	
	public Entete(String _name, Type _type, List<ParameterDeclaration> _parametres){
		this.name = _name;
		this.type = _type;
		this.parametres = _parametres;
	}
		
	public String getName() {
		return this.name;
	}

	public Type getType() {
		return this.type;
	}
	
	public List<ParameterDeclaration> getParametres(){
		return this.parametres;
	}
	
	public String toString() {
		if(parametres.isEmpty())
			return this.type.toString() + " " + this.name + "()";
		else
			return this.type.toString() + " " + this.name + "(" + this.parametres.toString() + ")";
	}
}
