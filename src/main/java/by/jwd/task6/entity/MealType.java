package by.jwd.task6.entity;

import java.util.Optional;

public enum MealType {
    
    BB(1), HB(2), FB(3), AI(4), NONE(5);
    
    private int typeIndex;
    
    private MealType(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    public int getTypeIndex() {
        return typeIndex;
    }
    
    public static Optional<MealType> getMealTypeByIndex(int index) {
        Optional<MealType> mealType = Optional.empty();
        for (MealType type : values()) {
            if (type.getTypeIndex() == index) {
                mealType = Optional.of(type);
            }
        }
        return mealType;
    }

}
