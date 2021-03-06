package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class AttributDeclaration implements Definition {
	
	protected String name;
	protected Type type;
	protected List<Keyword> keywords;
	
	
	// Offset dans la classe OU si static alors dans le code
	private int offset;
	private int length;
	

	public AttributDeclaration(List<Keyword> _keywords, Type _type, String _nom_classe, String _name) {
		this.name = _name;
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
		return this.type.resolve(_scope);
	}

	@Override
	public boolean checkType() {
		return true; // Attribut déclaration n'a pas besoin de check type
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		this.offset = _offset;
		this.length = this.type.length();
		
		//System.out.println("AD: "+ this.name + ":" + this.offset + " " + this.length);
		
		return this.length;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment code = _factory.createFragment();
		
		if(isStatic())	{
			code.add(_factory.createPush(this.length));
			code.add(_factory.createLoadL(0));
			code.add(_factory.createStore(Register.SB, this.offset, this.length));
			//code.add(_factory.createLoad(Register.SB, this.offset, this.length));		
		} else {
			// Renvoie l'offset par rapport au début dans le tas de l'objet
			code.add(_factory.createLoadL(this.offset));
		}

		
		return code;
	
	}
	@Override
	public boolean isPrivate() {
		return this.keywords.contains(Keyword.PRIVATE);
	}
	@Override
	public boolean isStatic() {
		return this.keywords.contains(Keyword.STATIC);
	}
	@Override	
	public boolean isFinal() {
		return this.keywords.contains(Keyword.FINAL);
	}

	@Override
	public boolean isPublic() {
		return this.keywords.contains(Keyword.PUBLIC);
	}
	
	@Override
	public boolean isAbstract() {
		return this.keywords.contains(Keyword.ABSTRACT);
	}

	public int getLength() {
		return this.type.length();
	}

	
	public int getOffset(){
		return this.offset;
	}

	@Override
	public Declaration copy(String nom_classe) {
		return new AttributDeclaration(this.keywords, this.type, nom_classe, this.name);
	}
	
	

}
