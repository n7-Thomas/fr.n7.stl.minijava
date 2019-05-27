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

public class MethodeDeclaration implements Declaration, Definition {

	private Entete entete;

	private Block corps;

	private List<Keyword> keywords;
	
	private int tailleParametres;
		
	public MethodeDeclaration(List<Keyword> _keywords, Entete _entete, Block bloc) {
		this.entete = _entete;
		this.corps = bloc;

		if (_keywords == null)
			this.keywords = new ArrayList<Keyword>();
		else
			this.keywords = _keywords;
				
	}

	@Override
	public String getName() {
		return this.entete.getName();
	}

	@Override
	public Type getType() {
		return this.entete.getType();
	}

	public String toString() {
		return keywords.toString() + entete.toString();// + corps.toString();
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if(!this.entete.resolve(_scope)){
			Logger.error("Erreur du resolve de l'entete de la méthode");
			return false;
		}
		
		
		SymbolTable tds = new SymbolTable(_scope);

		for (ParameterDeclaration pd : entete.getParametres()) {
			tds.register(pd);
		}
		
		if(this.corps != null) // Cas méthode abstraite
			return this.corps.resolve(tds);
		else 
			return true;
	}
	
	@Override
	public boolean checkType() {
		if(this.corps != null) // Cas méthode abstraite
			return this.corps.checkType();
		else 
			return true;
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		int _size = 1; // 1 pour l'objet pointé

		//this.register = _register;
		//this.offset = _offset;

		List<ParameterDeclaration> pd_reverse = new ArrayList<ParameterDeclaration>(this.entete.getParametres());
		Collections.reverse(pd_reverse);

		// On calcule la taille des paramètres
		for (ParameterDeclaration p : pd_reverse) {
			//System.out.println("PD: " + this.getName() + "->" +p.getName() + " : " + -1*_size );
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
		
		// Sauter à l'initialisation de la fonction
		f.add(_factory.createJump("fin_" + this.getLabel()));

		// Etiquette de début
		f.addSuffix(this.getLabel() + ":");
		
        f.append(this.corps.getCode(_factory));

        if (this.entete.getType() == AtomicType.VoidType) {
            f.add(_factory.createReturn(0, this.tailleParametres));
        }

		// Etiquette de fin
		f.addSuffix("fin_" + this.getLabel() + ":");

        return f;
	}

	public String getLabel() {
		return this.entete.getLabel();
	}

	public List<ParameterDeclaration> getParametres() {
		return this.entete.getParametres();
	}

	public boolean isPrivate() {
		return this.keywords.contains(Keyword.PRIVATE);
	}
	
	public boolean isStatic() {
		return this.keywords.contains(Keyword.STATIC);
	}

	@Override
	public boolean isPublic() {
		return this.keywords.contains(Keyword.PUBLIC);
	}

	@Override
	public boolean isFinal() { // Bloque la surcharge
		return this.keywords.contains(Keyword.FINAL);
	}
	
	@Override
	public boolean isAbstract() { // Force la surcharge
		return this.keywords.contains(Keyword.ABSTRACT);
	}

	public int getTailleParametres() {
		return this.tailleParametres;
	}
	
	@Override
	public Declaration copy(String nom_classe) {
		return new MethodeDeclaration(this.keywords, this.entete, this.corps);
	}
	
}
