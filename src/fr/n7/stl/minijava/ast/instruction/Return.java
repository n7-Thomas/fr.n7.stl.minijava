/**
 * 
 */
package fr.n7.stl.minijava.ast.instruction;

import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.objet.declaration.MethodeDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Implementation of the Abstract Syntax Tree node for a return instruction.
 * @author Marc Pantel
 *
 */
public class Return implements Instruction {

	protected Expression value;
	
	protected MethodeDeclaration md;
	
	protected String enteteMethode;

	public Return(Expression _value, String entCour) {
		this.value = _value;
		this.enteteMethode = entCour;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "return " + this.value + ";\n";
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.instruction.Instruction#resolve(fr.n7.stl.minijava.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if(_scope.knows(this.enteteMethode)){
			Declaration decl = _scope.get(this.enteteMethode);
			
			if(decl instanceof MethodeDeclaration){
				this.md = (MethodeDeclaration) decl;
				
				
				return this.value.resolve(_scope);
				
			} else {
				Logger.error("La déclaration trouvé n'est pas une méthode");
				return false;
			}
			
		} else {
			Logger.error("Pas pu trouvé la méthode avec son nom : " + this.enteteMethode + " dans le scope " + _scope);
			return false;
		}
		
		
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.minijava.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		if(this.value.getType().compatibleWith(this.md.getType())){
			return true;
		}else{
			Logger.error("Le type de retour ne correspond pas au type de la méthode");
			return false;
		}
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
		Fragment f = _factory.createFragment();
		
		// Code de l'expression
		f.append(this.value.getCode(_factory));
		
		// Return, à modifier car taille paramètres != 2
		f.add(_factory.createReturn(this.value.getType().length(), this.md.getTailleParametres())); // Remove la taille des paramètres
		
		return f;
	}

}
