package fr.n7.stl.minijava.ast.expression;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.MethodeDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.ClasseType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class MethodeCall implements Expression {

	private Expression objet;

	private String nomMethode;

	private List<Expression> arguments;
	
	private MethodeDeclaration declaration;

	public MethodeCall(Expression objet, String methode, List<Expression> _arguments) {
		this.objet = objet;
		this.nomMethode = methode;
		if (_arguments != null) {
			this.arguments = _arguments;
		} else {
			this.arguments = new ArrayList<Expression>();
		}
		
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
		
		if(!this.objet.resolve(_scope)) {
			Logger.error("Erreur resolve de l'objet");
			return false;
		}
		
		Type _typeObjet = this.objet.getType();
		
		if (!(_typeObjet instanceof ClasseType && _typeObjet.resolve(_scope))) {
			Logger.error("le type n'a pas pu etre resolve");
			return false;
		}

		ClasseDeclaration cd = ((ClasseType) _typeObjet).getDeclaration();
		this.declaration = cd.getMethode(this.nomMethode, this.arguments);
		
		if (this.declaration != null) {
			return true;
		} else {
			Logger.error("La méthode n'a pas pu être trouvé");
			return false;
		}
	}

	@Override
	public Type getType() {
		return this.declaration.getType();
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException("get code methode call");
	}

}
