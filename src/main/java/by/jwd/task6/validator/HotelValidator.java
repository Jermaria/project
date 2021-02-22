package by.jwd.task6.validator;

import java.util.regex.Pattern;

public class HotelValidator {
    
    private static final Pattern countryAndCityPattern = Pattern.compile("[a-zA-Z]{2,20}");
    private static final Pattern streetPattern = Pattern.compile("^[a-zA-Z0-9 ]*${2,20}");
    private static final Pattern buildingPattern = Pattern.compile("^[a-zA-Z0-9 ]*${1,5}");
    private static final Pattern hotelNamePattern = Pattern.compile("^[a-zA-Z0-9 ]*${3,15}"); // TODO подумать ещё
    
    public static boolean validateAddressData(String country, String city, String street, String building) {
        boolean isValid =  (countryAndCityPattern.matcher(country).matches() 
                            && countryAndCityPattern.matcher(city).matches() 
                            && streetPattern.matcher(street).matches()
                            && buildingPattern.matcher(building).matches());
        return isValid;
    }
    
    public static boolean validateHotelName(String name) { // подредактировать RE убрать isBlank
        boolean isValid =  (hotelNamePattern.matcher(name).matches() && !name.isBlank());
        return isValid;
    }

}
