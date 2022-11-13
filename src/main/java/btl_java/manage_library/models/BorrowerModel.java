package btl_java.manage_library.models;

import javafx.beans.property.SimpleStringProperty;

public class BorrowerModel {
    private final SimpleStringProperty stt = new SimpleStringProperty();
    private final SimpleStringProperty codeStudent = new SimpleStringProperty();
    private final SimpleStringProperty nameStudent = new SimpleStringProperty();
    private final SimpleStringProperty classStudent = new SimpleStringProperty();
    private final SimpleStringProperty phoneNumber = new SimpleStringProperty();


    public BorrowerModel(String codeStudent, String nameStudent, String classStudent, String phoneNumber) {
        this.codeStudent.set(codeStudent);
        this.nameStudent.set(nameStudent);
        this.classStudent.set(classStudent);
        this.phoneNumber.set(phoneNumber);
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


    public void setStt(String stt) {
        this.stt.set(stt);
    }

    @Override
    public String toString() {
        return "BorrowerModel{" +
                "stt=" + stt +
                ", codeStudent=" + codeStudent +
                ", nameStudent=" + nameStudent +
                ", classStudent=" + classStudent +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
