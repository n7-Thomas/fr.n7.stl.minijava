/**
 * 
 */
package fr.n7.stl.minijava.ast.expression.assignable;

import fr.n7.stl.minijava.ast.expression.AbstractAttribut;
import fr.n7.stl.minijava.ast.expression.BinaryOperator;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class AttributAssignment extends AbstractAttribut implements AssignableExpression {


	public AttributAssignment(AssignableExpression _objet, String _name) {
		super(_objet, _name);
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.impl.FieldAccessImpl#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();

		// On charge l'adresse de l'objet
		f.append(this.objet.getCode(_factory));
		
		// On charge l'index de l'attribut
		f.append(this.attribut.getCode(_factory));

		// On ajoute
		f.add(TAMFactory.createBinaryOperator(BinaryOperator.Add));
		
		return f;
	}
	
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if (super.resolve(_scope)) {
			if (this.attribut.isPrivate()) {
				Logger.error("L'attribut " + this.name + " est privé et ne peux être atteint ici");
				return false;
			}
			
			if (this.attribut.isFinal()) {
				Logger.error("L'attribut " + this.name + " est final et ne peux être modifié");
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
}
