package btl_java.manage_library.utils;

public class chuanhoa {
    public String chuanhoaBook(String s){
        s= s.trim().toLowerCase();
        s= s.replaceAll("\\s+"," ");
        String[] temp = s.split(" ");
        s="";
        for (int i = 0; i < temp.length; i++) {
            s+=String.valueOf(temp[i].charAt(0)).toUpperCase()+
                    temp[i].substring(1);
            if(i< temp.length-1) s+=" ";
        }
        return s;
    }
}
