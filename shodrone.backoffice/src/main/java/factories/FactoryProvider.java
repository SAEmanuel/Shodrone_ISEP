package factories;


import factories.impl.LocationFactoryImpl;
import factories.impl.ShowRequestFactoryImpl;

public class FactoryProvider {
    private static ShowRequestFactoryImpl showRequestFactory;
    private static LocationFactoryImpl locationFactory;

    public FactoryProvider() {
    }

    public static ShowRequestFactoryImpl getShowRequestFactory() {
        if(showRequestFactory == null){
            showRequestFactory = new ShowRequestFactoryImpl();
        }
        return showRequestFactory;
    }

    public static LocationFactoryImpl getLocationFactoryImpl() {
        if(locationFactory == null){
            locationFactory = new LocationFactoryImpl();
        }
        return locationFactory;
    }
}