package com.igaworks.dfinerysample.enums;

public enum BottomNavigationMenu {
    EVENT(1),
    PUSH(2),
    IN_APP_MESSAGE(3),
    SETTING(4);
    private int value;

    BottomNavigationMenu(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static BottomNavigationMenu get(int value){
        switch (value){
            case 1: return EVENT;
            case 2: return PUSH;
            case 3: return IN_APP_MESSAGE;
            case 4: return SETTING;
            default: return EVENT;
        }
    }
}
