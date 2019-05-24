/**
 * 
 */
package fr.n7.stl.minijava.ast.expression.accessible;

import fr.n7.stl.minijava.ast.expression.AbstractIdentifier;
import fr.n7.stl.minijava.ast.expression.AbstractUse;
import fr.n7.stl.minijava.ast.instruction.declaration.ConstantDeclaration;
import fr.n7.stl.minijava.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.minijava.ast.instruction.declaration.VariableDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.AttributDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Implementation of the Abstract Syntax Tree node for a variable use expression.
 * @author Marc Pantel
 * TODO : Should also hold a function and not only a variable.
 */
public class IdentifierAccess extends AbstractIdentifier implements AccessibleExpression {
	
	protected AbstractUse expression;
	
	/**
	 * Creates a variable use expression Abstract Syntax Tree node.
	 * @param _name Name of the used variable.
	 */
	public IdentifierAccess(String _name) {
		super(_name);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override 
	public String toString() {
		return this.name;
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.expression.Expression#resolve(fr.n7.stl.minijava.ast.scope.HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if (((HierarchicalScope<Declaration>)_scope).knows(this.name)) {
			Declaration _declaration = _scope.get(this.name);
		
			if (_declaration instanceof VariableDeclaration) {
				this.expression = new VariableUse((VariableDeclaration) _declaration);
				return true;
			} else if(_declaration instanceof ConstantDeclaration) {
				this.expression = new ConstantUse((ConstantDeclaration) _declaration);
				return true;
			} else if(_declaration instanceof ParameterDeclaration) {
				this.expression = new ParameterUse((ParameterDeclaration) _declaration);
				return true;
			} else if(_declaration instanceof AttributDeclaration) {
				this.expression = new AttributUse((AttributDeclaration) _declaration);
				return true;
			} else if(_declaration instanceof ClasseDeclaration) {
				this.expression = new ClasseUse((ClasseDeclaration) _declaration);
				return true;
			} else {
					Logger.error("The declaration for " + this.name + " is of the wrong kind.");
					return false;
			}
		} else {
			Logger.error("The identifier " + this.name + " has not been found.");
			return false;	
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Expression#getType()
	 */
	@Override
	public Type getType() {
		return this.expression.getType();
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		return this.expression.getCode(_factory);
	}

}
