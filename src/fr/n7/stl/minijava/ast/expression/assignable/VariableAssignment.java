/**
 * 
 */
package fr.n7.stl.minijava.ast.expression.assignable;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.expression.AbstractIdentifier;
import fr.n7.stl.minijava.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.minijava.ast.instruction.declaration.VariableDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.AttributDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.ArrayType;
import fr.n7.stl.minijava.ast.type.AtomicType;
import fr.n7.stl.minijava.ast.type.PointerType;
import fr.n7.stl.minijava.ast.type.RecordType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Abstract Syntax Tree node for an expression whose computation assigns a
 * variable.
 * 
 * @author Marc Pantel
 *
 */
public class VariableAssignment extends AbstractIdentifier implements AssignableExpression {

	protected Declaration declaration;

	/**
	 * Creates a variable assignment expression Abstract Syntax Tree node.
	 * 
	 * @param _name
	 *            Name of the assigned variable.
	 */
	public VariableAssignment(String _name) {
		super(_name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.n7.stl.minijava.ast.expression.AbstractIdentifier#resolve(fr.n7.stl.
	 * minijava.ast.scope.HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if (((HierarchicalScope<Declaration>) _scope).knows(this.name)) {
			Declaration _declaration = _scope.get(this.name);
			if (_declaration instanceof VariableDeclaration) {
				this.declaration = ((VariableDeclaration) _declaration);
				return true;
			} else if (_declaration instanceof ParameterDeclaration) {
				this.declaration = ((ParameterDeclaration) _declaration);
				return true;
			} else if (_declaration instanceof AttributDeclaration) {
				this.declaration = ((AttributDeclaration) _declaration);
				return true;
			} else{
				
				Logger.error("The declaration for " + this.name + " is of the wrong kind: " + _declaration.getClass());
				return false;
			}
		} else {
			Logger.error("The identifier " + this.name + " has not been found.");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.n7.stl.minijava.ast.impl.VariableUseImpl#getType()
	 */
	@Override
	public Type getType() {
		// System.out.println(this.declaration);
		return this.declaration.getType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.n7.stl.minijava.ast.impl.VariableUseImpl#getCode(fr.n7.stl.tam.ast.
	 * TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment code = _factory.createFragment();

		if (this.declaration instanceof ParameterDeclaration) {
			ParameterDeclaration pd = (ParameterDeclaration) this.declaration;
			
			if (this.declaration.getType() instanceof ArrayType) {
				code.add(_factory.createLoad(Register.LB, pd.getOffset(), 1));

			} else if (this.declaration.getType() instanceof PointerType) {
				code.add(_factory.createLoad(Register.LB, pd.getOffset(), 1));
			} else {
				// LOADA @id
				code.add(_factory.createLoadA(Register.LB, pd.getOffset()));
			}
		} else if (this.declaration instanceof VariableDeclaration) {
			VariableDeclaration vd = (VariableDeclaration) this.declaration;
			
			if (this.declaration.getType() instanceof ArrayType) {
				code.add(_factory.createLoad(vd.getRegister(), vd.getOffset(), 1));

			} else if (this.declaration.getType() instanceof PointerType) {
				code.add(_factory.createLoad(vd.getRegister(), vd.getOffset(), 1));
			} else {
				// LOADA @id
				code.add(_factory.createLoadA(Register.LB, vd.getOffset()));
			}
		}
		return code;
	}

}
