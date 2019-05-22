package fr.n7.stl.minijava.ast.objet.heritage;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.minijava.ast.scope.Declaration;
import fr.n7.stl.minijava.ast.scope.HierarchicalScope;

public class Extension {
	
	private Instanciation herites;
	private List<Instanciation> realises;
	
	public Extension(Instanciation herites, List<Instanciation> realises) {
		if(realises == null)
			this.realises = new ArrayList<Instanciation>();
		else
			this.realises = realises;
		
		this.herites = herites;

	}

	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean ok = true;
		if(this.herites != null)
			ok = ok && this.herites.resolve(_scope);
		if(this.realises != null) {
			for(Instanciation inst : this.realises){
				ok = ok && inst.resolve(_scope);
			}
		}
		
		return ok;
	}

	public Instanciation getHerites() {
		return herites;
	}

	public List<Instanciation> getRealises() {
		return realises;
	} 
	
	

}
