package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.n7.stl.minijava.ast.Block;
import fr.n7.stl.minijava.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.scope.SymbolTable;
import fr.n7.stl.minijava.ast.type.AtomicType;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class ConstructeurDeclaration implements Declaration, Definition {

	private String name;

	private ClasseDeclaration cd;

	private List<ParameterDeclaration> parametres;

	private Block corps;

	private List<Keyword> keywords;

	//private Register register;

	//private int offset;

	private int tailleParametres;

	private String label;

	public ConstructeurDeclaration(List<Keyword> _keywords, String _name, String _nom_classe, Block _corps,
			List<ParameterDeclaration> _parametres) {
		if (!_name.equals(_nom_classe))
			Logger.error("Erreur constructeur pas le nom de la classe : " + _nom_classe);
			
		this.name = _name;
		// this.nom_classe = _nom_classe;
			
		this.corps = _corps;
		if (_parametres != null)
			this.parametres = _parametres;
		else
			this.parametres = new ArrayList<ParameterDeclaration>();

		if (_keywords != null)
			this.keywords = _keywords;
		else
			this.keywords = new ArrayList<Keyword>();
	}

	public String toString() {
		if (this.parametres.isEmpty())
			return this.keywords.toString() + this.name + "()" + this.corps.toString();
		else
			return this.keywords.toString() + this.name + "(" + this.parametres.toString() + ")"
					+ this.corps.toString();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Type getType() { // Peut-être c'est faux
		return AtomicType.ConstructeurType;
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		SymbolTable tds = new SymbolTable(_scope);

		for (ParameterDeclaration pd : this.parametres) {
			if(!pd.getType().resolve(tds)){
				Logger.error("Le type d'un paramètre n'a pu être resolve");
				return false;
			}
				
			tds.register(pd);
		}

		this.cd = (ClasseDeclaration) _scope.get(name);
		
		if(this.cd.isAbstract()){
			Logger.error("Cette classe ne peut avoir de constructeur car elle est abstraite");
			return false;
		}
		

		return this.corps.resolve(tds);

	}

	@Override
	public boolean checkType() {
		return this.corps.checkType();
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		int _size = 1; // 1 car objet pointé

		//this.register = _register;
		//this.offset = _offset;

		List<ParameterDeclaration> pd_reverse = new ArrayList<ParameterDeclaration>(this.parametres);
		Collections.reverse(pd_reverse);

		// On calcule la taille des paramètres
		for (ParameterDeclaration p : pd_reverse) {
			_size += p.allocateMemory(Register.LB, -1 * _size - 1);
		}
		
		this.tailleParametres = _size;
		
		// On alloue la mémoire pour le bloc. Le 3 est réservé pour TAM
		this.corps.allocateMemory(Register.LB, 3);

		return 0;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
	
		this.label = this.cd.getName() + "_" + this.name + "_" + _factory.createLabelNumber();
		
		// Sauter à l'initialisation de la fonction
		f.add(_factory.createJump("fin_" + this.getLabel()));

		// Etiquette de début
		f.addSuffix(this.getLabel() + ":");
		
		// Code du corps
		f.append(this.corps.getCode(_factory));
		
		// Return de l'adresse de l'objet renvoyé
        f.add(_factory.createLoad(Register.LB, -1, 1));
        f.add(_factory.createReturn(this.cd.getLength(), this.tailleParametres));

		// Etiquette de fin
		f.addSuffix("fin_" + this.getLabel() + ":");

		return f;
	}

	public List<ParameterDeclaration> getParametres() {
		return parametres;
	}

	@Override
	public boolean isPrivate() {
		return this.keywords.contains(Keyword.PRIVATE);
	}

	@Override
	public boolean isStatic() {
		// throw new SemanticsUndefinedException("Un constructeur est il
		// statique");
		return true;
	}

	@Override
	public boolean isPublic() {
		return this.keywords.contains(Keyword.PUBLIC);
	}

	@Override
	public boolean isFinal() {
		// throw new SemanticsUndefinedException("Un constructeur est il
		// final");
		return true;
	}

	@Override
	public boolean isAbstract() {
		// throw new SemanticsUndefinedException("Un constructeur est il
		// abstract");
		return false;
	}

	public ClasseDeclaration getClasse() {
		return this.cd;
	}

	public String getLabel() {
		return this.label;
	}

	@Override
	public Declaration copy(String nom_classe) {
		Logger.error("Interdit de copier le constructeur");
		return null;
	}

}
