package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class AttributDeclaration implements Definition {
	
	private String name;
	private Type type;
	private List<Keyword> keywords;

	public AttributDeclaration(List<Keyword> _keywords, Type _type, String _nom_classe, String _name) {
		this.name = _name;
		this.type = _type;
		if (_keywords != null)
			this.keywords = _keywords;
		else
			this.keywords = new ArrayList<Keyword>();
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
	public String toString() {
		return this.keywords.toString() + this.type.toString() + " " + this.name + ";";
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		return this.type.resolve(_scope);
	}

	@Override
	public boolean checkType() {
		return true; // Attribut déclaration n'a pas besoin de check type
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		throw new SemanticsUndefinedException("allocate memory not impl");
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException("get code not impl");
	}

	public boolean isPrivate() {
		return this.keywords.contains(Keyword.PRIVATE);
	}
	
	public boolean isStatic() {
		return this.keywords.contains(Keyword.STATIC);
	}
	
	public boolean isFinal() {
		return this.keywords.contains(Keyword.FINAL);
	}

}
