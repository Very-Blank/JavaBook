package data;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


//Java black magic
public abstract class Data<T extends Data<T>>{
    //It would be nice that this was a final int, but
    //at init we don't really know the final ID for certain.
    private int ID;

    public Data(int ID){
        this.ID = ID;
    }

    // Converts json object to data and sets it
    public abstract void fromJsonObject(JSONObject json); // I would love to use static here, but java is stupid.

    public abstract JSONObject toJson();

    public static String dateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        return date.format(formatter);
    }

    public static LocalDate stringToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        return LocalDate.parse(date, formatter);
    }

    // Creates a deep copy of data
    public abstract T copy();

    public int getID(){
        return this.ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }
}
