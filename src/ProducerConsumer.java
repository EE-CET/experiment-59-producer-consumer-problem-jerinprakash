class SharedResource {

    int item;
    boolean available = false;

    // Producer method
    public synchronized void put(int item) {

        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        this.item = item;
        available = true;

        System.out.println("Produced: " + item);

        notify();
    }

    // Consumer method
    public synchronized void get() {

        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Consumed: " + item);

        available = false;

        notify();
    }
}

class Producer extends Thread {

    SharedResource resource;

    Producer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.put(i);
        }
    }
}

class Consumer extends Thread {

    SharedResource resource;

    Consumer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.get();
        }
    }
}

public class ProducerConsumer {

    public static void main(String[] args) {

        SharedResource resource = new SharedResource();

        Producer p = new Producer(resource);
        Consumer c = new Consumer(resource);

        p.start();
        c.start();
    }
}
