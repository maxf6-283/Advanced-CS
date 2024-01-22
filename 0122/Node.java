public class Node<E> {
    private Node<E> parent;
    private Node<E> left;
    private Node<E> right;
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
        this.left = left;
        if (left != null)
            left.parent = this;
    }

    public void setRight(Node<E> right) {
        this.right = right;
        if (right != null)
            right.parent = this;
    }
}