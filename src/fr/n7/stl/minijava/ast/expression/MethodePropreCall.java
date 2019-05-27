package fr.n7.stl.minijava.ast.expression;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.instruction.Instruction;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.MethodeDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.AtomicType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class MethodePropreCall implements Expression, Instruction {

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
		//System.out.println("this." + this.declaration + " t " + this.declaration.getType());
		return this.declaration.getType();
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
		
		// Code des arguments
		for(Expression e : this.arguments){
			f.append(e.getCode(_factory));
		}
		
		// Ajout de la référence à la classe
		f.add(_factory.createLoad(Register.LB, -1, 1));		
				
		// Appeler la fonction
		f.add(_factory.createCall(this.declaration.getLabel(), Register.LB));
		
		return f;
	}

	@Override
	public boolean checkType() {
		return this.getType().equals(AtomicType.VoidType);
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {	
		return 0;
	}

}
