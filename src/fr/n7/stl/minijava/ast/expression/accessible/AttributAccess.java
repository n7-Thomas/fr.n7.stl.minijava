package fr.n7.stl.minijava.ast.expression.accessible;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.expression.AbstractAttribut;
import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.expression.assignable.AssignableExpression;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class AttributAccess extends AbstractAttribut implements Expression {

	public AttributAccess(Expression objet, String nom_attribut) {
		super(objet, nom_attribut);
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		// Vérifier que objet existe, que Classe.nom_attribut existe, 
		// et lier à la déclaration de l'objet
		throw new SemanticsUndefinedException( "resolve is undefined in FieldAccess.");
	}

	@Override
	public Type getType() {
		throw new SemanticsUndefinedException( "getType is undefined in FieldAccess.");
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException( "getCode is undefined in FieldAccess.");
	}

}
