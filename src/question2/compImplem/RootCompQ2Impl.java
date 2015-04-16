package question2.compImplem;

import compImplem.BankCompImpl;
import compImplem.ClientCompImpl;
import compImplem.ProviderCompImpl;
import compImplem.StoreCompImpl;
import banking.BankComp;
import estore.ClientComp;
import estore.ProviderComp;
import estore.RootCompQ2;
import estore.StoreComp;
import estore.TraceComp;

public class RootCompQ2Impl extends RootCompQ2 {

	@Override
	protected ClientComp make_cl() {
		// TODO Auto-generated method stub
		return new ClientCompImpl();
	}
	
	@Override
	protected TraceComp make_tr() {
		// TODO Auto-generated method stub
		return new TraceCompImpl();
	}

	@Override
	protected StoreComp make_st() {
		// TODO Auto-generated method stub
		return new StoreCompImpl();
	}

	@Override
	protected ProviderComp make_pv() {
		// TODO Auto-generated method stub
		return new ProviderCompImpl();
	}

	@Override
	protected BankComp make_bk() {
		// TODO Auto-generated method stub
		return new BankCompImpl();
	}

}
