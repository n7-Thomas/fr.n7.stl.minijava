/**
 * 
 */
package fr.n7.stl.minijava.ast.instruction.declaration;

import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.instruction.Instruction;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.NamedType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Abstract Syntax Tree node for a variable declaration instruction.
 * @author Marc Pantel
 *
 */
public class VariableDeclaration implements Declaration, Instruction {

	/**
	 * Name of the declared variable.
	 */
	protected String name;
	
	/**
	 * AST node for the type of the declared variable.
	 */
	protected Type type;
	
	/**
	 * AST node for the initial value of the declared variable.
	 */
	protected Expression value;
	
	/**
	 * Address register that contains the base address used to store the declared variable.
	 */
	protected Register register;
	
	/**
	 * Offset from the base address used to store the declared variable
	 * i.e. the size of the memory allocated to the previous declared variables
	 */
	protected int offset;
	
	/**
	 * Creates a variable declaration instruction node for the Abstract Syntax Tree.
	 * @param _name Name of the declared variable.
	 * @param _type AST node for the type of the declared variable.
	 * @param _value AST node for the initial value of the declared variable.
	 */
	public VariableDeclaration(String _name, Type _type, Expression _value) {
		this.name = _name;
		this.type = _type;
		this.value = _value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.type + " " + this.name + " = " + this.value + ";\n";
	}

	/**
	 * Synthesized semantics attribute for the type of the declared variable.
	 * @return Type of the declared variable.
	 */
	public Type getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see fr.n7.minijava.ast.VariableDeclaration#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Synthesized semantics attribute for the register used to compute the address of the variable.
	 * @return Register used to compute the address where the declared variable will be stored.
	 */
	public Register getRegister() {
		return this.register;
	}
	
	/**
	 * Synthesized semantics attribute for the offset used to compute the address of the variable.
	 * @return Offset used to compute the address where the declared variable will be stored.
	 */
	public int getOffset() {
		return this.offset;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.instruction.Instruction#resolve(fr.n7.stl.minijava.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		//System.out.println("VD: " + this);
		if(_scope.contains(this.name)){
			Logger.error("Erreur identifiant déjà pris");
			return false;
		}else{
			boolean ok = this.value.resolve(_scope);
			boolean okType = this.type.resolve(_scope);
			if(ok && okType){
				_scope.register(this);
				return true;
			} else {
				return false;				
			}
		}
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		//System.out.println("\nComparaison types pour " + this.name);
		//System.out.println("ExpressionType: " + this.type);
		//System.out.println("ValueType: " + value.getType());
		//System.out.println(this.value.getType().compatibleWith(this.type));
		//System.out.println(this.type.compatibleWith(this.value.getType()));
		
		if(this.type instanceof NamedType){
			return this.value.getType().compatibleWith(this.type);
		}else{
			return this.type.compatibleWith(this.value.getType());
		}
		
		
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#allocateMemory(fr.n7.stl.tam.ast.Register, int)
	 */
	@Override
	public int allocateMemory(Register _register, int _offset) {
		this.register = _register;
		this.offset = _offset;
		return this.getType().length();
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment code = _factory.createFragment();

		// PUSH Type.Taille
		code.add(_factory.createPush(this.type.length()));
		
		// Code de l'expression
		//System.out.println("Variable Declaration : " + this + " : " + this.value);
		code.append(this.value.getCode(_factory));
		
		// STORE (Type.taille) @id
		code.add(_factory.createStore(this.register, this.offset, this.type.length()));
		
		
		return code;
	}

}
