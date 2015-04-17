package partie2.compImplem;

import estore.TraceCompQ3;
import question3.compImplem.TraceCompQ3Impl;
import servicesImplem.Bank;
import interfaces.IBankTransfert;
import DependenciesInjection.BankAccountInjector;
import banking.DeskComp;
import banking.DeskCompP2;
import banking.FacadeCompP2;

public class DeskCompP2Impl extends DeskCompP2 {
	
	private Bank deskPartProvider = new Bank();
	
	@Override
	protected void start() {
		BankAccountInjector.injectAccount(deskPartProvider,
											requires().storeAccount(),
											requires().anneAccount(),
											requires().bobAccount());
		super.start();
	}

	@Override
	protected DeskComp make_dc() {
		return new DeskComp() {
					
					@Override
					protected IBankTransfert make_deskTransfert() {
						return deskPartProvider;
					}
				};
	}

	@Override
	protected TraceCompQ3 make_tr() {
		// TODO Auto-generated method stub
		return new TraceCompQ3Impl();
	}

	@Override
	protected FacadeCompP2 make_fc() {
		// TODO Auto-generated method stub
		return new FacadeCompP2Impl();
	}

	@Override
	protected IBankTransfert make_deskTransfert() {
		// TODO Auto-generated method stub
		return parts().fc().deskTransfert();
	}
	

}
