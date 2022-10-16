package btl_java.manage_library.models;

import com.mysql.cj.conf.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LibraryModel {
    private final SimpleStringProperty stt = new SimpleStringProperty();
    private final SimpleStringProperty codeStudent = new SimpleStringProperty();
    private final SimpleStringProperty nameStudent = new SimpleStringProperty();
    private final SimpleStringProperty classStudent = new SimpleStringProperty();
    private final SimpleStringProperty phoneNumber = new SimpleStringProperty();
    private final SimpleStringProperty timeIn = new SimpleStringProperty();
    private final SimpleStringProperty timeOut = new SimpleStringProperty();

    public LibraryModel(String stt, String codeStudent, String nameStudent, String phoneNumber, String classStudent, String timeIn, String timeOut) {
        this.stt.set(stt);
        this.codeStudent.set(codeStudent);
        this.nameStudent.set(nameStudent);
        this.classStudent.set(classStudent);
        this.phoneNumber.set(phoneNumber);
        this.timeIn.set(timeIn);
        this.timeOut.set(timeOut);
    }

    public SimpleStringProperty getStt() {
        return stt;
    }

    public SimpleStringProperty getCodeStudent() {
        return codeStudent;
    }

    public SimpleStringProperty getNameStudent() {
        return nameStudent;
    }

    public SimpleStringProperty getClassStudent() {
        return classStudent;
    }

    public SimpleStringProperty getPhoneNumber() {
        return phoneNumber;
    }

    public SimpleStringProperty getTimeIn() {
        return timeIn;
    }

    public SimpleStringProperty getTimeOut() {
        return timeOut;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn.set(timeIn);
    }

    public void setTimeOut(String timeOut) {
        this.timeOut.set(timeOut);
    }

    @Override
    public String toString() {
        return "LibraryModel{" +
                "codeStudent=" + codeStudent +
                ", nameStudent=" + nameStudent +
                ", timeIn=" + timeIn +
                ", timeOut=" + timeOut +
                ", phoneNumber=" + phoneNumber +
                ", classStudent=" + classStudent +
                '}';
    }
}
