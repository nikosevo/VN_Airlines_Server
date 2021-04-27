package sample;

import java.util.ArrayList;
//todo fix or delete this whole class
public class Account
{
    private String password;              //this is going to be hashed ;)
    private String username;
    private String userId;
    private String age;
    private String lastdest;  //user last used/visited destination
    private String email;
    private ArrayList<String[]> reservations ;

    public Account(String password,String username,String userId,String age,String lastdest,String email)
    {
        this.age=age;
        this.email=email;

    }

}
