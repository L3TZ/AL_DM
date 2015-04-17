package question3;

import question3.compImplem.RootCompQ3Impl;
import estore.RootComp;

public class Main {

	public static void main(String[] args) {
		RootComp.Component comp = (new RootCompQ3Impl()).newComponent();
		comp.go().run();

	}

}
