package fr.n7.stl.minijava.ast.objet.declaration;

import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public interface Definition extends Declaration
{
	public boolean resolve(HierarchicalScope<Declaration> _scope);
	public boolean checkType(); 
	public int allocateMemory(Register _register, int _offset);
	public Fragment getCode(TAMFactory _factory);
	
	public boolean isPrivate();
	public boolean isStatic();
	public boolean isPublic();
	public boolean isFinal();
	public boolean isAbstract();
	
	public Declaration copy(String nom_classe);
	
}
