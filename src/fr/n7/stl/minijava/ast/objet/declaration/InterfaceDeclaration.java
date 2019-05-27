package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.objet.heritage.ExtensionInterface;
import fr.n7.stl.minijava.ast.objet.heritage.Instanciation;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class InterfaceDeclaration implements ObjetDeclaration, HierarchicalScope<Declaration> {

	private String name;

	private List<Entete> entetes;

	private ExtensionInterface extInt;

	private HierarchicalScope<Declaration> context;

	public InterfaceDeclaration(String _name, List<Entete> _entetes, ExtensionInterface _extension) {
		this.name = _name;
		if (_entetes != null)
			this.entetes = _entetes;
		else
			this.entetes = new ArrayList<Entete>();

		this.extInt = _extension;
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		context = _scope;

		if (!_scope.contains(this.name)) {
			_scope.register(this);
			boolean _result = true;
			for (Entete ent : this.entetes) {
				_result = _result && ent.resolve(this);
			}

			if (this.extInt != null) {

				for (Instanciation toHerite : this.extInt.getHerites()) {
					if (toHerite != null) {

						// Récupération de l'interface héritée
						Declaration decl = _scope.get(toHerite.getName());

						if (decl instanceof InterfaceDeclaration) {
							InterfaceDeclaration itvDecl = (InterfaceDeclaration) decl;

							// Ajout des nouvelles entetes
							for (Entete ent : itvDecl.getEntetes()) {
								if (!this.contains(ent.getName())) {
									this.register(ent);
								}
							}
						} else {
							Logger.error("Tentative de réalisation sur autre chose qu'une interface");
							return false;
						}
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkType() {
		return true; // Les entetes sont bien checktype
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		return 0;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		return _factory.createFragment();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Type getType() {
		throw new SemanticsUndefinedException("get Type pas implémenté");

	}

	public String toString() {
		return this.getName();
	}

	public List<Entete> getEntetes() {
		return this.entetes;
	}

	@Override
	public Declaration get(String _name) {
		boolean _found = false;
		Iterator<Entete> _iter = this.entetes.iterator();
		Entete _current = null;
		while (_iter.hasNext() && (!_found)) {
			_current = _iter.next();
			_found = _found || _current.getName().contentEquals(_name);
		}
		if (_found) {
			return _current;
		} else {
			return this.context.get(_name);
		}
	}

	@Override
	public boolean knows(String _name) {
		return this.contains(_name) || this.context.knows(_name);
	}

	@Override
	public boolean contains(String _name) {
		boolean _result = false;
		Iterator<Entete> _iter = this.entetes.iterator();
		while (_iter.hasNext() && (!_result)) {
			_result = _result || _iter.next().getName().contentEquals(_name);
		}
		return _result;
	}

	@Override
	public boolean accepts(Declaration _declaration) {
		return !this.contains(_declaration.getName());
	}

	@Override
	public void register(Declaration _declaration) {
		if (this.accepts(_declaration)) {
			this.entetes.add((Entete) _declaration);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int getLength() {
		return 0;
	}

}
