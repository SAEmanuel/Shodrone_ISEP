package factories;

import factories.impl.ShowRequestFactoryImpl;
import factories.interfaces.ShowRequestFactoryInt;

public class FactoryProvider {

    // ATENCAO : Privado pois nao queremos criar instancias desta classe... apenas e utilitaria nao tem estados...
    private FactoryProvider() {
    }

    // ATENCAO : Retorna a interface e nao a classe concreta com a implementacao, pois os resto do codigo deve so saber os metodos que esta fornece...
    public static ShowRequestFactoryInt getShowRequestFactory() {
        return new ShowRequestFactoryImpl();
    }

}
