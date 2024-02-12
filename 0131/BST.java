import java.awt.Color;
import java.awt.Graphics;

import java.util.List;

public class BST<E extends Comparable<E>> {
    private Node<E> root;
    private Node<E> highlighted;

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

    private void add(E data, Node<E> curr) {
        int comp = data.compareTo(curr.get());

        if (comp < 0) {
            if (curr.getLeft() == null) {
                curr.setLeft(new Node<E>(data));

                balanceAround(curr.getLeft());
            } else {
                add(data, curr.getLeft());
            }
        } else if (comp > 0) {
            if (curr.getRight() == null) {
                curr.setRight(new Node<E>(data));

                balanceAround(curr.getRight());

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

            s += curr.get() + ",";

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
        if (curr == null) {
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

        if (comp < 0) {
            if (curr.getLeft() == null) {
                return -1;
            } else {
                return getHeight(data, curr.getLeft());
            }
        } else if (comp > 0) {
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
        if (root != null) {
            draw(g, root, x, y, width);
        }
    }

    private void draw(Graphics g, Node<E> n, int x, int y, int width) {
        if (n.getLeft() != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x + 10, y + 10, x - width / 2 + 10, y + 50);
            draw(g, n.getLeft(), x - width / 2, y + 40, width / 2);
        }
        if (n.getRight() != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x + 10, y + 10, x + width / 2 + 10, y + 50);
            draw(g, n.getRight(), x + width / 2, y + 40, width / 2);
        }
        if (n == highlighted) {
            g.setColor(Color.CYAN);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillOval(x, y, 20, 20);
        g.setColor(Color.BLACK);
        if (n.get() instanceof Student s) {
            g.drawString(s.toString().substring(0, 2), x + 5, y + 15);
        } else {
            g.drawString(n.get().toString(), x + 5, y + 15);
        }
    }

    public void remove(E data) {
        remove(root, data);
    }

    private void remove(Node<E> curr, E data) {
        int comp = data.compareTo(curr.get());
        if (comp < 0) {
            if (curr.getLeft() == null) {
                return;// throw new IllegalArgumentException("Element not found");
            } else {
                remove(curr.getLeft(), data);
            }
        } else if (comp > 0) {
            if (curr.getRight() == null) {
                return;// throw new IllegalArgumentException("Element not found");
            } else {
                remove(curr.getRight(), data);
            }
        } else {
            if (curr.getRight() == null) {
                if (curr.getParent() == null) {
                    root = curr.getLeft();
                } else if (curr.getParent().getRight() == curr) {
                    curr.getParent().setRight(curr.getLeft());
                } else {
                    curr.getParent().setLeft(curr.getLeft());
                }
            } else {
                Node<E> maxNode = curr.getRight();
                while (maxNode.getLeft() != null) {
                    maxNode = maxNode.getLeft();
                }
                curr.set(maxNode.get());
                remove(maxNode, maxNode.get());
            }
        }
    }

    public E get(E data) {
        highlighted = get(root, data);
        return highlighted == null ? null : highlighted.get();
    }

    private Node<E> get(Node<E> curr, E data) {
        int comp = data.compareTo(curr.get());
        if (comp < 0) {
            if (curr.getLeft() == null) {
                return null;// throw new IllegalArgumentException("Element not found");
            } else {
                return get(curr.getLeft(), data);
            }
        } else if (comp > 0) {
            if (curr.getRight() == null) {
                return null;// throw new IllegalArgumentException("Element not found");
            } else {
                return get(curr.getRight(), data);
            }
        } else {
            return curr;
        }
    }

    public void balance() {
        DLList<E> elems = new DLList<>();
        addToList(elems, root);
        root.set(elems.get(elems.size() / 2));
        root.setRight(makeNode(elems, elems.size() / 2 + 1, elems.size()));
        root.setLeft(makeNode(elems, 0, elems.size() / 2));

    }

    private void addToList(List<E> l, Node<E> n) {
        if (n != null) {
            addToList(l, n.getLeft());
            l.addLast(n.get());
            addToList(l, n.getRight());
        }
    }

    private Node<E> makeNode(List<E> l, int startIndex, int endIndex) {
        if (endIndex - startIndex > 0) {
            Node<E> node = new Node<>(l.get((startIndex + endIndex) / 2));
            node.setRight(makeNode(l, (startIndex + endIndex) / 2 + 1, endIndex));
            node.setLeft(makeNode(l, startIndex, (startIndex + endIndex) / 2));
            return node;
        }
        return null;
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node<E> curr) {
        if (curr == null) {
            return true;
        }
        if (Math.abs(getHeight(curr.getLeft()) - getHeight(curr.getRight())) > 1) {
            return false;
        }
        return isBalanced(curr.getLeft()) && isBalanced(curr.getRight());
    }

    private void rotateRight(Node<E> n) {
        Node<E> parPar = n.getParent().getParent();
        n.setRight(n.getParent());
        n.getRight().setLeft(null);

        if (parPar != null && parPar.getRight() == n.getRight()) {
            parPar.setRight(n);
        } else if (parPar != null) {
            parPar.setLeft(n);
        } else {
            root = n;
        }
    }

    private void rotateLeft(Node<E> n) {
        Node<E> parPar = n.getParent().getParent();
        n.setLeft(n.getParent());
        n.getLeft().setRight(null);

        if (parPar != null && parPar.getLeft() == n.getLeft()) {
            parPar.setLeft(n);
        } else if (parPar != null) {
            parPar.setRight(n);
        } else {
            root = n;
        }
    }

    private void rotateLeftRight(Node<E> n) {
        rotateLeft(n);
        rotateRight(n);
    }

    private void rotateRightLeft(Node<E> n) {
        rotateRight(n);
        rotateLeft(n);
    }

    private void balanceAround(Node<E> n) {
        if(n.getParent() == null || n.getParent().getParent() == null) {
            return;
        }
        Node<E> parPar = n.getParent().getParent();

        if (isBalanced(parPar)) {
            Node<E> parParPar = parPar.getParent();
            if (isBalanced(parParPar)) {
                return;
            } else {
                
                if (parParPar.getLeft().getLeft() == null) {
                    // the left side of the tree has only one node
                    if (parPar.getLeft().getLeft() == n || parPar.getLeft().getRight() == n) {
                        if (n.getParent().getRight() == n) {
                            // 2C
                            rotateLeft(n);
                            n = n.getLeft();
                        }
                        // 2B
                        rotateRight(n);
                        parPar = n;
                    }
                    // 2A
                    Node<E> parParParPar = parParPar.getParent();
                    parParPar.setRight(parPar.getLeft());
                    parPar.setLeft(parParPar);
                    if (parParParPar != null && parParParPar.getRight() == parParPar) {
                        parParParPar.setRight(parPar);
                    } else if (parParParPar != null) {
                        parParParPar.setLeft(parPar);
                    } else {
                        root = parPar;
                    }
                } else if (parParPar.getRight().getRight() == null) {
                    // the right side of the tree had only one node
                    if (parPar.getRight().getRight() == n || parPar.getRight().getLeft() == n) {
                        if (n.getParent().getLeft() == n) {
                            // 2C
                            rotateRight(n);
                            n = n.getRight();
                        }
                        // 2B
                        rotateLeft(n);
                        parPar = n;
                    }
                    // 2A
                    Node<E> parParParPar = parParPar.getParent();
                    parParPar.setLeft(parPar.getRight());
                    parPar.setRight(parParPar);
                    if (parParParPar != null && parParParPar.getLeft() == parParPar) {
                        parParParPar.setLeft(parPar);
                    } else if (parParParPar != null) {
                        parParParPar.setRight(parPar);
                    } else {
                        root = parPar;
                    }
                }
            }
        } else {
            if (parPar.getLeft() == n.getParent()) {
                if (n.getParent().getLeft() == n) {
                    rotateRight(n.getParent());
                } else if (n.getParent().getRight() == n) {
                    rotateLeftRight(n);
                }
            } else if (parPar.getRight() == n.getParent()) {
                if (n.getParent().getLeft() == n) {
                    rotateRightLeft(n);
                } else if (n.getParent().getRight() == n) {
                    rotateLeft(n.getParent());
                }
            }
        }
    }
}