/*
 * Name:       Rob Lapkass
 *
 * Course:     CS-12
 *
 * Date:       06/02/15
 *
 * Filename:   CS12Date.java
 *
 * Purpose:    Provides a more richly-featured version
 *             of the (former textbook) SimpleDate.java.
 *             Intended to be full back-compatible with that class.
 *
 * History:    RNL 10/02/16: Updated print() to avoid tabbing, 
 *             problems being observed in Fall 2016 with output tabbing
 *             between jGRASP versions.
 *
 *             RNL 03/07/17: Fixed some javadoc problems involving 
 *             self-closing tag </br> with apparently newer HTML,
 *             and also several logic symbols, replaced then with HTML sequences.
 */

import java.util.Calendar;
import java.lang.String;

/**
 * Implements a M/D/Y user-annotated date with multiple date utilities,
 * for use in CS-12 programs.
 */
public class CS12Date {

    //=================================================================
    // instance variables
    //=================================================================
    private int month;     // must be 1-12
    private int day;       // must be 1-31 and correct for current month
    private int year;      // must be > 0, always stored as YYYY        
    private String text;   // optional descriptive text      
    
    //=================================================================
    // constructors
    //=================================================================
    
    /**
     * Default constructor, initializes to current date, with no annotation.
     */
    public CS12Date() {
        Calendar today = Calendar.getInstance();
        
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH) + 1;
        day = today.get(Calendar.DATE);
        text = "";
    }
    
    /**
     * Full constructor, allows user-specification of all fields,
     * any invalid numeric data defaults to that of the current date.
     * @param month the desired month
     * @param day the desired day
     * @param year the desired year
     * @param text descriptive text for the desired date
     */
    public CS12Date(int month, int day, int year, String text) {
        this();  // pull in defaults for all fields (current date)
        
        // if numeric values are invalid, defaults will be retained
        setYear(year);
        setMonth(month);
        setDay(day);      
        this.text = text;
    }

    /**
     * Alternate constructor, sets month, day, year only,
     * any invalid numeric data defaults to that of the current date.
     * @param month the desired month
     * @param day the desired day
     * @param year the desired year
     */
    public CS12Date(int month, int day, int year) {
        this();  // pull in defaults for all fields (current date)
        
        // if numeric values are invalid, defaults will be retained
        setYear(year);
        setMonth(month);
        setDay(day);     
    }
    
    /**
     * Alternate constructor, sets date to month and day of current year,
     * any invalid numeric data defaults to that of the current date.
     * @param month the desired month
     * @param day the desired day
     */
    public CS12Date(int month, int day) {
        this();  // pull in defaults for all fields (current date)
        
        // if numeric values are invalid, defaults will be retained
        Calendar today = Calendar.getInstance();
        
        setYear(today.get(Calendar.YEAR));
        setMonth(month);
        setDay(day);
    }

    /**
     * Alternate constructor, sets date to Jan 1st of specified year,
     * invalid year defaults to the current year.
     * @param year the desired year
     */
    public CS12Date(int year) {
        this();  // pull in defaults for all fields (current date)
        
        // if numeric values are invalid, defaults will be retained
        setYear(year);
        month = 1;
        day = 1; 
    }
    
    /**
     * Alternate constructor, sets date to current date with specified annotation,
     * @param text descriptive text for the desired date
     */
    public CS12Date(String text) {
        this();  // pull in defaults for all fields (current date)
        
        // set desired annotation
        this.text = text;
    }
    //=================================================================
    // display methods
    //=================================================================
    
    /** 
     * Returns a standard M/D/Y string, along with any descriptive text, in a comma-separated format.
     * @return M/D/Y String representation of date, plus any descriptive text
     */
    public String toString() {
        return month + "/" + day + "/" + year +             // date portion
               (text.length() > 0 ? (", "  + text) : text); // text portion
    }
    
    /** 
     * Displays labeled output of all fields, plus some additional values AND toString() output.
     */
    public void print() {
        String fmtInt  = "%-16s%d\n";
        String fmtStr  = "%-16s%s\n";
        String fmtBool = "%-16s%b\n";
        
        // original version: tabbing
        //System.out.println("year:\t\t" + year);
        //System.out.println("month:\t\t" + month);
        //System.out.println("day:\t\t" + day);
        //System.out.println("text:\t\t" + text);
        //System.out.println("toString():\t" + toString());
        //System.out.println("day in year:\t" + getDateNum());
        //System.out.println("leap year?\t" + isLeapYear(year));
        
        // updated version: format specifiers (non-tabbing)
        System.out.printf(fmtInt,  "year:", year);
        System.out.printf(fmtInt,  "month:", month);
        System.out.printf(fmtInt,  "day:", day);
        System.out.printf(fmtStr,  "text:", text);
        System.out.printf(fmtStr,  "toString():", toString());
        System.out.printf(fmtInt,  "day in year:", getDateNum());
        System.out.printf(fmtBool, "leap year?", isLeapYear(year));
    }
    
    /** 
     * Overloaded print, adds some additional user-specified message text.
     * @param message message to be prepended to print()
     */
    public void print(String message) {
        spacer();
        System.out.println(message);
        spacer();
        print();
        spacer();
    }
    
    // accessors/mutators ----------------------------------------------
    
    /**
     * year mutator, sets desired year, checks that year is A.D.
     * @param year desired year (YYYY)
     */
    public void setYear(int year) {
        if (year < 0) {
            error("year must be >= 0, does not handle B.C., not changed");
        }
        else {
            this.year = year;
        }       
    }
    
    /**
     * year accessor, returns year
     * @return currently specified year
     */
    public int getYear() {
        return year;
    }
    
    /**
     * month mutator, sets desired year, performs 1-12 checking on month
     * @param month desired month
     */
    public void setMonth(int month) {
        if ((month < 1) || (month > 12)) {
            error("month must be 1-12, not changed");
        }
        else {
            this.month = month;
        }
    }
    
    /**
     * month accessor, returns month
     * @return month currently specified month
     */
    public int getMonth() {
        return month;
    }
    
    /**
     * day mutator, sets desired day, checks whether day is valid for existing month/year
     * @param day desired day
     */
    public void setDay(int day) {
        int maxDays = daysInMonth(this.month, this.year);
        if ((day < 1) || (day > maxDays)) {
            error("day must be 1-" + maxDays + ", not changed");                
        }
        else {
            this.day = day;
        }
    }
    
    /**
     * day accessor, returns currently specified day
     * @return day currently specified day
     */
    public int getDay() {
        return day;
    }
    
    /**
     * text mutator, sets an (optional) user-specified text descriptor for the date
     * @param text desired text
     */
    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * text accessor, returns (optional) currently specified text
     * @return text currently specified text
     */
    public String getText() {
        return text;
    }
    
    // derived data accessors ------------------------------------------
    
    /**
     * Returns the ordered date number within the year (1-365 or 1-366)
     * @return day number within the current year
     */
    public int getDateNum() {
        int dateNum = 0;
        
        for (int i=1; i<month; i++) {
            dateNum += daysInMonth(i, this.year);
        }
        dateNum += day;
        
        return dateNum;
    }
      
    // specific date mutators ------------------------------------------
      
    /**
     * Sets the date to a specific month/day/year date
     * @param month desired month
     * @param day desired day
     * @param year desired year (YYYY)
     */
    public void setDate(int month, int day, int year) {
        setMonth(month);
        setDay(day);
        setYear(year);
    }
    
    /**
     * Sets the date to that of a specified ordinal date number within existing year, taking into account leap year
     * @param dateNum day number within the existing year
     */
    public void setDate(int dateNum) {
        setDate(dateNum, year);
    }
    
    /**
     * Sets the date to that of a specified ordinal date number within a given year, taking into account leap year
     * @param dateNum day number within the existing year
     * @param year the desired year
     */
    public void setDate(int dateNum, int year) {
    
        CS12Date temp = new CS12Date(year);
        
        // first, error checking for invalid date numbers
        if (dateNum < 1) {
            error("date number must be >= 0, date unchanged");
            return;
        }
        else if ((!temp.isLeapYear(year)) && (dateNum > 365)) {
            error("date number must be <= 365 for a non-leap year, date unchanged");
            return;
        }
        else if ((temp.isLeapYear(year)) && (dateNum > 366)) {
            error("date number must be <= 366 for a leap year, date unchanged");
            return;
        }
        
        // otherwise, the date number is valid, just find where in the year it lies
        else {            
            setMonth(1);
            setDay(1);
            setYear(year);         
            
            // advance the date from 1/1 by N-1
            laterDate(dateNum-1);
        }
    }

    /**
     * Advances the date by one day (back compatibility wrapper)
     */
    public void nextDay() {
        // provides back compatibility with the former SimpleDate
        nextDate();
    }
    
    /**
     * Advances the date by one day (uses consistent naming scheme)
     */
    public void nextDate() {
        // same as nextDay(), uses a consistent naming scheme ("...Date")
        laterDate(1);
    }
    
    /**
     * Advances the date by the desired number of days.
     * If numDays &lt; 0, the date will REGRESS.
     * @param numDays number of data to advance (numDays &ge; 0)
     */
    public void laterDate(int numDays) {
    
        // counter
        int temp = 1;
        
        if (numDays < 0) {
            priorDate(-numDays);
        }
        else {
            while (temp <= numDays) {
                day++;
                temp++;
                
                // if date is invalid, day is too big, update month
                if (!isValidDate(month, day, year)) {
                    day = 1;
                    month++;
                }
                
                // if date is still invalid, month is too big, update month/year
                if (!isValidDate(month, day, year)) {
                    month = 1;
                    year++;
                }
                
            } // end while
        } // end else
    } // end method
    
    /**
     * Regresses the date by the desired number of days.
     * If numDays &lt; 0, the date will ADVANCE.
     * @param numDays number of data to regress (numDays &ge; 0)
     */
    public void priorDate(int numDays) {
    
        // counter
        int temp = 1;
        
        if (numDays < 0) {
            laterDate(-numDays);
        }
        
        else {
            while (temp <= numDays) {
                day--;
                temp++;
                
                // if date is invalid, day is too small, update month
                if (!isValidDate(month, day, year)) {
                    month--;
                    day = daysInMonth(month, year);
                }
                
                // if date is still invalid, month is too small, update month/year
                if (!isValidDate(month, day, year)) {
                    month = 12;
                    year--;
                    day = 31;
                }
                
            } // end while
        }
    }
    
    // equivalence and comparison --------------------------------------
    
    /**
     * Determines the equality of a CS12Date against another one, or any other object.
     * Text fields must also be equal for two objects to be equal.
     * @param obj generic object to be compared
     * @return equality status
     */
    public boolean equals(Object obj) {
        if (obj instanceof CS12Date) {
            // good object, so cast it
            CS12Date temp = (CS12Date) obj;
            
            // check data field-by-field
            if ((this.getYear() == temp.getYear()) &&
                (this.getMonth() == temp.getMonth()) &&
                (this.getDay() == temp.getDay()) &&
                (this.getText().equals(temp.getText())) ) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            // object is not a CS12Date, so can't compare
            return false;
        }
    }
    
    /**
     * Compares two CS12Date objects date-wise. <br>
     * If result = 0, the two dates are the same <br>
     * If result &gt; 0, date2 is later in time than current date <br>
     * If result &lt; 0, date2 is earlier in time than the current date
     * @param date2 the CS12Date which is to be compared
     * @return comparison result
     */
    public int compare(CS12Date date2) {
    
        int result;
        int y1 = this.year;
        int y2 = date2.getYear();
        int m1 = this.month;
        int m2 = date2.getMonth();
        int d1 = this.day;
        int d2 = date2.getDay();
        
        // all fields are the same: dates are identical
        if ((y1 == y2) && (m1 == m2) && (d1 == d2)) {
            result = 0;
        }
        
        // years differ
        else if (y1 < y2) {
            result = 1;
        }
        else if (y1 > y2) {
            result = -1;
        }
        
        // months differ
        else if (m1 < m2) {
            result = 1;
        }
        else if (m1 > m2) {
            result = -1;
        }
        
        // days differ
        else if (d1 < d2) {
            result = 1;
        }
        else {
            result = -1;
        }
        
        return result;
    }

    // private utility methods -----------------------------------------
    
    /**
     * Checks T/F whether provided data parameters constitute a valid date.
     * Intended for private internal use of this class.
     * @param mm Month parameter to be checked
     * @param dd Day parameter to be checked
     * @param yyyy Year parameter to be checked
     * @return validity status of the date
     */
    private boolean isValidDate(int mm, int dd, int yyyy) {
                
        // first check for arguments validity
        if ((mm < 1) || (mm > 12)) {
            return false;
        }
        else if (yyyy < 1) {
            return false;
        }
        else if ((dd < 1) || (dd > daysInMonth(mm, yyyy))) {
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     * Checks whether the year is a leap year
     * @param yyyy Year to be checked
     * @return flag telling whether the year is a leap year
     */
    private boolean isLeapYear(int yyyy) {
        if ((yyyy % 4) > 0) {
            // definitely not a leap year
            return false;
        }
        else if ( ((yyyy % 100) == 0) && ((yyyy % 400) > 0) ) {
            // simple divisible by 100 is not enough,
            // 1800 and 1900 are NOT leap years
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     * Returns the number of days in a given month of a given year.
     * @param mm Month to be checked
     * @param yyyy Year to be checked
     * @return numDays Number of days in the given month/year
     */
    private int daysInMonth(int mm, int yyyy) {
        int numDays;
        
        // determine days in a given month
        switch (mm) {
            // "... all the rest have 31"
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                numDays = 31;
                break;
                
            // "30 days hath September, April, June, and November..."
            case 4:
            case 6:
            case 9:
            case 11:
                numDays = 30;
                break;
            
            // February
            case 2:
                numDays = (isLeapYear(yyyy) ? 29 : 28);
                break;
                
            default:
                // should never get here for this internal method
                numDays = 0;
                break;
        }
        return numDays;
    }
    
    /**
     * Prints a user-defined spacer line to stdout.
     * Intended for private internal use of this class.
     * @param ch character to be used for the spacer
     * @param num width of the spacer, in characters
     */
    private void spacer(char ch, int num) {
        for (int i=0; i<num; i++) {
            System.out.print(ch);
        }
        System.out.println();
    }
    
    /**
     * Prints a default spacer ('=' x40) to stdout.
     * Intended for private internal use of this class.
     */
    private void spacer() {
        final char SEP_CHAR = '=';   // default separator character
        final int SEP_NUM = 40;      // default separator width
        
        spacer(SEP_CHAR, SEP_NUM);
    }
    
    /**
     * Prints a user-supplied message in a standard error format.
     * Intended for private internal use of this class.
     * @param message error message to be displayed
     */
    private void error(String message) {
        System.out.println("ERROR: " + message);
    }
    
} // end class