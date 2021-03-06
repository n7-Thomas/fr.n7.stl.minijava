package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
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

public class MainMethodeDeclaration implements Declaration, Definition {
	
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
	
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		
		SymbolTable tds = new SymbolTable(_scope);
		
		for(ParameterDeclaration pd : parametres){
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
		this.corps.allocateMemory(_register, _offset);
		return 0;		
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
		f.append(this.corps.getCode(_factory));
		return f;		
	}

	@Override
	public boolean isPrivate() {
		return false;
	}

	@Override
	public boolean isStatic() {
		return true;
	}

	@Override
	public boolean isPublic() {
		return true;
	}

	@Override
	public boolean isFinal() {
		return false;
	}

	@Override
	public boolean isAbstract() {
		return false;
	}

	@Override
	public Declaration copy(String nom_classe) {
		Logger.error("Interdit de copier le constructeur");
		return null;
	}

}
