/**
 * 
 */
package fr.n7.stl.minijava.ast.expression.assignable;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.expression.AbstractAttribut;
import fr.n7.stl.minijava.ast.expression.AbstractField;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class AttributAssignment extends AbstractAttribut implements AssignableExpression {


	public AttributAssignment(AssignableExpression _objet, String _name) {
		super(_objet, _name);
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.impl.FieldAccessImpl#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException("Err");
	}
	
}
