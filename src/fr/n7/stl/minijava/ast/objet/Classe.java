package fr.n7.stl.minijava.ast.objet;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.instruction.Instruction;
import fr.n7.stl.minijava.ast.scope.Declaration;

import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class Classe implements Objet {
	
	private List<Keyword> keywords; 
	
	private Instruction declaration;
	
	private List<Definition> definitions;
	/*
	private Expression extension;
	
	private List<Definition> definitions;
	
	
	public Classe(Instruction declaration, Expression extension, List<Definition> definitions){
		this.declaration = declaration;
		this.extension = extension;
		this.definitions = definitions;
	}
	*/
	
	public Classe(Instruction _declaration, List<Definition> definitions){
		this.declaration = _declaration;
		this.keywords = new ArrayList<Keyword>();
		this.definitions = definitions;
	}
	
	public Classe(Instruction _declaration, Keyword keyword, List<Definition> definitions){
		this.declaration = _declaration;
		this.keywords = new ArrayList<Keyword>();
		this.keywords.add(keyword);
		this.definitions = definitions;
	}
	
	
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkType() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void allocateMemory(Register _register, int _offset) {
		// TODO Auto-generated method stub

	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString(){
		String str = "";
		str += keywords.toString() + " class " + this.declaration.toString();
		str += " {\n";
		for(Definition def : definitions){
			str += def.toString() + "\n";
		}
		str += "}";
		
		return str;
	}

}
