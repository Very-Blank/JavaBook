package data;

import java.util.ArrayList;

public class TreeNode<T> {
    private int key;
    private ArrayList<T> values;
    private ArrayList<TreeNode<T>> branches;

    public TreeNode(int key){
        this.key = key;
        this.values = new ArrayList<T>();
        this.branches = new ArrayList<TreeNode<T>>();
    }

    public int getKey(){
        return this.key;
    }

    public void addValue(T value){
        this.values.add(value);
    }

    public ArrayList<T> getNodesValues(){
        return this.values;
    }

    public ArrayList<T> getValues(){
        ArrayList<T> values  = new ArrayList<T>();
        values.addAll(this.values);

        for (int i = 0; i < branches.size(); i++) {
            values.addAll(branches.get(i).getValues());
        }

        return values;
    }

    public void addBranch(TreeNode<T> branch){
        branches.add(branch);
    }

    public ArrayList<TreeNode<T>> getBranches(){
        return this.branches;
    }

    // public ArrayList<T> getBr
}
