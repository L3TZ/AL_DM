package DependenciesInjection;
import interfaces.IAccountBasicOperation;
import servicesImplem.Account;
import servicesImplem.Bank;


public class BankAccountInjector {

    public static void injectAccount (Bank b,
    									IAccountBasicOperation es,
    									IAccountBasicOperation an,
    									IAccountBasicOperation bo) {
    	// code d'injection des dépendances. Peut être réalisé de diverses autres manières (cf. cours JEE DL)
		b.init(es, an, bo);
    }
    
    public static void injectAmountOwner(Account acc, int amount, String owner) {
    	acc.setAmount(amount);
    	acc.setOwner(owner);
    }

}
