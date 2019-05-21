package fr.n7.stl.minijava.ast.objet.definition;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.ConstructeurDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class ConstructeurCall implements Expression {

	private Type type;

	protected List<Expression> arguments;

	private ConstructeurDeclaration declaration;
	
	public ConstructeurCall(Type type, List<Expression> _arguments) {
		this.type = type;
		if(_arguments == null)
			this.arguments = new ArrayList<Expression>();
		else
			this.arguments = _arguments;
		
		this.declaration = null;
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		String name = this.type.toString(); // A REFAIRE ?
		
		if (this.declaration == null) {
			if (_scope.knows(name)) {
				try {
					ClasseDeclaration cd = (ClasseDeclaration) _scope.get(name);
					
					this.declaration = cd.getConstructeur(this.arguments);
					
					return this.declaration != null;
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
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return this.type.toString() + this.declaration.toString() + "(" + this.arguments.toString() + ")";
	}

}
