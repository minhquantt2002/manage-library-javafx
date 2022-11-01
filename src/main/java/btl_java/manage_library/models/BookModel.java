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

    public SimpleStringProperty getStt() {
        return stt;
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
        return remainingBook;
    }


    @Override
    public String toString() {
        return "BookModel{" +"stt=" + stt +", categoryBook=" + categoryBook +", nameBook=" + nameBook +
                ", authorBook=" + authorBook +", totalBook=" + totalBook +", remainingBook=" + remainingBook +'}';
    }
}
