package fr.n7.stl.minijava.ast.type;

import java.util.List;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.ObjetDeclaration;
import fr.n7.stl.minijava.ast.objet.heritage.Instanciation;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.util.Logger;

public class ClasseType implements Type {

	private Instanciation instanciation;

	private ObjetDeclaration declaration;
		

	public ClasseType(Instanciation _instanciation) {
		this.instanciation = _instanciation;
	}

	@Override
	public boolean equalsTo(Type _other) {
		if(_other instanceof ClasseType){
			return this.instanciation.equalsTo(((ClasseType) _other).instanciation);
		} else {
			throw new SemanticsUndefinedException("equals to pas impl");
			//return false;
		}
	}

	@Override
	public boolean compatibleWith(Type _other) {
		if(_other instanceof ClasseType){
			ClasseType other = ((ClasseType) _other);
			
			//System.out.println("Compatible ? " + this.instanciation + " =? " + ((ClasseType) _other).instanciation + " decl :" + this.declaration);
			
			if(this.instanciation.compatibleWith(other.instanciation))
				return true;
			
			if(other.declaration instanceof ClasseDeclaration){
				List<Instanciation> interfacesRealises = ((ClasseDeclaration) other.declaration).getInterfacesRealisees();
				Instanciation classeHerite = ((ClasseDeclaration) other.declaration).getClasseHeritee();
				
				if(this.instanciation.compatibleWith(classeHerite))
					return true;
				
				for(Instanciation itfs : interfacesRealises){
					if(this.instanciation.compatibleWith(itfs))
						return true;
				}
				
			}		

			return false;
		} else {
			throw new SemanticsUndefinedException("compatiblewith to pas impl");
			//return false;
		}
	}

	@Override
	public Type merge(Type _other) {
		throw new SemanticsUndefinedException("merge pas impl");
	}

	@Override
	public int length() {
		return this.declaration.getLength();
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		String name = this.instanciation.getName();
		if (this.declaration == null) {
			if (_scope.knows(name)) {
				try {
					ObjetDeclaration _declaration = (ObjetDeclaration) _scope.get(name);
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
	public String toString() {
		return this.instanciation.getName();
	}

	public ObjetDeclaration getDeclaration() {
		return this.declaration;
	}

	public String getTailleMemoire() {
		// TODO Auto-generated method stub
		return null;
	}

}
