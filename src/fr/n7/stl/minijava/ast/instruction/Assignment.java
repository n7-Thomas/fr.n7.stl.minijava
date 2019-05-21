/**
 * 
 */
package fr.n7.stl.minijava.ast.instruction;

import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.expression.assignable.AssignableExpression;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Implementation of the Abstract Syntax Tree node for an array type.
 * @author Marc Pantel
 *
 */
public class Assignment implements Instruction, Expression {

	protected Expression value;
	protected AssignableExpression assignable;

	/**
	 * Create an assignment instruction implementation from the assignable expression
	 * and the assigned value.
	 * @param _assignable Expression that can be assigned a value.
	 * @param _value Value assigned to the expression.
	 */
	public Assignment(AssignableExpression _assignable, Expression _value) {
		this.assignable = _assignable;
		this.value = _value;
		/* This attribute will be assigned to the appropriate value by the resolve action */
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.assignable + " = " + this.value.toString() + ";\n";
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.instruction.Instruction#resolve(fr.n7.stl.minijava.ast.scope.HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean _ok = this.assignable.resolve(_scope);
		boolean _ok1 = this.value.resolve(_scope);
		return _ok && _ok1;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.expression.Expression#getType()
	 */
	@Override
	public Type getType() {
		return this.assignable.getType();
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		//System.out.println("Comparaison : " + this.value.getType());
		//System.out.println(this.assignable.getType());
		return this.value.getType().compatibleWith(this.assignable.getType());
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#allocateMemory(fr.n7.stl.tam.ast.Register, int)
	 */
	@Override
	public int allocateMemory(Register _register, int _offset) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment code = _factory.createFragment();
		
		// Code de l'expression
		code.append(this.value.getCode(_factory));
		
		// Code de l'assignable 
		code.append(this.assignable.getCode(_factory));
		
		// STOREI(taille type)
		code.add(_factory.createStoreI(this.getType().length()));
		
		return code;
	}

}
