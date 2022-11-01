package btl_java.manage_library.models;

import javafx.beans.property.SimpleStringProperty;

public class BookModel {
    private final SimpleStringProperty stt = new SimpleStringProperty();
    private final SimpleStringProperty code = new SimpleStringProperty();
    private final SimpleStringProperty categoryBook = new SimpleStringProperty();
    private final SimpleStringProperty nameBook = new SimpleStringProperty();
    private final SimpleStringProperty authorBook = new SimpleStringProperty();
    private final SimpleStringProperty totalBook = new SimpleStringProperty();
    private final SimpleStringProperty remainBook = new SimpleStringProperty();

    public BookModel(String code, String categoryBook, String nameBook, String authorBook, String totalBook) {
        this.code.set(code);
        this.categoryBook.set(categoryBook);
        this.nameBook.set(nameBook);
        this.authorBook.set(authorBook);
        this.totalBook.set(totalBook);
    }

    public SimpleStringProperty getStt() {return stt;}

    public SimpleStringProperty getCode() {
        return code;
    }

    public SimpleStringProperty getCategoryBook() {
        return categoryBook;
    }

    public SimpleStringProperty getNameBook() {
        return nameBook;
    }

    public SimpleStringProperty getAuthorBook() {
        return authorBook;
    }

    public SimpleStringProperty getTotalBook() {
        return totalBook;
    }

    public SimpleStringProperty getRemainingBook() {
        return remainBook;
    }

    public void setStt(String stt) {
        this.stt.set(stt);
    }

    public void setRemainBook(String remainingBook) {
        this.remainBook.set(remainingBook);
    }

    @Override
    public String toString() {
        return "BookModel{" + "code=" + code + ", categoryBook=" + categoryBook + ", nameBook=" + nameBook +
                ", authorBook=" + authorBook + ", totalBook=" + totalBook + ", remainingBook=" + remainBook + '}';
    }
}