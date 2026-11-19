
package supermarketmanagementsystem;

/**
 * Circular array-based FIFO queue of tree nodes, used for level-order insertion
 * and traversal.
 *
 * @author diogodias
 */
// Circular array-based FIFO queue of tree nodes.
// Based on the ArrayQueue from BFSArrayQueue.java shown in class.
public class NodeQueue {

    private EmployeeNode[] items;
    private int front;
    private int rear;
    private int capacity;
    private int count; // number of nodes currently in the queue

    public NodeQueue(int capacity) {
        this.capacity = capacity;
        items = new EmployeeNode[capacity];
        front = -1; // -1 means the queue is empty
        rear = -1;
        count = 0;
    }

    public boolean isEmpty() {
        return front == -1;
    }

    public boolean isFull() {
        return (rear + 1) % capacity == front;
    }

    public int size() {
        return count;
    }

    // Adds a node at the rear. Returns false if the queue is full.
    public boolean enqueue(EmployeeNode node) {
        if (isFull()) {
            System.out.println("Queue is full!");
            return false;
        }
        if (isEmpty()) {
            front = 0;
            rear = 0;
        } else {
            rear = (rear + 1) % capacity;
        }
        items[rear] = node;
        count++;
        return true;
    }

    // Removes and returns the node at the front, or null if the queue is empty.
    public EmployeeNode dequeue() {
        if (isEmpty()) {
            return null;
        }

        EmployeeNode node = items[front];

        if (front == rear) { // last element removed
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % capacity;
        }
        count--;
        return node;
    }
}