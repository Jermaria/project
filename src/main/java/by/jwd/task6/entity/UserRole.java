package by.jwd.task6.entity;

import java.util.Optional;

public enum UserRole {
    
    ADMIN(1), TRAVELLER(2), HOTELIER(3), GUEST(4);
    
    private int roleIndex;
    
    private UserRole(int roleIndex) {
        this.roleIndex = roleIndex;
    }

    public int getRoleIndex() {
        return roleIndex;
    }
    
    public static Optional<UserRole> getRoleByIndex(int index) {
        Optional<UserRole> userRole = Optional.empty();
        for (UserRole role : values()) {
            if (role.getRoleIndex() == index) {
                userRole = Optional.of(role);
            }
        }
        return userRole;
    }

}
