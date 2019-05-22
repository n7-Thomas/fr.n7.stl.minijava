package fr.n7.stl.minijava.ast.expression.allocation;

import fr.n7.stl.minijava.ast.expression.ConstructeurCall;
import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * @author Marc Pantel
 *
 */
public class ClasseAllocation implements Expression {

	protected Type type_classe;
	
	protected ConstructeurCall constructeurCall;
	
	public ClasseAllocation(Type _classe, ConstructeurCall _constructeurCall) {
		this.type_classe = _classe;
		this.constructeurCall = _constructeurCall;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "new " + this.constructeurCall; 
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.expression.Expression#resolve(fr.n7.stl.minijava.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		return this.type_classe.resolve(_scope) && this.constructeurCall.resolve(_scope);
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Expression#getType()
	 */
	@Override
	public Type getType() {
		return this.type_classe;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		return null;
	}

}
