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

public class MethodeStaticCall implements Expression {

	private String nomClasse;

	private String nomMethode;

	private List<Expression> arguments;
	
	private MethodeDeclaration declaration;

	public MethodeStaticCall(String nom_classe, String nom_methode, List<Expression> _arguments) {
		this.nomClasse = nom_classe;
		this.nomMethode = nom_methode;
		if (_arguments != null) {
			this.arguments = _arguments;
		} else {
			this.arguments = new ArrayList<Expression>();
		}
		
		this.declaration = null;
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		
		throw new SemanticsUndefinedException("Appel méthode statique pas implémentée");
		/*
		// Vérification que les arguments sont bien resolve
		boolean okArg = true;

		for (Expression arg : this.arguments) {
			okArg = okArg && arg.resolve(_scope);
		}

		if (!okArg) {
			Logger.error("les arguments sont pas bons");
			return false;
		}
		
		if(!_scope.knows(nomClasse)){
			Logger.error("La classe " + nomClasse + " n'est pas connue ");
			return false;		
		}
		
		Declaration	d_obj = _scope.get(nomClasse);
		
		if (!(d_obj.getType() instanceof ClasseType)) {
			Logger.error("L'objet " + d_obj.getName() + " n'est pas une classe");
			return false;
		}
		
		Declaration	d = _scope.get(d_obj.getName().toString());
		
		if (!(d.getType() instanceof ClasseType)) {
			Logger.error("L'objet " + d.getName() + " n'est pas une classe");
			return false;
		}
		

		ClasseDeclaration cd = (ClasseDeclaration) d;
		this.declaration = cd.getMethode(this.nomMethode, this.arguments);
		
		if (this.declaration != null) {	
			if(this.declaration.isPrivate() && !nomClasse.equals(cd.getName())){
				Logger.error("La méthode " + this.nomMethode + " est privée et n'est pas accessible ici");
				return false;
			}
			
			if(!this.declaration.isStatic()){
				Logger.error("La méthode " + this.nomMethode + " n'est pas statique, elle ne peut être invoquée");
				return false;
			}
			
			
			return true;
		} else {
			Logger.error("La méthode n'a pas pu être trouvé");
			return false;
		}*/
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
