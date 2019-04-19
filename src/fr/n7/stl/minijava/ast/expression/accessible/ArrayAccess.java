/**
 * 
 */
package fr.n7.stl.minijava.ast.expression.accessible;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.expression.AbstractArray;
import fr.n7.stl.minijava.ast.expression.BinaryOperator;
import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Implementation of the Abstract Syntax Tree node for accessing an array element.
 * @author Marc Pantel
 *
 */
public class ArrayAccess extends AbstractArray implements AccessibleExpression {

	/**
	 * Construction for the implementation of an array element access expression Abstract Syntax Tree node.
	 * @param _array Abstract Syntax Tree for the array part in an array element access expression.
	 * @param _index Abstract Syntax Tree for the index part in an array element access expression.
	 */
	public ArrayAccess(Expression _array, Expression _index) {
		super(_array,_index);
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
		
		// On charge l'adresse du tableau
		f.append(this.array.getCode(_factory));
		
		// On charge l'index du type
		f.append(this.index.getCode(_factory));

		// On LOADL taille elt table
		f.add(_factory.createLoadL(this.array.getType().length()));
		
		// On multiplie
		f.add(TAMFactory.createBinaryOperator(BinaryOperator.Multiply));
		
		// On ajoute
		f.add(TAMFactory.createBinaryOperator(BinaryOperator.Add));
		
		// Loadi
		f.add(_factory.createLoadI(this.array.getType().length()));
		
		return f;
	}

}
