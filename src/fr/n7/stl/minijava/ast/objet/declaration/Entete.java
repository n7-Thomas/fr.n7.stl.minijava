package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.minijava.ast.type.Type;

public class Entete {
	
	private String name;
	
	private Type type;
	
	private List<ParameterDeclaration> parametres;
	
	public Entete(String _name, String _nom_classe, Type _type, List<ParameterDeclaration> _parametres){
		this.name = _nom_classe + "." + _name;
		this.type = _type;
		if(_parametres != null)
			this.parametres = _parametres;
		else
			this.parametres = new ArrayList<ParameterDeclaration>();
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
