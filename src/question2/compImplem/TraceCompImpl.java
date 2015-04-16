package question2.compImplem;

import question2.servicesImplem.TraceImpl;
import interfaces.IProvider;
import estore.TraceComp;

public class TraceCompImpl extends TraceComp {
	
private TraceImpl trace = new TraceImpl();
	
	@Override
	protected void start() {
		trace.init(requires().provider());
		super.start();
	}

	@Override
	protected IProvider make_providing() {
		// TODO Auto-generated method stub
		return trace;
	}

}
