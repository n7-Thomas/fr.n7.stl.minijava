package fr.n7.stl.minijava.ast.objet;

import java.util.List;

import fr.n7.stl.minijava.ast.scope.Declaration;

public class MethodeDefinition implements Definition {
	private List<Keyword> keywords;
	
	private Declaration declaration;
	
	public MethodeDefinition(List<Keyword> keywords, Declaration declaration){
		this.keywords = keywords;
		this.declaration = declaration;
	}
	
	public String toString(){
		return this.keywords.toString() + " " + this.declaration.toString();
	}
	
}
