class Node<T> {

    private T key;

    private Node<T> left;

    private Node<T> right;

    T getKey() {
        return key;
    }

    Node<T> getLeft() {
        return left;
    }

    Node<T> getRight() {
        return right;
    }

    void setKey(T key) {
        this.key = key;
    }

    void setLeft(Node<T> left) {
        this.left = left;
    }

    void setRight(Node<T> right) {
        this.right = right;
    }

    Node(T key) {
        this.key = key;
    }
}
