package btl_java.manage_library.models;

import com.mysql.cj.conf.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class LibraryModel {
    private final SimpleStringProperty codeStudent = new SimpleStringProperty();
    private final SimpleStringProperty nameStudent = new SimpleStringProperty();
    private final SimpleStringProperty bookTook = new SimpleStringProperty();

    public LibraryModel(String codeStudent, String nameStudent, String bookTook) {
        this.codeStudent.set(codeStudent);
        this.nameStudent.set(nameStudent);
        this.bookTook.set(bookTook);
    }

//    public Date getTimeIn() {
//        return timeIn;
//    }
//
//    public Date getTimeOut() {
//        return timeOut;
//    }

    public SimpleStringProperty getCodeStudent() {
        return codeStudent;
    }

    public SimpleStringProperty getNameStudent() {
        return nameStudent;
    }

    public SimpleStringProperty getBookTook() {
        return bookTook;
    }

    @Override
    public String toString() {
        return "LibraryModel{" +
//                "timeIn=" + timeIn +
//                ", timeOut=" + timeOut +
                ", codeStudent='" + codeStudent + '\'' +
                ", nameStudent='" + nameStudent + '\'' +
                ", bookTook='" + bookTook + '\'' +
                '}';
    }
}
