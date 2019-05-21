/**
 * 
 */
package fr.n7.stl.minijava.ast.instruction;

import fr.n7.stl.minijava.ast.Block;
import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.AtomicType;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Implementation of the Abstract Syntax Tree node for a conditional instruction.
 * @author Marc Pantel
 *
 */
public class Iteration implements Instruction {

	protected Expression condition;
	protected Block body;

	public Iteration(Expression _condition, Block _body) {
		this.condition = _condition;
		this.body = _body;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "while (" + this.condition + " )" + this.body;
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.instruction.Instruction#resolve(fr.n7.stl.minijava.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean _ok1 = this.condition.resolve(_scope);
		boolean _ok2 = this.body.resolve(_scope);
		return _ok1 && _ok2;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		return this.condition.getType().equals(AtomicType.BooleanType) && this.body.checkType();
		
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#allocateMemory(fr.n7.stl.tam.ast.Register, int)
	 */
	@Override
	public int allocateMemory(Register _register, int _offset) {
		this.body.allocateMemory(_register, _offset);
		return 0;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment code = _factory.createFragment();
		
		int label_number = _factory.createLabelNumber();
		
		// Code de la condition
		code.append(this.condition.getCode(_factory));
		
		// create etiquette debut_while
		code.addPrefix("debut_while_" + label_number + ":");		
		
		// JUMPIF (0) FIN_TANT_QUE
		code.add(_factory.createJumpIf("fin_while_" + label_number, 0));
			
		// Code du bloc then
		code.append(this.body.getCode(_factory));
			
		// JUMP DEBUT_WHILE
		code.add(_factory.createJump("debut_while_" + label_number));
			
		// create etiquette fin_while
		code.addSuffix("fin_while_" + label_number + ":");
		
		return code;
	}

}
