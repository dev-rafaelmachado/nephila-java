package share.Queue;

public class Queue<T> {
    private QueueNode<T> front;
    private QueueNode<T> rear;

    public Queue() {
        this.front = null;
        this.rear = null;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public void enqueue(T data) {
        QueueNode<T> newNode = new QueueNode<T>(data);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.setNext(newNode);
            rear = newNode;
        }
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        T data = front.getData();
        front = front.getNext();
        if (front == null) {
            rear = null;
        }
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return front.getData();
    }

    public boolean hasNode(T data) {
        QueueNode<T> current = front;
        while (current != null) {
            if (current.getData().equals(data)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        QueueNode<T> current = front;
        while (current != null) {
            sb.append(current.getData().toString());
            sb.append(" ");
            current = current.getNext();
        }
        return sb.toString();
    }
}
