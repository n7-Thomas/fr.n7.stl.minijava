/**
 * 
 */
package fr.n7.stl.minijava.ast.expression.accessible;

import fr.n7.stl.minijava.ast.expression.AbstractUse;
import fr.n7.stl.minijava.ast.instruction.declaration.VariableDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Implementation of the Abstract Syntax Tree node for a variable use expression.
 * @author Marc Pantel
 */
public class VariableUse extends AbstractUse {
	
	protected VariableDeclaration declaration;
	
	/**
	 * Creates a variable use expression Abstract Syntax Tree node.
	 * @param _name Name of the used variable.
	 */
	public VariableUse(VariableDeclaration _declaration) {
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
		Fragment _result = _factory.createFragment();
		_result.add(_factory.createLoad(
				this.declaration.getRegister(), 
				this.declaration.getOffset(),
				this.declaration.getType().length()));
		//_result.addComment(this.toString());
		return _result;
	}
	
	public String toString(){
		return "Variable use : " + this.declaration.toString();
	}
	
}
