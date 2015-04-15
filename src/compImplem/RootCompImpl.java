package compImplem;

import banking.BankComp;
import estore.ClientComp;
import estore.ProviderComp;
import estore.RootComp;
import estore.StoreComp;

public class RootCompImpl extends RootComp {

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
		return new ProviderCompImpl();
	}

	@Override
	protected BankComp make_bk() {
		// TODO Auto-generated method stub
		return new BankCompImpl();
	}

}
