package data;

import java.util.ArrayList;
import java.time.LocalDate;
import org.json.JSONObject;
import org.json.JSONArray;

public class User extends Data<User> {
    private String name;
    private String email;
    private String phoneNumber;
    private ArrayList<Integer> loans;
    private LocalDate birthday;

    public User() {
        super(-1);
        this.name = "default";
        this.email = "default";
        this.phoneNumber = "default";
        this.loans = new ArrayList<Integer>();
        this.birthday = LocalDate.now();
    }

    public User(int ID, String name, String email, String phoneNumber, ArrayList<Integer> loans, LocalDate birthday) {
        super(ID);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.loans = loans;
        this.birthday = birthday;
    }

    public String[] toStringArray() {
        return new String[] { this.name, this.email, this.phoneNumber, Data.dateToString(this.birthday) };
    }

    public void fromJsonObject(JSONObject json) {
        this.setID(json.getInt("id"));
        this.name = json.getString("name");
        this.email = json.getString("email");
        this.phoneNumber = json.getString("phoneNumber");
        this.birthday = Data.stringToDate(json.getString("birthday"));
        JSONArray array = json.getJSONArray("loans");
        ArrayList<Integer> newList = new ArrayList<Integer>(array.length());

        for (int i = 0; i < array.length(); i++) {
            newList.add(array.getInt(i));
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("id", this.getID());
        object.put("name", this.name);
        object.put("email", this.email);
        object.put("loans", this.loans);
        object.put("birthday", Data.dateToString(this.birthday));

        return object;
    }

    public User copy() {
        ArrayList<Integer> newList = new ArrayList<Integer>(this.loans.size());
        for (int i = 0; i < newList.size(); i++) {
            newList.add(this.loans.get(i));
        }
        return new User(this.getID(), this.name, this.email, this.phoneNumber, newList, this.birthday);
    }
}
