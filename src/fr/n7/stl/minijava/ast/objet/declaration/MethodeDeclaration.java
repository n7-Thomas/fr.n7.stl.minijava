package fr.n7.stl.minijava.ast.objet.declaration;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.Block;
import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.type.Type;

public class MethodeDeclaration implements Declaration {

	private Entete entete;

	private Block corps;

	private List<Keyword> keywords;

	public MethodeDeclaration(Entete _entete, Block _corps) {
		this.entete = _entete;
		this.corps = _corps;
		this.keywords = new ArrayList<Keyword>();
	}

	public MethodeDeclaration(Entete _entete, List<Keyword> _keywords, Block _corps) {
		this.entete = _entete;
		this.corps = _corps;
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
}
