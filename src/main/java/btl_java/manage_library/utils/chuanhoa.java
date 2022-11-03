package btl_java.manage_library.utils;

public class chuanhoa {
    public String chuanhoaBook(String s){
        s= s.trim().toLowerCase();
        s= s.replaceAll("\\s+"," ");
        String[] temp = s.split(" ");
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < temp.length; i++) {
            sBuilder.append(String.valueOf(temp[i].charAt(0)).toUpperCase()).append(temp[i].substring(1));
            if(i< temp.length-1) sBuilder.append(" ");
        }
        s = sBuilder.toString();
        return s;
    }
}
