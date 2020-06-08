package sklep;


import java.util.*;
import java.util.concurrent.TimeUnit;

public class Manager implements Runnable {

    private final StoreQueue<Client>[] queues;
    private final StoreQueue<Client> store;
    private final ClientGenerator clientGenerator;

    private final int m;
    private final int n;


    private final StoreQueue<Message> messageQueue;
    public final int[] isWorking;
    private final Random rand;

    public Manager(StoreQueue<Client>[] queues, StoreQueue<Client> store, int m, int n, StoreQueue<Message> messageQueue, ClientGenerator clientGenerator) {
        this.queues = queues;
        this.store = store;
        this.m = m;
        this.n = n;
        this.messageQueue = messageQueue;
        this.clientGenerator = clientGenerator;
        isWorking = new int[m];
        rand  = new Random();

        for (int i = 0; i < m; i++) {
            isWorking[i] = 1;
        }

    }


    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(50);

                if (messageQueue.size() > 0)
                    cashierBreak();

                assignClient();

            }
        } catch (InterruptedException e) {
            System.out.println("Przerwano zadanie " + this);
        }
        System.out.println("Zakoñczono zadanie " + this);
    }

    public synchronized String toString() {
        return "Manager ";
    }


     private void assignClient() throws InterruptedException {
        if (workingCashiers() > 0) {
            try {
                boolean flag = true;
                int x = rand.nextInt(m);
                while (flag) {
                    if (isWorking[x] == 1) {
                        queues[x].put(store.take());
                        flag = false;
                    } else {
                        x = rand.nextInt(m);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Przerwano zadanie " + this);
                throw e;
            }
        }
    }

    private void cashierBreak() {
        Message temp = null;
        int[] temp1;
        try {
            temp = messageQueue.take();
        } catch (InterruptedException e) {
            System.out.println("Przerwano zadanie " + this);
        }
        if (temp != null) {
            temp1 = temp.getCode();
            isWorking[temp1[0]] = temp1[1];
            System.out.println("Wiadomosc: " + temp1[0] + temp1[1]);
        }
    }

     public synchronized int workingCashiers() {
        int sum = 0;
         for (int value : isWorking) {
             if (value == 1)
                 sum += 1;
         }
        return sum;
    }

    public synchronized int[] getIsWorking() {
        return isWorking;
    }


}
