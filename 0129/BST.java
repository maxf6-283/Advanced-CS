import java.awt.Graphics;

public class BST<E extends Comparable<E>> {
    private Node<E> root;

    public BST() {
        root = null;
    }

    public void add(E data) {
        if (root == null) {
            root = new Node<E>(data);
        } else {
            add(data, root);
        }
    }

    public void add(E data, Node<E> curr) {
        int comp = data.compareTo(curr.get());

        if(comp < 0) {
            if (curr.getLeft() == null) {
                curr.setLeft(new Node<E>(data));
            } else {
                add(data, curr.getLeft());
            }
        } else if(comp > 0) {
            if (curr.getRight() == null) {
                curr.setRight(new Node<E>(data));
            } else {
                add(data, curr.getRight());
            }
        }
    }

    public String toString() {
        return inOrderString(root);
    }

    private String inOrderString(Node<E> curr) {
        String s = "";

        if (curr != null) {
            s += inOrderString(curr.getLeft());

            s += curr.get() + " ";

            s += inOrderString(curr.getRight());
        }

        return s;
    }

    public String toStringPreOrder() {
        return preOrderString(root);
    }

    private String preOrderString(Node<E> curr) {
        String s = "";

        if (curr != null) {
            s += curr.get() + " ";
            
            s += preOrderString(curr.getLeft());
            
            s += preOrderString(curr.getRight());

        }

        return s;
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node<E> curr) {
        if(curr == null) {
            return -1;
        } else {
            return Math.max(getHeight(curr.getLeft()), getHeight(curr.getRight())) + 1;
        } 
    }
    

    public int getHeight(E data) {
        return getHeight(data, root);
    }

    public int getLevel() {
        return getHeight() + 1;
    }

    private int getHeight(E data, Node<E> curr) {
        int comp = data.compareTo(curr.get());

        if(comp < 0) {
            if (curr.getLeft() == null) {
                return -1;
            } else {
                return getHeight(data, curr.getLeft());
            }
        } else if(comp > 0) {
            if (curr.getRight() == null) {
                return -1;
            } else {
                return getHeight(data, curr.getRight());
            }
        } else {
            return getHeight(curr);
        }
    }

    public void clear() {
        root = null;
    }

    public void draw(Graphics g, int x, int y, int width) {
        draw(g, root, x, y, width);
    }

    private void draw(Graphics g, Node<E> n, int x, int y, int width) {
        g.drawString(n.get().toString(), x, y);
        if(n.getLeft() != null) {
            g.drawLine(x, y, x-width/2, y+40);
            draw(g, n.getLeft(), x-width/2, y+40, width/2);
        }
        if(n.getRight() != null) {
            g.drawLine(x, y, x+width/2, y+40);
            draw(g, n.getRight(), x+width/2, y+40, width/2);
        }
    }
}