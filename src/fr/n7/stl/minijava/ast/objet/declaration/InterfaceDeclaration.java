package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class InterfaceDeclaration implements ObjetDeclaration {

	private String name;

	private List<Entete> entetes;

	public InterfaceDeclaration(String _name, List<Entete> _entetes) {
		this.name = _name;
		if(_entetes != null)
			this.entetes = _entetes;
		else
			this.entetes = new ArrayList<Entete>();
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if(!_scope.contains(this.name)){
			_scope.register(this);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkType() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Type getType() {
		//TODO
		return null;
	}
	
	public String toString() {
		return this.getName();
	}

}
