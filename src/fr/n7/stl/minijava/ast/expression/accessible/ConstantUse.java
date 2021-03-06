/**
 * 
 */
package fr.n7.stl.minijava.ast.expression.accessible;

import fr.n7.stl.minijava.ast.expression.AbstractUse;
import fr.n7.stl.minijava.ast.instruction.declaration.ConstantDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Implementation of the Abstract Syntax Tree node for a constant use expression.
 * @author Marc Pantel
 */
public class ConstantUse extends AbstractUse {
	
	protected ConstantDeclaration declaration;
	
	/**
	 * Creates a variable use expression Abstract Syntax Tree node.
	 * @param _name Name of the used variable.
	 */
	public ConstantUse(ConstantDeclaration _declaration) {
		this.declaration = _declaration;
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.expression.AbstractUse#getDeclaration()
	 */
	public Declaration getDeclaration() {
		return this.declaration;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.expression.AbstractUse#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	public Fragment getCode(TAMFactory _factory) {
		return this.declaration.getCode(_factory);
	}

}
