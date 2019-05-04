package fr.n7.stl.minijava.ast.objet;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.objet.declaration.Entete;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class Interface implements Objet {

	private Declaration declaration;

	private List<Entete> entetes;

	public Interface(Declaration _declaration) {
		this.declaration = _declaration;
		this.entetes = new ArrayList<Entete>();
	}
	
	public Interface(Declaration _declaration, List<Entete> _entetes) {
		this.declaration = _declaration;
		this.entetes = _entetes;
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
	public String toString() {
		String str = "";
		str += "interface " + this.declaration.toString();
		str += " {\n";
		for (Entete entete : entetes) {
			str += entete.toString() + "\n";
		}
		str += "}";

		return str;
	}

}
