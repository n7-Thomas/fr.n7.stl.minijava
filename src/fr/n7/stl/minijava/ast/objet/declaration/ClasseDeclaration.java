package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.minijava.ast.objet.definition.Definition;
import fr.n7.stl.minijava.ast.objet.definition.Instanciation;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class ClasseDeclaration implements ObjetDeclaration, HierarchicalScope<Declaration> {

	private String name;

	private List<Definition> definitions;

	private List<Definition> constructeurs;

	private Keyword keyword;

	private HierarchicalScope<Declaration> context;

	public ClasseDeclaration(String _name, List<Definition> _definitions) {
		this.name = _name;

		this.setKeyword(Keyword.NONE);

		this.definitions = new LinkedList<Definition>();
		this.constructeurs = new LinkedList<Definition>();

		for (Definition _definition : _definitions) {
			if (!(_definition instanceof ConstructeurDeclaration))
				this.definitions.add(_definition);
			else
				this.constructeurs.add(_definition);
		}

	}

	public ClasseDeclaration(String _name, Keyword _keyword, List<Definition> _definitions) {
		this.name = _name;

		this.setKeyword(_keyword);

		this.definitions = new LinkedList<Definition>();
		for (Definition _definition : _definitions) {
			if (!(_definition instanceof ConstructeurDeclaration))
				this.definitions.add(_definition);
			else
				this.constructeurs.add((ConstructeurDeclaration) _definition);
		}
	}

	public ClasseDeclaration(Instanciation instanciation) {
		this.name = instanciation.getName();

		this.definitions = new LinkedList<Definition>();
	}

	public List<Definition> getElements() {
		return this.definitions;
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		context = _scope;
		if (!_scope.contains(this.name)) {
			_scope.register(this);
			boolean _result = true;
			for (Definition def : this.definitions) {
				_result = _result && def.resolve(this);
			}
			for (Definition def : this.constructeurs) {
				_result = _result && def.resolve(this);
			}
			return _result;
		} else {
			Logger.error("Identifiant : " + this.name + " déjà pris");
			return false;
		}

	}

	@Override
	public boolean checkType() {
		
		boolean ok = true;
		for(Definition def : this.definitions){
			ok = ok && def.checkType();
		}
		
		for(Definition def : this.constructeurs){
			ok = ok && def.checkType();
		}
		
		return ok;
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		throw new SemanticsUndefinedException("allocate memory pas implémenté");
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		throw new SemanticsUndefinedException("get code pas implémenté");
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Type getType() {
		throw new SemanticsUndefinedException("get Type pas implémenté");
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public Declaration get(String _name) {
		boolean _found = false;
		Iterator<Definition> _iter = this.definitions.iterator();
		Definition _current = null;
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
	public boolean contains(String _name) {
		boolean _result = false;
		Iterator<Definition> _iter = this.definitions.iterator();
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
			this.definitions.add((Definition) _declaration);
		} else {
			throw new IllegalArgumentException();
		}
	}
	/*
	 * @Override public boolean equalsTo(Type _other) { throw new
	 * SemanticsUndefinedException("equals to pas implémenté"); }
	 * 
	 * @Override public boolean compatibleWith(Type _other) { throw new
	 * SemanticsUndefinedException("compatible with to pas implémenté"); }
	 * 
	 * @Override public Type merge(Type _other) { throw new
	 * SemanticsUndefinedException("merge to pas implémenté"); }
	 * 
	 * @Override public int length() { throw new SemanticsUndefinedException(
	 * "length to pas implémenté"); }
	 */

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}

	@Override
	public boolean knows(String _name) {
		return this.contains(_name) || this.context.knows(_name);
	}

	public List<Definition> getConstructeurs() {
		return constructeurs;
	}

	public void setConstructeurs(List<Definition> constructeurs) {
		for (Definition _definition : constructeurs) {
			if (_definition instanceof ConstructeurDeclaration)
				this.constructeurs.add(_definition);
		}
	}

	public ConstructeurDeclaration getConstructeur(List<Expression> arguments) {
		boolean _found = false;
		Iterator<Definition> _iter = this.constructeurs.iterator();
		Definition _current = null;
		while (_iter.hasNext() && (!_found)) {
			_current = _iter.next();
			if(_current instanceof ConstructeurDeclaration){
				
				if(arguments.size() == ((ConstructeurDeclaration) _current).getParametres().size()) {
					boolean correspond = true;
					Iterator<Expression> _iterExpr = arguments.iterator();
					Iterator<ParameterDeclaration> _iterPd = ((ConstructeurDeclaration) _current).getParametres().iterator();
				
					while(_iterExpr.hasNext() && _iterPd.hasNext() && correspond){
						ParameterDeclaration pd = _iterPd.next();
						Expression exp = _iterExpr.next();
						correspond = correspond && pd.getType().compatibleWith(exp.getType());
					}
					
					_found = correspond;
					
				}		
				
			}			
		}
		if (_found) {
			return (ConstructeurDeclaration) _current;
		} else {
			return null;
		}
	}

	public MethodeDeclaration getMethode(String nomMethode, List<Expression> arguments) {
		boolean _found = false;
		Iterator<Definition> _iter = this.definitions.iterator();
		Definition _current = null;
		while (_iter.hasNext() && (!_found)) {
			_current = _iter.next();
			if(_current instanceof MethodeDeclaration){
				
				if(arguments.size() == ((MethodeDeclaration) _current).getParametres().size()) {
					boolean correspond = true;
					Iterator<Expression> _iterExpr = arguments.iterator();
					Iterator<ParameterDeclaration> _iterPd = ((MethodeDeclaration) _current).getParametres().iterator();
				
					while(_iterExpr.hasNext() && _iterPd.hasNext() && correspond){
						ParameterDeclaration pd = _iterPd.next();
						Expression exp = _iterExpr.next();
						correspond = correspond && pd.getType().compatibleWith(exp.getType());
					}
					
					_found = correspond;
					
				}		
				
			}			
		}
		if (_found) {
			return (MethodeDeclaration) _current;
		} else {
			return null;
		}
	}

}
