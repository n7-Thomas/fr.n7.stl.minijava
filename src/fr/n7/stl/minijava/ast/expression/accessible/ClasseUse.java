package fr.n7.stl.minijava.ast.expression.accessible;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.expression.AbstractUse;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class ClasseUse extends AbstractUse {
	protected ClasseDeclaration declaration;
	
	public ClasseUse(ClasseDeclaration _declaration) {
		this.declaration = _declaration;
	}

	@Override
	protected Declaration getDeclaration() {
		return this.declaration;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException("get code pas impl");
	}

}
