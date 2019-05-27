package fr.n7.stl.minijava.ast.objet.declaration;

import fr.n7.stl.minijava.ast.instruction.Instruction;
import fr.n7.stl.minijava.ast.scope.Declaration;

public interface ObjetDeclaration extends Instruction, Declaration {
	int getLength();
}
