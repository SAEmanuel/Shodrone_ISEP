package factories;

import factories.impl.LocationFactoryImpl;
import factories.impl.ShowRequestFactoryImpl;

/**
 * The FactoryProvider class is a singleton-like utility class designed to provide access
 * to specific factory instances for creating entities related to ShowRequest and Location.
 * <p>
 * This class ensures that only one instance of each factory is created, providing a global
 * point of access to the factory objects. It leverages the Lazy Initialization pattern
 * to create the factory instances only when they are first requested.
 * </p>
 */
public class FactoryProvider {

    // Factory instances
    private static ShowRequestFactoryImpl showRequestFactory;
    private static LocationFactoryImpl locationFactory;

    /**
     * Default constructor for the FactoryProvider class.
     * This constructor is private to ensure that no external instantiation is possible,
     * enforcing the singleton-like behavior for the factories.
     */
    public FactoryProvider() {
    }

    /**
     * Provides a singleton instance of ShowRequestFactoryImpl.
     *
     * <p>If the instance has not yet been created, it initializes a new ShowRequestFactoryImpl
     * instance and returns it. If the instance already exists, it simply returns the existing instance.</p>
     *
     * @return the singleton instance of ShowRequestFactoryImpl
     */
    public static ShowRequestFactoryImpl getShowRequestFactory() {
        if(showRequestFactory == null){
            showRequestFactory = new ShowRequestFactoryImpl();
        }
        return showRequestFactory;
    }

    /**
     * Provides a singleton instance of LocationFactoryImpl.
     *
     * <p>If the instance has not yet been created, it initializes a new LocationFactoryImpl
     * instance and returns it. If the instance already exists, it simply returns the existing instance.</p>
     *
     * @return the singleton instance of LocationFactoryImpl
     */
    public static LocationFactoryImpl getLocationFactoryImpl() {
        if(locationFactory == null){
            locationFactory = new LocationFactoryImpl();
        }
        return locationFactory;
    }
}
