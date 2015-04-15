package servicesImplem;
import basic.Cart;
import basic.Order;
import interfaces.IBankTransfert;
import interfaces.IJustHaveALook;
import interfaces.IStoreFastLane;
import interfaces.IStoreLane;
import exception.InsufficientBalanceException;
import exception.InvalidCartException;
import exception.UnknownAccountException;
import exception.UnknownItemException;

public class Client implements Runnable {

//    private Store store;
	private IJustHaveALook look;
	private IStoreFastLane fast;
	private IStoreLane normal;

    public Client () {
    }
    
    // méthode ajoutée pour l'injection de dépendance
    public void init (IJustHaveALook jhal, IStoreFastLane sfl, IStoreLane sl){
    	look = jhal;
    	fast = sfl;
    	normal = sl;
    }

    // -----------------------------------------------------
    // Implementation of the Runnable interface
    // -----------------------------------------------------

    public void run() {
        
        // Scenario 1
        // Direct ordering of an item
        // The scenario is run twice
        System.out.println("Scenario 1");
        scenario1("CD",2,"Lille","Bob");
        scenario1("CD",1,"Lille","Anne");
        System.out.println();
        
        // Scenario 2
        // Ordering of several items by using a cart
        System.out.println("Scenario 2");
        scenario2(new String[]{"DVD","CD"},new int[]{2,1},"Lille","Bob");
        System.out.println();
    }
    
    private void scenario1(
            String item, int qty, String address, String account ) {
        
        try {
            _scenario1(item,qty,address,account);
        }
        catch (Exception e) {
            System.err.println("Exception: "+e.getMessage());
            e.printStackTrace();
        }        
    }
    
    private void _scenario1(
            String item, int qty, String address, String account )
    throws
    UnknownItemException,
    InsufficientBalanceException, UnknownAccountException{
        
        System.out.println("Ordering "+qty+" "+item+" for "+account+"...");
        Order order = fast.oneShotOrder(this,item,qty,address,account);
        System.out.println(order);
    }

    private void scenario2(
            String[] items, int[] qties, String address, String account ) {
        
        try {
            _scenario2(items,qties,address,account);
        }
        catch (Exception e) {
            System.err.println("Exception: "+e.getMessage());
            e.printStackTrace();
        }        
    }
    
    private void _scenario2(
            String[] items, int[] qties, String address, String account )
    throws
    InsufficientBalanceException, UnknownAccountException,
    UnknownItemException, InvalidCartException{
    	
        System.out.println("Ordering for "+account+"...");
        Cart cart = null;
        for (int i = 0; i < items.length; i++) {
            System.out.println("Item: "+items[i]+", quantity: "+qties[i]);
            cart = normal.addItemToCart(cart,this,items[i],qties[i]);
        }
        Order order = normal.pay(cart,address,account);
        System.out.println(order);
    }

}
