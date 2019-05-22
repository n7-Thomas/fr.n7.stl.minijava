package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.scope.SymbolTable;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.util.Logger;

public class Entete implements Declaration {

	private String name;

	private Type type;

	private List<ParameterDeclaration> parametres;

	public Entete(String _name, String _nom_classe, Type _type, List<ParameterDeclaration> _parametres) {
		this.name = _name;
		this.type = _type;
		if (_parametres != null)
			this.parametres = _parametres;
		else
			this.parametres = new ArrayList<ParameterDeclaration>();
	}

	public String getName() {
		return this.name;
	}

	public Type getType() {
		return this.type;
	}

	public List<ParameterDeclaration> getParametres() {
		return this.parametres;
	}

	public String toString() {
		if (parametres.isEmpty())
			return this.type.toString() + " " + this.name + "()";
		else
			return this.type.toString() + " " + this.name + "(" + this.parametres.toString() + ")";
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean ok = true;

		if (!this.type.resolve(_scope)) {
			Logger.error("Le type de l'entête n'a pas pu être resolve");
			return false;
		}

		SymbolTable tdsEntete = new SymbolTable(_scope);

		for (ParameterDeclaration pd : this.parametres) {
			ok = ok && pd.getType().resolve(_scope);

			if (!tdsEntete.contains(pd.getName())) {
				tdsEntete.register(pd);
			} else {
				Logger.error("Plusieurs parametres ont le même nom");
				return false;
			}

		}
		if (ok) {
			return true;
		} else {
			Logger.error("Resolve entete faux, vérifiez les parametres");
			return false;
		}
	}

}
