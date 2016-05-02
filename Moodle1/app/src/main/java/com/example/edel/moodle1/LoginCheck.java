package com.example.edel.moodle1;

/**
 * Created by Apple on 19/02/16.
 */
public class LoginCheck {
    static String[] u = { "cs1110200", "cs5110281", "cs5110300","cs5110271","vinay","scgupta","subodh" };
    static String[] p = { "john", "jasmeet", "shubham","abhishek","vinay","scgupta","subodh" };


    public static Boolean check(String a, String b){
        int i = 0;
        while(i<u.length){
            if(u[i].equals(a)){
                break;
            }else{
                i++;
            }
        }
        if(i<u.length){
            if(p[i].equals(b)){
                return true;
            }else{
                return false;
            }
        }
    else{
        return false;
    }
    }
}
