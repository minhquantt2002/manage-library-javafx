package btl_java.manage_library.models;

import javafx.beans.property.SimpleStringProperty;

public class BookModel {
    private final SimpleStringProperty stt =new SimpleStringProperty();
    private final SimpleStringProperty categoryBook =new SimpleStringProperty();
    private final SimpleStringProperty nameBook =new SimpleStringProperty();
    private final SimpleStringProperty authorBook =new SimpleStringProperty();
    private final SimpleStringProperty totalBook =new SimpleStringProperty();
    private final SimpleStringProperty remainingBook =new SimpleStringProperty();
    public BookModel(String stt,String categoryBook,String nameBook,String authorBook,String totalBook,String remainingBook){
        this.stt.set(stt);
        this.categoryBook.set(categoryBook);
        this.nameBook.set(nameBook);
        this.authorBook.set(authorBook);
        this.totalBook.set(totalBook);
        this.remainingBook.set(remainingBook);
    }

    public String getStt() {
        return stt.get();
    }

    public SimpleStringProperty sttProperty() {
        return stt;
    }

    public String getCategoryBook() {
        return categoryBook.get();
    }

    public SimpleStringProperty categoryBookProperty() {
        return categoryBook;
    }

    public String getNameBook() {
        return nameBook.get();
    }

    public SimpleStringProperty nameBookProperty() {
        return nameBook;
    }

    public String getAuthorBook() {
        return authorBook.get();
    }

    public SimpleStringProperty authorBookProperty() {
        return authorBook;
    }

    public String getTotalBook() {
        return totalBook.get();
    }

    public SimpleStringProperty totalBookProperty() {
        return totalBook;
    }

    public String getRemainingBook() {
        return remainingBook.get();
    }

    public SimpleStringProperty remainingBookProperty() {
        return remainingBook;
    }

    @Override
    public String toString() {
        return "BookModel{" +"stt=" + stt +", categoryBook=" + categoryBook +", nameBook=" + nameBook +
                ", authorBook=" + authorBook +", totalBook=" + totalBook +", remainingBook=" + remainingBook +'}';
    }
}
