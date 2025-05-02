package factories.impl;

import domain.valueObjects.Address;
import domain.valueObjects.Location;
import utils.Utils;

public class LocationFactoryImpl {

    public LocationFactoryImpl() {
    }

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
                break;
            } catch (Exception e) {
                Utils.printAlterMessage("Invalid longitude.");
            }
        }

        Address address = new Address(street, city, postalCode, country);
        return new Location(address, latitude, longitude, null);
    }
}
