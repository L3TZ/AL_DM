package compImplem;

import servicesImplem.Provider;
import interfaces.IProvider;
import estore.ProviderComp;

public class ProviderCompImpl extends ProviderComp {

	@Override
	protected IProvider make_providing() {
		// TODO Auto-generated method stub
		return new Provider();
	}

}
