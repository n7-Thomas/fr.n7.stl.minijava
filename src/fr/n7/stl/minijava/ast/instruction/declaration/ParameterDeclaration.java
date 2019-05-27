/**
 * 
 */
package fr.n7.stl.minijava.ast.instruction.declaration;

import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Register;

/**
 * Abstract Syntax Tree node for a formal parameter in a function declaration.
 * @author Marc Pantel
 */
public class ParameterDeclaration implements Declaration {
	
	/**
	 * Name of the formal parameter
	 */
	protected String name;
	
	/**
	 * AST node for the type of the formal parameter
	 */
	protected Type type;
	
	
	protected Register register;
	
	/**
	 * Offset of the formal parameter in the list of parameters for the function
	 * i.e. the size of the memory allocated to the previous parameters
	 */
	protected int offset;

	/**
	 * Builds an AST node for a formal parameter declaration
	 * @param _name : Name of the formal parameter
	 * @param _type : AST node for the type of the formal parameter
	 */
	public ParameterDeclaration(String _name, Type _type) {

		this.name = _name;
		this.type = _type;
		this.offset = -1; // This value should never occur...
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Declaration#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.type + " " + this.name;
	}

	/**
	 * Provide the type of the formal parameter in the list of formal parameters for the function
	 * @return Type of the formal parameter
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * Provide the offset of the formal parameter in the list of formal parameters for the function
	 * @return Offset of the formal parameter
	 */
	public int getOffset() {
		return this.offset;
	}
	
	public void setOffset(int offset){
		this.offset = offset;
	}
	
	
	public int allocateMemory(Register _register, int _offset){
		this.register = _register;
		this.offset = _offset;
		//System.out.println("PD : " + this.name + " = " + _register + " : " + _offset);
		return this.type.length();		
	}
	
	

}
