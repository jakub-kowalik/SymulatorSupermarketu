package sklep;

import java.util.concurrent.TimeUnit;

public class Supervisor implements Runnable {
    private final GraphicInterfaceController controller;
    private final Manager manager;
    private final ClientGenerator clientGenerator;
    private final StoreQueue<Client>[] queues;
    private final CashRegister[] cashRegisters;

    public Supervisor(GraphicInterfaceController controller, Manager manager, ClientGenerator clientGenerator, StoreQueue<Client>[] queues, CashRegister[] cashRegisters) {
        this.controller = controller;
        this.manager = manager;
        this.clientGenerator = clientGenerator;
        this.queues = queues;
        this.cashRegisters = cashRegisters;
    }

    public void run()  {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.SECONDS.sleep(1);

                controller.updateAllClients(new int[] {clientGenerator.overallStatus, clientGenerator.storeStatus, clientGenerator.queueStatus, manager.workingCashiers()});
                controller.updateGraph(queues);
                controller.updateTable(queues, manager.getIsWorking(), cashRegisters);
            }
        } catch (InterruptedException e) {

        }

    }
}

