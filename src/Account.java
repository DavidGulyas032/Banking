import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Account {
    int balance;
    int previousTransaction;
    int pinCode;
    String customerName;
    String customerID;
    int loan;
    List<String> userData = new ArrayList<String>();
    File data = new File("data.csv");

    List<List<String>> records = new ArrayList<>();



    Account(String cname,String cid,int pin) {
        customerName=cname;
        customerID=cid;
        pinCode = pin;
    }
    /**function about the customer**/

    public void getInfo(String custID) {
        if (custID.equals(this.customerID)) {
            System.out.println("Name: "+this.customerName+" Balance: "+"$"+this.balance + " Latest transaction: "+this.previousTransaction+"$");
        }
    }


    /**function for depositing money into your account**/
    public void makeDeposit(int amount) {
        if (amount > 0) {
            balance +=amount;
            previousTransaction = amount;
        }
        else {
            System.out.println("Please try again the amount is smaller than 0");
        }
    }

    /**function to withdraw money from your account**/
    public void makeWithdraw(int amount) {
        if (amount > 0 && this.balance > 0 && this.balance >= amount) {
            balance -= amount;
            previousTransaction = -amount;
        }
        else {
            System.out.println("You have no money in your account or the amount is smaller than 0!");
        }
    }

    /**this function calculates the interest if you keep your money in the bank**/
    public void interestCalcualtor(int years) {
            double interestRate = 0.015;
            double newBalance = (this.balance * interestRate * years) + this.balance;
            System.out.println("The interest rate right now : "+(100*interestRate)+" %");
            System.out.println("Your new balance after: "+ years +" years will be: " + "$"+ newBalance);

    }


    /**this function saves the datas into an array for further process**/
    public void saveData(String id) {
        String infos = this.customerID +","+ this.customerName +","+this.balance+","+this.previousTransaction;
        userData.add(infos);
    }

    /**this one saves the datas into a csv file to store infos about customers for further actions**/
    public void datasToCsv()  throws FileNotFoundException {

        try {
            if (!data.exists()) {
                data.createNewFile();
            }
            PrintWriter out = new PrintWriter( new FileWriter(data,true));
            for (String i:this.userData) {
                out.write(i+"\n");
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**it reads the datas from the csv and saves it into an array**/
    public void readFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        }
    }

    /**loads back the datas into the matching attributes**/
    public void loadBack() throws IOException {
        readFile();
        int size = this.records.size();
        for(int i = 0; i < size; i++) {
            if((this.records.get(i).toArray()[0]).equals(this.customerID)) {
                this.balance =Integer.valueOf((this.records.get(i).toArray()[2]).toString());
                this.previousTransaction = Integer.valueOf((this.records.get(i).toArray()[3]).toString());
            }
        }
    }
    /**the main window with every function in**/
    public void  mainWindow() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome "+this.customerName+" !");
        System.out.println("Please type your pin code in!");
        int pinCode = scan.nextInt();
        if (pinCode !=this.pinCode) {
            System.out.println("The pin code is wrong!");
        }
        else{

        loadBack();
        System.out.println("What would you like to do? Please choose an option!");
        System.out.println();
        System.out.println("[1] Information  about your account ");
        System.out.println("[2] Make a deposit ");
        System.out.println("[3] Make a withdraw ");
        System.out.println("[4] Interest calculator ");
        System.out.println("[5] Exit ");
        int option = 0;
            if ( pinCode ==this.pinCode) {
                while (option != 5) {
                    System.out.println("Enter an option!");
                    int option2 = scan.nextInt();
                    switch (option2) {

                        case 1:
                            getInfo(this.customerID);
                            break;
                        case 2:
                            System.out.println("Enter the amount you would like to deposit: ");
                            int depoAmount = scan.nextInt();
                            makeDeposit(depoAmount);
                            break;
                        case 3:
                            System.out.println("Enter the amount you would like to withdraw: ");
                            int withAmount = scan.nextInt();
                            makeWithdraw(withAmount);
                            break;
                        case 4:
                            System.out.println("Enter the years for the interest calculator: ");
                            int numYears = scan.nextInt();
                            interestCalcualtor(numYears);
                            break;

                        case 5:
                            System.out.println("Good bye!");
                            option = 5;
                            saveData(this.customerID);
                            datasToCsv();
                            break;
                        default:
                            System.out.println("There is no option like that");
                            break;

                    }
                }
            }
            }
    }

}
