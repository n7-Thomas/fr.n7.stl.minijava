package fr.n7.stl.minijava.ast.expression.accessible;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.expression.AbstractAttribut;
import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class AttributAccess extends AbstractAttribut implements Expression {

	public AttributAccess(Expression objet, String nom_attribut) {
		super(objet, nom_attribut);
	}

	@Override
	public Type getType() {
		return this.attribut.getType();
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException( "getCode is undefined in AttributAccess.");
	}

}
