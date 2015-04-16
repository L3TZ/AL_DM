package question2;

import question2.compImplem.RootCompQ2Impl;
import estore.RootCompQ2;


public class Main {

	public static void main(String[] args) {
		
		RootCompQ2.Component comp = (new RootCompQ2Impl()).newComponent();
		comp.go().run();

	}

}
