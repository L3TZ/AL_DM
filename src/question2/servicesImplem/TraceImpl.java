package question2.servicesImplem;

import exception.UnknownItemException;
import interfaces.IProvider;
import interfaces.IStoreLane;

public class TraceImpl implements IProvider {
	
	private IProvider provider;
	
	public void init(IProvider prov) {
        provider = prov;
    }

	@Override
	public double getPrice(Object item) throws UnknownItemException {
		// TODO Auto-generated method stub
		System.out.println("TRACE : Demande de prix de l'objet : "+item);
		double price = provider.getPrice(item);
		System.out.println("TRACE : Réponse à la demande de prix : "+price);
		return price;
	}

	@Override
	public int order(IStoreLane store, Object item, int qty)
			throws UnknownItemException {
		// TODO Auto-generated method stub
		System.out.println("TRACE : Commande effectuée : "+item+" x"+qty);
		int r = provider.order(store, item, qty);
		System.out.println("TRACE : Délai annoncé : "+r);
		return r;
	}

}
