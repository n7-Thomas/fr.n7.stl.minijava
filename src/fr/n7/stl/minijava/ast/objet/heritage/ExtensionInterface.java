package fr.n7.stl.minijava.ast.objet.heritage;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;

public class ExtensionInterface {
	
	private List<Instanciation> herites;

	public ExtensionInterface(List<Instanciation> herites) {
		if(herites == null)
			this.herites = new ArrayList<Instanciation>();
		else
			this.herites = herites;
	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean ok = true;
		if(this.herites != null) {
			for(Instanciation inst : this.herites){
				ok = ok && inst.resolve(_scope);
			}
		}
		return ok;
	}

	public List<Instanciation> getHerites() {
		return this.herites;
	}
}
