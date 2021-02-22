package by.jwd.task6.entity;

import java.io.Serializable;
import java.util.Set;

public class Booking implements Serializable {
    
    private int bookingId;
    private Hotel hotel;
    private long from;
    private long till;
    private int guestsNumber;
    private MealType meal;
    private Set<Room> room;
    
    public Booking() {}

    public Booking(int bookingId, Hotel hotel, long from, long till, int guestsNumber, MealType meal, Set<Room> room) {
        this.bookingId = bookingId;
        this.hotel = hotel;
        this.from = from;
        this.till = till;
        this.guestsNumber = guestsNumber;
        this.meal = meal;
        this.room = room;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTill() {
        return till;
    }

    public void setTill(long till) {
        this.till = till;
    }

    public int getGuestsNumber() {
        return guestsNumber;
    }

    public void setGuestsNumber(int guestsNumber) {
        this.guestsNumber = guestsNumber;
    }

    public MealType getMeal() {
        return meal;
    }

    public void setMeal(MealType meal) {
        this.meal = meal;
    }

    public Set<Room> getRoom() {
        return room;
    }

    public void setRoom(Set<Room> room) {
        this.room = room;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + bookingId;
        result = prime * result + (int) (from ^ (from >>> 32));
        result = prime * result + guestsNumber;
        result = prime * result + ((hotel == null) ? 0 : hotel.hashCode());
        result = prime * result + ((meal == null) ? 0 : meal.hashCode());
        result = prime * result + ((room == null) ? 0 : room.hashCode());
        result = prime * result + (int) (till ^ (till >>> 32));
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
        Booking other = (Booking) obj;
        if (bookingId != other.bookingId)
            return false;
        if (from != other.from)
            return false;
        if (guestsNumber != other.guestsNumber)
            return false;
        if (hotel == null) {
            if (other.hotel != null)
                return false;
        } else if (!hotel.equals(other.hotel))
            return false;
        if (meal == null) {
            if (other.meal != null)
                return false;
        } else if (!meal.equals(other.meal))
            return false;
        if (room == null) {
            if (other.room != null)
                return false;
        } else if (!room.equals(other.room))
            return false;
        if (till != other.till)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Booking [bookingId=");
        builder.append(bookingId);
        builder.append(", hotel=");
        builder.append(hotel);
        builder.append(", from=");
        builder.append(from);
        builder.append(", till=");
        builder.append(till);
        builder.append(", guestsNumber=");
        builder.append(guestsNumber);
        builder.append(", meal=");
        builder.append(meal);
        builder.append(", room=");
        builder.append(room);
        builder.append("]");
        return builder.toString();
    }

}
