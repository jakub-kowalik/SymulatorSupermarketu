package sklep;

// import projekt.CustomerLine;


import java.util.concurrent.TimeUnit;

public class CashRegister implements Runnable {

    int id;
    private int timeWorking;
    private int breakTime;
    private int workTime;
    public volatile String cashierName;

    private boolean changeCashier;

    private final StoreQueue<Client> clients;
    private final StoreQueue<Message> messageQueue;
    private final StoreQueue<Cashier> workers;

    private Cashier currentWorker;

    public CashRegister(StoreQueue<Client> cq, int id, StoreQueue<Message> messageQueue, StoreQueue<Cashier> workers) {
        this.clients = cq;
        this.id = id;
        this.messageQueue = messageQueue;
        this.workers = workers;
        try {
            currentWorker = workers.take();
        } catch (Exception e) {
            System.out.println("Przerwano zadanie " + this);
        }

        timeWorking = 0;

        cashierName = currentWorker.getName();
        workTime = currentWorker.getWorkTime();
        breakTime = currentWorker.getBreakTime();
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                if (timeWorking < workTime) {
                    Client customer = clients.take();
                    timeWorking += customer.getServiceTime();
                    TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                } else {
                    int[] wiadomosc = {id, 0};
                    messageQueue.put(new Message(wiadomosc));
                    while (clients.size() != 0) {
                        Client customer = clients.take();
                        timeWorking += customer.getServiceTime();
                        TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                    }
                    przerwa();
                }
            }
        } catch (InterruptedException e) {

        }
    }

    public String toString() {
        return "Kasa nr " + id;
    }

    private void przerwa() throws InterruptedException {
        try {
            if (!changeCashier) {
                messageQueue.put(new Message(new int[]{id, 2}));
                timeWorking = 0;
                TimeUnit.MILLISECONDS.sleep(breakTime);
                messageQueue.put(new Message(new int[]{id, 1}));
                changeCashier = true;
            } else {
                messageQueue.put(new Message(new int[]{id, 4}));
                timeWorking = 0;
                workers.put(currentWorker);
                cashierName = "[Zmiana]";
                currentWorker = workers.take();
                TimeUnit.SECONDS.sleep(5);
                cashierName = currentWorker.getName();
                messageQueue.put(new Message(new int[]{id, 1}));
                changeCashier = false;
            }
        } catch (InterruptedException e) {
            throw e;
        }
    }

}
