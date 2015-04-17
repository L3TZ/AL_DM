package partie2.servicesImplem;

import question3.interfaces.ITrace;
import exception.InsufficientBalanceException;
import exception.UnknownAccountException;
import interfaces.IBankTransfert;

public class FacadeP2Impl implements IBankTransfert{
	
	private IBankTransfert desk;
	private ITrace trace;
	
	public void init(IBankTransfert de, ITrace tra) {
        desk = de;
        trace = tra;
    }

	@Override
	public void transfert(String from, String to, double amount)
			throws InsufficientBalanceException, UnknownAccountException {
		desk.transfert(from, to, amount);
		trace.affiche("TRACE BK > Transfert de "+from+" vers "+to+". Valeur : "+amount);
		
	}

}
