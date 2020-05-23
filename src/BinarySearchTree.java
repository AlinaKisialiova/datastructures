import java.util.LinkedList;
import java.util.Queue;

/**
 * Binary Search Tree is a node-based binary tree data structure which has the following properties:
 * - The left subtree of a node contains only nodes with keys lesser than the node’s key.
 * - The right subtree of a node contains only nodes with keys greater than the node’s key.
 * - The left and right subtree each must also be a binary search tree.
 * <p>
 * Depth First Traversals:
 * - In-order (Left, Root, Right): 2 3 4 5 6 7 8 9
 * - Pre-order (Root, Left, Right): 5 2 4 3 8 6 7 9
 * - Post-order (Left, Right, Root): 3 4 2 7 6 9 8 5
 * <p>
 * Example of Binary Search Tree:
 *
 *              5
 *           /     \
 *         2        8
 *          \     /   \
 *          4    6     9
 *         /      \
 *       3         7
 *
 */
class BinarySearchTree {

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        Node<Integer> root = new Node<>(5);
        tree.insert(2, root);
        tree.insert(4, root);
        tree.insert(8, root);
        tree.insert(6, root);
        tree.insert(7, root);
        tree.insert(3, root);
        tree.insert(9, root);

        System.out.println("\nTraversing in order");
        tree.traverseInOrder(root);
        System.out.println("\nTraversing in pre order");
        tree.traversePreOrder(root);
        System.out.println("\nTraversing in post order");
        tree.traversePostOrder(root);
        System.out.println("\nTraversing in level order (BFS)");
        tree.traverseLevelOrder(root);

        //true
        System.out.println("\nLooking for 9. Found: " + tree.contains(root, 9));
        //false
        System.out.println("Looking for 11. Found: " + tree.contains(root, 11));

        root = tree.delete(4, root);
        System.out.println("\nTraversing in order after deleting 4");
        tree.traverseInOrder(root);
    }

    void insert(Integer key, Node<Integer> node) {
        if (key == null) {
            System.out.println("It is impossible insert null value");
            return;
        }
        if (key < node.getKey()) {
            if (node.getLeft() != null) {
                insert(key, node.getLeft());
            } else {
                node.setLeft(new Node<>(key));
                System.out.println(key + " inserted in the left of " + node.getKey());
            }
        } else if (key > node.getKey()) {
            if (node.getRight() != null) {
                insert(key, node.getRight());
            } else {
                node.setRight(new Node<>(key));
                System.out.println(key + " inserted in the right of " + node.getKey());
            }
        }
    }

    Node<Integer> delete(int key, Node<Integer> node) {
        if (node == null) {
            return null;
        }
        if (key == node.getKey()) {
            // node is leaf
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }
            // node has only one child
            if (node.getLeft() == null) {
                return node.getRight();
            }
            if (node.getRight() == null) {
                return node.getLeft();
            }
            //node has 2 children
            int smallestInRightSubtree = findTheSmallest(node.getRight());
            node.setKey(smallestInRightSubtree);
            node.setRight(delete(smallestInRightSubtree, node.getRight()));
            return node;

        }
        if (key < node.getKey()) {
            node.setLeft(delete(key, node.getLeft()));
            return node;
        }
        node.setRight(delete(key, node.getRight()));
        return node;
    }

    private int findTheSmallest(Node<Integer> node) {
        return node.getLeft() == null ? node.getKey() : findTheSmallest(node.getLeft());
    }

    void traverseInOrder(Node<Integer> node) {
        if (node == null)
            return;
        traverseInOrder(node.getLeft());
        System.out.print(" " + node.getKey());
        traverseInOrder(node.getRight());
    }

    void traversePreOrder(Node<Integer> node) {
        if (node == null)
            return;
        System.out.print(" " + node.getKey());
        traversePreOrder(node.getLeft());
        traversePreOrder(node.getRight());
    }

    void traversePostOrder(Node<Integer> node) {
        if (node == null)
            return;
        traversePostOrder(node.getLeft());
        traversePostOrder(node.getRight());
        System.out.print(" " + node.getKey());
    }

    void traverseLevelOrder(Node<Integer> root) {
        Queue<Node<Integer>> nodes = new LinkedList<>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            Node<Integer> current = nodes.remove();
            System.out.print(current.getKey() + " ");
            if (current.getLeft() != null) {
                nodes.add(current.getLeft());
            }
            if (current.getRight() != null) {
                nodes.add(current.getRight());
            }
        }

    }

    Boolean contains(Node<Integer> current, int key) {
        if (current == null) {
            return false;
        }
        if (current.getKey() == key) {
            return true;
        }
        return key < current.getKey() ?
                contains(current.getLeft(), key) :
                contains(current.getRight(), key);
    }
}
