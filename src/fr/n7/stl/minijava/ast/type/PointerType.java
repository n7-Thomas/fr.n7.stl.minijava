/**
 * 
 */
package fr.n7.stl.minijava.ast.type;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.util.Logger;

/**
 * Implementation of the Abstract Syntax Tree node for a pointer type.
 * @author Marc Pantel
 *
 */
public class PointerType implements Type {

	protected Type element;

	public PointerType(Type _element) {
		this.element = _element;
	}
	
	public Type getPointedType() {
		return this.element;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Type#equalsTo(fr.n7.stl.minijava.ast.Type)
	 */
	@Override
	public boolean equalsTo(Type _other) {
		if(_other instanceof PointerType){
			Type t = ((PointerType) _other).getPointedType();
			if(this.element.equalsTo(t)){
				return true;
			}else{
				Logger.error("Erreur les 2 sont pas compatibles : " + this.getPointedType() + " avec " + _other);
				return false;
			}
			
		} else {
			Logger.error("Erreur ceci n'est pas une pipe");
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Type#compatibleWith(fr.n7.stl.minijava.ast.Type)
	 */
	@Override
	public boolean compatibleWith(Type _other) {
		if(_other instanceof PointerType){
			Type t = ((PointerType) _other).getPointedType();
			if(this.element.compatibleWith(t)){
				return true;
			}else{
				Logger.error("Erreur les 2 sont pas compatibles : " + this.getPointedType() + " avec " + _other);
				return false;
			}
			
		} else {
			Logger.error("Erreur ceci n'est pas une pipe");
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Type#merge(fr.n7.stl.minijava.ast.Type)
	 */
	@Override
	public Type merge(Type _other) {
		throw new SemanticsUndefinedException("Semantics merge undefined in PointerType.");
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Type#length(int)
	 */
	@Override
	public int length() {
		return 1; //ou ça return this.getPointedType().length();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + this.element + " *)";
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.type.Type#resolve(fr.n7.stl.minijava.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		return this.element.resolve(_scope);
	}

}
