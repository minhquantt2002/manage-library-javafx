package btl_java.manage_library.models;

import javafx.beans.property.SimpleStringProperty;

public class BorrowDetailModel {
    private final SimpleStringProperty stt = new SimpleStringProperty();
    private final SimpleStringProperty codeBook = new SimpleStringProperty();
    private final SimpleStringProperty nameBook = new SimpleStringProperty();
    private final SimpleStringProperty totalBook = new SimpleStringProperty();

    public BorrowDetailModel(String stt, String codeBook, String nameBook, String totalBook) {
        this.stt.set(stt);
        this.codeBook.set(codeBook);
        this.nameBook.set(nameBook);
        this.totalBook.set(totalBook);
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


    public SimpleStringProperty getTotalBook() {
        return totalBook;
    }

    @Override
    public String toString() {
        return "BorrowDetailModel{" +
                "stt=" + stt +
                ", codeBook=" + codeBook +
                ", nameBook=" + nameBook +
                ", totalBook=" + totalBook +
                '}';
    }
}
