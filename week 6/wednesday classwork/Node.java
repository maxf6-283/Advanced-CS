public class Node{
    private String data; 
    private Node next;
    
    public Node(String d) {
        data = d;
    }

    public String get() {
        return data;
    }

    public Node next() {
        return next;
    }

    public void setNext(Node n){
        next = n;
    }

    public void set(String d) {
        data = d;
    }
    
}