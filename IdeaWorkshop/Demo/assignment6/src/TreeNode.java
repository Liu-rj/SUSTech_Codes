public class TreeNode<T> {
    private T value;
    private TreeNode<T> leftNode;
    private TreeNode<T> rightNode;

    public TreeNode(T value) {
        this.value = value;
        leftNode = null;
        rightNode = null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public TreeNode<T> getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(TreeNode<T> leftNode) {
        this.leftNode = leftNode;
    }

    public TreeNode<T> getRightNode() {
        return rightNode;
    }

    public void setRightNode(TreeNode<T> rightNode) {
        this.rightNode = rightNode;
    }
}
