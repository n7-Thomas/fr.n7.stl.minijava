package fr.n7.stl.minijava.ast.expression;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.ConstructeurDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.InterfaceDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.ObjetDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.ClasseType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class ConstructeurCall implements Expression {

	private Type type;

	protected List<Expression> arguments;


	private ConstructeurDeclaration declaration;


	public ConstructeurCall(Type type, List<Expression> _arguments) {
		this.type = type;

		if (_arguments == null)
			this.arguments = new ArrayList<Expression>();
		else
			this.arguments = _arguments;

		this.declaration = null;
	
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		// Vérification que les arguments sont bien resolve
		boolean okArg = true;

		for (Expression arg : this.arguments) {
			okArg = okArg && arg.resolve(_scope);
		}

		if (!okArg) {
			Logger.error("les arguments sont pas bons");
			return false;
		}

		if (!(this.type instanceof ClasseType && this.type.resolve(_scope))) {
			Logger.error("le type n'a pas pu etre resolve");
			return false;
		}

		ObjetDeclaration cd = ((ClasseType) this.type).getDeclaration();
		if(cd instanceof InterfaceDeclaration)
			return false;
		
		this.declaration = ((ClasseDeclaration) cd).getConstructeur(this.arguments);
		if(this.declaration != null){
			return true;
		} else {
			Logger.error("Le constructeur n'a pas pu être trouvé");
			return false;
		}
	}

	@Override
	public Type getType() {
		throw new SemanticsUndefinedException("Get type pas implémenté");
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
				
		// Appeler la fonction
		f.add(_factory.createCall(this.declaration.getLabel(), Register.LB));
		
		return f;
	}

	@Override
	public String toString() {
		return this.type.toString() + this.declaration.toString() + "(" + this.arguments.toString() + ")";
	}

	public ClasseDeclaration getClasse() {
		return this.declaration.getClasse();
	}
	
	public List<Expression> getArguments() {
		return arguments;
	}

	
}
