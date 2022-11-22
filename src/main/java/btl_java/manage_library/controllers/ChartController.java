package btl_java.manage_library.controllers;

import btl_java.manage_library.models.BorrowDetailModel;
import btl_java.manage_library.models.BorrowerModel;
import btl_java.manage_library.utils.ConnectionUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

import btl_java.manage_library.models.LibraryModel;


public class ChartController implements Initializable {
    @FXML
    private Button All;
    @FXML
    private ChoiceBox<String> nam;

    @FXML
    private ChoiceBox<String> thang;
    @FXML
    private Button b_refresh;
    @FXML
    private LineChart<String, Number> chart_S;

    @FXML
    private LineChart<String, Number> chart_Sv;

    Connection connection = new ConnectionUtils().connectDB();
    LinkedHashMap<String, Integer> S_data = new LinkedHashMap<>();
   LinkedHashMap<String, Integer> B_data = new LinkedHashMap<>();

    public void S_chart_getData(String stmt) {

        ArrayList<String> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            int i = 1;
            resultSet = connection.createStatement().executeQuery(stmt);
            while (resultSet.next()) {
                LibraryModel row = new LibraryModel(
                        Integer.toString(i),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
                i++;
                list.add(row.getTimeIn().getValue().substring(9));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (String s : list) {
            if (!S_data.containsKey(s)) {
                S_data.put(s, 1);
            } else {
                int x = S_data.get(s);
                x += 1;
                S_data.put(s, x);
            }
        }
    }

    private void B_chart_getData(String stmt) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ResultSet resultSet, resultSet2;
        try {
            resultSet = connection.createStatement().executeQuery(stmt);
            while (resultSet.next()) {
                BorrowerModel row = new BorrowerModel(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                list.add(row.getCodeStudent().getValue());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        for (String l : list) {
            try {
                resultSet2 = connection.createStatement().executeQuery("SELECT * FROM borrowed_book_detail where borrower_id='"+l+"'");
                while (resultSet2.next()) {
                    BorrowDetailModel row = new BorrowDetailModel(
                            resultSet2.getString(1),
                            resultSet2.getString(2),
                            resultSet2.getString(3),
                            resultSet2.getString(4),
                            resultSet2.getString(5));
                    String o = row.getBorrowDate().getValue().substring(9);

//                    System.out.println(o);
                    list2.add(o);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
        for (String q : list2) {
            if (!B_data.containsKey(q)) {
                B_data.put(q, 1);
            } else {
                int x =B_data.get(q);
                x += 1;
                B_data.put(q, x);
            }
        }

    }
    String[] C_thang={"Tháng 01","Tháng 02","Tháng 03","Tháng 04","Tháng 05","Tháng 06","Tháng 07","Tháng 08","Tháng 09","Tháng 10","Tháng 11","Tháng 12"};
    String[] C_nam={"năm 2020","năm 2021","năm 2022"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        thang.getItems().addAll(C_thang);
        nam.getItems().addAll(C_nam);
        S_chart_getData("SELECT * FROM library_manager");
        B_chart_getData("SELECT * FROM borrower");
        chart_Sv();
        chartB();
        b_refresh.setOnAction(this::refresh);
        All.setOnAction(this::All);
    }

//    data chart Lượng sinh viên vào ra thư viện :_______________________________________________________


    //    Luong sv vao ra:
    private void chart_Sv() {
        XYChart.Series series = new XYChart.Series();

        for (Map.Entry<String, Integer> x : S_data.entrySet()) {
            String s = x.getKey();
            int n = x.getValue();
            series.getData().add(new XYChart.Data(s, n));
//            System.out.println(s + " " + n);

        }
        chart_Sv.getData().add(series);

    }
    private void chart_Sv2(String s){
        XYChart.Series series=new XYChart.Series();
        for(Map.Entry<String,Integer> x:S_data.entrySet()){
            String l=x.getKey().substring(3);
            int n=x.getValue();
            if(l.equals(s)){
                series.getData().add(new XYChart.Data(x.getKey(),n));
            }
        }
        chart_Sv.getData().add(series);
    }


//    Lượng sách mượn :__________________________________________________________________________-

    private void chartB() {
        XYChart.Series series = new XYChart.Series();

        for (Map.Entry<String, Integer> x : B_data.entrySet()) {
            String s = x.getKey();
            int n = x.getValue();
            series.getData().add(new XYChart.Data(s, n));
//            System.out.println(s + " " + n);

        }

        chart_S.getData().add(series);

    }

    private void chartB2(String s){
        XYChart.Series series=new XYChart.Series();
        for(Map.Entry<String,Integer> x:B_data.entrySet()){
            String l=x.getKey().substring(3);
            int n=x.getValue();
            if(l.equals(s)){
                series.getData().add(new XYChart.Data(x.getKey(),n));
            }
        }
        chart_S.getData().add(series);
    }




//    refresh
    private void refresh(ActionEvent e){
        chart_S.setData(FXCollections.observableArrayList());
        chart_Sv.setData(FXCollections.observableArrayList());
        S_data.clear();
        B_data.clear();
        S_chart_getData("SELECT * FROM library_manager");
        B_chart_getData("SELECT * FROM borrower");

        String n=nam.getValue();
        String t=thang.getValue();
        String kt=t+n;

        if(kt.equals("nullnull")){
            chartB();
            chart_Sv();
        }  else {
            String loc=t.substring(6)+"/"+n.substring(4);
            chart_Sv2(loc);
            chartB2(loc);
        }

    }

    private void All(ActionEvent e){
        chart_S.setData(FXCollections.observableArrayList());
        chart_Sv.setData(FXCollections.observableArrayList());
        S_data.clear();
        B_data.clear();
        S_chart_getData("SELECT * FROM library_manager");
        B_chart_getData("SELECT * FROM borrower");
        chartB();
        chart_Sv();
        thang.setValue(null);
        nam.setValue(null);

    }

}