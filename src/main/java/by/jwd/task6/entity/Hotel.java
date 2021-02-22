package by.jwd.task6.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Hotel implements Serializable {
    private int hotelId;
    private String name;
    private Address address;
    private RoomFund roomFund;
    private Map<MealType, BigDecimal> meals;
    private List<String> photoPaths;
    private int starsNumber;
    private boolean wifi;
    
    public Hotel() {}

    public Hotel(int hotelId, String name, Address address, RoomFund roomFund) {
        super();
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.roomFund = roomFund;
    }
    
    public Hotel(int hotelId, String name, Address address) {
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
    }

    public Hotel(int hotelId, String name, Address address, int starsNumber, boolean wifi) {
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.starsNumber = starsNumber;
        this.wifi = wifi;
    }
    
    

    public Hotel(int hotelId, String name, Address address, RoomFund roomFund, Map<MealType, BigDecimal> meals, List<String> photoPaths,
                        int starsNumber, boolean wifi) {
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.roomFund = roomFund;
       // this.rooms = rooms;
        this.meals = meals;
        this.photoPaths = photoPaths;
        this.starsNumber = starsNumber;
        this.wifi = wifi;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public RoomFund getRoomFund() {
        return roomFund;
    }

    public void setRoomFund(RoomFund roomFund) {
        this.roomFund = roomFund;
    }

    public Map<MealType, BigDecimal> getMeals() {
        return meals;
    }

    public void setMeals(Map<MealType, BigDecimal> meals) {
        this.meals = meals;
    }

    public List<String> getPhotoPaths() {
        return photoPaths;
    }

    public void setPhotoPaths(List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }

    public int getStarsNumber() {
        return starsNumber;
    }

    public void setStarsNumber(int starsNumber) {
        this.starsNumber = starsNumber;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + hotelId;
        result = prime * result + ((meals == null) ? 0 : meals.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((photoPaths == null) ? 0 : photoPaths.hashCode());
        result = prime * result + ((roomFund == null) ? 0 : roomFund.hashCode());
        result = prime * result + starsNumber;
        result = prime * result + (wifi ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Hotel other = (Hotel) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (hotelId != other.hotelId)
            return false;
        if (meals == null) {
            if (other.meals != null)
                return false;
        } else if (!meals.equals(other.meals))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (photoPaths == null) {
            if (other.photoPaths != null)
                return false;
        } else if (!photoPaths.equals(other.photoPaths))
            return false;
        if (roomFund == null) {
            if (other.roomFund != null)
                return false;
        } else if (!roomFund.equals(other.roomFund))
            return false;
        if (starsNumber != other.starsNumber)
            return false;
        if (wifi != other.wifi)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Hotel [hotelId=");
        builder.append(hotelId);
        builder.append(", name=");
        builder.append(name);
        builder.append(", address=");
        builder.append(address);
        builder.append(", roomFund=");
        builder.append(roomFund);
        builder.append(", meals=");
        builder.append(meals);
        builder.append(", photoPaths=");
        builder.append(photoPaths);
        builder.append(", starsNumber=");
        builder.append(starsNumber);
        builder.append(", wifi=");
        builder.append(wifi);
        builder.append("]");
        return builder.toString();
    }



    
}
