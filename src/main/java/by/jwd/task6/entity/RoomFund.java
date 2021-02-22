package by.jwd.task6.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class RoomFund implements Serializable {
    
    private Map<RoomType, BigDecimal> rooms = new HashMap<>();       // TODO подумать еще. 
    private Map<RoomType, Integer> numberOfRooms = new HashMap<>(); // да, вычисляемое поле, но нет нужды создавать и
                                                                    //хранить десятки одинаковых комнат
                                                            // List<Room> будет тяжелым, а из него все равно нужно только 3 типа комнат
    public RoomFund() {}

    public RoomFund(Map<RoomType, BigDecimal> rooms, Map<RoomType, Integer> numberOfRooms) {
        this.rooms = rooms;
        this.numberOfRooms = numberOfRooms;
    }

    public Map<RoomType, BigDecimal> getRooms() {
        return rooms;
    }

    public void setRooms(Map<RoomType, BigDecimal> rooms) {
        this.rooms = rooms;
    }

    public Map<RoomType, Integer> getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Map<RoomType, Integer> numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numberOfRooms == null) ? 0 : numberOfRooms.hashCode());
        result = prime * result + ((rooms == null) ? 0 : rooms.hashCode());
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
        RoomFund other = (RoomFund) obj;
        if (numberOfRooms == null) {
            if (other.numberOfRooms != null)
                return false;
        } else if (!numberOfRooms.equals(other.numberOfRooms))
            return false;
        if (rooms == null) {
            if (other.rooms != null)
                return false;
        } else if (!rooms.equals(other.rooms))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RoomFund [rooms=");
        builder.append(rooms);
        builder.append(", numberOfRooms=");
        builder.append(numberOfRooms);
        builder.append("]");
        return builder.toString();
    }
    
    

}
