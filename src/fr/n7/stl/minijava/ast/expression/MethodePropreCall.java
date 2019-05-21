package fr.n7.stl.minijava.ast.expression;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.MethodeDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class MethodePropreCall implements Expression {

	private String nomClasse;

	private String nomMethode;

	private List<Expression> arguments;

	private MethodeDeclaration declaration;

	public MethodePropreCall(String nom_classe, String methode, List<Expression> _arguments) {
		this.nomClasse = nom_classe;
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
			Logger.error("les arguments sont pas resolvables");
			return false;
		}

		Declaration decl = _scope.get(this.nomClasse);

		if (!(decl instanceof ClasseDeclaration)) {
			Logger.error("La déclaration n'est pas une Classe Declaration");
			return false;
		}

		ClasseDeclaration cd = (ClasseDeclaration) decl;
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
