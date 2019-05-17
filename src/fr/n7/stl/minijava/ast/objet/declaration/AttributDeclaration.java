package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.objet.definition.Definition;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class AttributDeclaration implements Declaration, Definition {
	
	private String name;
	//private String nom_classe;
	private Type type;
	private List<Keyword> keywords;

	public AttributDeclaration(List<Keyword> _keywords, Type _type, String _nom_classe, String _name) {
		this.name = _nom_classe + "." + _name;
		//this.nom_classe = _nom_classe;
		this.type = _type;
		if (_keywords != null)
			this.keywords = _keywords;
		else
			this.keywords = new ArrayList<Keyword>();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return this.keywords.toString() + this.type.toString() + " " + this.name + ";";
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if(_scope.contains(this.name)){
			Logger.error("Erreur identifiant déjà pris");
			return false;
		}else{
			boolean okType = this.type.resolve(_scope);
			if(okType){
				_scope.register(this);
				return true;
			} else {
				return false;				
			}
		}
	}

	@Override
	public boolean checkType() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		// TODO Auto-generated method stub
		return null;
	}

}
