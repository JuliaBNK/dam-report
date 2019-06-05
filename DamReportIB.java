/*
 * Name:     Iuliia Buniak
 *
 * Course:   CS-12, Fall 2018
 *
 * Date:     12/11/18
 *
 * Filename: DamReportIB.java
 *
 * Purpose:  Create a program to set up an array of Dam objects gotten from a file,
 *           to display the used Dams, to report on the overall water system status   
 */

import java.util.Scanner;  // to set up a file read
import java.io.File;       // to set up a file read
import java.io.IOException;

public class DamReportIB {
    public static void main (String [] arg) throws IOException {
        // Data declaration
        char input;
        int count = 0;
        final int MAX_DAM = 50;
        DamIB [] dams  = new DamIB [MAX_DAM];
        String menu = "\n----------------------------\n" +
                        "DAM OPTIONS\n" +
                        "----------------------------\n" +
                        "Read data from file      [R]\n" +
                        "Print dam summaries      [S]\n" +
                        "Print dam detailes       [D]\n" +  
                        "Overall water status     [W]\n" + 
                        "Quit                     [Q]\n";
 
        // priming read
        System.out.print(menu);
        input = UtilsIB.readChar("Enter option: ", false);
        // loop will work until user press Q or q option 
        while ((input !='q') && (input !='Q')){
            
            // depending on user's pick different methods will be called
            switch(input) {
            
                // to read data from file 
                case 'R':
                case 'r':
                    count = readDataFromFile(dams);    
                    break;
                
                // to print dam summaries
                case 'S':
                case 's':
                    printDamSummaries(dams, count);
                    break;
                
                // to print dam detailes
                case 'D':
                case 'd':
                    printDamDetails(dams, count);
                    break;
                    
                // to display overall water status
                case 'W':
                case 'w':
                    showWaterStatus(dams, count);
                    break;
    
                // program's reply if user enters something except available options           
                default:
                    System.out.println ("Unrecognized option " + input + ", please try again\n");
                    break;
        
            }//end switch
            
            // update read
            System.out.print(menu);
            input = UtilsIB.readChar ("Enter option: ", false);
 
        }// end while
        
        // termination
        System.out.println("Exit upon user request");

    } //end main

    // to convert an input String into Dam object 
    private static DamIB convertString2Dam(String data) {
        // instance variables for dam object
        String name;
        int year;
        double storage, capacity, inflow, outflow; 
        CS12Date date;
        DamIB dam;

        // splitting the string by comma separator
        String [] tokens1 = data.split(",");
        for (int i=0; i < tokens1.length; i++) {
            // removing any leading or trailing whitespace
            tokens1[i] = tokens1[i].trim();
        }
        
        // splitting a date string by "/" separator
        String [] tokens2 = tokens1[6].split("/");
        for (int i=0; i < tokens2.length; i++) {
            // removing any leading or trailing whitespace
            tokens2[i] = tokens2[i].trim();
        }
        
        // assembling an object from extracted data
        name = tokens1[0];
        year = Integer.parseInt(tokens1[1]);
        storage = Double.parseDouble(tokens1[2]); 
        capacity = Double.parseDouble(tokens1[3]); 
        inflow = Double.parseDouble(tokens1[4]);
        outflow = Double.parseDouble(tokens1[5]);                   
        date = new CS12Date (Integer.parseInt(tokens2[0]),
                             Integer.parseInt(tokens2[1]),
                             Integer.parseInt(tokens2[2]));
                                                
        // use all data to create a new Dam object with full constructor 
        dam = new DamIB(name, year, storage, capacity, inflow, outflow, date);
        return dam;
        
    } // end convertString2Dam()

    // method for reading a file
    private static int readDataFromFile(DamIB[] dams) throws IOException  {
        String filename, text;
        int count = 0;
        
        // initializing an array and wiping out any prior data 
        for (int i=0; i < dams.length; i++) {
            dams[i] = null;
        }
        
        // reading an input filename using UtilsIB
        filename = UtilsIB.readString("Enter text file name: ", false);
        
        // setting up a new Scanner to read from that file
        File infile = new File(filename);
        Scanner fileInput = new Scanner(infile);
        
        // reading line-by-line from the file 
        while (fileInput.hasNext()) {
            text = fileInput.nextLine();
            dams[count] = convertString2Dam(text);
            count++;        
        } 
        
        // printing out number of created objects and name of a file
        System.out.println(count + " dams read from file: " + filename);
        return count;
        
    }// end readDataFromFile()
    
    // method for printing of used Dams in tabular form
    private static void printDamSummaries(DamIB [] dams, int count) {
        System.out.println();
    
        // checking if some dams exist; if array is empty, error message will be displayed 
        if (count == 0) {
            System.out.println ("ERROR: no Dams currently exist! Must import from file."); 
        }
        else {
            System.out.printf("%-13s%13s%13s%13s%13s%13s%13s\n", "Name", "Year",
                              "Storage", "Capacity", "Inflow", "Outflow", "Date");
           
            for (int i=0; i<count; i++) {
                System.out.printf("%-13s%13d%,13.0f%,13.0f%,13.0f%,13.0f%13s\n",
                                   dams[i].getName(), dams[i].getYear(),
                                   dams[i].getStorage(),dams[i].getCapacity(), 
                                   dams[i].getInflow(), dams[i].getOutflow(),
                                   dams[i].getDate());
            }
        }
        System.out.println();
        
    } // end printDamSummaries()
    
    
    // method for printing used Dams using their own print()
    private static void printDamDetails(DamIB[] dams, int count) {
        System.out.println();
    
        // checking if some dams exist, if array is empty, error message will be displayed 
        if (count == 0) {
            System.out.println ("ERROR: no Dams currently exist! Must import from file."); 
        }

        else {
            for (int i=0; i<count; i++) {
                String str = dams[i].getName(); 
                dams[i].print(str);
            }
        }
        System.out.println();
        
    } // end printDamDetails()
    
    
    // method to report on the overall water system status 
    private static void showWaterStatus(DamIB[] dams, int count) {
        
        double superCapacity = 0.0;
        double superStorage = 0.0;
        double superInflow = 0.0;
        double superOutflow = 0.0;
        CS12Date date = new CS12Date();
        CS12Date refDate = new CS12Date(11,23,2018);
        System.out.println();
    
        // checking if some dams exist, if array is empty, error message will be displayed 
        if (count == 0) {
            System.out.println ("ERROR: no Dams currently exist! Must import from file."); 
        }

        else {
            // creation of super dam that will acumulate all water-related quantities 
            DamIB superDam = new DamIB();
            
            // setting up Super Dam's name, year of current date and specified date
            superDam.setName("Super Dam");
            superDam.setYear(date.getYear());
            superDam.setDate(refDate);
            
            // summing up water-related quantities of dams from array
            for (int i=0; i<count; i++){
                superCapacity += dams[i].getCapacity();
                superStorage += dams[i].getStorage();
                superInflow += dams[i].getInflow();
                superOutflow += dams[i].getOutflow();
            }
            
            // setting up total water features to super dam
            superDam.setCapacity(superCapacity);
            superDam.setStorage(superStorage);
            superDam.setInflow(superInflow);
            superDam.setOutflow(superOutflow);
            
            superDam.print("OVERALL WATER HEALTH");    
        }   
    } // end showWaterStatus()
    
                     
} // end class 