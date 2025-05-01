package factories;


import factories.impl.ShowRequestFactoryImpl;
import persistence.inmemory.InMemoryAuthenticationRepository;
import persistence.inmemory.InMemoryCustomerRepository;
import persistence.inmemory.InMemoryFigureCategoryRepository;
import persistence.inmemory.InMemoryShowRequestRepository;
import persistence.interfaces.CostumerRepository;
import persistence.interfaces.FigureCategoryRepository;
import persistence.interfaces.ShowRequestRepository;
import persistence.interfaces.AuthenticationRepository;
import persistence.jpa.JPAImpl.AuthenticationRepositoryJPAImpl;
import persistence.jpa.JPAImpl.ShowRequestJPAImpl;

public class FactoryProvider {
    private static ShowRequestFactoryImpl showRequestFactory;

    public FactoryProvider() {
    }

    public static ShowRequestFactoryImpl getShowRequestFactory() {
        if(showRequestFactory == null){
            showRequestFactory = new ShowRequestFactoryImpl();
        }
        return showRequestFactory;
    }
}