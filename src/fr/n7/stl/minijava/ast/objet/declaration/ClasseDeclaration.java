package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fr.n7.stl.minijava.ast.SemanticsUndefinedException;
import fr.n7.stl.minijava.ast.expression.Expression;
import fr.n7.stl.minijava.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.minijava.ast.objet.heritage.Extension;
import fr.n7.stl.minijava.ast.objet.heritage.Instanciation;
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

	private Extension extension;
    
	private int tailleMemoire;
	
	private final int length = 1;

	private Register register;

	private int offset;
		

	public ClasseDeclaration(String _name, Keyword _keyword, List<Definition> _definitions, Extension extension) {
		this.name = _name;

		this.setKeyword(_keyword);

		this.definitions = new LinkedList<Definition>();
		this.constructeurs = new LinkedList<Definition>();

		if (_definitions != null) {
			for (Definition _definition : _definitions) {
				if (!(_definition instanceof ConstructeurDeclaration)) {
					this.definitions.add(_definition);
					if(_definition.isAbstract() && !_keyword.equals(Keyword.ABSTRACT)){
						Logger.error("La classe " + this.name + " possède une méthode abstraite, elle doit donc être abstraite");
						return;
					}
				} else {
					this.constructeurs.add((ConstructeurDeclaration) _definition);
				}
			}
		}

		this.extension = extension;
	}

	public List<Definition> getElements() {
		return this.definitions;
	}

	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		context = _scope;
		boolean _result = true;

		if (this.extension != null) {
			if (!this.extension.resolve(_scope)) {
				Logger.error("L'héritage ou la réalisation n'a pas pu être résolue");
				return false;
			}

			// Definitions héritées
			Instanciation toHerite = this.extension.getHerites();
			if (toHerite != null) {

				// Récupération de la classe héritée
				Declaration decl = _scope.get(toHerite.getName());

				// Vérification que c'est une classe
				if (decl instanceof ClasseDeclaration) {
					ClasseDeclaration claDecl = (ClasseDeclaration) decl;

					// Si la classe est final on ne peut en hériter
					if (!claDecl.isFinal()) {

						// Pour chaque déf, on l'ajoute à la classe si elle est
						// publique
						for (Definition def : claDecl.getElements()) {
							if (this.contains(def.getName()) && def.isFinal()) {
								Logger.error("La définition " + def.getName()
										+ " ne peut être surchargée car elle est final");
								return false;
							}

							if (!this.contains(def.getName()) && def.isAbstract()) {
								Logger.error(
										"La méthode " + def.getName() + " doit être surchargée car elle est abstract");
								return false;
							}

							if (!this.contains(def.getName()) && def.isPublic()) {							
								this.register(def);
								//this.register(def.copy(this.name));
							}
						}
					} else {
						Logger.error("Tentative d'héritage sur une classe final");
						return false;
					}
				} else {
					Logger.error("Tentative d'héritage sur autre chose qu'une classe");
					return false;
				}
			}

			// Definitions réalisées
			for (Instanciation toImplemente : this.extension.getRealises()) {
				if (toImplemente != null) {

					// Récupération de la classe héritée
					Declaration decl = _scope.get(toImplemente.getName());

					if (decl instanceof InterfaceDeclaration) {
						InterfaceDeclaration itfDecl = (InterfaceDeclaration) decl;

						// Pour chaque entête, vérification que la méthode est
						// réalisée
						for (Entete e : itfDecl.getEntetes()) {
							if (!this.contains(e.getName())) {
								Logger.error("Cette classe n'implémente pas : " + e.getName());
								return false;
							}

							Declaration d = this.get(e.getName());

							if (d instanceof MethodeDeclaration) {
								if (!sameParametres(e.getParametres(), ((MethodeDeclaration) d).getParametres())) {
									Logger.error("La méthode de la classe n'a pas la même entête : " + e.getName());
									return false;
								}
							} else {
								Logger.error("La déclaration  : " + e.getName() + " n'est pas une méthode !");
								return false;
							}

						}

					} else {
						Logger.error("Tentative de réalisation sur autre chose qu'une interface");
						return false;
					}
				}
			}

		}

		if (!_scope.contains(this.name)) {
			_scope.register(this);

			for (Definition def : this.definitions) {
				_result = _result && def.resolve(this);
			}
			for (Definition def : this.constructeurs) {
				_result = _result && def.resolve(this);
			}
		} else {
			Logger.error("Identifiant : " + this.name + " déjà pris");
			return false;
		}

		return _result;
	}

	public boolean isFinal() {
		return this.keyword.equals(Keyword.FINAL);
	}

	public boolean isAbstract() {
		return this.keyword.equals(Keyword.ABSTRACT);
	}

	private boolean sameParametres(List<ParameterDeclaration> parametres, List<ParameterDeclaration> parametres2) {
		if (parametres.size() == parametres2.size()) {
			boolean correspond = true;
			Iterator<ParameterDeclaration> _iterPd1 = parametres.iterator();
			Iterator<ParameterDeclaration> _iterPd2 = parametres2.iterator();

			while (_iterPd1.hasNext() && _iterPd2.hasNext() && correspond) {
				ParameterDeclaration pd1 = _iterPd1.next();
				ParameterDeclaration pd2 = _iterPd2.next();
				correspond = correspond && pd1.getType().compatibleWith(pd2.getType());
			}

			return correspond;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkType() {

		boolean ok = true;
		for (Definition def : this.definitions) {
			ok = ok && def.checkType();
		}

		for (Definition def : this.constructeurs) {
			ok = ok && def.checkType();
		}

		return ok;
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		this.tailleMemoire = 0;
		
		int staticSize = 0;
		int staticOffset = _offset;
		
		this.register = _register;
		this.offset = _offset;
		
		for(Definition d : this.definitions) {
			if(d instanceof AttributDeclaration) {
				if(d.isStatic()){
					int dep = d.allocateMemory(_register, staticOffset);
					staticOffset += dep;
					staticSize += dep;
					
				} else {
					d.allocateMemory(_register, this.tailleMemoire);
					this.tailleMemoire += ((AttributDeclaration) d).getLength();
				}

			} else {
				if(!d.isAbstract())
					d.allocateMemory(_register, _offset);
			}
			
		}
		
		for(Definition d : this.constructeurs){
			d.allocateMemory(_register, _offset);
		}

		return staticSize;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment f = _factory.createFragment();
	
		for(Definition cd : this.constructeurs){
			f.append(cd.getCode(_factory));
		}
		
		for(Definition md : this.definitions){
			if(md instanceof MethodeDeclaration && !md.isAbstract())
				f.append(md.getCode(_factory));
			
			if(md instanceof AttributDeclaration && md.isStatic())
				f.append(md.getCode(_factory));
			
		}
		
		return f;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Type getType() {
		// TODO C'EST DEGUEU
		// return new ClasseType(new Instanciation(this.name));
		throw new SemanticsUndefinedException("get type pas impl");
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
			if (_current instanceof ConstructeurDeclaration) {
				_found = parametresCorrespond(arguments, ((ConstructeurDeclaration) _current).getParametres());
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
			if (_current instanceof MethodeDeclaration) {
				_found = _current.getName().equals(nomMethode)
						&& parametresCorrespond(arguments, ((MethodeDeclaration) _current).getParametres());
			}
		}
		if (_found) {
			return (MethodeDeclaration) _current;
		} else {
			return null;
		}
	}

	private boolean parametresCorrespond(List<Expression> arguments, List<ParameterDeclaration> parametres) {
		if (arguments.size() == parametres.size()) {
			boolean correspond = true;
			Iterator<Expression> _iterExpr = arguments.iterator();
			Iterator<ParameterDeclaration> _iterPd = parametres.iterator();

			while (_iterExpr.hasNext() && _iterPd.hasNext() && correspond) {
				ParameterDeclaration pd = _iterPd.next();
				Expression exp = _iterExpr.next();
				correspond = correspond && pd.getType().compatibleWith(exp.getType());
			}

			return correspond;
		} else {
			return false;
		}
	}

	@Override
	public int getLength() {
		return this.length;
	}
	
	public int getTailleMemoire() {
		return this.tailleMemoire;
	}

	public List<AttributDeclaration> getAttributDeclaration() {
		List<AttributDeclaration> ads = new ArrayList<AttributDeclaration>();
		for(Definition d : this.definitions){
			if(d instanceof AttributDeclaration){
				ads.add((AttributDeclaration) d);
			}
		}
		return ads;
	}

	public Register getRegister() {
		return this.register;
	}
	
	public int getOffset(){
		return this.offset;
	}

	public Instanciation getClasseHeritee() {
		if(this.extension != null)
			return this.extension.getHerites();
		else
			return null;
	}
	
	public List<Instanciation> getInterfacesRealisees() {
		if(this.extension != null && this.extension.getRealises() != null)
			return this.extension.getRealises();
		else
			return new ArrayList<Instanciation>();
	}
	

}
