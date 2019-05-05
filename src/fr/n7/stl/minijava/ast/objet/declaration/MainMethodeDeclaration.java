package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.Block;
import fr.n7.stl.minijava.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.type.AtomicType;
import fr.n7.stl.minijava.ast.type.Type;

public class MainMethodeDeclaration implements Declaration {
	
	private String name;
	
	private Type type;
	
	private Block corps;
	
	private List<ParameterDeclaration> parametres;
	
	public MainMethodeDeclaration(Block _corps){
		this.name = "main";
		this.type = AtomicType.VoidType;
		this.corps = _corps;
		this.parametres = new ArrayList<ParameterDeclaration>();
	}
	
	public MainMethodeDeclaration(List<ParameterDeclaration> _parametres, Block _corps){
		this.name = "main";
		this.type = AtomicType.VoidType;
		this.corps = _corps;
		this.parametres = _parametres;
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
		return "public static void main (" + this.parametres.toString() + ")" + this.corps.toString();
	}

}
