package fr.n7.stl.minijava.ast.type;

import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.objet.definition.Instanciation;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.util.Logger;

public class ClasseType implements Type {
	
	private Instanciation instanciation;
	
	private ClasseDeclaration declaration;
	
	public ClasseType(Instanciation _instanciation){
		this.instanciation = _instanciation;
	}
	
	@Override
	public boolean equalsTo(Type _other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean compatibleWith(Type _other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Type merge(Type _other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int length() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		String name = this.instanciation.getName();
		
		if (this.declaration == null) {
			if (_scope.knows(name)) {
				try {
					ClasseDeclaration _declaration = (ClasseDeclaration) _scope.get(name);
					this.declaration = _declaration;
					return true;
				} catch (ClassCastException e) {
					Logger.error("The declaration for " + name + " is of the wrong kind.");
					return false;
				}
			} else {
				Logger.error("The identifier " + name + " has not been found.");
				return false;
			}
		} else {
			return true;
		}
	}
	
	@Override
	public String toString(){
		return this.instanciation.getName();
	}

}
