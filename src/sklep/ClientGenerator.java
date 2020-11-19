package sklep;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class ClientGenerator implements Runnable {

    private final StoreQueue<Client> store;
    private final StoreQueue<Client>[] cashRegisterQueues;
    public volatile int overallStatus, storeStatus, queueStatus;
    private final int n;
    private final int m;
    private final Random rand = new Random();

    public ClientGenerator(StoreQueue<Client>[] cashRegisterQueues, StoreQueue<Client> cq, int n, int m) {
        this.cashRegisterQueues = cashRegisterQueues;
        store = cq;
        this.n = n;
        this.m = m;
        this.overallStatus = 0;
    }

    public void run() {
        try {
            while(!Thread.interrupted()) {
                int y = 0;

                TimeUnit.MILLISECONDS.sleep(10);

                y += readStore();
                queueStatus = readQueues();
                storeStatus = y;

                y += queueStatus;
                overallStatus = y;

                if(y < n)
                    store.put(new Client(rand.nextInt(5000)));

                }
        } catch(InterruptedException e) {

        }
    }

    private int readQueues() {
        int temp = 0;
        for (int i = 0; i < m; i++) {
            temp += cashRegisterQueues[i].size;
        }
        return temp;
    }

    private int readStore() {
        int temp;
        temp = store.size;
        return temp;
    }
}