package interfaces;
import exception.UnknownItemException;

public interface IProvider {

	/**
	 * Get the price of an item provided by this provider.
	 * 
	 * @param item
	 * @return
	 */
	public abstract double getPrice(Object item) throws UnknownItemException;

	/**
	 * Emit an order for items.
	 * The provider returns the delay for delivering the items.
	 * 
	 * @param store  the store that emits the order
	 * @param item   the item ordered
	 * @param qty    the quantity ordered
	 * @return       the delay (in hours)
	 */
	public abstract int order(IStoreLane store, Object item, int qty)
			throws UnknownItemException;

}