package question1;

import question1.compImplem.RootCompImplQ1;
import estore.RootComp;

public class Main {

	public static void main(String[] args) {
		
		RootComp.Component comp = (new RootCompImplQ1()).newComponent();
		comp.go().run();

	}

}
