package partie2.compImplem;

import partie2.servicesImplem.FacadeP2Impl;
import interfaces.IBankTransfert;
import banking.FacadeCompP2;

public class FacadeCompP2Impl extends FacadeCompP2 {
	
	private FacadeP2Impl facade = new FacadeP2Impl();
	
	@Override
	protected void start() {
		facade.init(requires().deskPort(),requires().trace());
		super.start();
	}

	@Override
	protected IBankTransfert make_deskTransfert() {
		// TODO Auto-generated method stub
		return facade;
	}

}
