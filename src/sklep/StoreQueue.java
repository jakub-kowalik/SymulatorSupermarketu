package sklep;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class StoreQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final int id;
    public volatile int size;

    public StoreQueue(int capacity, int id) {
        this.capacity = capacity;
        this.id = id;
        this.size = 0;
    }

    public void put(T element) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                notFull.await();
            }
            queue.add(element);
            size++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            T item = queue.remove();
            size--;
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }

    public T poll() throws InterruptedException {
        lock.lock();
        try {
            if (queue.isEmpty()) {
                return null;
            }
            T item = queue.remove();
            size--;
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }

    public synchronized int size() {
            return queue.size();
    }

    public synchronized String getInfo() {
        return "Kasa " + id;
    }

     public synchronized String toString() {
        if(this.size() == 0)
            return "Kolejka " + id;
        StringBuilder result = new StringBuilder();

        result.append("Kolejka: ").append(id).append(" Stan kolejki: ").append(this.size()).append(" Stan kolejki: ");

        for(T klient : this.queue)
            result.append(klient);
        return result.toString();
    }
}