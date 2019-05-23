package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.List;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class AttributFinalDeclaration extends AttributDeclaration {

	private Expression valeur;
	
	public AttributFinalDeclaration(List<Keyword> _keywords, Type _type, String _nom_classe, String _name, Expression valeur) {
		super(_keywords, _type, _nom_classe, _name);
		this.valeur = valeur;
	}
	
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		return this.valeur.resolve(_scope) && this.type.resolve(_scope);
	}

	@Override
	public boolean checkType() {
		return this.valeur.getType().compatibleWith(this.type);
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		throw new SemanticsUndefinedException("allocate memory not impl");
	}
	
	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException("get code not impl");
	}

}
