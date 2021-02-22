package by.jwd.task6.entity;

import java.util.Optional;

public enum RoomType {
    
    SINGLE(1), DOUBLE(2), DELUXE(3);
    
    private int typeIndex;
    
    private RoomType(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    public int getTypeIndex() {
        return typeIndex;
    }
    
    public static Optional<RoomType> getRoomTypeByIndex(int index) {
        Optional<RoomType> roomType = Optional.empty();
        for (RoomType type : values()) {
            if (type.getTypeIndex() == index) {
                roomType = Optional.of(type);
            }
        }
        return roomType;
    }

}
