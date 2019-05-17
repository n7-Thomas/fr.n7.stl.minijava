/**
 * 
 */
package fr.n7.stl.minijava.ast.expression.assignable;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.expression.AbstractAttribut;
import fr.n7.stl.minijava.ast.expression.AbstractField;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class AttributPropreAssignment extends AbstractAttribut implements AssignableExpression {

	private String nomClasse;
	
	public AttributPropreAssignment(String nomClasse, String _name) {
		super(null, _name);
		this.nomClasse = nomClasse;
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.impl.FieldAccessImpl#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException("Err");
	}
	
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.expression.Expression#resolve(fr.n7.stl.minijava.ast.scope.HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		
	}
}
