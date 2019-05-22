package fr.n7.stl.minijava.ast.expression.accessible;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.expression.AbstractAttribut;
import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

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
		throw new SemanticsUndefinedException("getCode is undefined in AttributAccess.");
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if (super.resolve(_scope)) {
			if (this.attribut.isPrivate()) {
				Logger.error("L'attribut " + this.name + " est privé et ne peux être atteint ici");
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

}
