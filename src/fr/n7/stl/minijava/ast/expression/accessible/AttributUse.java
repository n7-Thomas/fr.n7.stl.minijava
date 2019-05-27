package fr.n7.stl.minijava.ast.expression.accessible;

import fr.n7.stl.minijava.ast.expression.AbstractUse;
import fr.n7.stl.minijava.ast.objet.declaration.AttributDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;


/*
 * Utilisé quand on met pas this, à ne faire que quand on accède à un attribut final
 * 
 */
public class AttributUse extends AbstractUse {
	
	protected AttributDeclaration declaration;
	
	public AttributUse(AttributDeclaration _declaration) {
		this.declaration = _declaration;
	}

	@Override
	protected Declaration getDeclaration() {
		return this.declaration;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
		
		f.append(this.declaration.getCode(_factory));
		
		return f;
	}

}
