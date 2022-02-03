import java.io.*;
import java.util.ArrayList;

/**
 * @author Fatih Goncagül
 */

public class Path {

    static String input;
    static String output;
    static String x;

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        String[] strArr;
        input = args[0];
        output = args[1];
        x = args[2];
        int given = Integer.parseInt(x);


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

        ArrayList<Integer> emptyArr = new ArrayList<>();

        //TreeMap<Integer, Tree.TreeNode> indexRootMap = new TreeMap<>();

        Tree.findPath(tree.root, emptyArr, output, 0, given);

    }


}


class Tree {


    TreeNode root;

    /**
     * i added a int field "index" to keep indexes of nodes.
     */
    static class TreeNode {

        int data;
        TreeNode leftChild;
        TreeNode rightChild;
        int index;


        TreeNode(int data) {
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
            index = 0;
        }
    }

    /**
     * finds paths for given number and writes it to the output file
     *
     * @param node   the node that passed from the input file
     * @param arr    empty arraylist to keep paths
     * @param output output directory
     * @param sum    to keep the sum of paths
     * @param x      given number
     *               returns none
     */

    static void findPath(TreeNode node, ArrayList<Integer> arr, String output, int sum, int x) {
        if (node == null) {
            return;
        }

        sum = sum + node.data;
        //stack.push(node.data);
        arr.add(node.index);
        //arr.add(node.data)
        if (sum == x) {
            if (node.rightChild == null && node.leftChild == null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(output));) {
                    for (int i = 0; i < arr.size(); i++) {
                        if (i != arr.size() - 1) {
                            writer.write("T[" + arr.get(i) + "]+");
                            System.out.print("T[" + arr.get(i) + "]+");
                        } else {
                            writer.write("T[" + arr.get(i) + "]=" + x);
                            System.out.println("T[" + arr.get(i) + "]=" + x);
                        }
                        // T[0]+T[1]+T[3]+T[8]=22
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        if (node.leftChild != null) {
            findPath(node.leftChild, arr, output, sum, x);
        }

        if (node.rightChild != null) {
            findPath(node.rightChild, arr, output, sum, x);
        }
        // sum = sum - node.data;
        //stack.pop();
        arr.remove(arr.size() - 1);
    }

    /**
     * @param arr  it contains the required node values
     * @param root
     * @param i
     * @param size
     * @return returns the tree that has been constructed
     */
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
                root.index = i;

            } else {
                return root;
            }
        }
        if (root.leftChild == null && root.rightChild == null) {
            if (left < size && arr.get(left) != null) {
                root.leftChild = new TreeNode(arr.get(left));
                root.leftChild.index = left;
            }
            if (right < size && arr.get(right) != null) {
                root.rightChild = new TreeNode(arr.get(right));
                root.rightChild.index = right;
            }
        }

        levelorder(arr, root.leftChild, left, size);
        levelorder(arr, root.rightChild, right, size);

        return root;
    }

}