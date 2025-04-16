package data;
import java.time.LocalDate;

import org.json.JSONObject;

public class Loan extends Data<Loan> {
    private int bookID;
    private int userID;
    private LocalDate dueDate;

    public Loan(){
        super(-1);
        this.bookID = -1;
        this.userID = -1;
    }

    public Loan(int ID, int book, int user, LocalDate date){
        super(ID);
        this.bookID = book;
        this.userID = user;
        this.dueDate = date;
    }

    public void fromJsonObject(JSONObject json){
        this.setID(json.getInt("id"));
        this.bookID = json.getInt("bookID");
        this.userID = json.getInt("userID");
        this.dueDate = Data.stringToDate(json.getString("dueDate"));
    }


    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("id", "" + this.getID());
        object.put("userID", "" + this.bookID);
        object.put("bookID", this.userID);
        object.put("dueDate", Data.dateToString(this.dueDate));

        return object;
    }

    public int getBookID(){
        return this.bookID;
    }

    public int getUserID(){
        return this.userID;
    }

    public Loan copy(){
        return new Loan(this.getID(), bookID, userID, dueDate);
    }
}
