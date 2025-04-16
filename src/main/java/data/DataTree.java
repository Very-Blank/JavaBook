package data;

import java.util.ArrayList;

public class DataTree<T> {
    private ArrayList<TreeNode<T>> branches;

    public DataTree(){
        this.branches = new ArrayList<TreeNode<T>>();
    }

    public void addBranch(Branch<T> branch){
        ArrayList<Integer> keys = branch.getKeys();
        ArrayList<TreeNode<T>> currentBranches = this.branches;
        TreeNode<T> currentNode = null;
        int currentKey = 0;

        while (true) {
            for (int i = 0; i < currentBranches.size(); i++) {
                if (currentBranches.get(i).getKey() == keys.get(currentKey)) {
                    currentBranches = currentBranches.get(i).getBranches();
                    currentNode = currentBranches.get(i);

                    currentKey++;

                    if(currentKey == keys.size()){
                        currentNode.addValue(branch.getValue());
                        break;
                    }

                    continue;
                }
            }

            if(currentNode == null){
                TreeNode<T> newBranch = new TreeNode<T>(keys.get(currentKey));
                this.branches.add(newBranch);
                currentNode = newBranch;
                currentKey++;
            }

            for (int i = currentKey; i < keys.size(); i++) {
                TreeNode<T> newBranch = new TreeNode<T>(keys.get(i));
                currentNode.addBranch(newBranch);
                currentNode = newBranch;
            }

            currentNode.addValue(branch.getValue());
            break;
        }
    }
}
