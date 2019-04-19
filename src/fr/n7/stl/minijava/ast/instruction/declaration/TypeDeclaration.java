package fr.n7.stl.minijava.ast.instruction.declaration;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.instruction.Instruction;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Implementation of the Abstract Syntax Tree node for a type declaration.
 * @author Marc Pantel
 *
 */
public class TypeDeclaration implements Declaration, Instruction {

	/**
	 * Name of the declared type
	 */
	private String name;
	
	/**
	 * AST node for the type associated to the name
	 */
	private Type type;

	/**
	 * Builds an AST node for a type declaration
	 * @param _name : Name of the declared type
	 * @param _type : AST node for the type associated to the name
	 */
	public TypeDeclaration(String _name, Type _type) {
		this.name = _name;
		this.type = _type;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Declaration#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.instruction.Instruction#resolve(fr.n7.stl.minijava.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if(_scope.contains(this.name)){
			throw new SemanticsUndefinedException("Erreur identifiant déjà pris");
		}else{
			boolean ok = this.type.resolve(_scope);
			if(ok){
				_scope.register(this);
				return true;
			} else {
				return false;				
			}
		}
	}

	/**
	 * Provide the type associated to a name in a type declaration.
	 * @return Type from the declaration.
	 */
	public Type getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "typedef " + this.type + " " + this.name + ";\n";
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		return true;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#allocateMemory(fr.n7.stl.tam.ast.Register, int)
	 */
	@Override
	public int allocateMemory(Register _register, int _offset) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		return _factory.createFragment();
	}

}
