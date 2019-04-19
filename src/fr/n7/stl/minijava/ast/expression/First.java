/**
 * 
 */
package fr.n7.stl.minijava.ast.expression;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.AtomicType;
import fr.n7.stl.minijava.ast.type.CoupleType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Abstract Syntax Tree node for an expression extracting the first component in a couple.
 * @author Marc Pantel
 *
 */
public class First implements Expression {

	/**
	 * AST node for the expression whose value must whose first element is extracted by the expression.
	 */
	protected Expression target;

	/**
	 * Builds an Abstract Syntax Tree node for an expression extracting the first component of a couple.
	 * @param _target : AST node for the expression whose value must whose first element is extracted by the expression.
	 */
	public First(Expression _target) {
		this.target = _target;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(fst" + this.target + ")";
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.expression.Expression#resolve(fr.n7.stl.minijava.ast.scope.HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean _ok = this.target.resolve(_scope);
		return _ok;
		
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Expression#getType()
	 */
	@Override
	public Type getType() {
		Type t = this.target.getType();
		
		if(t instanceof CoupleType){
			return ((CoupleType) t).getFirst();
		}
		else
		{
			Logger.error("Target pas de type couple");
			return AtomicType.ErrorType;
		}

	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
		
		// On met le couple en haut de la pile
		f.append(this.target.getCode(_factory));
		
		// On pop (d) n
		int n = ((CoupleType) this.target.getType()).getSecond().length();
		int d = ((CoupleType) this.target.getType()).getFirst().length();

		f.add(_factory.createPop(0, n));
		
		return f;
		
	}

}
