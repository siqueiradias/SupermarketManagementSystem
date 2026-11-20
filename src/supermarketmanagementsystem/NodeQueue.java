package supermarketmanagementsystem;

/**
 * Circular array-based FIFO queue of tree nodes, used for level-order insertion
 * and traversal. Same circular design used in CA-1 (FoodCircularQueue).
 *
 * @author diogodias
 */
public class NodeQueue {

    private final EmployeeNode[] items;
    private final int capacity;
    private int front; // index of the next node to remove
    private int rear;  // index of the last node added
    private int count; // number of nodes currently in the queue

    // Creates an empty queue that can hold up to 'capacity' nodes.
    public NodeQueue(int capacity) {
        this.capacity = capacity;
        items = new EmployeeNode[capacity];
        front = 0;
        rear = -1;
        count = 0;
    }

    // The queue is empty when it holds no nodes.
    public boolean isEmpty() {
        return count == 0;
    }

    // The queue is full when the number of nodes equals the size of the array.
    // The counter makes this clear and reliable: comparing only front and rear
    // could not tell a full queue from an empty one, because in both cases
    // rear sits just before front in the circle.
    public boolean isFull() {
        return count == capacity;
    }

    // Returns the number of nodes currently in the queue.
    public int size() {
        return count;
    }

    // Adds a node at the rear. Returns false if the queue is full.
    public boolean enqueue(EmployeeNode node) {
        if (isFull()) {
            System.out.println("Queue is full!");
            return false;
        }
        // Move rear one position forward; % capacity wraps it back to 0
        // after the last index (e.g. capacity 8: (7 + 1) % 8 = 0)
        rear = (rear + 1) % capacity;
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
        items[front] = null;
        // Move front one position forward, wrapping around in the same way as rear
        front = (front + 1) % capacity;
        count--;
        return node;
    }
}
