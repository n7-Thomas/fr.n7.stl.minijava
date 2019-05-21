/**
 * 
 */
package fr.n7.stl.minijava.ast.expression;

import java.util.Iterator;
import java.util.List;

import fr.n7.stl.minijava.ast.instruction.declaration.FunctionDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.AtomicType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Abstract Syntax Tree node for a function call expression.
 * @author Marc Pantel
 *
 */
public class FunctionCall implements Expression {

	/**
	 * Name of the called function.
	 * TODO : Should be an expression.
	 */
	protected String name;
	
	/**
	 * Declaration of the called function after name resolution.
	 * TODO : Should rely on the VariableUse class.
	 */
	protected FunctionDeclaration function;
	
	/**
	 * List of AST nodes that computes the values of the parameters for the function call.
	 */
	protected List<Expression> arguments;
	
	/**
	 * @param _name : Name of the called function.
	 * @param _arguments : List of AST nodes that computes the values of the parameters for the function call.
	 */
	public FunctionCall(String _name, List<Expression> _arguments) {
		this.name = _name;
		this.function = null;
		this.arguments = _arguments;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String _result = ((this.function == null)?this.name:this.function) + "( ";
		Iterator<Expression> _iter = this.arguments.iterator();
		if (_iter.hasNext()) {
			_result += _iter.next();
		}
		while (_iter.hasNext()) {
			_result += " ," + _iter.next();
		}
		return  _result + ")";
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.expression.Expression#resolve(fr.n7.stl.minijava.ast.scope.HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if(_scope.contains(this.name)){
			boolean ok = true;
			this.function = (FunctionDeclaration) _scope.get(this.name);	
			for(Expression e : this.arguments){
				ok = ok && e.resolve(_scope);
			}
			return ok;
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Expression#getType()
	 */
	@Override
	public Type getType() {
		boolean ok = true;
		
		for(int i = 0; i < this.function.getParameters().size(); i++){
			Type t1 = this.function.getParameters().get(i).getType();
			//System.out.println(t1 + " =? " + this.arguments.get(i));
			//System.out.println(this.arguments.get(i).getType());
			Type t2 = this.arguments.get(i).getType();
			ok = ok && t1.compatibleWith(t2);		
		}
		if(ok)
			return this.function.getType();
		else
			return AtomicType.ErrorType;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
		
		// Code des arguments
		for(Expression e : this.arguments){
			f.append(e.getCode(_factory));
		}
		
		// Appeler la fonction
		f.add(_factory.createCall(this.name, Register.LB));
		
		return f;
	}


}
