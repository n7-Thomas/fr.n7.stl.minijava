package fr.n7.stl.minijava;

class Driver {

	public static void main(String[] args) throws Exception {
		Parser parser = null;
		if (args.length == 0) {
			parser = new Parser( "tests_unitaires/test_01.minijava" );
			parser.parse();
		} else {
			for (String name : args) {
				parser = new Parser( name );
				parser.parse();
			}
		}
	}
	
}