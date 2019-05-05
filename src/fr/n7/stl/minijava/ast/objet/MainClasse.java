package fr.n7.stl.minijava.ast.objet;

import java.util.List;

import fr.n7.stl.minijava.ast.objet.declaration.MainMethodeDeclaration;
import fr.n7.stl.minijava.ast.objet.definition.Definition;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class MainClasse implements Objet {

	private Declaration declaration;
	
	private MainMethodeDeclaration mainMethode;

	public MainClasse(Declaration _declaration, MainMethodeDeclaration _mainMethode) {
		this.declaration = _declaration;
		this.mainMethode = _mainMethode;
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
	
	public String toString(){
		String str = "";
		str += "public " + this.declaration.toString();
		str += " {\n";
		str += this.mainMethode.toString();
		str += "}";
		
		return str;	
	}

}
