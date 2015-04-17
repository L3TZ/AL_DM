package question3.compImplem;

import question3.interfaces.ITrace;
import question3.servicesImplem.TraceQ3Impl;
import estore.TraceCompQ3;

public class TraceCompQ3Impl extends TraceCompQ3 {

	@Override
	protected ITrace make_trace() {
		// TODO Auto-generated method stub
		return new TraceQ3Impl();
	}

}
