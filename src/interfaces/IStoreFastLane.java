package interfaces;
import servicesImplem.Client;
import basic.Order;
import exception.InsufficientBalanceException;
import exception.UnknownAccountException;
import exception.UnknownItemException;

public interface IStoreFastLane {

	/**
	 * Used by a client to order an item.
	 * The whole process of ordering is encapsulated by this method.
	 * If several items need to be ordered, this method needs to be
	 * called several times, but the items will appear in separate orders.
	 * 
	 * @param client
	 * @param item
	 * @param qty
	 * @param address
	 * @param bankAccountRef
	 * @return  the order
	 * 
	 * @throws UnknownItemException
	 * @throws InsufficientBalanceException
	 * @throws UnknownAccountException
	 */
	public abstract Order oneShotOrder(Client client, Object item, int qty,
			String address, String bankAccountRef) throws UnknownItemException,
			InsufficientBalanceException, UnknownAccountException;

}