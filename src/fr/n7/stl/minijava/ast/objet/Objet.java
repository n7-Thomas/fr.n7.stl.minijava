package fr.n7.stl.minijava.ast.objet;

import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Représente une classe ou une interface
 * @author tdarget
 *
 */
public interface Objet {
	public String toString();
	public boolean resolve(HierarchicalScope<Declaration> _scope);
	public boolean checkType();
	public void allocateMemory(Register _register, int _offset);
	public Fragment getCode(TAMFactory _factory);
	
}
