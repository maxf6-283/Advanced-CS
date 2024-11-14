public class BST<E extends Comparable<E>> {
    private Node<E> root;

    public BST() {
        root = null;
    }

    public void add(E data) {
        if (root == null) {
            root = new Node<>(data);
            return;
        }
        Node<E> current = root;
        while (true) {
            if (data.compareTo(current.get()) < 0) {
                if (current.getLeft() == null) {
                    current.setLeft(new Node<>(data));
                    return;
                } else {
                    current = current.getLeft();
                }
            } else {
                if (current.getRight() == null) {
                    current.setRight(new Node<>(data));
                    return;
                } else {
                    current = current.getRight();
                }
            }
        }
    }

    public String toString() {
        return inOrderString(root);
    }

    private String inOrderString(Node<E> current) {
        String s = "";

        if (current != null) {
            s += inOrderString(current.getLeft());

            s += current + " ";

            s += inOrderString(current.getRight());
        }

        return s;
    }

    public String toStringPreOrder() {
        return preOrderString(root);
    }

    private String preOrderString(Node<E> current) {
        String s = "";

        if (current != null) {
            s += current + " ";

            s += preOrderString(current.getLeft());

            s += preOrderString(current.getRight());
        }

        return s;
    }

    public boolean contains(E data) {
        Node<E> current = root;
        while (true) {
            int comp = data.compareTo(current.get());
            if (comp < 0) {
                if (current.getLeft() == null) {
                    return false;
                } else {
                    current = current.getLeft();
                }
            } else if (comp > 0) {
                if (current.getRight() == null) {
                    return false;
                } else {
                    current = current.getRight();
                }
            } else {
                return true;
            }
        }
    }

    public void remove(E data) {
        Node<E> current = root;
        Node<E> prevNode = current;

        while (true) {
            int comp = data.compareTo(current.get());
            if (comp < 0) {
                if (current.getLeft() == null) {
                    return;
                } else {
                    prevNode = current;
                    current = current.getLeft();
                }
            } else if (comp > 0) {
                if (current.getRight() == null) {
                    return;
                } else {
                    prevNode = current;
                    current = current.getRight();
                }
            } else {
                if (current == root) {
                    if (root.getLeft() == null) {
                        root = root.getRight();
                    } else if (root.getRight() == null) {
                        root = root.getLeft();
                    } else {
                        prevNode = root;
                        current = root.getLeft();
                        while (current.getRight() != null) {
                            prevNode = current;
                            current = current.getRight();
                        }
                        root.set(current.get());
                        if (prevNode == root) {
                            root.setLeft(current.getLeft());
                        } else {
                            prevNode.setRight(current.getLeft());
                        }
                    }
                } else if (current.getLeft() == null && current.getRight() == null) {
                    if (prevNode.getLeft() == current) {
                        prevNode.setLeft(null);
                    } else {
                        prevNode.setRight(null);
                    }
                } else if (current.getLeft() == null) {
                    if (prevNode.getLeft() == current) {
                        prevNode.setLeft(current.getRight());
                    } else {
                        prevNode.setRight(current.getRight());
                    }
                } else {
                    Node<E> currentCurrent = current;
                    prevNode = current;
                    current = current.getLeft();
                    while (current.getRight() != null) {
                        prevNode = current;
                        current = current.getRight();
                    }
                    currentCurrent.set(current.get());
                    if (prevNode == currentCurrent) {
                        currentCurrent.setLeft(current.getLeft());
                    } else {
                        prevNode.setRight(current.getLeft());
                    }
                }
                return;
            }
        }
    }

    public void clear() {
        root = null;
    }
}
