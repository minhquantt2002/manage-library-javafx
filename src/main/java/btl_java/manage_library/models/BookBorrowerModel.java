package btl_java.manage_library.models;

import javafx.beans.property.SimpleStringProperty;

public class BookBorrowerModel {
    private final SimpleStringProperty stt = new SimpleStringProperty();
    private final SimpleStringProperty codeStudent = new SimpleStringProperty();
    private final SimpleStringProperty nameStudent = new SimpleStringProperty();
    private final SimpleStringProperty classStudent = new SimpleStringProperty();
    private final SimpleStringProperty phoneNumber = new SimpleStringProperty();
    private final SimpleStringProperty borrowDate = new SimpleStringProperty();
    private final SimpleStringProperty returnDate = new SimpleStringProperty();

    public BookBorrowerModel(String codeStudent, String nameStudent, String classStudent, String phoneNumber, String borrowDate, String returnDate) {
        this.codeStudent.set(codeStudent);
        this.nameStudent.set(nameStudent);
        this.classStudent.set(classStudent);
        this.phoneNumber.set(phoneNumber);
        this.borrowDate.set(borrowDate);
        this.returnDate.set(returnDate);
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

    public SimpleStringProperty getBorrowDate() {
        return borrowDate;
    }

    public SimpleStringProperty getReturnDate() {
        return returnDate;
    }


    public void setReturnDate(String returnDate) {
        this.returnDate.set(returnDate);
    }

    public void setStt(String stt) {
        this.stt.set(stt);
    }

    @Override
    public String toString() {
        return "BookBorrowerModel{" + "stt=" + stt + ", codeStudent=" + codeStudent +
                ", nameStudent=" + nameStudent + ", classStudent=" + classStudent + ", phoneNumber=" + phoneNumber +
                ", borrowDate=" + borrowDate + ", returnDate=" + returnDate + '}';
    }
}
