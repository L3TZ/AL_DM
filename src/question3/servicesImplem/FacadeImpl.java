package question3.servicesImplem;

import question3.interfaces.ITrace;
import exception.UnknownItemException;
import interfaces.IProvider;
import interfaces.IStoreLane;

public class FacadeImpl implements IProvider {
	
	private IProvider provider;
	private ITrace trace;
	
	public void init(IProvider prov, ITrace tra) {
        provider = prov;
        trace = tra;
    }

	@Override
	public double getPrice(Object item) throws UnknownItemException {
		double price = provider.getPrice(item);
		trace.affiche("TRACE > Demande de prix de l'objet : "+item);
		trace.affiche("TRACE > Réponse à la demande de prix : "+price);
		return price;
	}

	@Override
	public int order(IStoreLane store, Object item, int qty)
			throws UnknownItemException {
		int r = provider.order(store, item, qty);
		trace.affiche("TRACE > Commande effectuée : "+item+" x"+qty);
		trace.affiche("TRACE > Délai annoncé : "+r);
		return r;
	}

}
