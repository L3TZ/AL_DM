import compImplem.RootCompImpl;

import estore.RootComp;
import DependenciesInjection.BankAccountInjector;
import DependenciesInjection.ClientEstoreLinking;
import servicesImplem.Account;
import servicesImplem.Bank;
import servicesImplem.Client;
import servicesImplem.Provider;
import servicesImplem.Store;


public class Main {

    public static void main (String [] args) {
		Provider prov = new Provider();
		Bank bank = new Bank();
		Account estore = new Account();
		Account anne = new Account();
		Account bob = new Account();
		BankAccountInjector.injectAmountOwner(estore, 0, "Estore");
		BankAccountInjector.injectAmountOwner(anne, 30, "Anne");
		BankAccountInjector.injectAmountOwner(bob, 100, "Bob");
		BankAccountInjector.injectAccount(bank, estore, anne, bob);
		Store store = new Store();
		store.init(prov, bank);
		Client cl = new Client();
		ClientEstoreLinking.injectEstore(cl, store, store, store);
		
		cl.run();
		
		
//		RootComp.Component comp = (new RootCompImpl()).newComponent();
//		comp.go().run();
		
    }

}
