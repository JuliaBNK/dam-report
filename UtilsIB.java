/*
 * Name:       Iuliia Buniak       
 *
 * Course:     CS-12, Fall 2018
 *
 * Date:       10/28/2018
 *
 * Filename:   UtilsIB
 *
 * Purpose:    To create static class which embraces often-used utility methods.
 *             All methods should be called statically: UtilsIB.method()
 */

import java.util.Random; 
import java.util.Scanner;
import javax.swing.JOptionPane;

public class UtilsIB {

    //---------------------------------------------------------------------------
    // user input methods, by datatype
    //---------------------------------------------------------------------------
    
    // method to get an int value
    public static int readInt(String prompt, boolean flag) {
        
        // set up data and objects
        String dataString;
        Scanner input;
        int data=0;
        String garbage;
        
        // input using popup dialog (flag = true)    
        if (flag) {
            boolean failed = true;
            
            // checking the input and reprompting if input is not an integer
            while (failed == true) {
                try {
                    dataString = JOptionPane.showInputDialog(null, prompt);
                    data = Integer.parseInt(dataString);
                    failed = false;
                }
                catch(NumberFormatException nfe) {
                }
            }//end while
     
        }//end if
         
        // input from command line (flag = false)            
        else {
            input = new Scanner(System.in);
            System.out.print(prompt);
            
            // if input is not an integer, user will be reprompted to enter an int   
            while ( !input.hasNextInt() ){
                garbage = input.nextLine();
                System.out.print(prompt);
            }//and while
            
            // an int will be read
            data = input.nextInt();
        } //end else
         
        return data;     
    }
    
    public static double readDouble(String prompt, boolean flag) {
        
        // set up data and objects
        String dataString;
        Scanner input;
        double data=0.0;
        String garbage;
        
        // input using popup dialog (flag = true)    
        if (flag) {
            boolean failed = true;
            
            // checking the input and reprompting if input is not a double
            while (failed == true) {
                try {
                    dataString = JOptionPane.showInputDialog(null, prompt);
                    data = Double.parseDouble(dataString);
                    failed = false;
                }
                catch(NumberFormatException nfe) {
                }
            }//end while
     
        }//end if
         
        // input from command line (flag = false)            
        else {
            input = new Scanner(System.in);
            System.out.print(prompt);
            
            // if input is not a double, user will be reprompted to enter a double 
            while ( !input.hasNextDouble() ){
                garbage = input.nextLine();
                System.out.print(prompt);
            }
            
            // a double will be read
            data = input.nextDouble();
        } //end else
         
        return data;     
    }
    
    
    // method to get char value
    public static char readChar(String prompt, boolean flag) {
        
        // set up data and objects
        String dataString;
        Scanner input;
        char data;
        
        // input using popup dialog (flag = true)    
        if (flag) {
            dataString = JOptionPane.showInputDialog(null, prompt);
            data = dataString.charAt(0);       
        } 
        // input from command line (flag = false)            
        else {
            input = new Scanner(System.in);
            System.out.print(prompt);
            dataString = input.nextLine();
            data = dataString.charAt(0);
        }
        return data;     
    }
    
    
    // method to get a string
    public static String readString(String prompt, boolean flag) {
        
        // set up data and objects
        String data;
        Scanner input;
        
        // input using popup dialog (flag = true)    
        if (flag) {
            data = JOptionPane.showInputDialog(null, prompt);      
        } 
        // input from command line (flag = false)            
        else {
            input = new Scanner(System.in);
            System.out.print(prompt);
            data = input.nextLine();
        }
        return data;     
    }
    // random number generator---------------------------------------------------
    
    // method to generate random numbers
    public static int randomInt(int minValue, int maxValue){
        Random rand = new Random();
        int randNum;
        randNum = rand.nextInt(maxValue - minValue + 1) + minValue;
        return randNum; 
 
    }
    
    // execution related methods-------------------------------------------------
    
    // pauses execution until any key is pressed, specific prompt
    public static void pause(String message){
    
        // ignores any returned value 
        readString(message, false);
        System.out.println();    // add a blank line
    }
    
    // pause execution until any key is pressed, generic prompt
    public static void pause(){
        pause("Press <Enter> to continue... ");   
    }
    
    //---------------------------------------------------------------------------
    // age-related methods
    //---------------------------------------------------------------------------
    
    // returns the age as of some REFERENCE date (2-input overloaded form)
    public static int getAge(CS12Date dateBd, CS12Date dateRef) {
        int age = -1; // default value
        int bdMonth, bdDay, bdYear;
        int refMonth, refDay, refYear;
        
        // getting separate int (M,D,Y) from objects of class CS12Date 
        bdYear = dateBd.getYear();
        bdMonth = dateBd.getMonth();
        bdDay = dateBd.getDay();
        refYear = dateRef.getYear();
        refMonth = dateRef.getMonth();
        refDay = dateRef.getDay();
        
        // displaying the error message for future birthday reletive to reference date 
        // person wasn't born yet 
        if (dateBd.compare(dateRef) == -1) {
            System.out.println("Error: provided birthdate " + dateBd + 
                               " is after reference date " +  dateRef);  
        }
        // calculating age if person will celebrate birthday in later months of reference year
        else if (bdMonth > refMonth) {
            age = refYear - bdYear - 1;    
        }
        // calculating age if person will celebrate birthday later in reference months
        else if (bdDay > refDay && bdMonth == refMonth) {
            age = refYear - bdYear - 1;
        }
        // calculating age if person has birthday today or has alredy celebrated it this year
        else {
            age = refYear - bdYear;
        }     
        return age;  
    } 
    
    
    // returns the age as of TODAY'S date (1-input overloaded form)
    public static int getAge(CS12Date dateBd) {
        int age;
        CS12Date dateToday = today();
        
        // calls overloaded version of above method, using TODAY as the reference date
        age = getAge(dateBd, dateToday);  
        return age;    
    }
    
   
    // returns today's date as a CS12Date 
    public static CS12Date today() {
    
        return new CS12Date();
        
    }
       
} // end class