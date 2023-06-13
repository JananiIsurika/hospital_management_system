package lk.ijse.hospital.controller;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hospital.model.PaymentModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;

public class HomePageFormController {
    public AnchorPane Dashboard;
    public BarChart chart;

    public void initialize(){
        try {
            setChart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setChart() throws SQLException {
        HashMap<Integer, Double> monthlyIncome = PaymentModel.getMonthlyIncome();
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName(String.valueOf(LocalDate.now().getYear()));

        for(int i = 0; i <12 ; i++){
            String ar = Month.of(i + 1).getDisplayName(TextStyle.FULL, new Locale("en"));
            Double aDouble = monthlyIncome.get(i+1);
            if(aDouble!=null){
                dataSeries1.getData().add(new XYChart.Data( ar, aDouble));
            }else {
                dataSeries1.getData().add(new XYChart.Data( ar, 0.0));
            }
        }

        /*dataSeries1.getData().add(new XYChart.Data( "1", 567));
        dataSeries1.getData().add(new XYChart.Data( "2", 612));
        dataSeries1.getData().add(new XYChart.Data("3", 800));
        dataSeries1.getData().add(new XYChart.Data("4", 780));
        dataSeries1.getData().add(new XYChart.Data("5", 810));
        dataSeries1.getData().add(new XYChart.Data("6", 850));*/

        chart.getData().add(dataSeries1);
    }
}
