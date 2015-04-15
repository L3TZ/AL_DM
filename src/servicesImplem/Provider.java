package servicesImplem;
import interfaces.IProvider;
import interfaces.IStoreLane;

import java.util.HashMap;
import java.util.Map;

import exception.UnknownItemException;

public class Provider implements IProvider {
    
    private Map itemPrices = new HashMap();

    /**
     * Constructs a new ProviderImpl
     */
    public Provider() {
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
        
        Double price = (Double) itemPrices.get(item);
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
        
        // Actually the production process is quite chaotic
        // We only know that the production a random number of hours!!
        double r = Math.random() * 10 * qty;
        return (int)r;
    }

}
