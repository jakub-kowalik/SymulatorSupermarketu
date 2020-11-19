package sklep;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GraphicInterfaceController {
    @FXML TextField wartoscM;
    @FXML TextField wartoscN;
    @FXML TextField wartoscPraca;
    @FXML TextField wartoscPrzerwa;
    @FXML Text allClients;
    @FXML Text hallClients;
    @FXML Text checkoutClients;
    @FXML Text textOtwarte;

    @FXML NumberAxis yAxis;
    @FXML CategoryAxis xAxis;

    @FXML BarChart<String, Number> graphKasy;

    XYChart.Series<String, Number> series1;

    @FXML TableView<Cell> tableView;
    @FXML TableColumn<String, Cell> tableKasy;
    @FXML TableColumn<String, Cell> tableKasjer;
    @FXML TableColumn<String, Cell> tableStatus;
    @FXML TableColumn<String, Cell> tableKlienci;

    int m;
    int n;
    int praca;
    int przerwa;
    boolean isRunning = false;
    ExecutorService exec;

    @FXML protected void startButton(ActionEvent event) {

       if ( checkN() && checkM() && checkPraca() && checkPrzerwa() && !isRunning) {
           exec = Executors.newCachedThreadPool();
           StoreQueue<Message> messageQueue = new StoreQueue<>(m, -2);

           StoreQueue<Cashier> pracownicy = new StoreQueue<>(m*2, -3);

           for (int i = 0; i < 2*m; i++) {
               try {
                   pracownicy.put(new Cashier(NameGenerator.randomName(), praca, przerwa));
               } catch (Exception e) {

               }
           }

           series1 = new XYChart.Series<> ();
           StoreQueue<Client>[] storeQueue = new StoreQueue[m];

           for (int i = 0; i < m; i++) {
               storeQueue[i] = new StoreQueue<>(n, i);
           }

           StoreQueue<Client> nasklepie = new StoreQueue<>(n, -1);

           ClientGenerator generatorSklep = new ClientGenerator(storeQueue, nasklepie, n, m);

           CashRegister[] cashRegister = new CashRegister[m];

           for (int i = 0; i < m; i++) {
               cashRegister[i] = new CashRegister(storeQueue[i], i, messageQueue, pracownicy);
           }

           Manager kier = new Manager(storeQueue, nasklepie, m, n, messageQueue, generatorSklep);

           createGraph(storeQueue, m);

           Supervisor supervisor = new Supervisor(this, kier, generatorSklep, storeQueue, cashRegister);

           exec.execute(kier);
           exec.execute(generatorSklep);
           for (int i = 0; i < m; i++)
               exec.execute(cashRegister[i]);
           exec.execute(supervisor);
           isRunning = true;
       }
    }

    @FXML protected void stopButton(ActionEvent event) {
        if (isRunning) {
            exec.shutdownNow();
            graphKasy.getData().clear();
            graphKasy.layout();
            tableView.getItems().clear();
            updateAllClients(new int[] {0,0,0,0});
            isRunning = false;
        }
    }

    private boolean checkM() {
        try {
            m = Integer.parseInt(wartoscM.getText());
            return true;
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
            wartoscM.setText("Bład danych");
            return false;
        }
    }

    private boolean checkN() {
        try {
            n = Integer.parseInt(wartoscN.getText());
            return true;
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
            wartoscN.setText("Bład danych");
            return false;
        }
    }

    private boolean checkPraca() {
        try {
            praca = (Integer.parseInt(wartoscPraca.getText()) * 1000) / 2;
            return true;
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
            wartoscPraca.setText("Bład danych");
            return false;
        }
    }

    private boolean checkPrzerwa() {
        try {
            przerwa = Integer.parseInt(wartoscPrzerwa.getText()) * 1000;
            return true;
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
            wartoscPrzerwa.setText("Bład danych");
            return false;
        }
    }

    public void updateAllClients(int[] x) {
        allClients.setText( String.valueOf(x[0]));
        hallClients.setText( String.valueOf(x[1]));
        checkoutClients.setText( String.valueOf(x[2]));
        textOtwarte.setText(String.valueOf(x[3]));
    }

    @FXML
    public void shutdown() {
        if (isRunning)
            exec.shutdownNow();
    }

    public void createGraph(StoreQueue<Client>[] storeQueue, int m) {
        for (int i = 0; i < m; i++) {
            series1.getData().add(new XYChart.Data<>(storeQueue[i].toString(), storeQueue[i].size));
        }

        graphKasy.getData().add(series1);
    }

    public void updateGraph(StoreQueue<Client>[] kolejki) {
        int i = 0;
            for (XYChart.Data<String, Number> data : series1.getData()) {
                data.setYValue(kolejki[i].size());
                i++;
            }
    }

    public void updateTable(StoreQueue<Client>[] storeQueue, int[] x, CashRegister[] cashRegister) {
        try {
            tableView.getItems().clear();
            tableKasy.setCellValueFactory(new PropertyValueFactory<>("cashRegister"));
            tableKasjer.setCellValueFactory(new PropertyValueFactory<>("cashier"));
            tableKlienci.setCellValueFactory(new PropertyValueFactory<>("clients"));
            tableStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            for (int i = 0; i < m; i++) {
                tableView.getItems().add(new Cell(storeQueue[i].getInfo(), cashRegister[i].cashierName, String.valueOf(storeQueue[i].size), calculateStatus(x[i])));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private String calculateStatus(int x) {
        if (x == 1)
            return "Otwarta";
        if (x == 2)
            return "Przerwa";
        if (x == 0)
            return "Zamknieta";
        else
            return "Zmiana kasjera";
    }
}