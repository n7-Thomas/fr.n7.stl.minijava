package fr.n7.stl.minijava.ast.objet.definition;

import java.util.List;

import fr.n7.stl.minijava.ast.objet.declaration.Keyword;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class AttributDefinition implements Definition {
	
	private List<Keyword> keywords;
	
	private Declaration declaration;
	
	public AttributDefinition(List<Keyword> keywords, Declaration declaration){
		this.keywords = keywords;
		this.declaration = declaration;
	}
	
	public String toString(){
		return this.keywords.toString() + " " + this.declaration.getType() + " " + this.declaration.getName() + ";";
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkType() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
