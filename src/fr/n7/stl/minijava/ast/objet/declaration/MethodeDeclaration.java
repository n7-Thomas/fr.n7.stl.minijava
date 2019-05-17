package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.Block;
import fr.n7.stl.minijava.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.minijava.ast.objet.definition.Definition;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;
import fr.n7.stl.minijava.ast.scope.SymbolTable;
import fr.n7.stl.minijava.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class MethodeDeclaration implements Declaration, Definition {

	private Entete entete;

	private Block corps;

	private List<Keyword> keywords;

	public MethodeDeclaration(List<Keyword> _keywords, Entete _entete, Block _corps) {
		this.entete = _entete;
		this.corps = _corps;

		if (_keywords == null)
			this.keywords = new ArrayList<Keyword>();
		else
			this.keywords = _keywords;
	}

	@Override
	public String getName() {
		return this.entete.getName();
	}

	@Override
	public Type getType() {
		return this.entete.getType();
	}

	public String toString() {
		return keywords.toString() + entete.toString() + corps.toString();
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if (_scope.contains(this.entete.getName())) {
			Logger.error("Erreur identifiant déjà pris");
			return false;
		} else {

			_scope.register(this);

			SymbolTable tds = new SymbolTable(_scope);

			for (ParameterDeclaration pd : entete.getParametres()) {
				tds.register(pd);
			}

			return this.corps.resolve(tds);
		}
	}

	@Override
	public boolean checkType() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int allocateMemory(Register _register, int _offset) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Fragment getCode(TAMFactory _factory) {
		// TODO Auto-generated method stub
		return null;
	}
}
