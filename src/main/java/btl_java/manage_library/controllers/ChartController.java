package btl_java.manage_library.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;


public class ChartController implements Initializable {
    @FXML
    private LineChart<String, Number> chart_S;

    @FXML
    private LineChart<String, Number> chart_Sv;

    @FXML
    private ChoiceBox<String> choice_S;

    @FXML
    private ChoiceBox<String> choice_Sv;
    String[] choices={"Tháng","Tuần"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choice_S.getItems().addAll(choices);
        choice_Sv.getItems().addAll(choices);
        choice_Sv.setValue("Tuần");
        choice_S.setValue("Tuần");

        W_chartS();
        W_chart_Sv();
        choice_Sv.setOnAction(this::get_choiceSv);
        choice_S.setOnAction(this::get_choiceS);
    }

//    data chart Lượng sinh viên vào ra thư viện :_______________________________________________________


    //    vào ra theo tuần:
    private void W_chart_Sv(){
        XYChart.Series W_series=new XYChart.Series();
        W_series.getData().add(new XYChart.Data("Tuần 1",500));
        W_series.getData().add(new XYChart.Data("Tuần 2",768));
        W_series.getData().add(new XYChart.Data("Tuần 3",616));
        W_series.getData().add(new XYChart.Data("Tuần 4",277));
        chart_Sv.getData().add(W_series);

    }
    //    vào ra theo tháng:
    private void M_chartSv(){
        XYChart.Series M_series=new XYChart.Series();
        M_series.getData().add(new XYChart.Data("Tháng 1",1343));
        M_series.getData().add(new XYChart.Data("Tháng 2",634));
        M_series.getData().add(new XYChart.Data("Tháng 3",572));
        M_series.getData().add(new XYChart.Data("Tháng 4",1553));

        chart_Sv.getData().add(M_series);
    }

    //    xly lựa choice Tuần/Tháng:
    public void get_choiceSv(ActionEvent event){
        chart_Sv.setData(FXCollections.observableArrayList());

        String x=choice_Sv.getValue();
        if(x.equals("Tuần")){
            W_chart_Sv();
        }
        else{
            M_chartSv();
        }
    }

//    data chart Lượng sách mượn :__________________________________________________________________________-

//    theo tuần:

    private void W_chartS(){
        XYChart.Series W_series=new XYChart.Series();
        W_series.getData().add(new XYChart.Data("Tuần 1",233));
        W_series.getData().add(new XYChart.Data("Tuần 2",768));
        W_series.getData().add(new XYChart.Data("Tuần 3",423));
        W_series.getData().add(new XYChart.Data("Tuần 4",563));
        chart_S.getData().add(W_series);

    }
    //    theo tháng:
    private void M_chartS(){

        XYChart.Series M_series=new XYChart.Series();
        M_series.getData().add(new XYChart.Data("Tháng 1",1343));
        M_series.getData().add(new XYChart.Data("Tháng 2",1268));
        M_series.getData().add(new XYChart.Data("Tháng 3",923));
        M_series.getData().add(new XYChart.Data("Tháng 4",1353));
        chart_S.getData().add(M_series);

    }
    //    xly lựa choice Tuần/Tháng:
    public void get_choiceS(ActionEvent event){
        chart_S.setData(FXCollections.observableArrayList());
        String x=choice_S.getValue();
        if(x.equals("Tuần")){
            W_chartS();
        }
        else{
            M_chartS();
        }

    }

}