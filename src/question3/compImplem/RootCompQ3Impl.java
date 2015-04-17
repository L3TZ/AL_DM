package question3.compImplem;

import compImplem.BankCompImpl;
import compImplem.ClientCompImpl;
import compImplem.StoreCompImpl;
import banking.BankComp;
import estore.ClientComp;
import estore.ProviderComp;
import estore.RootComp;
import estore.StoreComp;

public class RootCompQ3Impl extends RootComp {

	@Override
	protected ClientComp make_cl() {
		// TODO Auto-generated method stub
		return new ClientCompImpl();
	}

	@Override
	protected StoreComp make_st() {
		// TODO Auto-generated method stub
		return new StoreCompImpl();
	}

	@Override
	protected ProviderComp make_pv() {
		// TODO Auto-generated method stub
		return new ProviderCompositeCompQ3Impl();
	}

	@Override
	protected BankComp make_bk() {
		// TODO Auto-generated method stub
		return new BankCompImpl();
	}

}
