package factories;

import factories.impl.ShowRequestFactoryImpl;

public class FactoryProvider {

    // ATENCAO: Privado pois nao queremos criar instancias desta classe... apenas e utilitaria nao tem estados...
    private FactoryProvider() {
    }

    public static ShowRequestFactoryImpl getShowRequestFactory() {
        return new ShowRequestFactoryImpl();
    }

}
