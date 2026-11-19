
package supermarketmanagementsystem;

import java.util.ArrayList;

/**
 * Binary tree that represents the employee hierarchy, 
 * with level-order insertion and traversal, height and node count.
 *
 * @author diogodias
 */
public class EmployeeTree {

    private EmployeeNode root;
    private int size; // number of nodes, used to size the queue

    public EmployeeTree() {
        root = null;
        size = 0;
    }

    // Level-order insertion: the new node goes into the first free position,
    // level by level, from left to right. This guarantees that no node has
    // more than two children and keeps the tree as compact as possible.
    public void insert(Employee employee) {
        EmployeeNode newNode = new EmployeeNode(employee);

        if (root == null) {
            root = newNode;
            size = 1;
            return;
        }

        NodeQueue queue = new NodeQueue(size + 1);
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            EmployeeNode current = queue.dequeue();

            if (current.left == null) {
                current.left = newNode;
                size++;
                return;
            }
            queue.enqueue(current.left);

            if (current.right == null) {
                current.right = newNode;
                size++;
                return;
            }
            queue.enqueue(current.right);
        }
    }

    // Builds the hierarchy: all rank 1 employees are inserted first, then rank 2,
    // and so on. Because insertion fills the tree level by level, higher-ranked
    // employees end up closer to the root.
    public void buildFromEmployees(ArrayList<Employee> employees) {
        root = null;
        size = 0;

        // Find the lowest level in the hierarchy (the largest rank number)
        int lowestRank = 0;
        for (Employee employee : employees) {
            if (employee.getHierarchyRank() > lowestRank) {
                lowestRank = employee.getHierarchyRank();
            }
        }

        for (int rank = 1; rank <= lowestRank; rank++) {
            for (Employee employee : employees) {
                if (employee.getHierarchyRank() == rank) {
                    insert(employee);
                }
            }
        }
    }

    // Counts all nodes recursively (same approach as countNodes in BFSArrayQueue.java)
    public int countNodes() {
        return countNodes(root);
    }

    private int countNodes(EmployeeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

        // Level-order (breadth-first) traversal: visits the tree level by level,
    // from left to right, using a FIFO queue. Nodes are printed grouped by level.
    public void displayLevelOrder() {
        if (root == null) {
            System.out.println("The tree is empty.");
            return;
        }

        NodeQueue queue = new NodeQueue(size + 1);
        queue.enqueue(root);
        int level = 1;

        while (!queue.isEmpty()) {
            // At this point the queue holds exactly the nodes of the current level
            int nodesInLevel = queue.size();
            System.out.println("Level " + level + ":");

            for (int i = 0; i < nodesInLevel; i++) {
                EmployeeNode current = queue.dequeue();
                System.out.println("  " + current);

                if (current.left != null) {
                    queue.enqueue(current.left);
                }
                if (current.right != null) {
                    queue.enqueue(current.right);
                }
            }
            level++;
        }
    }

    // Height of the tree, measured in levels (empty tree = 0, root only = 1).
    public int height() {
        return height(root);
    }

    private int height(EmployeeNode node) {
        // Base case: an empty subtree adds no levels
        if (node == null) {
            return 0;
        }
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        return 1 + Math.max(leftHeight, rightHeight);
    }
    
    public EmployeeNode getRoot() {
        return root;
    }
}