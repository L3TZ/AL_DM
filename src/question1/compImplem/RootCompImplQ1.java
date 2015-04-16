package question1.compImplem;

import banking.BankComp;

import compImplem.BankCompImpl;
import compImplem.ClientCompImpl;
import compImplem.ProviderCompImpl;
import compImplem.StoreCompImpl;

import estore.ClientComp;
import estore.ProviderComp;
import estore.StoreComp;
import estore.RootComp;

public class RootCompImplQ1 extends RootComp {
	
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
		return new ProviderCompImplQ1();
	}

	@Override
	protected BankComp make_bk() {
		// TODO Auto-generated method stub
		return new BankCompImpl();
	}

}
