import java.io.*;
import java.util.ArrayList;

/**
 * @author Fatih Goncagül
 */


public class Balanced {
    static String input;
    static String output;

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        String[] strArr;
        input = args[0];
        output = args[1];
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {

            String s = reader.readLine();
            strArr = s.split(" ");

            for (int i = 0; i < strArr.length; i++) {
                if (!(strArr[i].contains("-"))) {
                    arrayList.add(Integer.parseInt(strArr[i]));
                } else {
                    arrayList.add(null);
                }
            }

            System.out.println(arrayList.toString());

        } catch (Exception e) {
            System.out.println(e);
        }


        int n = arrayList.size();
        Tree tree = new Tree();
        tree.root = Tree.levelorder(arrayList, tree.root, 0, n);


        boolean isBalance = false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            isBalance = Tree.isBalanced(tree.root);
            writer.write(String.valueOf(isBalance));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(isBalance);

    }


}

class Tree {


    TreeNode root;

    static class TreeNode {

        int data;
        TreeNode leftChild;
        TreeNode rightChild;

        TreeNode(int data) {
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
        }


    }

    static boolean isBalanced(TreeNode node) {
        int left;
        int right;

        if (node == null) {
            return true;
        }
        left = findHeight(node.leftChild);
        right = findHeight(node.rightChild);

        int difference = Math.abs(left - right);

        if (difference <= 1) {

            return true;
        }

        return false;

    }

    static int findHeight(TreeNode node) {

        if (node == null) {
            return 0;
        }
        return 1 + Math.max(findHeight(node.rightChild), findHeight(node.leftChild));
    }


    static TreeNode levelorder(ArrayList<Integer> arr, TreeNode root, int i, int size) {

        int left = 2 * i + 1;
        int right = 2 * i + 2;


        if (left > size || right > size) {
            return root;
        }
        if (root == null) {
            if (arr.get(i) != null) {
                TreeNode temp = new TreeNode(arr.get(i));
                root = temp;
            } else {
                return root;
            }

        }
        if (root.leftChild == null && root.rightChild == null) {
            if (left < size && arr.get(left) != null) {

                root.leftChild = new TreeNode(arr.get(left));
            }
            if (right < size && arr.get(right) != null) {

                root.rightChild = new TreeNode(arr.get(right));
            }
        }

        levelorder(arr, root.leftChild, left, size);
        levelorder(arr, root.rightChild, right, size);


        return root;
    }


}