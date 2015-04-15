package compImplem;

import interfaces.IAccountBasicOperation;
import interfaces.IBankTransfert;
import servicesImplem.Account;
import servicesImplem.Bank;
import DependenciesInjection.BankAccountInjector;
import banking.AccountComp;
import banking.BankComp;
import banking.DeskComp;

public class BankCompImpl extends BankComp {

	private Bank deskPartProvider = new Bank();
	private Account estorePartProvider = new Account();
	private Account annePartProvider = new Account();
	private Account bobPartProvider = new Account();
	
	@Override
	protected void start() {
		BankAccountInjector.injectAmountOwner(estorePartProvider, 0, "Estore");
		BankAccountInjector.injectAmountOwner(annePartProvider, 30, "Anne");
		BankAccountInjector.injectAmountOwner(bobPartProvider, 100, "Bob");
		BankAccountInjector.injectAccount(deskPartProvider,
											parts().estorePart().accountTransfer(),
											parts().annePart().accountTransfer(),
											parts().bobPart().accountTransfer());
		super.start();
	}

	@Override
	protected AccountComp make_estorePart() {
		return new AccountComp() {
			
			@Override
			protected IAccountBasicOperation make_accountTransfer() {
				return estorePartProvider;
			}
		};
	}

	@Override
	protected AccountComp make_annePart() {
		return new AccountComp() {
			
			@Override
			protected IAccountBasicOperation make_accountTransfer() {
				return annePartProvider;
			}
		};
	}

	@Override
	protected AccountComp make_bobPart() {
		return new AccountComp() {
			
			@Override
			protected IAccountBasicOperation make_accountTransfer() {
				return bobPartProvider;
			}
		};
	}

	@Override
	protected DeskComp make_deskPart() {
		return new DeskComp() {
			
			@Override
			protected IBankTransfert make_deskTransfert() {
				return deskPartProvider;
			}
		};
	}

}
