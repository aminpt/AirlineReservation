/*
 * this project uploaded on https://github.com/aminpt/AirlineReservation.git
 */
/*
 * This class is intended to be the main class for this Project. All necessary methods are getting calls from this class.
 *
 *
 */
package UI;
import Control.*;
import FlightOperation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {

    //        ************************************************************ Fields ************************************************************

    /*2D Array to store admin credentials. Default credentials are stored on [0][0] index. Max num of admins can be 10....*/
    protected static String[][] adminUserNameAndPassword = new String[10][2];
    private static List<Customer> customersCollection = new ArrayList<>();

    //        ************************************************************ Behaviours/Methods ************************************************************

    public static void main(String[] args) {
        int countNumOfUsers = 1;

        /**
         * @param adminUserNameAndPassword with information in AdminList.csv
         */
        try {
            Scanner sc=new Scanner(new File("data/AdminList.csv"));
            sc.useDelimiter(",");
            int i=0;
            String input;
            sc.next();
            sc.next();
            String[] splited;
            while (sc.hasNext())
            {
                input=sc.next();
                if(i%2==0) {
                    splited = input.split("\n");
                    if(splited.length!=2)
                        break;
                    adminUserNameAndPassword[countNumOfUsers][0] = splited[1];
                }
                else {
                    adminUserNameAndPassword[countNumOfUsers][1] = input;
                    countNumOfUsers++;
                }
                i++;

            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Flight f1 = new Flight();
        /**
         * @param flightList with information in FlightList.csv
         */

        try {
            Scanner sc=new Scanner(new File("data/FlightList.csv"));
            sc.useDelimiter(",");
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            String input;
            String day=null;
            String date=null;
            String hour=null;
            String flightNo=null;
            String origin=null;
            String destination=null;
            String arrivalTime=null;
            String gate=null;
            String distance=null;
            int seats = 0;
            int i=0;
            String[] splited;
            while(sc.hasNext())
            {
                input=sc.next();
                switch (i){
                    case 0:
                       splited=input.split("\n");
                       if(splited.length!=2)
                           break;
                       day=splited[1];
                       break;
                    case 1:
                        date=input;
                        break;
                    case 2:
                        hour=input;
                        break;
                    case 3:
                        flightNo=input;
                        break;
                    case 4:
                        seats= Integer.parseInt(input);
                         break;
                    case 5:
                        origin=input;
                        break;
                    case 6:
                        destination=input;
                        break;
                    case 7:
                        arrivalTime=input;
                        break;
                    case 8:
                        gate=input;
                        break;
                    case 9:
                        i=-1;
                        distance=input;
                        f1.addFlight(day,date,hour,flightNo,seats,origin,destination,arrivalTime,gate,distance);
                        break;

                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /**
         * @param customerList with information in CustomerList.csv
         */
        Customer c1 = new Customer();
        try {
            Scanner sc=new Scanner(new File("data/CustomerList.csv"));
            sc.useDelimiter(",");
            int i=0;
            String input;
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            String id = null;
            String name=null;
            int age=0;
            String email=null;
            String address=null;
            String password=null;
            String phoneNumber=null;
            String[] splited;
            while (sc.hasNext())
            {
                input=sc.next();
                switch (i) {
                    case 0:
                       splited = input.split("\n");
                       if(splited.length!=2)
                           break;
                        id = splited[1];
                        break;
                    case 1:
                        name= input;
                        break;
                    case 2:
                        age= Integer.parseInt(input);
                        break;
                    case 3:
                        email=input;
                        break;
                    case 4:
                        address=input;
                        break;
                    case 5:
                        phoneNumber=input;
                        break;
                    case 6:
                        password=input;
                        i=-1;
                        c1.addCustomer(id,name,age,email,address,phoneNumber,password);
                        break;
                }
              i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RolesAndPermissions r1 = new RolesAndPermissions();
        FlightReservation bookingAndReserving = new FlightReservation();
        f1.flightScheduler();
        Scanner read = new Scanner(System.in);
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t+++++++++++++ Welcome to Amin AirLines +++++++++++++\n\nTo Further Proceed, Please enter a value.");
        System.out.println("\n***** Default Username && Password is amin-amin ***** Using Default Credentials will restrict you to just view the list of Passengers....\n");
        displayMainMenu();
        int desiredOption = read.nextInt();
        while (desiredOption < 0 || desiredOption > 8) {
            System.out.print("ERROR!! Please enter value between 0 - 4. Enter the value again :\t");
            desiredOption = read.nextInt();
        }


        do {
            Scanner read1 = new Scanner(System.in);
            /* If desiredOption is 1 then call the login method.... if default credentials are used then set the permission
             * level to standard/default where the user can just view the customer's data...if not found, then return -1, and if
             * data is found then show the user display menu for adding, updating, deleting and searching users/customers...
             * */
            switch (desiredOption) {
                case 1 :

                    /*Default username and password....*/
                    adminUserNameAndPassword[0][0] = "amin";
                    adminUserNameAndPassword[0][1] = "amin";
                    System.out.print("\nEnter the UserName to login to the Management System :     ");
                    String username =null;
                    username=read1.nextLine();
                    System.out.print("Enter the Password to login to the Management System :    ");
                    String password = null;
                    password=read1.nextLine();
                    System.out.println();

                    /*Checking the RolesAndPermissions......*/
                    if (r1.isPrivilegedUserOrNot(username, password) == -1) {
                        System.out.printf("\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... Try Creating New Credentials or get yourself register by pressing 4....\n", "");
                    } else if (r1.isPrivilegedUserOrNot(username, password) == 0) {
                        System.out.println("You've default privileges to access the data... You can just view customers data..." + "Can't perform any actions on them....");
                        c1.displayCustomersData(true);
                    } else {
                        System.out.printf("%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....", "", username);

                        /*Going to Display the CRUD operations to be performed by the privileged user.....Which includes Creating, Updating
                         * Reading(Searching) and deleting a customer....
                         * */
                        do {
                            System.out.printf("\n\n%-60s+++++++++ 2nd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "", "", username);
                            System.out.printf("%-30s (a) Enter 1 to add new Passenger....\n", "");
                            System.out.printf("%-30s (b) Enter 2 to search a Passenger....\n", "");
                            System.out.printf("%-30s (c) Enter 3 to update the Data of the Passenger....\n", "");
                            System.out.printf("%-30s (d) Enter 4 to delete a Passenger....\n", "");
                            System.out.printf("%-30s (e) Enter 5 to Display all Passengers....\n", "");
                            System.out.printf("%-30s (f) Enter 6 to Display all flights registered by a Passenger...\n", "");
                            System.out.printf("%-30s (g) Enter 7 to Display all registered Passengers in a Flight....\n", "");
                            System.out.printf("%-30s (h) Enter 8 to Delete a Flight....\n", "");
                            System.out.printf("%-30s (h) Enter 9 to Add a Flight....\n", "");
                            System.out.printf("%-30s (i) Enter 0 to Go back to the Main Menu/Logout....\n", "");
                            System.out.print("Enter the desired Choice :   ");
                            desiredOption = read.nextInt();
                            /*If 1 is entered by the privileged user, then add a new customer......*/
                            if (desiredOption == 1) {
                                c1.addNewCustomer();
                            } else if (desiredOption == 2) {
                                /*If 2 is entered by the privileged user, then call the search method of the Customer class*/
                                c1.displayCustomersData(false);
                                System.out.print("Enter the CustomerID to Search :\t");
                                String customerID = read1.nextLine();
                                System.out.println();
                                c1.searchUser(customerID);
                            } else if (desiredOption == 3) {
                                /*If 3 is entered by the user, then call the update method of the Customer Class with required
                                 * arguments.....
                                 * */
                                c1.displayCustomersData(false);
                                System.out.print("Enter the CustomerID to Update its Data :\t");
                                String customerID = read1.nextLine();
                                if (customersCollection.size() > 0) {
                                    c1.editUserInfo(customerID);
                                } else {
                                    System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID);
                                }

                            } else if (desiredOption == 4) {
                                /*If 4 is entered, then ask the user to enter the customer id, and then delete
                                 * that customer....
                                 * */
                                c1.displayCustomersData(false);
                                System.out.print("Enter the CustomerID to Delete its Data :\t");
                                String customerID = read1.nextLine();
                                if (customersCollection.size() > 0) {
                                    c1.deleteUser(customerID);
                                } else {
                                    System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID);
                                }
                            } else if (desiredOption == 5) {
                                /*Call the Display Method of Customer Class....*/
                                c1.displayCustomersData(false);
                            } else if (desiredOption == 6) {
                                c1.displayCustomersData(false);
                                System.out.print("\n\nEnter the ID of the user to display all flights registered by that user...");
                                String id = read1.nextLine();
                                bookingAndReserving.displayFlightsRegisteredByOneUser(id);
                            } else if (desiredOption == 7) {
                                System.out.print("Do you want to display Passengers of all flights or a specific flight.... 'Y/y' for displaying all flights and 'N/n' to look for a" +
                                        " specific flight.... ");
                                char choice = read1.nextLine().charAt(0);
                                if ('y' == choice || 'Y' == choice) {
                                    bookingAndReserving.displayRegisteredUsersForAllFlight();
                                } else if ('n' == choice || 'N' == choice) {
                                    f1.displayFlightSchedule();
                                    System.out.print("Enter the Flight Number to display the list of passengers registered in that flight... ");
                                    String flightNum = read1.nextLine();
                                    bookingAndReserving.displayRegisteredUsersForASpecificFlight(flightNum);
                                } else {
                                    System.out.println("Invalid Choice...No Response...!");
                                }
                            } else if (desiredOption == 8) {
                                f1.displayFlightSchedule();
                                System.out.print("Enter the Flight Number to delete the flight : ");
                                String flightNum = read1.nextLine();
                                f1.deleteFlight(flightNum);

                            } else if (desiredOption == 9) {
                                System.out.print("Enter the origin : ");
                                String origin=read1.nextLine();
                                System.out.println("Enter the destination : ");
                                String destination=read1.nextLine();
                                System.out.println("Enter number of seats : ");
                                int seats=read1.nextInt();
                                read1.nextLine();
                                System.out.println("Enter the flight schedule : ");
                                String flightSchedule=read1.nextLine();
                                System.out.println("Enter the arrival time : ");
                                String arrivalTime=read1.nextLine();
                                System.out.println("Enter the flight gate : ");
                                String gate=read1.nextLine();
                                System.out.println("Enter the distance between origin and destination in miles : ");
                                String distance=read1.nextLine();
                                f1.addNewFlight(origin,destination,seats,flightSchedule,gate,distance,arrivalTime);

                            } else if (desiredOption == 0) {
                                System.out.println("Thanks for Using BAV Airlines Ticketing System...!!!");

                            } else {
                                System.out.println("Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
                                desiredOption = 0;
                            }

                        } while (desiredOption != 0);

                    }
                    break;
                case 2 :
                    /*If desiredOption is 2, then call the registration method to register a user,UserName contains either number or letters,Password contains any characters......*/
                    boolean isCorrect=false;
                    username=null;
                    while (isCorrect==false){
                       System.out.print("\nEnter the UserName to Register :    ");
                       username = read1.nextLine();
                       if(username.matches("[a-zA-Z0-9]+"))
                           isCorrect=true;
                       else
                           System.out.println("Invalid form of UserName,Please enter UserName again :    ");
                    }
                    System.out.print("Enter the Password to Register :     ");
                    password = read1.nextLine();
                    while (r1.isPrivilegedUserOrNot(username, password) != -1) {
                        System.out.print("ERROR!!! Admin with same UserName already exist. Enter new UserName:   ");
                        username = read1.nextLine();
                        System.out.print("Enter the Password Again:   ");
                        password = read1.nextLine();
                    }

                    /*Setting the credentials entered by the user.....*/
                    adminUserNameAndPassword[countNumOfUsers][0] = username;
                    adminUserNameAndPassword[countNumOfUsers][1] = password;
                    try {
                        FileWriter writer=new FileWriter("AdminList.csv",true);
                        writer.append(username+","+password+","+"\n");
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    /*Incrementing the numOfUsers */
                     countNumOfUsers++;
                     break;
                case 3:
                    System.out.print("\n\nEnter the Email to Login : \t");
                    String userName = read1.nextLine();
                    System.out.print("Enter the Password : \t");
                    password = read1.nextLine();
                    String[] result = r1.isPassengerRegistered(userName, password).split("-");

                    if (Integer.parseInt(result[0]) == 1) {
                        int desiredChoice;
                        System.out.printf("\n\n%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....", "", userName);
                        do {
                            System.out.printf("\n\n%-60s+++++++++ 3rd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "", "", userName);
                            System.out.printf("%-40s (a) Enter 1 to Book a flight....\n", "");
                            System.out.printf("%-40s (b) Enter 2 to update your Data....\n", "");
                            System.out.printf("%-40s (c) Enter 3 to delete your account....\n", "");
                            System.out.printf("%-40s (d) Enter 4 to Display Flight Schedule....\n", "");
                            System.out.printf("%-40s (e) Enter 5 to Cancel a Flight....\n", "");
                            System.out.printf("%-40s (f) Enter 6 to Display all flights registered by \"%s\"....\n", "", userName);
                            System.out.printf("%-40s (g) Enter 0 to Go back to the Main Menu/Logout....\n", "");
                            System.out.print("Enter the desired Choice :   ");
                            desiredChoice = read.nextInt();
                            if (desiredChoice == 1) {
                                f1.displayFlightSchedule();
                                System.out.print("\nEnter the desired flight number to book :\t ");
                                String flightToBeBooked = read1.nextLine();
                                System.out.print("Enter the Number of tickets for " + flightToBeBooked + " flight :   ");
                                int numOfTickets = read.nextInt();
                                while (numOfTickets > 10) {
                                    System.out.print("ERROR!! You can't book more than 10 tickets at a time for single flight....Enter number of tickets again : ");
                                    numOfTickets = read.nextInt();
                                }
                                bookingAndReserving.bookFlight(flightToBeBooked, numOfTickets, result[1]);
                            } else if (desiredChoice == 2) {
                                c1.editUserInfo(result[1]);
                            } else if (desiredChoice == 3) {
                                System.out.print("Are you sure to delete your account...It's an irreversible action...Enter Y/y to confirm...");
                                char confirmationChar = read1.nextLine().charAt(0);
                                if (confirmationChar == 'Y' || confirmationChar == 'y') {
                                    c1.deleteUser(result[1]);
                                    System.out.printf("User %s's account deleted Successfully...!!!", userName);
                                    desiredChoice = 0;
                                } else {
                                    System.out.println("Action has been cancelled...");
                                }
                            } else if (desiredChoice == 4) {
                                f1.displayFlightSchedule();
                            } else if (desiredChoice == 5) {
                                bookingAndReserving.cancelFlight(result[1]);
                            } else if (desiredChoice == 6) {
                                bookingAndReserving.displayFlightsRegisteredByOneUser(result[1]);
                            } else {
                                if (desiredChoice != 0) {
                                    System.out.println("Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
                                }
                                desiredChoice = 0;
                            }
                        } while (desiredChoice != 0);

                    } else {
                        System.out.printf("\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... Try Creating New Credentials or get yourself register by pressing 4....\n", "");
                    }
                 break;
                case 4:
                    c1.addNewCustomer();
                break;
                case 5:
                    manualInstructions();
                break;

            }
            displayMainMenu();
            desiredOption = read1.nextInt();
            while (desiredOption < 0 || desiredOption > 8) {
                System.out.print("ERROR!! Please enter value between 0 - 4. Enter the value again :\t");
                desiredOption = read1.nextInt();
            }

        } while (desiredOption != 0);

    }

    static void displayMainMenu() {
        System.out.println("\n\n\t\t(a) Press 0 to Exit.");
        System.out.println("\t\t(b) Press 1 to Login as admin.");
        System.out.println("\t\t(c) Press 2 to Register as admin.");
        System.out.println("\t\t(d) Press 3 to Login as Passenger.");
        System.out.println("\t\t(e) Press 4 to Register as Passenger.");
        System.out.println("\t\t(f) Press 5 to Display the User Manual.");
        System.out.print("\t\tEnter the desired option:    ");
    }

    static void manualInstructions() {
        Scanner read = new Scanner(System.in);
        System.out.printf("%n%n%50s %s Welcome to BAV Airlines User Manual %s", "", "+++++++++++++++++", "+++++++++++++++++");
        System.out.println("\n\n\t\t(a) Press 1 to display Admin Manual.");
        System.out.println("\t\t(b) Press 2 to display User Manual.");
        System.out.print("\nEnter the desired option :    ");
        int choice = read.nextInt();
        while (choice < 1 || choice > 2) {
            System.out.print("ERROR!!! Invalid entry...Please enter a value either 1 or 2....Enter again....");
            choice = read.nextInt();
        }
        if (choice == 1) {
            System.out.println("\n\n(1) Admin have the access to all users data...Admin can delete, update, add and can perform search for any customer...\n");
            System.out.println("(2) In order to access the admin module, you've to get yourself register by pressing 2, when the main menu gets displayed...\n");
            System.out.println("(3) Provide the required details i.e., name, email, id...Once you've registered yourself, press 1 to login as an admin... \n");
            System.out.println("(4) Once you've logged in, 2nd layer menu will be displayed on the screen...From here on, you can select from variety of options...\n");
            System.out.println("(5) Pressing \"1\" will add a new Passenger, provide the program with required details to add the passenger...\n");
            System.out.println("(6) Pressing \"2\" will search for any passenger, given the admin(you) provides the ID from the table printing above....  \n");
            System.out.println("(7) Pressing \"3\" will let you update any passengers data given the user ID provided to program...\n");
            System.out.println("(8) Pressing \"4\" will let you delete any passenger given its ID provided...\n");
            System.out.println("(9) Pressing \"5\" will let you display all registered passenger...\n");
            System.out.println("(10) Pressing \"6\" will let you display all registered passengers...After selecting, program will ask, if you want to display passengers for all flights(Y/y) or a specific flight(N/n)\n");
            System.out.println("(11) Pressing \"7\" will let you delete any flight given its flight number provided...\n");
            System.out.println("(11) Pressing \"0\" will make you logged out of the program...You can login again any time you want during the program execution....\n");
        } else {
            System.out.println("\n\n(1) Local user has the access to its data only...He/She won't be able to change/update other users data...\n");
            System.out.println("(2) In order to access local users benefits, you've to get yourself register by pressing 4, when the main menu gets displayed...\n");
            System.out.println("(3) Provide the details asked by the program to add you to the users list...Once you've registered yourself, press \"3\" to login as a passenger...\n");
            System.out.println("(4) Once you've logged in, 3rd layer menu will be displayed...From here on, you embarked on the journey to fly with us...\n");
            System.out.println("(5) Pressing \"1\" will display available/scheduled list of flights...To get yourself booked for a flight, enter the flight number and number of tickets for the flight...Max num of tickets at a time is 10 ...\n");
            System.out.println("(7) Pressing \"2\" will let you update your own data...You won't be able to update other's data... \n");
            System.out.println("(8) Pressing \"3\" will delete your account... \n");
            System.out.println("(9) Pressing \"4\" will display randomly designed flight schedule for this runtime...\n");
            System.out.println("(10) Pressing \"5\" will let you cancel any flight registered by you...\n");
            System.out.println("(11) Pressing \"6\" will display all flights registered by you...\n");
            System.out.println("(12) Pressing \"0\" will make you logout of the program...You can login back at anytime with your credentials...for this particular run-time... \n");
        }
    }

    //        ************************************************************ Setters & Getters ************************************************************

    public static List<Customer> getCustomersCollection() {
        return customersCollection;
    }
}