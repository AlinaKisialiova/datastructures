/**
 * Stack is a linear data structure which follows LIFO operations order.
 * push(), pop(), top() all take O(1) time.
 * <p>
 * Array-based implementation.
 */
class FixedSizeStack<T> {

    private T[] items;
    private int size;
    private int capacity;

    FixedSizeStack(int capacity) {
        items = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean isFull() {
        return size == items.length;
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * Adds an item in the stack.
     */
    void push(T item) {
        if (isFull()) {
            throw new IllegalArgumentException("You cannot add item. Stack is full. Stack capacity is " + getCapacity());
        }
        items[size++] = item;
    }

    /**
     * Removes last pushed item from the stack.
     */
    T pop() {
        if (isEmpty()) {
            throw new IllegalArgumentException("You cannot pop item. Stack is empty.");
        }
        return items[--size];
    }

    /**
     * Returns top element of stack.
     */
    T top() {
        return items[size - 1];
    }

     void printFromBottom(){
         FixedSizeStack<T> temp = new FixedSizeStack<>(getCapacity());
         while (!isEmpty()) {
             temp.push(pop());
         }
         while (!temp.isEmpty()){
             System.out.println(temp.top());
             push(temp.pop());
         }
     }

    public static void main(String[] args) {
        FixedSizeStack<String> stack = new FixedSizeStack<>(6);
        stack.push("From the poem 'Hto ty hetki' (Janka Kupala):");
        stack.push("Chto ty hetki? — Svoj, tutejšy.");
        stack.push("Čaho chočaš? — Doli lepšaj.");
        stack.push("Jakoj doli? — Chleba, soli.");
        stack.push("Čaho bolej? — Ziamli, voli.");
        stack.push("<...>");

        System.out.println("--------------------------");
        stack.printFromBottom();
        System.out.println("--------------------------");
        System.out.println("Pop(): " + stack.pop());
        System.out.println("Top() after Pop(): " + stack.top());
    }
}
