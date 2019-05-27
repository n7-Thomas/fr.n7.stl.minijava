package fr.n7.stl.minijava.ast.objet.declaration;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class MainClasseDeclaration implements ObjetDeclaration {

	private String name;

	private MainMethodeDeclaration mainMethode;

	public MainClasseDeclaration(String _name, MainMethodeDeclaration _mainMethode) {
		this.name = _name;
		this.mainMethode = _mainMethode;
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if (_scope.contains(this.name)) {
			return false;
		} else {
			_scope.register(this);
			return this.mainMethode.resolve(_scope);
		}
	}

	@Override
	public boolean checkType() {
		return this.mainMethode.checkType();
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		
		
		this.mainMethode.allocateMemory(_register, _offset);
		
		return 0;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
		
		f.append(this.mainMethode.getCode(_factory));
		
		return f;
		
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Type getType() {
		throw new SemanticsUndefinedException("get Type pas impl");
	}

	public String toString() {
		return this.getName();
	}

	@Override
	public int getLength() {
		return 0;
	}

}
