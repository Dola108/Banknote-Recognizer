package com.google.android.gms.samples.vision.ocrreader;

public class country {

    public static String bangladesh (String s){
        s = s.toLowerCase();
        int len = s.length();
        for (int i = 0; i < len - 2; i++){
            if ((s.charAt(i) == 'a' && s.charAt(i + 1) == 'n' && s.charAt(i + 2) == 'g')
                    || (s.charAt(i) == 'n' && s.charAt(i + 1) == 'g' && s.charAt(i + 2) == 'l')
                    || (s.charAt(i) == 'g' && s.charAt(i + 1) == 'l' && s.charAt(i + 2) == 'a')
                    || (s.charAt(i) == 'l' && s.charAt(i + 1) == 'a' && s.charAt(i + 2) == 'd')
                    || (s.charAt(i) == 'a' && s.charAt(i + 1) == 'd' && s.charAt(i + 2) == 'e')
                    || (s.charAt(i) == 'd' && s.charAt(i + 1) == 'e' && s.charAt(i + 2) == 's')
                    || (s.charAt(i) == 'e' && s.charAt(i + 1) == 's' && s.charAt(i + 2) == 'h')
                    || (s.charAt(i) == 't' && s.charAt(i + 1) == 'a' && s.charAt(i + 2) == 'k')
                    || (s.charAt(i) == 'a' && s.charAt(i + 1) == 'k' && s.charAt(i + 2) == 'a')){
                return "BANGLADESHI TAKA";
            }
        }
        return "";
    }

    public static String brazil (String s) {
        s = s.toLowerCase();
        int len = s.length();
        for (int i = 0; i < len - 2; i++){
            if ((s.charAt(i) == 'b' && s.charAt(i + 1) == 'r' && s.charAt(i + 2) == 'a')
                    || (s.charAt(i) == 'r' && s.charAt(i + 1) == 'a' && s.charAt(i + 2) == 's')){
                return "BRAZILIAN REAIS";
            }
        }
        return "";
    }

    public static String canada (String s) {
        s = s.toLowerCase();
        int len = s.length();
        for (int i = 0; i < len - 2; i++){
            if ((s.charAt(i) == 'c' && s.charAt(i + 1) == 'a' && s.charAt(i + 2) == 'n')
                || (s.charAt(i) == 'a' && s.charAt(i + 1) == 'n' && s.charAt(i + 2) == 'a')
                || (s.charAt(i) == 'n' && s.charAt(i + 1) == 'a' && s.charAt(i + 2) == 'd')
                || (s.charAt(i) == 'a' && s.charAt(i + 1) == 'd' && s.charAt(i + 2) == 'a')){
                return "CANADIAN DOLLAR";
            }
        }
        return "";
    }

    public static String srilanka (String s) {
        s = s.toLowerCase();
        int len = s.length();
        for (int i = 0; i < len - 2; i++){
            if ((s.charAt(i) == 'c' && s.charAt(i + 1) == 'a' && s.charAt(i + 2) == 'n')
                    || (s.charAt(i) == 'l' && s.charAt(i + 1) == 'a' && s.charAt(i + 2) == 'n')
                    || (s.charAt(i) == 'a' && s.charAt(i + 1) == 'n' && s.charAt(i + 2) == 'k')
                    || (s.charAt(i) == 'n' && s.charAt(i + 1) == 'k' && s.charAt(i + 2) == 'a')){
                return "SRI-LANKAN RUPEE";
            }
        }
        return "";
    }

    public static String getCountry (String s) {
        String res = "";
        res = bangladesh (s);
        if (res.length() == 0) res = canada (s);
        if (res.length() == 0) res = srilanka (s);
        if (res.length() == 0) res = brazil (s);
        return res;
    }
}
