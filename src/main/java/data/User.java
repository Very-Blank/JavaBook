package data;

import java.util.ArrayList;
import java.time.LocalDate;
import org.json.JSONObject;
import org.json.JSONArray;

public class User extends Data<User> {
    private String name;
    private String email;
    private String phoneNumber;
    private String countryCode;

    private ArrayList<Integer> loans;
    private LocalDate birthday;

    public User() {
        super(-1);
        this.name = "";
        this.email = "";
        this.phoneNumber = "";
        this.countryCode = "+358";
        this.loans = new ArrayList<Integer>();
        this.birthday = LocalDate.now();
    }

    public User(int ID, String name, String email, String countryCode, String phoneNumber, ArrayList<Integer> loans,
            LocalDate birthday) {
        super(ID);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
        this.loans = loans;
        this.birthday = birthday;
    }

    public void fromJsonObject(JSONObject json) {
        this.setID(json.getInt("id"));
        this.name = json.getString("name");
        this.email = json.getString("email");
        this.phoneNumber = json.getString("phoneNumber");
        this.countryCode = json.getString("countryCode");
        this.birthday = Data.stringToDate(json.getString("birthday"));
        JSONArray array = json.getJSONArray("loans");
        ArrayList<Integer> newList = new ArrayList<Integer>(array.length());

        for (int i = 0; i < array.length(); i++) {
            newList.add(array.getInt(i));
        }

        this.loans = newList;
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("id", this.getID());
        object.put("name", this.name);
        object.put("email", this.email);
        object.put("phoneNumber", this.phoneNumber);
        object.put("countryCode", this.countryCode);
        object.put("loans", this.loans);
        object.put("birthday", Data.dateToString(this.birthday));

        return object;
    }

    public User copy() {
        ArrayList<Integer> newList = new ArrayList<Integer>(this.loans.size());
        for (int i = 0; i < this.loans.size(); i++) {
            newList.add(this.loans.get(i));
        }

        return new User(this.getID(), this.name, this.email, this.phoneNumber, this.countryCode, newList,
                this.birthday);
    }

    public void update(String name, String email, String countryCode, String phoneNumber, ArrayList<Integer> loans,
            LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.loans = loans;
        this.birthday = birthday;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public ArrayList<Integer> getLoans() {
        return this.loans;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }
}
