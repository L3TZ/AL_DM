package interfaces;
import exception.InsufficientBalanceException;
import exception.UnknownAccountException;

public interface IBankTransfert {

	public abstract void transfert(String from, String to, double amount)
			throws InsufficientBalanceException, UnknownAccountException;

}