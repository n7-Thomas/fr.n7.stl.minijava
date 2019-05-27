package fr.n7.stl.minijava.ast.expression;

import java.util.ArrayList;
import java.util.List;
import fr.n7.stl.minijava.ast.instruction.Instruction;
import fr.n7.stl.minijava.ast.objet.declaration.ClasseDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.InterfaceDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.MethodeDeclaration;
import fr.n7.stl.minijava.ast.objet.declaration.ObjetDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.AtomicType;
import fr.n7.stl.minijava.ast.type.ClasseType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class MethodeCall implements Expression, Instruction {

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
	
		if (!this.objet.resolve(_scope)) {
			Logger.error("Erreur resolve de l'objet");
			return false;
		}

		Type _typeObjet = this.objet.getType();

		if (!(_typeObjet instanceof ClasseType && _typeObjet.resolve(_scope))) {
			Logger.error("le type n'a pas pu etre resolve");
			return false;
		}

		ObjetDeclaration cd = ((ClasseType) _typeObjet).getDeclaration();
		
		if(cd instanceof InterfaceDeclaration)
			return false;
		
		
		this.declaration = ((ClasseDeclaration) cd).getMethode(this.nomMethode, this.arguments);

		if (this.declaration != null) {

			if (this.declaration.isPrivate()) {
				Logger.error("La méthode " + this.nomMethode + " est privée et n'est pas accessible ici");
				return false;
			}

			return true;
		} else {
			Logger.error("La méthode " + this.nomMethode + " n'a pas pu être trouvé");
			return false;
		}
	}

	@Override
	public Type getType() {
		return this.declaration.getType();
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
		
		// Code des arguments
		for(Expression e : this.arguments){
			f.append(e.getCode(_factory));
		}
		
		// Code de l'objet
		f.append(this.objet.getCode(_factory));
				
		// Appeler la fonction
		f.add(_factory.createCall(this.declaration.getLabel(), Register.LB));
		
		return f;
	}

	@Override
	public boolean checkType() {
		// Dans le cas où on appelle checkType c'est que MethodeCall 
		// est une instruction donc est appelée avec call => return void
		return this.getType().equals(AtomicType.VoidType);
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {	
				
		return 0;
	}

}
