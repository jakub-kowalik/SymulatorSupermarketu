package sklep;

public class Cell {
    private final String cashRegister;
    private final String cashier;
    private final String clients;
    private final String status;

    public Cell(String cashRegister, String cashier, String clients, String status) {
        this.cashRegister = cashRegister;
        this.cashier = cashier;
        this.clients = clients;
        this.status = status;
    }

    public String getCashRegister() {
        return cashRegister;
    }

    public String getCashier() { return cashier; }


    public String getStatus() {
        return status;
    }


    public String getClients() {
        return clients;
    }
}
