package fr.n7.stl.minijava.ast.expression.allocation;

import fr.n7.stl.minijava.ast.expression.MemoryOperator;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.InterfaceDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.ObjetDeclaration;
import fr.n7.stl.minijava.ast.expression.ConstructeurCall;
import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.ClasseType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * @author Marc Pantel
 *
 */
public class ClasseAllocation implements Expression {

	protected Type type_classe;
	
	protected ConstructeurCall constructeurCall;
	
	protected ClasseDeclaration cd;
	
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
		boolean ok = this.type_classe.resolve(_scope) && this.constructeurCall.resolve(_scope);
		ObjetDeclaration od = ((ClasseType) this.type_classe).getDeclaration();
		if(od instanceof InterfaceDeclaration){
			Logger.error("On ne peut instancier une interface");
			return false;
		}		
		this.cd = (ClasseDeclaration) od;
		
		
		return ok;
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
		Fragment f = _factory.createFragment();
		
		// Code des arguments
		for(Expression e : this.constructeurCall.getArguments()){
			f.append(e.getCode(_factory));
		}
			
		// Malloc de la taille de A
		// On charge la taille
		f.add(_factory.createLoadL(this.cd.getTailleMemoire()));
		
		// On alloue et on met en haut de la pile l'adresse du
		f.add(TAMFactory.createMemoryOperator(MemoryOperator.Allocation));
						
		// Appel du constructeur
		f.append(this.constructeurCall.getCode(_factory));
		
		
		return f;
	}
		
}
