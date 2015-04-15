package servicesImplem;
import interfaces.IAccountBasicOperation;
import interfaces.IBankDI;
import interfaces.IBankTransfert;
import exception.InsufficientBalanceException;
import exception.UnknownAccountException;

public class Bank implements IBankTransfert, IBankDI {

//	private Account estore;
//	private Account anne, bob;
	private IAccountBasicOperation estore;
	private IAccountBasicOperation anne, bob;

	
	public Bank () {
// ce code était de l'injection de dépendance. Il doit se trouver en dehors de la classe.
// Pour illustrer l'injection par interface, cette classe implante une interface dédiée particulière

//		estore = new Account();
//		anne = new Account();
//		bob = new Account();
//		
//		estore.setOwner("Estore");
//		estore.setAmount(0);
//		anne.setOwner("Anne");
//		anne.setAmount(30);
//		bob.setOwner("Bob");
//		bob.setAmount(100);
	}

     /* (non-Javadoc)
	 * @see IBankTransfert#transfert(java.lang.String, java.lang.String, double)
	 */
    @Override
	public void transfert(String from, String to, double amount)
        throws InsufficientBalanceException, UnknownAccountException {
//        Account Afrom=null, Ato=null;        
        IAccountBasicOperation Afrom=null, Ato=null;        
 
        if (from.equals("E-Store")) Afrom = estore;
        	if (from.equals("Anne")) Afrom = anne;
        	if (from.equals("Bob")) Afrom = bob;
        	
        	if (to.equals("E-Store")) Ato = estore;
        	if (to.equals("Anne")) Ato = anne;
        	if (to.equals("Bob")) Ato = bob;
        	
//            // Get the balance of the account to widthdraw
//            double fromBalance = Afrom.getAmount();
//            
//            // Check whether the account is sufficiently balanced
//            if ( fromBalance < amount )
//                throw new InsufficientBalanceException(from.toString());
//            
//            // Get the balance of the account to credit
//            double toBalance = Ato.getAmount();
//            
//            // Perform the transfert
//			Afrom.setAmount( fromBalance - amount );
//			Ato.setAmount( toBalance + amount );
        	
        	Afrom.withdraw(amount);
        	Ato.credit(amount);
    }
    
    /* (non-Javadoc)
	 * @see servicesImplem.IBankDI#init(interfaces.IAccountBasicOperation, interfaces.IAccountBasicOperation, interfaces.IAccountBasicOperation)
	 */
    @Override
	public void init(IAccountBasicOperation es, IAccountBasicOperation a, IAccountBasicOperation b) {
		estore = es;
		anne = a;
		bob = b;
    }
    
 }
