package servicesImplem;
import exception.InsufficientBalanceException;
import interfaces.IAccountDependencyInjection;
import interfaces.IAccountBasicOperation;
import interfaces.IAccountConsult;

public class Account implements IAccountBasicOperation, IAccountConsult, IAccountDependencyInjection{

    private double amount;
    private String owner;

    /* (non-Javadoc)
	 * @see IAccountConsult#getOwner()
	 */
    @Override
	public String getOwner() {
        return owner;
    }

	/* (non-Javadoc)
	 * @see IAccountAdmin#setOwner(java.lang.String)
	 */
	@Override
	public void setOwner(String owner) {
        this.owner = owner;
    }

    /* (non-Javadoc)
	 * @see IAccountConsult#getAmount()
	 */
    @Override
	public double getAmount() {
        return amount;
    }

	/* (non-Javadoc)
	 * @see IAccountAdmin#setAmount(double)
	 */
	@Override
	public void setAmount(double amount) {
        this.amount = amount;
    }
    
    /* (non-Javadoc)
	 * @see IAccountBasicOperation#credit(double)
	 */
    @Override
	public void credit(double amount) {
        this.amount += amount;        
    }

    /* (non-Javadoc)
	 * @see IAccountBasicOperation#withdraw(double)
	 */
    @Override
	public void withdraw(double amount) throws InsufficientBalanceException {
        if ( this.amount < amount )
            throw new InsufficientBalanceException(owner);
        this.amount -= amount;
    }
    
    /**
     * Two AccountImpl instances are considered equals
     * if they share the same owner.
     * Of course, in a more realistic implementation,
     * we should have a account number.
     */
    public boolean equals( Object other ) {
        if( ! (other instanceof Account) )
            return false;
        Account otherAccount = (Account) other;
        return ( otherAccount.owner == owner);
    }
    
}
