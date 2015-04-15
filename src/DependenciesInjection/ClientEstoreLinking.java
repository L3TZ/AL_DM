package DependenciesInjection;

import interfaces.IJustHaveALook;
import interfaces.IStoreFastLane;
import interfaces.IStoreLane;
import servicesImplem.Client;

public class ClientEstoreLinking {

    public static void injectEstore (Client c, IJustHaveALook look, IStoreFastLane fastLane, IStoreLane lane) {
    	// code d'injection des dépendances. Peut être réalisé de diverses autres manières (cf. cours JEE DL)
		
		c.init(look, fastLane, lane);
    }

}
