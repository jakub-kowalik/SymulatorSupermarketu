package sklep;

public class Cashier {
    private final String name;
    private final int workTime;
    private final int breakTime;

    public Cashier(String name, int workTime, int breakTime) {
        this.name = name;
        this.workTime = workTime;
        this.breakTime = breakTime;
    }

    public String getName() {
        return name;
    }

    public int getWorkTime() {
        return workTime;
    }

    public int getBreakTime() {
        return breakTime;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "name='" + name + '\'' +
                '}';
    }
}
