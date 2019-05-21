/**
 * 
 */
package fr.n7.stl.minijava.ast.expression.assignable;

import fr.n7.stl.minijava.ast.expression.AbstractPointer;
import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Abstract Syntax Tree node for an expression whose computation assigns the content of a pointed cell.
 * @author Marc Pantel
 *
 */
public class PointerAssignment extends AbstractPointer implements AssignableExpression {

	/**
	 * Construction for the implementation of a pointer content assignment expression Abstract Syntax Tree node.
	 * @param _pointer Abstract Syntax Tree for the pointer expression in a pointer content assignment expression.
	 */
	public PointerAssignment(Expression _pointer) {
		super(_pointer);
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.impl.PointerAccessImpl#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
		System.out.println("PointerAssignment: " + this.pointer);
		// On charge l'adresse du pointeur
		f.append(this.pointer.getCode(_factory));
		
		// On Loadi ce qui est en haut de la pile
		f.add(_factory.createLoadI(this.pointer.getType().length()));
		return f;
	}
	
}
