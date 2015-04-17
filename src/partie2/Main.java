package partie2;

import partie2.compImplem.RootCompP2Impl;
import estore.RootComp;

public class Main {

	public static void main(String[] args) {
		RootComp.Component comp = (new RootCompP2Impl()).newComponent();
		comp.go().run();

	}

}
