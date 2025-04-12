package data;

public class Date{
    private int date;

    public Date(int day, int month, int year) {
        if (day < 1 || day > 31) throw new IllegalArgumentException("Day must be between 1 and 31");
        if (month < 1 || month > 12) throw new IllegalArgumentException("Month must be between 1 and 12");
        if (year < 0 || year > 65535) throw new IllegalArgumentException("Year must be between 0 and 65535");

        date = (day << 24) | (month << 16) | year;
    }

    // BUG: check that the data is within acceptable limits
    public Date(int rawData) {
        //if (day < 1 || day > 31) throw new IllegalArgumentException("Day must be between 1 and 31");
        //if (month < 1 || month > 12) throw new IllegalArgumentException("Month must be between 1 and 12");
        //if (year < 0 || year > 65535) throw new IllegalArgumentException("Year must be between 0 and 65535");
        date = rawData;
    }

    public void setDate(int day, int month, int year) {
        if (day < 1 || day > 31) throw new IllegalArgumentException("Day must be between 1 and 31");
        if (month < 1 || month > 12) throw new IllegalArgumentException("Month must be between 1 and 12");
        if (year < 0 || year > 65535) throw new IllegalArgumentException("Year must be between 0 and 65535");

        date = (day << 24) | (month << 16) | year;
    }

    public int getDay() {
        return (date >> 24) & 0xFF;
    }

    public int getMonth() {
        return (date >> 16) & 0xFF;
    }

    public int getYear() {
        return date & 0xFFFF;
    }
}

