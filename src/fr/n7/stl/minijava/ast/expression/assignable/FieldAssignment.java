/**
 * 
 */
package fr.n7.stl.minijava.ast.expression.assignable;

import fr.n7.stl.minijava.ast.expression.AbstractField;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Abstract Syntax Tree node for an expression whose computation assigns a field in a record.
 * @author Marc Pantel
 *
 */
public class FieldAssignment extends AbstractField implements AssignableExpression {

	/**
	 * Construction for the implementation of a record field assignment expression Abstract Syntax Tree node.
	 * @param _record Abstract Syntax Tree for the record part in a record field assignment expression.
	 * @param _name Name of the field in the record field assignment expression.
	 */
	public FieldAssignment(AssignableExpression _record, String _name) {
		super(_record, _name);
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.impl.FieldAccessImpl#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment code = _factory.createFragment();

		// Code de assignable
		code.append(this.record.getCode(_factory));
			
		// LOADL (deplacement de l'id dans le record)
		//code.add(_factory.createLoadL());
		
		return code;
	}
	
}
