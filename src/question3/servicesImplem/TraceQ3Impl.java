package question3.servicesImplem;

import interfaces.IStoreLane;
import question3.interfaces.ITrace;

public class TraceQ3Impl implements ITrace {
	
	@Override 
	public void affiche(String message) {
		System.out.println(message);
	}

}
