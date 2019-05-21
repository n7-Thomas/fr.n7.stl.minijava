/**
 * 
 */
package fr.n7.stl.minijava.ast.instruction;

import java.util.Optional;

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
public class Conditional implements Instruction {

	protected Expression condition;
	protected Block thenBranch;
	protected Optional<Block> elseBranch;

	public Conditional(Expression _condition, Block _then, Block _else) {
		this.condition = _condition;
		this.thenBranch = _then;
		this.elseBranch = Optional.of(_else);
	}

	public Conditional(Expression _condition, Block _then) {
		this.condition = _condition;
		this.thenBranch = _then;
		this.elseBranch = Optional.empty();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "if (" + this.condition + " )" + this.thenBranch + ((this.elseBranch.isPresent())?(" else " + this.elseBranch.get()):"");
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.instruction.Instruction#resolve(fr.n7.stl.minijava.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean _ok1 = this.condition.resolve(_scope);
		boolean _ok2 = this.thenBranch.resolve(_scope);
		if (this.elseBranch.isPresent())
			return _ok1 && _ok2 && this.elseBranch.get().resolve(_scope);
		else
			return _ok1 && _ok2;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		boolean _okElse = true;
		if(this.elseBranch.isPresent())
			_okElse = this.elseBranch.isPresent();

		
		return this.condition.getType().equals(AtomicType.BooleanType) && this.thenBranch.checkType() && _okElse;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#allocateMemory(fr.n7.stl.tam.ast.Register, int)
	 */
	@Override
	public int allocateMemory(Register _register, int _offset) {
		this.thenBranch.allocateMemory(_register, _offset);
		if(this.elseBranch.isPresent())
			this.elseBranch.get().allocateMemory(_register, _offset);
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
				
		if(this.elseBranch.isPresent()){
			// JUMPIF (0) ELSE
			code.add(_factory.createJumpIf("else_" + label_number, 0));
			
			// Code du bloc then
			code.append(this.thenBranch.getCode(_factory));
			
			// JUMP FIN_IF
			code.add(_factory.createJump("fin_if_" + label_number));
			
			// create etiquette else_label_number_else
			code.addSuffix("else_" + label_number + ":");
			
			// Code du bloc else
			code.append(this.elseBranch.get().getCode(_factory));
			

		}else{
			// JUMPIF (0) FIN_IF
			code.add(_factory.createJumpIf("fin_if_" + label_number, 0));
			
			// Code du bloc then
			code.append(this.thenBranch.getCode(_factory));

		}
		
		// create etiquette else_label_number_fin_if
		code.addSuffix("fin_if_" + label_number + ":");
		
		
		
		return code;
	}

}
