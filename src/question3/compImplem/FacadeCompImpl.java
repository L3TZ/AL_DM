package question3.compImplem;



import question3.servicesImplem.FacadeImpl;
import interfaces.IProvider;
import estore.FacadeComp;

public class FacadeCompImpl extends FacadeComp {
	
	private FacadeImpl facade = new FacadeImpl();
	
	@Override
	protected void start() {
		facade.init(requires().provider(),requires().trace());
		super.start();
	}

	@Override
	protected IProvider make_providing() {
		// TODO Auto-generated method stub
		return facade;
	}

}
