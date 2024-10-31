import java.util.Scanner;
import java.io.*;
import java.net.*;


public class Database implements DatabaseInterface {


    @Override
    public boolean storeNewUser() {
        return false;
    }

    @Override
    public boolean storeNewMessage() {
        return false;
    }

    @Override
    public String getUserMessage() {
        return "";
    }

    @Override
    public String getUserName() {
        return "";
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsersName() {
        return "";
    }

    @Override
    public String getBlockedUsers() {
        return "";
    }

    @Override
    public boolean removeMessage() {
        return false;
    }

    @Override
    public String getUserBio() {
        return "";
    }

    @Override
    public String getUserFreinds() {
        return "";
    }
}
