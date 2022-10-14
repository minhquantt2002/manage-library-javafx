package btl_java.manage_library.models;

import java.util.Date;

public class LibraryModel {
    public Date timeIn;
    public Date timeOut;
    public String codeStudent;
    public String nameStudent;
    public String bookTook;

    public LibraryModel(Date timeIn, Date timeOut, String codeStudent, String nameStudent, String bookTook) {
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.codeStudent = codeStudent;
        this.nameStudent = nameStudent;
        this.bookTook = bookTook;
    }

    @Override
    public String toString() {
        return "LibraryModel{" +
                "timeIn=" + timeIn +
                ", timeOut=" + timeOut +
                ", codeStudent='" + codeStudent + '\'' +
                ", nameStudent='" + nameStudent + '\'' +
                ", bookTook='" + bookTook + '\'' +
                '}';
    }
}
