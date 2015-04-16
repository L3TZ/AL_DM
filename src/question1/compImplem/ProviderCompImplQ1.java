package question1.compImplem;

import question1.servicesImplem.ProviderImplQ1;
import interfaces.IProvider;
import estore.ProviderComp;

public class ProviderCompImplQ1 extends ProviderComp  {

	@Override
	protected IProvider make_providing() {
		// TODO Auto-generated method stub
		return new ProviderImplQ1();
	}

}
