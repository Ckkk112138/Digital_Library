package com.example.home;

public class CurrentUser {
    private static String currentUserName = null;
    private static boolean login = false;


    public static String getCurrentUserName()
    {
        return currentUserName;
    }
    public static boolean getLoginState()
    {return login;}


    public static String setCurrentUserName(String newCurrentUserName)
    {
        currentUserName = newCurrentUserName;
        return currentUserName;
    }

    public static void setLoginState(boolean loginState)
    {
        login = loginState;

    }


}
