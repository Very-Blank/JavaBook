package data;

import java.util.ArrayList;

public class Branch <T> {
    private ArrayList<Integer> keys;
    private T value;

    public Branch(String stringKeys, T value){
        assert stringKeys.length() != 0;

        this.keys = new ArrayList<Integer>(stringKeys.length());
        for (int i = 0; i < stringKeys.length(); i++){
            keys.add(stringKeys.codePointAt(i));
        }
        this.value = value;
    }

    public T getValue(){
        return this.value;
    }

    public ArrayList<Integer> getKeys(){
        return this.keys;
    }
}
