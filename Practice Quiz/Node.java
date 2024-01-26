public class Node<E> {
    private Node<E> left;
    private Node<E> right;
    private Node<E> parent;
    private E data;

    public Node(E data) {
        this.data = data;
    }

    public E get() {
        return data;
    }

    public Node<E> getLeft() {
        return left;
    }

    public Node<E> getRight() {
        return right;
    }

    public Node<E> getParent() {
        return parent;
    }

    public void set(E data) {
        this.data = data;
    }

    public void setLeft(Node<E> left) {
        if (left != null)
            left.parent = this;
        
        this.left = left;
    }

    public void setRight(Node<E> right) {
        if (right != null)
            right.parent = this;

        this.right = right;
    }
}
