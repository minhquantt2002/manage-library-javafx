package btl_java.manage_library.models;

import javafx.beans.property.SimpleStringProperty;

public class BorrowDetailModel {
    private final SimpleStringProperty stt = new SimpleStringProperty();
    private final SimpleStringProperty codeBook = new SimpleStringProperty();
    private final SimpleStringProperty nameBook = new SimpleStringProperty();
    private final SimpleStringProperty borrowDate = new SimpleStringProperty();
    private final SimpleStringProperty returnDate = new SimpleStringProperty();

    public BorrowDetailModel(String stt, String codeBook, String nameBook, String borrowDate, String returnDate) {
        this.stt.set(stt);
        this.codeBook.set(codeBook);
        this.nameBook.set(nameBook);
        this.borrowDate.set(borrowDate);
        this.returnDate.set(returnDate);
    }

    public SimpleStringProperty getStt() {
        return stt;
    }

    public SimpleStringProperty getCodeBook() {
        return codeBook;
    }

    public SimpleStringProperty getNameBook() {
        return nameBook;
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
        return "BorrowDetailModel{" +
                "stt=" + stt +
                ", codeBook=" + codeBook +
                ", nameBook=" + nameBook +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
