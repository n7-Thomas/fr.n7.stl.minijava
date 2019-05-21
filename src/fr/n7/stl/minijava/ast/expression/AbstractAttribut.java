package fr.n7.stl.minijava.ast.expression;

import fr.n7.stl.minijava.ast.objet.declaration.AttributDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.ClasseType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.util.Logger;

/**
 * Common elements between left (Assignable) and right (Expression) end sides of
 * assignments. These elements share attributes, toString and getType methods.
 * 
 * @author Marc Pantel
 *
 */
public abstract class AbstractAttribut implements Expression {

	protected Expression objet;
	protected String name;
	protected AttributDeclaration attribut;

	/**
	 * Construction for the implementation of a record field access expression
	 * Abstract Syntax Tree node.
	 * 
	 * @param _record
	 *            Abstract Syntax Tree for the record part in a record field
	 *            access expression.
	 * @param _name
	 *            Name of the field in the record field access expression.
	 */
	public AbstractAttribut(Expression _objet, String _name) {
		this.objet = _objet;
		this.name = _name;
		System.out.println("AbstractAttribut : " + this.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(this.objet != null)
			return this.objet + "." + this.name;
		else
			return "this." + this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.n7.stl.minijava.ast.expression.Expression#resolve(fr.n7.stl.minijava.
	 * ast.scope.HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if (!this.objet.resolve(_scope)) {
			Logger.error("Impossible de resolve " + this.objet);
			return false;
		}

		Type type = this.objet.getType();

		if (type instanceof ClasseType) {
			ClasseDeclaration cd = ((ClasseType) type).getDeclaration();

			if (cd == null) {
				Logger.error("La déclaration de la classe n'a pas été retrouvée");
				return false;
			}

			if (cd.contains(this.name)) {

				Declaration attr_decl = cd.get(this.name);

				if (attr_decl instanceof AttributDeclaration) {
					this.attribut = (AttributDeclaration) attr_decl;
					return true;
				} else {
					Logger.error("Cette déclaration n'est pas un attribut");
					return false;
				}

			} else {
				Logger.error("Cette classe ne connait pas " + this.name);
				return false;
			}

		} else {
			Logger.error("L'objet n'est pas une classe.");
			return false;
		}
	}

	/**
	 * Synthesized Semantics attribute to compute the type of an expression.
	 * 
	 * @return Synthesized Type of the expression.
	 */
	public Type getType() {
		return this.attribut.getType();
	}

}