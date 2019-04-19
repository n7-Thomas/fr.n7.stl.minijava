package fr.n7.stl.minijava.ast.expression;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.AtomicType;
import fr.n7.stl.minijava.ast.type.PointerType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.util.Logger;

/**
 * Common elements between left (Assignable) and right (Expression) end sides of
 * assignments. These elements share attributes, toString and getType methods.
 * 
 * @author Marc Pantel
 *
 */
public abstract class AbstractPointer implements Expression {

	/**
	 * AST node that represents an expression whose value is a pointer.
	 */
	protected Expression pointer;

	/**
	 * Construction for the implementation of a pointer content access
	 * expression Abstract Syntax Tree node.
	 * 
	 * @param _pointer
	 *            Abstract Syntax Tree for the pointer expression in a pointer
	 *            content access expression.
	 */
	public AbstractPointer(Expression _pointer) {
		this.pointer = _pointer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(*" + this.pointer + ")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.n7.stl.minijava.ast.expression.Expression#resolve(fr.n7.stl.minijava.ast.
	 * scope.HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		return this.pointer.resolve(_scope);
	}

	/**
	 * Synthesized Semantics attribute to compute the type of an expression.
	 * 
	 * @return Synthesized Type of the expression.
	 */
	public Type getType() {
		Type t = this.pointer.getType();
		if (t instanceof PointerType) {
			return ((PointerType) t).getPointedType();
		} else {
			Logger.error("Not a pointertype");
			return AtomicType.ErrorType;
		}
	}

}