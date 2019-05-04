package fr.n7.stl.minijava.ast.objet.definition;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.objet.declaration.Keyword;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class ConstructeurDefinition implements Definition {

	private List<Keyword> keywords;
	
	private Declaration declaration;

	public ConstructeurDefinition(Declaration _declaration) {
		this.declaration = _declaration;
		this.keywords = new ArrayList<Keyword>();
	}
	
	public ConstructeurDefinition(List<Keyword> _keywords, Declaration _declaration) {
		this.declaration = _declaration;
		this.keywords = _keywords;
	}

	public String toString() {
		return this.keywords.toString() + " " + this.declaration.toString();
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
