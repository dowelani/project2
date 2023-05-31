public class Node {
    private String data;
    private Node head;
    private Node next;
    Node(String data)
    {
        this.data = data;
        head=null;
        next=null;
    }

    public String getData() {
        return data;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
