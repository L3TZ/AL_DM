package compImplem;

import servicesImplem.Client;
import estore.ClientComp;

public class ClientCompImpl extends ClientComp {

	Client c= new Client();
	
	@Override
	protected Runnable make_go() {
		// TODO Auto-generated method stub
		return c;
	}

	@Override
	protected void start() {
		// TODO Auto-generated method stub
		c.init(requires().needConsult(), requires().fast(), requires().cool());
		super.start();
	}
}
