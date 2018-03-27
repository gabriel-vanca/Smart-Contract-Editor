package pipe.constants;

public enum FontSizeEnum {

    VERY_SMALL(8),
    SMALL(10),
    NORMAL(12),
    LARGE(14),
    VERY_LARGE(16),
    HUGE(20);

    FontSizeEnum(int i) {
        this.size = i;
    }

    private final int size;

//    public boolean equalsName(String otherName) {
//        // (otherName == null) check is not needed because name.equals(null) returns false
//        return name.equals(otherName);
//    }

    public String toString() {
        return String.valueOf(this.size);
    }

    public int getValue() {return this.size;}

    public static FontSizeEnum getNextInCycle(FontSizeEnum fontSizeEnum) {
        int oldFontSize = fontSizeEnum.getValue();
        for(FontSizeEnum fontSize : FontSizeEnum.values()) {
            int currentValue = fontSize.getValue();
            if(currentValue > oldFontSize)
                return fontSize;
        }
        return VERY_SMALL;
    }
}
