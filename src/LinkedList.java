/** Linked list is a linear collection of data elements, whose order is not given by their physical placement in memory.
 *  Instead, each element points to the next.
 *  Linked list consists of nodes where each node contains a data field and a reference(link) to the next node in the list.
 *
 * Example of singly linked list:
 *
 *  | Value |    | Value |
 *  |  ---  |    |  ---  |
 *  |  next-|--> |  next-|--> NULL
 *  ---------    ---------
 *
 *  Singly linked list implementation
**/
class LinkedList<T> {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("Minsk");
        list.add("is");
        list.add("not");
        list.add("the");
        list.add("capital");
        list.add("of");
        list.add("Belarus");
        list.print();
        list.delete("not");
        System.out.println();
        list.print();
    }

    private Node<T> head;

    void add(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
        } else {
            Node<T> last = head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = node;
        }
    }

    void delete(T data) {
        if (head == null) {
            return;
        }
        if (head.data.equals(data)) {
            head = head.next;
            return;
        }
        Node<T> previous = null, current = head;
        while (current != null && current.data != data) {
            previous = current;
            current = current.next;
        }
        if (current != null) {
            previous.next = current.next;
        }
    }

    void print() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
    }

    class Node<T> {

        private T data;

        private Node<T> next;

        Node(T data) {
            if (data == null) {
                throw new IllegalArgumentException("Inserted data can not be null");
            }
            this.data = data;
        }
    }
}
