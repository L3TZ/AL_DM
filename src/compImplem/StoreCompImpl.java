package compImplem;

import DependenciesInjection.ClientEstoreLinking;
import interfaces.IJustHaveALook;
import interfaces.IStoreFastLane;
import interfaces.IStoreLane;
import servicesImplem.Store;
import estore.StoreComp;

public class StoreCompImpl extends StoreComp {
	
	private Store store = new Store();
	
	@Override
	protected void start() {
		super.start();
		store.init(requires().provider(), requires().payment());
	}

	@Override
	protected IJustHaveALook make_giveConsult() {
		return store;
	}

	@Override
	protected IStoreFastLane make_express() {
		return store;
	}

	@Override
	protected IStoreLane make_classic() {
		return store;
	}

}
