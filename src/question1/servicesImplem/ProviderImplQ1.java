package question1.servicesImplem;

import java.util.HashMap;
import java.util.Map;

import exception.UnknownItemException;
import interfaces.IProvider;
import interfaces.IStoreLane;

public class ProviderImplQ1 implements IProvider {
	  
    private Map itemPrices = new HashMap();

    /**
     * Constructs a new ProviderImpl
     */
    public ProviderImplQ1() {
        itemPrices.put("CD",new Double(15));
        itemPrices.put("DVD",new Double(20));
    }


    /* (non-Javadoc)
	 * @see IProvider#getPrice(java.lang.Object)
	 */
    @Override
	public double getPrice( Object item ) throws UnknownItemException {
        
        if ( ! itemPrices.containsKey(item) )
            throw new UnknownItemException(
                    "Item "+item+
                    " is not an item delivered by this provider.");
        System.out.println("TRACE : Demande de prix de l'objet : "+item);
        
        Double price = (Double) itemPrices.get(item);
        System.out.println("TRACE : Réponse à la demande de prix : "+price.doubleValue());
        return price.doubleValue();
    }
    
    /* (non-Javadoc)
	 * @see IProvider#order(interfaces.IStoreLane, java.lang.Object, int)
	 */
    @Override
	public int order(IStoreLane store, Object item, int qty )
    throws UnknownItemException {
        
        if ( ! itemPrices.containsKey(item) )
            throw new UnknownItemException(
                    "Item "+item+
                    " is not an item delivered by this provider.");
        
        System.out.println("TRACE : Commande effectuée : "+item+" x"+qty);
        
        // Actually the production process is quite chaotic
        // We only know that the production a random number of hours!!
        double r = Math.random() * 10 * qty;
        System.out.println("TRACE : Délai annoncé : "+(int)r);
        return (int)r;
    }

}
