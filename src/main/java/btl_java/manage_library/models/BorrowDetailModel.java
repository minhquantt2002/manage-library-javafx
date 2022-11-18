package btl_java.manage_library.models;

import javafx.beans.property.SimpleStringProperty;

public class BorrowDetailModel {
    private final SimpleStringProperty stt = new SimpleStringProperty();
    private final SimpleStringProperty codeBook = new SimpleStringProperty();
    private final SimpleStringProperty nameBook = new SimpleStringProperty();
    private final SimpleStringProperty borrowDate = new SimpleStringProperty();
    private final SimpleStringProperty returnDate = new SimpleStringProperty();
    private final SimpleStringProperty id = new SimpleStringProperty();

    private final SimpleStringProperty student_code = new SimpleStringProperty();

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

    public SimpleStringProperty getId() {return id;}

    public SimpleStringProperty getStudent_code() {return student_code;}

    public void setId(String id) {this.id.set(id);}
    public void setStudent_code(String student_code) {this.student_code.set(student_code);}


    @Override
    public String toString() {
        return "BorrowDetailModel{" +
                "id=" + id +
                "stt=" + stt +
                ", codeBook=" + codeBook +
                ", nameBook=" + nameBook +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
