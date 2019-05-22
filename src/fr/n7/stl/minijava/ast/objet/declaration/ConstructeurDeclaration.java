package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.Block;
import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
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
	
	//private String nom_classe;

	private List<ParameterDeclaration> parametres;
	
	private Block corps;
	
	private List<Keyword> keywords;


	public ConstructeurDeclaration(List<Keyword> _keywords, String _name, String _nom_classe, Block _corps, List<ParameterDeclaration> _parametres) {
		if(!_name.equals(_nom_classe))
			Logger.error("Erreur constructeur pas le nom de la classe : " + _nom_classe);
		
		
		this.name = _name;
		//this.nom_classe = _nom_classe;
		this.corps = _corps;
		if(_parametres != null)
			this.parametres = _parametres;
		else
			this.parametres = new ArrayList<ParameterDeclaration>();
		
		if(_keywords != null)
			this.keywords = _keywords;
		else
			this.keywords = new ArrayList<Keyword>();
	}

	public String toString() {
		if(this.parametres.isEmpty())
			return this.keywords.toString() + this.name + "()" + this.corps.toString();
		else
			return this.keywords.toString() + this.name + "(" + this.parametres.toString() + ")" + this.corps.toString();
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
			tds.register(pd);
		}
	
		return this.corps.resolve(tds);
		
	}

	@Override
	public boolean checkType() {
		return this.corps.checkType();
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		throw new SemanticsUndefinedException("allocate memory not imple");
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException("allocate memory not imple");
	}

	public List<ParameterDeclaration> getParametres() {
		return parametres;
	}
}
