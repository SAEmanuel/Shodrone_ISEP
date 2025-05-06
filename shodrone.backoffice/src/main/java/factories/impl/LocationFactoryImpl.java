package factories.impl;

import domain.valueObjects.Address;
import domain.valueObjects.Location;
import utils.Utils;

/**
 * The LocationFactoryImpl class is responsible for creating instances of the Location entity.
 * It provides a method to read user input from the console to gather location data and validate it
 * before creating a valid Location object.
 * <p>
 * This class ensures that all required information (address, latitude, and longitude) is collected
 * and validated through multiple input prompts, and if any input is invalid, the user will be
 * prompted again until valid data is provided.
 * </p>
 */
public class LocationFactoryImpl {

    /**
     * Default constructor for the LocationFactoryImpl class.
     * It does not perform any operations but is required for creating instances of the factory.
     */
    public LocationFactoryImpl() {
    }

    /**
     * Creates a new Location object by reading the address, latitude, and longitude inputs
     * from the user through the console, validating the input values, and returning a new Location
     * instance if all inputs are valid.
     * <p>
     * The method validates the address inputs (street, city, postal code, and country)
     * and ensures that latitude and longitude values are within acceptable ranges:
     * <ul>
     *     <li>Latitude must be between -90 and 90.</li>
     *     <li>Longitude must be between -180 and 180.</li>
     * </ul>
     * If any input is invalid, the user is prompted to enter the data again.
     * </p>
     *
     * @return a new Location object with the validated address, latitude, and longitude.
     */
    public Location createLocationObject() {
        String street;
        String city;
        String postalCode;
        String country;
        double latitude = 0.0;
        double longitude = 0.0;

        // Read address fields with validation
        while (true) {
            try {
                street = Utils.readLineFromConsole("    1.Street").trim();
                city = Utils.readLineFromConsole("    2.City").trim();
                postalCode = Utils.readLineFromConsole("    3.Postal Code (format: NNNN-NNN)").trim();
                country = Utils.readLineFromConsole("    4.Country").trim();

                // Try to create address to validate inputs
                new Address(street, city, postalCode, country);
                break;
            } catch (IllegalArgumentException e) {
                Utils.printAlterMessage("Invalid address input: " + e.getMessage());
            }
        }

        // Read latitude with validation
        while (true) {
            try {
                latitude = Utils.readDoubleFromConsole("    5.Latitude (between -90 and 90)");
                if (latitude < -90 || latitude > 90)
                    Utils.printAlterMessage("Latitude must be between -90 and 90.");
                else
                    break;
            } catch (Exception e) {
                Utils.printAlterMessage("Invalid latitude.");
            }
        }

        // Read longitude with validation
        while (true) {
            try {
                longitude = Utils.readDoubleFromConsole("    6.Longitude (between -180 and 180)");
                if (longitude < -180 || longitude > 180)
                    Utils.printAlterMessage("Longitude must be between -180 and 180.");
                else
                    break;
            } catch (Exception e) {
                Utils.printAlterMessage("Invalid longitude.");
            }
        }

        Address address = new Address(street, city, postalCode, country);
        return new Location(address, latitude, longitude, null);
    }
}
