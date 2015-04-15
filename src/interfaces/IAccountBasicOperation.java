package interfaces;
import exception.InsufficientBalanceException;

public interface IAccountBasicOperation {

	public abstract void credit(double amount);

	public abstract void withdraw(double amount)
			throws InsufficientBalanceException;

}