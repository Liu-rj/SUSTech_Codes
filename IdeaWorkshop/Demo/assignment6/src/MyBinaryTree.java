import java.util.LinkedList;

public class MyBinaryTree<T> {
    private TreeNode<T> root;
    private int size;

    public MyBinaryTree(T value) {
        this.root = new TreeNode<T>(value);
        size = 1;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addTreeNodes(String directions, T[] values){
        char[] chars = directions.toCharArray();
        TreeNode<T> treeNode = root;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '0'){
                if (treeNode.getLeftNode() == null){
                    treeNode.setLeftNode(new TreeNode<>(values[i]));
                    size++;
                } else {
                    treeNode.getLeftNode().setValue(values[i]);
                }
                treeNode = treeNode.getLeftNode();
            } else if (chars[i] == '1'){
                if (treeNode.getRightNode() == null){
                    treeNode.setRightNode(new TreeNode<>(values[i]));
                    size++;
                } else {
                    treeNode.getRightNode().setValue(values[i]);
                }
                treeNode = treeNode.getRightNode();
            }
        }
    }

    public String levelTraverse(){
        StringBuilder result = new StringBuilder();
        TreeNode<T> treeNode;
        if (root == null){
            return null;
        }
        LinkedList<TreeNode<T>> linkedList = new LinkedList<>();
        linkedList.add(root);
        while (!linkedList.isEmpty()){
            treeNode = linkedList.poll();
            result.append(treeNode.getValue()).append(" ");
            if (treeNode.getLeftNode() != null){
                linkedList.add(treeNode.getLeftNode());
            }
            if (treeNode.getRightNode() != null){
                linkedList.add(treeNode.getRightNode());
            }
        }
        return result.toString();
    }
}
