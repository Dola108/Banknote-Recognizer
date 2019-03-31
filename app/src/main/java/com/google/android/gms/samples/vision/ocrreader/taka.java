package com.google.android.gms.samples.vision.ocrreader;

public class taka {

    public static String one (String s) {
        s = s.toLowerCase();
        int len = s.length();
        for (int i = 0; i < len - 1; i++) {
            if ((s.charAt(i) == 'o' && s.charAt(i + 1) == 'n')
                    || (s.charAt(i) == 'n' && s.charAt (i + 1) == 'e')) {
                return "ONE";
            }
        }
        return "";
    }

    public static String two (String s) {
        s = s.toLowerCase();
        int len = s.length();
        for (int i = 0; i < len - 1; i++) {
            if ((s.charAt(i) == 'w' && s.charAt (i + 1) == 'o')) {
                return "TWO";
            }
        }
        return "";
    }

    public static String five (String s) {
        s = s.toLowerCase();
        int len = s.length();
        for (int i = 0; i < len - 1; i++) {
            if ((s.charAt(i) == 'v' && s.charAt(i + 1) == 'e')) {
                return "FIVE";
            }
        }
        return "";
    }

    public static String ten (String s) {
        s = s.toLowerCase();
        int len = s.length();
        boolean ypresent = false;
        for (int i = 0; i < len; i++){
            if (s.charAt(i) == 'y') {
                ypresent = true;
            }
        }
        for (int i = 0; i < len - 1; i++) {
            if ((s.charAt(i) == 't' && s.charAt(i + 1) == 'e')
                    || (s.charAt(i) == 'e' && s.charAt(i + 1) == 'n' && ypresent == false)
                    || (s.charAt(i) == 'x')) {
                return "TEN";
            }
        }
        return "";
    }

    public static String twenty (String s){
        s = s.toLowerCase();
        int len = s.length();
        boolean wpresent = false;
        boolean npresent = false;
        boolean ypresent = false;
        for (int i = 0; i < len; i++){
            if (s.charAt(i) == 'w') wpresent = true;
            if (s.charAt(i) == 'n') npresent = true;
            if (s.charAt(i) == 'y') ypresent = true;
        }
        for (int i = 0; i < len - 1; i++){
            if ((s.charAt(i) == 'w' && s.charAt(i + 1) == 'e')
                    || (s.charAt(i) == 'n' && s.charAt(i + 1) == 't')
                    || ((wpresent || npresent) && ypresent)) {
                return "TWENTY";
            }
        }
        return "";
    }

    public static String fifty (String s) {
        s = s.toLowerCase();
        int len = s.length();
        boolean fpresent = false;
        boolean ypresent = false;
        for (int i = 0; i < len; i++){
            if (s.charAt(i) == 'f') fpresent = true;
            if (s.charAt(i) == 'y') ypresent = true;
        }
        for (int i = 0; i < len - 1; i++) {
            if ((s.charAt(i) == 'i' && s.charAt(i + 1) == 'f')
                    || (s.charAt (i) == 'f' && s.charAt(i + 1) == 't')
                    || (fpresent && ypresent)) {
                return "FIFTY";
            }
        }
        return "";
    }

    public static String hundred (String s) {
        s = s.toLowerCase();
        int len = s.length();
        for (int i = 0; i < len - 1; i++) {
            if ((s.charAt(i) == 'h' && s.charAt(i + 1) == 'u')
                    || (s.charAt(i) == 'u' && s.charAt(i + 1) == 'n')
                    || (s.charAt(i) == 'd' && s.charAt(i + 1) == 'r')
                    || (s.charAt(i) == '0' && s.charAt(i + 1) == '0')) {
                return "HUNDRED";
            }
        }
        return "";
    }

    public static String thousand (String s) {
        s = s.toLowerCase();
        int len = s.length();
        //System.out.println(s);
        for (int i = 0; i < len - 1; i++) {
            if ((s.charAt(i) == 't' && s.charAt(i + 1) == 'h')
                    || (s.charAt(i) == 'h' && s.charAt(i + 1) == 'o')
                    || (s.charAt(i) == 'o' && s.charAt(i + 1) == 'u')
                    || (s.charAt(i) == 'u' && s.charAt(i + 1) == 's')
                    || (s.charAt(i) == 'u' && s.charAt(i + 1) == 's')
                    || (s.charAt(i) == 's' && s.charAt(i + 1) == 'a')) {
                return "THOUSAND";
            }
        }
        return "";
    }

    public static String getTaka (String s) {
        String res = "";

        String hu = hundred (s);
        String th = thousand (s);

        String ok = "";
        if (ok.length() == 0) ok = one (s);
        if (ok.length() == 0) ok = two (s);
        if (ok.length() == 0) ok = five (s);
        if (ok.length() == 0) ok = ten (s);
        if (ok.length() == 0) ok = twenty (s);
        if (ok.length() == 0) ok = fifty (s);

        return ok + " " + th + hu;
    }

}