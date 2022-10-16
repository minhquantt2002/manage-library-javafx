package btl_java.manage_library.models;

import com.mysql.cj.conf.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

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

    public LibraryModel(int stt, String codeStudent, String nameStudent, String phoneNumber, String classStudent) {
        this.stt.set(Integer.toString(stt));
        this.codeStudent.set(codeStudent);
        this.nameStudent.set(nameStudent);
        this.timeIn.set(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
        this.phoneNumber.set(phoneNumber);
        this.classStudent.set(classStudent);
        this.timeOut.set("");
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

    public void setTimeOut() {

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
