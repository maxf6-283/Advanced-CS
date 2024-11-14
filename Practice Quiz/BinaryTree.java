public class BinaryTree<E extends Comparable<E>> {
    private Node<E> root = null;

    public void add(E data) {
        if(root == null) {
            root = new Node<>(data);
        } else {
            add(data, root);
        }
    }

    private void add(E data, Node<E> node) {
        int comp = data.compareTo(node.get());
        if(comp < 0) {
            if(node.getLeft() == null) {
                node.setLeft(new Node<>(data));
            } else {
                add(data, node.getLeft());
            }
        } else if (comp > 0) {
            if(node.getRight() == null) {
                node.setRight(new Node<>(data));
            } else {
                add(data, node.getRight());
            }
        }
    }

    public void remove(E data) {
        remove(data, root);
    }

    private boolean remove(E data, Node<E> node) {
        int comp = data.compareTo(node.get());
        if(comp < 0) {
            if(node.getLeft() == null) {
                return false;
            } else {
                return remove(data, node.getLeft());
            }
        } else if (comp > 0) {
            if(node.getRight() == null) {
                return false;
            } else {
                return remove(data, node.getRight());
            }
        } else {
            if(node.getLeft() == null) {
                if(node.getParent().getLeft() == node) {
                    node.getParent().setLeft(node.getRight());
                    return true;
                } else {
                    node.getParent().setRight(node.getRight());
                    return true;
                }
            } else {
                Node<E> n = node.getLeft();
                while(n.getRight() != null) {
                    n = n.getRight();
                }
                node.set(n.get());
                return remove(n.get(), n);
            }
        }
    }

    @Override
    public String toString() {
        return inOrderString(root);
    }

    public String inOrderString(Node<E> node) {
        String r = "";

        if(node != null) {
            r += inOrderString(node.getLeft());

            r += node.get() + ", ";

            r += inOrderString(node.getRight());
        }

        return r;
    }

    public boolean contains(E data) {
        return contains(data, root);
    }

    private boolean contains(E data, Node<E> node) {
        int comp = data.compareTo(node.get());
        if(comp < 0) {
            if(node.getLeft() == null) {
                return false;
            } else {
                return contains(data, node.getLeft());
            }
        } else if (comp > 0) {
            if(node.getRight() == null) {
                return false;
            } else {
                return contains(data, node.getRight());
            }
        } else {
            return true;
        }
    }
}
