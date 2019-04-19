/**
 * 
 */
package fr.n7.stl.minijava.ast.expression.allocation;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.expression.BinaryOperator;
import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.expression.MemoryOperator;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.ArrayType;
import fr.n7.stl.minijava.ast.type.AtomicType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * @author Marc Pantel
 *
 */
public class ArrayAllocation implements Expression {

	protected Type element;
	protected Expression size;

	public ArrayAllocation(Type _element, Expression _size) {
		this.element = _element;
		this.size = _size;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "new " + this.element + "[ " + this.size + " ]"; 
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.expression.Expression#resolve(fr.n7.stl.minijava.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean _ok1 = this.element.resolve(_scope);
		boolean _ok2 = this.size.resolve(_scope);
		return _ok1 && _ok2;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Expression#getType()
	 */
	@Override
	public Type getType() {
		if(this.size.getType().compatibleWith(AtomicType.IntegerType)){
			return new ArrayType(this.element);
		}else{
			Logger.error("Size pas de type int");
			return AtomicType.ErrorType;
		}
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
		
		// On charge la taille du tableau
		f.append(this.size.getCode(_factory));
		
		// On charge la taille du type
		f.add(_factory.createLoadL(this.element.length()));
		
		// On multiplie
		f.add(TAMFactory.createBinaryOperator(BinaryOperator.Multiply));
		
		// On alloue
		f.add(TAMFactory.createMemoryOperator(MemoryOperator.Allocation));
		
		return f;
	}

}
