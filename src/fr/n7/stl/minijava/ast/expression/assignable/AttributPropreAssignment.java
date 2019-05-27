/**
 * 
 */
package fr.n7.stl.minijava.ast.expression.assignable;

import fr.n7.stl.minijava.ast.expression.AbstractAttribut;
import fr.n7.stl.minijava.ast.expression.BinaryOperator;
import fr.n7.stl.minijava.ast.objet.declaration.AttributDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class AttributPropreAssignment extends AbstractAttribut implements AssignableExpression {

	private String nomClasse;
	
	

	public AttributPropreAssignment(String nomClasse, String _name) {
		super(null, _name);
		this.nomClasse = nomClasse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.n7.stl.minijava.ast.impl.FieldAccessImpl#getCode(fr.n7.stl.tam.ast.
	 * TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
		
		// Il faut charger l'adresse de l'objet qui est en haut de la pile
		f.add(_factory.createLoad(Register.LB, -1, 1));		
		
		// On charge l'offset à laquel est l'attribut
		f.append(this.attribut.getCode(_factory));

		// On ajoute
		f.add(TAMFactory.createBinaryOperator(BinaryOperator.Add));
		

		return f;
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

		Declaration decl = _scope.get(nomClasse);

		if (decl == null) {
			Logger.error("La déclaration de la classe appelée par this n'a pas été retrouvée");
			return false;
		}

		if (decl instanceof ClasseDeclaration) {

			if (((ClasseDeclaration) decl).contains(this.name)) {
				Declaration attr_decl = ((ClasseDeclaration) decl).get(this.name);

				if (attr_decl instanceof AttributDeclaration) {
					this.attribut = (AttributDeclaration) attr_decl;
					
					if(this.attribut.isFinal()){
						Logger.error("L'attribut " + this.name + " ne peut être modifié car il est final");
						return false;
					}
					
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
}
