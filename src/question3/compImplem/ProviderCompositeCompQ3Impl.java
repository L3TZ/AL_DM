package question3.compImplem;

import compImplem.ProviderCompImpl;

import interfaces.IProvider;
import estore.FacadeComp;
import estore.ProviderComp;
import estore.ProviderCompositeCompQ3;
import estore.TraceCompQ3;

public class ProviderCompositeCompQ3Impl extends ProviderCompositeCompQ3 {

	@Override
	protected FacadeComp make_fc() {
		// TODO Auto-generated method stub
		return new FacadeCompImpl();
	}

	@Override
	protected TraceCompQ3 make_tr() {
		// TODO Auto-generated method stub
		return new TraceCompQ3Impl();
	}

	@Override
	protected ProviderComp make_pv() {
		// TODO Auto-generated method stub
		return new ProviderCompImpl();
	}

	@Override
	protected IProvider make_providing() {
		// TODO Auto-generated method stub
		return parts().fc().providing();
	}

}
