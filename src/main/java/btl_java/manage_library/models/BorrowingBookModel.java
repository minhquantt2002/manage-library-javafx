package btl_java.manage_library.models;

import javafx.beans.property.SimpleStringProperty;

public class BorrowingBookModel {
    private final SimpleStringProperty stt = new SimpleStringProperty();
    private final SimpleStringProperty codeStudent = new SimpleStringProperty();
    private final SimpleStringProperty nameStudent = new SimpleStringProperty();
    private final SimpleStringProperty nameBook = new SimpleStringProperty();
    private final SimpleStringProperty phoneNumber = new SimpleStringProperty();
    private final SimpleStringProperty borrowDate = new SimpleStringProperty();
    private final SimpleStringProperty returnDate = new SimpleStringProperty();

    public BorrowingBookModel(String stt, String codeStudent, String nameStudent, String phoneNumber, String nameBook, String borrowDate, String returnDate) {
        this.stt.set(stt);
        this.codeStudent.set(codeStudent);
        this.nameStudent.set(nameStudent);
        this.phoneNumber.set(phoneNumber);
        this.nameBook.set(nameBook);
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

    public SimpleStringProperty getNameBook() {
        return nameBook;
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

    public void setBorrowDate(String borrowDate) {
        this.borrowDate.set(borrowDate);
    }

    public void setReturnDate(String returnDate) {
        this.returnDate.set(returnDate);
    }

    @Override
    public String toString() {
        return "BorrowingBookModel{" + "stt=" + stt + ", codeStudent=" + codeStudent +
                ", nameStudent=" + nameStudent + ", nameBook=" + nameBook + ", phoneNumber=" + phoneNumber +
                ", borrowDate=" + borrowDate + ", returnDate=" + returnDate + '}';
    }
}
