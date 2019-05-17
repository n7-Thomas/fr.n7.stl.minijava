package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.objet.definition.Definition;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class ClasseDeclaration implements ObjetDeclaration {

	private String name;

	private List<Definition> definitions;

	private Keyword keyword;

	public ClasseDeclaration(String _name, List<Definition> _definitions) {
		this.name = _name;

		if (_definitions != null)
			this.definitions = _definitions;
		else
			this.definitions = new ArrayList<Definition>();
	}

	public ClasseDeclaration(String _name, Keyword _keyword, List<Definition> _definitions) {
		this.name = _name;

		this.keyword = _keyword;

		if (_definitions != null)
			this.definitions = _definitions;
		else
			this.definitions = new ArrayList<Definition>();
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if (!_scope.contains(this.name)) {
			_scope.register(this);
			
			boolean ok = true;
			
			for (Definition def : this.definitions) {
				ok = ok && def.resolve(_scope);
			}

			return ok;
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
		// TODO
		return null;
	}

	public String toString() {
		return this.getName();
	}

}
