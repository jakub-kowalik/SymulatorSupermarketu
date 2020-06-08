package sklep;

public class Client {
    private final int serviceTime;
    public Client(int tm) { serviceTime = tm; }
    public int getServiceTime() { return serviceTime; }
    public String toString() {
        return "[" + serviceTime + "]";
    }
}
