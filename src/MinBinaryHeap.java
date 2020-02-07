import java.util.NoSuchElementException;

/**
 * A binary heap is defined as a binary tree with two additional constraints:
 *
 * Shape property: a binary heap is a complete binary tree; that is, all levels of the tree, except possibly the last one (deepest) are fully filled, and,
 * if the last level of the tree is not complete, the nodes of that level are filled from left to right.
 *
 * Heap property: the key stored in each node is either greater than or equal to (≥) or less than or equal to (≤) the keys in the node's children, according to some total order.
 *
 * Examples of Min Heap:
 *
 *             10                      10
 *          /      \               /       \
 *        20        100          15         30
 *       /                      /  \        /  \
 *     30                     40    50    100   40
 */
public class MinBinaryHeap {

    public static void main(String[] args) {
        System.out.println("The Min Heap is ");
        MinBinaryHeap minHeap = new MinBinaryHeap(10);
        minHeap.insert(6);
        minHeap.insert(2);
        minHeap.insert(14);
        minHeap.insert(1);
        minHeap.insert(99);
        minHeap.insert(50);
        minHeap.insert(33);
        minHeap.insert(7);
        minHeap.insert(4);
        minHeap.insert(20);
        minHeap.print();
        System.out.println("Min element: " + minHeap.peek());
        System.out.println("Deleted: " + minHeap.remove(1));
        minHeap.print();
        System.out.println("Min element: " + minHeap.peek());
    }

    private int[] heap;

    private int size;

    public MinBinaryHeap(int capacity) {
        heap = new int[capacity + 1];
        heap[0] = Integer.MIN_VALUE;
    }

    void insert(int el) {
        if (size >= heap.length) {
            throw new IndexOutOfBoundsException("Heap is already full");
        }
        // fill the next empty leaf
        heap[++size] = el;
        int currentIndex = size;
        // if this node-leaf is less it's parent => swap parent and leaf
        while (heap[currentIndex] < heap[getParentIndex(currentIndex)]) {
            swap(currentIndex, getParentIndex(currentIndex));
            currentIndex = getParentIndex(currentIndex);
        }
    }

    int remove(int index) {
        int removed = heap[index];
        heap[index] = heap[--size];
        heapify(index);
        return removed;
    }

    // makes i-th subtree min binary heap
    void heapify(int i) {
        if (!isLeaf(i)) {
            if (heap[i] > heap[getLeftChildIndex(i)] || heap[i] > heap[getRightChildIndex(i)]) {
                if (heap[getLeftChildIndex(i)] < heap[getRightChildIndex(i)]) {
                    swap(i, getLeftChildIndex(i));
                    heapify(getLeftChildIndex(i));
                } else {
                    swap(i, getRightChildIndex(i));
                    heapify(getRightChildIndex(i));
                }
            }
        }
    }

    int peek() {
        if (size <= 0) {
            throw new NoSuchElementException("Heap is empty. Nothing to peek");
        }
        return heap[1];
    }

    void print() {
        for (int i = 1; i <= size / 2; i++) {
            int right = size >= 2 * i + 1 ? heap[2 * i + 1] : Integer.MIN_VALUE;
            System.out.printf("Parent: %s, Left: %s, Right: %s", heap[i], heap[2 * i], right);
            System.out.println();
        }
    }

    private int getParentIndex(int i) {
        return i / 2;
    }

    private int getLeftChildIndex(int i) {
        return 2 * i;
    }

    private int getRightChildIndex(int i) {
        return 2 * i + 1;
    }

    private boolean isLeaf(int i) {
        int lastNotLeaf = size / 2;
        return i >= lastNotLeaf && i <= size;
    }

    private void swap(int currentIndex, int parentIndex) {
        int temp = heap[currentIndex];
        heap[currentIndex] = heap[parentIndex];
        heap[parentIndex] = temp;
    }
}
