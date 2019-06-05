/*
 * Name:     Iuliia Buniak
 *
 * Course:   CS-12, Fall 2018
 *
 * Date:     11/22/18
 *
 * Filename: DamIB.java
 *
 * Purpose:  Create class Dam which allows to check inflow, outflow, storage, capacity, state of a dam 
 *           and to predict time of possible event (overflow or draining)  
 */

public class DamIB {
        
    // instance variables--------------
    private String name;       // name of the dam
    private int year;          // year in which the dam was completed
    private double storage;    // current water storage of the dam, in [acre-feet]
    private double capacity;   // maximum water storage of the dam, in [acre-feet]
    private double inflow;     // water inflow rate, in [cu-ft/sec] or [cfs]
    private double outflow;    // water outflow rate, in [cu-ft/sec] or [cfs]
    private CS12Date date;     // date stamp of current data
            
    // constants-----------------------
    private final double TOL = 0.0001;            // Floating Points tolerance
    private final double CUFT_PER_ACREFT = 43560; // convertion rate (from cubic feet to acres)
    private final int SECS_PER_HR = 3600;         // amount of seconds in one hour
    private final int HRS_PER_DAY = 24;           // amount of hours per day
    private final double MIN_CAPACITY = 0.0;      // the lower bound of capacity; capacity cannot be < 0.0
    private final double MIN_STORAGE = 0.0;       // the lower bound of storage; storage cannot be < 0.0
    private final double MIN_INFLOW = 0.0;        // the lower bound of inflow;  inflow cannot be < 0.0
    private final double MIN_OUTFLOW = 0.0;       // the lower bound of outflow; outflow cannot be < 0.0
    private final double MIN_IMPORT_WATER = 0.0;  // the lower bound of imported water; cannot be < 0.0
    private final double MIN_RELEASE_WATER = 0.0; // the lower bound of released water; cannot be < 0.0
    
      
    // other class data----------------
            
    // data above here
    //=================================
    // methods below here
        
    // constructors--------------------
        
    // default constructor 
    public DamIB() {
        name = "<default dam>";
        year = 1900; 
        storage = 0.0;
        capacity = 0.0; 
        inflow = 0.0;
        outflow = 0.0; 
        date = new CS12Date();    
    }
        
    // full constructor; all values specified 
    public DamIB(String name, int year, double storage, 
                 double capacity, double inflow,
                 double outflow, CS12Date date) {
        this();
        this.name = name;
        setYear(year);
        setCapacity(capacity);
        setStorage(storage);
        setInflow(inflow); 
        setOutflow (outflow);
        setDate(date);  
    }
    
    // alternate constructor #1; specified dam name and opening year 
    // all other instance variable with default values 
    public DamIB (String name, int year) {
        // pulls in ALL defaults, must be the first statement
        this();
        // overwrite only variables with changes
        this.name = name;
        setYear(year);
    }
    
    // alternate constructor #2; specified dam name and capacity
    // all other instance variable with default values
    public DamIB (String name, double capacity) {
        // pulls in ALL defaults, must be the first statement
        this();
        // overwrite only the ones with changes
        this.name = name;
        setCapacity(capacity);
    }
           
    // display methods-----------------
        
    // returns all instance variables in a comma-separated String format
    public String toString() {
        String str = String.format("%5s%7d%,10.0f%,10.0f%,10.0f%,10.0f%15s", name, year, storage, capacity, inflow, outflow, date); 
        return str;
    }
        
    // displays all instance variables AND derived data in a label-value output format
    public void print() {
        System.out.printf("%-25s%-10s\n", "name:", name);
        System.out.printf("%-25s%-10d\n", "year opened", year);
        System.out.printf("%-25s%-10s\n", "data as of:", date);
        System.out.printf("%-25s%,-10.0f\n", "storage [acre-ft]:", storage);
        System.out.printf("%-25s%,-10.0f\n", "capacity [acre-ft]:", capacity);
        System.out.printf("%-25s%,-10.0f\n", "inflow [cu-ft/s]:", inflow);
        System.out.printf("%-25s%,-10.0f\n", "outflow [cu-ft/s]:", outflow);
        System.out.printf("%-25s%-10d\n", "age [yrs]:", getAge());
        System.out.printf("%-25s%-10s\n", "status:", getStatus());
        System.out.printf("%-25s%-4.1f%s\n", "% full:", getPercentFull(), "%");
        System.out.printf("%-25s%-10d\n", "days until dam event:", getEventDays());
        System.out.printf("%-25s%-10s\n", "date of dam event", getEventDate());
        System.out.println();
    }
        
    // displays all instance variables AND derived data in a label-value output format,
    // preceded by a message in a formatted box
    public void print (String message) {
        System.out.println("========================="); 
        System.out.println(message);
        System.out.println("=========================");
        print();
    }
        
    // accessors, mutators-------------
    
    // returns the NAME of the dam
    public String getName(){
        return name;
    } 
    
    // sets the name of the dam
    public void setName(String name) {
        this.name = name;
    }
    
    //overloaded mutator; sets the name of the dam, using prompted user input
    public void setName(boolean mode) {
        String data = UtilsIB.readString("Enter name of the dam > ", mode);
        setName(data);
    }
    
    
    // returns the YEAR the dam was completed
    public int getYear() {
        return year;
    }      
    
    // sets the opening year of the dam
    public void setYear(int year) {
        // checking if the year isn't less than the default value (1900)
        if (year < 1900) {
            System.out.println("ERROR: year must be set to >= 1900, value unchanged"); 
        }
        else {
            this.year = year;
        }
    }  
    
    //overloaded mutator; sets the year of the dam's completion, using prompted user input
    public void setYear(boolean mode) {
        int data = UtilsIB.readInt("Enter the year of the dam's completion > ", mode);
        setYear(data);
    }
    
    
     // returns the CAPACITY of the dam
    public double getCapacity() {
        return capacity;
    } 
    
    // sets the capacity of the dam
    public void setCapacity(double capacity) {
        if (capacity < MIN_CAPACITY) {                   //  if capacity < 0.0, value won't be changed
                System.out.println("ERROR: capacity must be set to >= 0.0, value unchanged");
            }
            else if (capacity <= storage) {              // capacity that is less than storage won't be set
                System.out.println("ERROR: capacity must be >= storage, value unchanged");
            }
            else {
                this.capacity = capacity;
            }     
    }
    
    //overloaded mutator; sets the capacity of the dam, using prompted user input
    public void setCapacity(boolean mode) {
        double data = UtilsIB.readDouble("Enter total dam water capacity [acre-ft] > ", mode);
        setCapacity(data);
    }


    // returns the STORAGE of the dam 
    public double getStorage() {
        return storage;
    } 
    
    // sets the storage of the dam
    public void setStorage(double storage) {
        if (storage < MIN_STORAGE) {                       // if storage < 0.0, value won't be changed         
            System.out.println("ERROR: storage must be set to >= 0.0, value unchanged");
        }
        else if (storage > capacity) {                     // if storage > capacity, value won't be changed
            System.out.println("ERROR: storage must be <= capacity, value unchanged");
        }
        else {
            this.storage = storage;
        }    
    }
    
    //overloaded mutator; sets the storage of the dam, using prompted user input
    public void setStorage(boolean mode) {
        double data = UtilsIB.readDouble("Enter current dam water storage [acre-ft] > ", mode);
        setStorage(data);
    }  
        
    
    // returns the INFLOW of the dam
    public double getInflow() {
        return inflow;
    } 
    
    // sets the inflow of the dam
    public void setInflow(double inflow) { 
        if (inflow < MIN_INFLOW) {                           // if inflow < 0.0, value won't be changed
            System.out.println("ERROR: inflow must be set to >= 0.0, value unchanged");
        }
        else {
            this.inflow = inflow;
        }  
    }
    
    //overloaded mutator; sets the inflow of the dam, using prompted user input
    public void setInflow(boolean mode) {
        double data = UtilsIB.readDouble("Enter current water inflow rate [cu-ft/sec] > ", mode);
        setInflow(data);
    }

    
    // returns the OUTFLOW of the dam
    public double getOutflow() {
        return outflow;
    } 
    
    // sets the outflow of the dam
    public void setOutflow(double outflow) {
        if (outflow < MIN_OUTFLOW) {                       // if outflow <0.0, value won't be changed 
            System.out.println("ERROR: outflow must be set to >= 0.0, value unchanged");
        }
        else {
            this.outflow = outflow;
        } 
        
    }
    
    //overloaded mutator; sets the outflow of the dam, using prompted user input
    public void setOutflow(boolean mode) {
        double data = UtilsIB.readDouble("Enter current water outflow [cu-ft/sec] > ", mode);
        setOutflow(data);
    }
    
    
    // returns the DATE of the current dam measurements
    public CS12Date getDate() {
        // creating a "trowaway" copy of date field-by-field for safe returning the object  
        CS12Date temp = new CS12Date();
        temp.setMonth(this.date.getMonth());
        temp.setDay(this.date.getDay());
        temp.setYear(this.date.getYear());
        return temp;
    } 
    
    // sets the date of the current dam measurements
    public void setDate(CS12Date date) {
        // safe transfering the object field-by-field
        (this.date).setMonth(date.getMonth());
        (this.date).setDay(date.getDay());
        (this.date).setYear(date.getYear());
    }
    
    // overloaded mutator; sets the date of the current dam measurements,
    // using prompted user input
    public void setDate(boolean mode) {
        int mm = UtilsIB.readInt("Enter the month of the current dam measurements > ", mode);
        int dd = UtilsIB.readInt("Enter the day of the current dam measurements > ", mode);
        int yy = UtilsIB.readInt("Enter the year of the current dam measurements > ", mode);
        CS12Date date = new CS12Date(mm,dd,yy); 
        setDate(date);  
    }

    // equivalance---------------------
    
    // compares this Dam object to another Dam object
    public boolean equals(Object obj) {
        //first, check whether objects of the same type
        if (!(obj instanceof DamIB)) {
        
            // stop, we are not comparing apples to oranges
            return false;
        }
        else {
            // typecast into intended object types
            DamIB d = (DamIB) obj;
        
            // check field-by-field on ALL fields
            if ( (d.getName().equals(this.name)) && 
                 (d.getYear() == this.year) &&
                 (Math.abs(d.getStorage() - this.storage) <= TOL ) &&
                 (Math.abs(d.getCapacity() - this.capacity) <= TOL ) &&
                 (Math.abs(d.getInflow() - this.inflow) <= TOL ) &&
                 (Math.abs(d.getOutflow() - this.outflow) <= TOL ) &&
                 (d.getDate().equals(this.date) )) {
                return true;
            }
            else {
                return false;
            }
        }
    }//end equals 
        
    // derived data accessors----------
    
    // returns the age of the dam as of today 
    public int getAge() {
        return UtilsIB.getAge(new CS12Date(1,1, year));
    }
    
    // returns the current status of the dam (filling, empting, holding), based upon net water flow rates 
    public String getStatus() {
        if (inflow > outflow ) {
            return "filling"; 
        }
        else if (inflow < outflow) {
            return "emptying";
        }
        else 
            return "holding";   
    }
    
    // returns the current percentage of full capacity for the dam
    public double getPercentFull() {
        if (capacity <= MIN_CAPACITY) {          // preventing division by 0.0 (defaulf capacity)
            return 0.0;                          // in such case 0.0 will be returned
        }
        else
            return (storage / capacity) * 100;  
    }
        
    // returns the WHOLE number of days until a dam "event" (overflow or draining completely), rounded DOWN to the nearest integer. 
    // if the dam is "holding", simply return a -1  
    public int getEventDays() {
        double time;
        if (inflow > outflow) {                  // "filling" case
            // calculating the number of seconds till owerflow and converting seconds into days  
            time =(capacity - storage) / (inflow - outflow) * (CUFT_PER_ACREFT / (SECS_PER_HR * HRS_PER_DAY));      
            return (int)Math.round(time-0.5);    // rounding down the number of days, -0.5 allows always to round down  
        }
        else if (inflow < outflow){              // "empting case
            // calculating the number of seconds till complete draining and converting seconds into days  
            time = (0 - storage) / (inflow - outflow) * (CUFT_PER_ACREFT / (SECS_PER_HR * HRS_PER_DAY));
            return (int)Math.round(time-0.5);    // rounding down the number of days, -0.5 allows always to round down 
        }
        else  
            return -1;                           // "holding" case; if inflow = outflow, returns -1
    }    
    
    // returns the actual calendar date of a dam "event" (overflow or draining comletely).
    // if the dam is "holding", returns an arbitrary date obviously far out into the future (i.e. 100 years away)
    public CS12Date getEventDate() {
        // creating a "trowaway" copy of date field-by-field for safe returning the object  
        CS12Date temp = new CS12Date();
        temp.setMonth(this.date.getMonth());
        temp.setDay(this.date.getDay());
        temp.setYear(this.date.getYear());
        if (inflow - outflow == 0.0) {             // checking if inflow = outflow
            temp.setYear(date.getYear() + 100);    // if inflow = outflow, returns date +100 years since measurement date 
            return temp;
        }
        else {
            temp.laterDate(getEventDays());        // calculates actual date of draining of the dam or overflow
            return temp;
        }
    }
    
    
    // utility methods-----------------
    
    // allows the user to update ALL instance variables, using prompted user input
    public void update(boolean mode) {
        setName(mode);
        setYear(mode);
        setCapacity(mode);
        setStorage(mode);
        setInflow(mode);
        setOutflow(mode);
        setDate(mode);
    }
    
    // imports a specified amount of water, and increases the storage by that amount.
    // checks to make sure that import amount is >=0.0, AND will not overflow dam 
    public void importWater(double acreFeet) {
        if (acreFeet < MIN_IMPORT_WATER) {                // if amount of imported water < 0.0, print error message 
            System.out.println("ERROR: can't import a negative amount, import value must be >= 0.0");
        }
        else if (acreFeet >= (capacity - storage)) {      // if amount of imported water > than available room, print error message 
            System.out.println("ERROR: unable to import that amount without overflowing dam, no import");            
        }
        else
            storage = storage + acreFeet;                 // calculating a new storage after importing some amount of water
    }
    
    // releases a specified amount of water and decreases the storage by that amount
    // checks to make sure that release amount >=0.0, AND will not empty dam
    public void releaseWater(double acreFeet) {
        if (acreFeet < MIN_RELEASE_WATER) {               // if amount of released water < 0.0; print error message 
            System.out.println("ERROR: can't release a negative amount, release value must be >= 0.0");
        }
        else if (acreFeet >= storage) {                   // amount of released water will empty dam; print error message
            System.out.println("ERROR: unable to release that amount without draining the dam, no release");            
        }
        else
            storage = storage - acreFeet;                 // calculating a new storage after releasing some amount of water
    } 
    
    // increases the outflow by a specified additional amount
    // checks to make sure increase amount is >=0.0
    public void increaseOutflow(double cuFtSec) {
        if (storage == MIN_STORAGE) {                     // if dam is empty, it's impossible to increase outflow
            System.out.println("ERROR: The dam is empty, can't increase outflow");
        }
        else
            if (cuFtSec < MIN_OUTFLOW) {                  // cannot increase outflow by negative number   
                System.out.println("ERROR: increase in outflow must be >= 0.0, not changed");          
            }
            else
                outflow = outflow + cuFtSec;              // calculating a new outflow after its increasing
    }
    
    // decrease the outflow by a specified lessened amount
    // checks to make sure decrease amount is >=0.0, AND will not go below zero
    public void decreaseOutflow(double cuFtSec) {
        if (storage == MIN_STORAGE) {                     // if dam is empty, it's impossible to decrease outflow
            System.out.println("ERROR: The dam is empty, can't decrease outflow");
        }
        else
            if (cuFtSec < MIN_OUTFLOW) {                  // cannot decrease outflow by negative number  
                System.out.println("ERROR: decrease in outflow must be >= 0.0, not changed");              
            }
            else if (outflow - cuFtSec < MIN_STORAGE){    // outflow cannot drop below 0.0 
                System.out.println("ERROR: unable to reduce outflow below 0, not changed");                
            }
            else
                outflow = outflow - cuFtSec;            // calculating a new outflow after its decreasing 
    }
    
    
    // unit test code------------------
    
    // test driver for this class  
      
    public static void main (String [] arg){
    
        // creating Dam objects with each of 4 constructors
        DamIB d1 = new DamIB();                  // default constructor
        DamIB d2 = new DamIB("Folsom", 1978, 100.00, 200.00, 50.00, 20.00, new CS12Date()); // full constructor
        DamIB d3 = new DamIB("Folsom", 1978);    // constructor with specified name and opening year
        DamIB d4 = new DamIB("Folsom", 200.00);  // constructor with specified name and capacity
        
        // displaying object created with default constructor
        d1.print("Object created with default constructor");
        System.out.println("toString: " + d1);     // implicit toString() 
        System.out.println();
        UtilsIB.pause();
        
        // displaying object created with full constructor 
        d2.print("Object created with full constructor");
        System.out.println("toString: " + d2);     // implicit toString() 
        System.out.println();
        UtilsIB.pause();
        
        // displaying object created with constructor with specified name and opening year
        d3.print("Object created with constructor with specified name and opening year");
        System.out.println("toString: " + d3);     // implicit toString() 
        System.out.println();
        UtilsIB.pause();
        
        // displaying object created with constructor with specified name and capacity        
        d4.print("Object created with constructor with specified name and capacity");
        System.out.println("toString: " + d4);     // implicit toString() 
        System.out.println();
        UtilsIB.pause();
        
        // test accessor methods upon object created with full constructor
        System.out.println("Testing of accessor methods upon object created with full constructor");
        System.out.println("d2 name = "     + d2.getName());
        System.out.println("d2 year = "     + d2.getYear());
        System.out.println("d2 storage = "  + d2.getStorage());
        System.out.println("d2 capacity = " + d2.getCapacity());
        System.out.println("d2 inflow = "   + d2.getInflow());
        System.out.println("d2 outflow = "  + d2.getOutflow());
        System.out.println("d2 date = "     + d2.getDate());
        System.out.println();
        UtilsIB.pause();
        
        //test mutator methods upon object created with full constructor
        d2.setName("Oroville");
        d2.setYear(1990);
        d2.setStorage(20000.99);
        d2.setCapacity(3000.00);
        d2.setInflow(550.55);
        d2.setOutflow(330.55);
        d2.setDate(new CS12Date());
        d2.print("d2 after mutator");
        System.out.println();
        UtilsIB.pause();
        
        // test prompting mutator methods upon object created with full constructor
        d2.setName(false);
        d2.setYear(false);
        d2.setStorage(false);
        d2.setCapacity(false);
        d2.setInflow(false);
        d2.setOutflow(false);
        d2.setDate(false);
        System.out.println();
        d2.print("d2 after mutator prompts");
        System.out.println();
        UtilsIB.pause();
        
        // test equality
        
        System.out.println("Testing the equality of two objects");
                 
        // test one dam against any other dam (should be false)
        System.out.println("d1 equals d2? (should be false)\t\t" + d1.equals(d2));
        
        // test one dam against itself (should be true)
        System.out.println("d2 equals d2? (should be true)\t\t" + d2.equals(d2));
        
        // test one dam against some other type of object (should be false) 
        String temp = new String ("testing");
        System.out.println("d2 equals temp? (should be false)\t" + d2.equals(temp));
       
   
    } // end main
    
} // end class