package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.Block;
import fr.n7.stl.minijava.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.type.AtomicType;
import fr.n7.stl.minijava.ast.type.Type;

public class ConstructeurDeclaration implements Declaration {

	private String name;

	private List<ParameterDeclaration> parametres;
	
	private Block corps;

	public ConstructeurDeclaration(String _name, Block _corps) {
		this.name = _name;
		this.corps = _corps;
		this.parametres = new ArrayList<ParameterDeclaration>();
	}
	
	public ConstructeurDeclaration(String _name, Block _corps,List<ParameterDeclaration> _parametres) {
		this.name = _name;
		this.corps = _corps;
		this.parametres = _parametres;
	}

	public String toString() {
		if(this.parametres.isEmpty())
			return this.name + "()" + this.corps.toString();
		else
			return this.name + "(" + this.parametres.toString() + ")" + this.corps.toString();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Type getType() { // Peut-être c'est faux
		return AtomicType.ConstructeurType;
	}
}
