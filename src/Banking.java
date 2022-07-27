import javax.sound.midi.Soundbank;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;


public class Banking {
    public static  void main(String[] args) throws IOException {
        //creating accounts
        Account David = new Account("Dávid","A00001",3450);
        Account Tomi = new Account("Tomi","B00001", 3450);
        Tomi.mainWindow();
    }

}