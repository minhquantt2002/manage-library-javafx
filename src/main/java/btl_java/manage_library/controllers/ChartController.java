package btl_java.manage_library.controllers;

import btl_java.manage_library.models.BorrowDetailModel;
import btl_java.manage_library.utils.AlertWarningUtils;
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
    private Button filterall;
    @FXML
    private ChoiceBox<String> year;

    @FXML
    private ChoiceBox<String> month;
    @FXML
    private Button filter;
    @FXML
    private LineChart<String, Number> chartBookLine;

    @FXML
    private LineChart<String, Number> chartStudentline;

    Connection connection = new ConnectionUtils().connectDB();
    LinkedHashMap<String, Integer> StudentData = new LinkedHashMap<>();
    LinkedHashMap<String, Integer> BookData = new LinkedHashMap<>();

    public void StudentgetData(String stmt) {

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
            if (!StudentData.containsKey(s)) {
                StudentData.put(s, 1);
            } else {
                int x = StudentData.get(s);
                x += 1;
                StudentData.put(s, x);
            }
        }
    }

    private void BookgetData(String stmt) {
        ArrayList<String> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = connection.createStatement().executeQuery(stmt);
            while (resultSet.next()) {
                BorrowDetailModel row = new BorrowDetailModel(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                list.add(row.getBorrowDate().getValue().substring(9));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        for (String q : list) {
            if (!BookData.containsKey(q)) {
                BookData.put(q, 1);
            } else {
                int x = BookData.get(q);
                x += 1;
                BookData.put(q, x);
            }
        }

    }

    String[] ChooseMonth = {"Tháng 01", "Tháng 02", "Tháng 03", "Tháng 04", "Tháng 05", "Tháng 06", "Tháng 07", "Tháng 08", "Tháng 09", "Tháng 10", "Tháng 11", "Tháng 12"};
    String[] ChooseYear = {"năm 2020", "năm 2021", "năm 2022"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        month.getItems().addAll(ChooseMonth);
        year.getItems().addAll(ChooseYear);
        StudentgetData("SELECT * FROM library_manager");
        BookgetData("SELECT * FROM borrowed_book_detail");
        ChartStudent();
        ChartBook();
        filter.setOnAction(this::Refinement);
        filterall.setOnAction(this::RefinementAll);
    }

//    data chart Lượng sinh viên vào ra thư viện :_______________________________________________________


    //    Luong sv vao ra:
    private void ChartStudent() {
        XYChart.Series series = new XYChart.Series();

        for (Map.Entry<String, Integer> x : StudentData.entrySet()) {
            String s = x.getKey();
            int n = x.getValue();
            series.getData().add(new XYChart.Data(s, n));
//            System.out.println(s + " " + n);
        }
        chartStudentline.getData().add(series);

    }

    private void ChartStudentrefinement(String s) {
        XYChart.Series series = new XYChart.Series();
        for (Map.Entry<String, Integer> x : StudentData.entrySet()) {
            String l = x.getKey().substring(3);
            int n = x.getValue();
            if (l.equals(s)) {
                series.getData().add(new XYChart.Data(x.getKey(), n));
            }
        }
        chartStudentline.getData().add(series);
    }


//    Lượng sách mượn :__________________________________________________________________________-

    private void ChartBook() {
        XYChart.Series series = new XYChart.Series();

        for (Map.Entry<String, Integer> x : BookData.entrySet()) {
            String s = x.getKey();
            int n = x.getValue();
            series.getData().add(new XYChart.Data(s, n));
            System.out.println(s + " " + n);

        }

        chartBookLine.getData().add(series);

    }

    private void ChartBookrefinement(String s) {
        XYChart.Series series = new XYChart.Series();
        for (Map.Entry<String, Integer> x : BookData.entrySet()) {
            String l = x.getKey().substring(3);
            int n = x.getValue();
            if (l.equals(s)) {
                series.getData().add(new XYChart.Data(x.getKey(), n));
            }
        }
        chartBookLine.getData().add(series);
    }


    //    refresh
    private void Refinement(ActionEvent e) {
        chartBookLine.setData(FXCollections.observableArrayList());
        chartStudentline.setData(FXCollections.observableArrayList());
        StudentData.clear();
        BookData.clear();
        StudentgetData("SELECT * FROM library_manager");
        BookgetData("SELECT * FROM borrower");

        String n = year.getValue();
        String t = month.getValue();
        String kt = t + n;
//        System.out.println(t+" "+n);
        if (n == null && t == null) {
            ChartBook();
            ChartStudent();
        } else if (n == null || t == null) {
            new AlertWarningUtils().showAlertWarning("Chưa đủ bộ lọc!");
        } else {
            String loc = t.substring(6) + "/" + n.substring(4);
            ChartStudentrefinement(loc);
            ChartBookrefinement(loc);
        }

    }

    private void RefinementAll(ActionEvent e) {
        chartBookLine.setData(FXCollections.observableArrayList());
        chartStudentline.setData(FXCollections.observableArrayList());
        StudentData.clear();
        BookData.clear();
        StudentgetData("SELECT * FROM library_manager");
        BookgetData("SELECT * FROM borrower");
        ChartBook();
        ChartStudent();
        month.setValue(null);
        year.setValue(null);
    }

}