package interfaces;
import servicesImplem.Client;
import basic.Cart;
import basic.Order;
import exception.InsufficientBalanceException;
import exception.InvalidCartException;
import exception.UnknownAccountException;
import exception.UnknownItemException;

public interface IStoreLane {

	/**
	 * Add an item to a cart.
	 * If the cart does not exist yet, create a new one.
	 * This method is called for each item one wants to add to the cart.
	 * 
	 * @param cart    a previously created cart or null
	 * @param client
	 * @param item
	 * @param qty
	 * @return
	 *      Implementation dependant.
	 *      Either a new cart at each call or the same cart updated.
	 * 
	 * @throws UnknownItemException
	 * @throws MismatchClientCartException
	 *      if the given client does not own the given cart
	 */
	public abstract Cart addItemToCart(Cart cart, Client client, Object item,
			int qty) throws UnknownItemException, InvalidCartException;

	/**
	 * Once all the items have been added to the cart,
	 * this method finish make the payment
	 *  
	 * @param cart
	 * @param address
	 * @param bankAccountRef
	 * @return  the order
	 * 
	 * @throws UnknownItemException
	 */
	public abstract Order pay(Cart cart, String address, String bankAccountRef)
			throws InvalidCartException, UnknownItemException,
			InsufficientBalanceException, UnknownAccountException;

}