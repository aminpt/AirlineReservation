package UI;
import FlightOperation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Customer {

    //        ************************************************************ Fields ************************************************************
    private final String userID;
    private String email;
    private String name;
    private String phone;
    private final String password;
    private String address;
    private int age;
    public List<Flight> flightsRegisteredByUser;
    public List<Integer> numOfTicketsBookedByUser;
    public static final List<Customer> customerCollection = User.getCustomersCollection();

    //        ************************************************************ Behaviours/Methods ************************************************************


    Customer() {
        this.userID = null;
        this.name = null;
        this.email = null;
        this.password = null;
        this.phone = null;
        this.address = null;
        this.age = 0;
    }

    /**
     * Registers new customer to the program. Obj of RandomGenerator(Composition) is
     * being used to assign unique userID to the newly created customer.
     *
     * @param name     name of the customer
     * @param email    customer's email
     * @param password customer's account password
     * @param phone    customer's phone-number
     * @param address  customer's address
     * @param age      customer's age
     */
    Customer(String id,String name, String email, String password, String phone, String address, int age) {
        this.name = name;
        this.userID = id;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.flightsRegisteredByUser = new ArrayList<>();
        this.numOfTicketsBookedByUser = new ArrayList<>();
    }
    public void addCustomer(String id,String name,int age,String email,String address,String phone,String password)
    {
        RandomGenerator random = new RandomGenerator();
        random.randomIDGen();
        customerCollection.add(new Customer(id,name, email, password, phone, address, age));
    }
    /**
     * Takes input for the new customer and adds them to programs memory. isUniqueData() validates the entered email
     * and returns true if the entered email is already registered. If email is already registered, program will ask the user
     * to enter new email address to get himself register.
     * Name and Address contains only letters.
     * Password contains any characters.
     * Email contains ***@gmail.com.
     * Age and PhoneNumber contains only numbers.
     */
    public void addNewCustomer() {
        System.out.printf("\n\n\n%60s ++++++++++++++ Welcome to the Customer Registration Portal ++++++++++++++", "");
        Scanner read = new Scanner(System.in);
        String email=null;
        String name=null;
        String phone=null;
        String address=null;
        int age=0;
        boolean isCorrect=false;
        while (isCorrect==false)
        {
            System.out.print("\nEnter your name :\t");
            name = read.nextLine();
            if(name.matches("[a-zA-Z]+"))
                isCorrect=true;
            else
                System.out.println("Incorrect form of Name,Please try again :   ");
        }
        isCorrect=false;
        while (isCorrect==false) {
            System.out.print("Enter your email address :\t");
            email= read.nextLine();
            if(email.matches(".+@gmail.com")) {
                isCorrect = true;
            }
            else
                System.out.println("Incorrect form of Name,Please try again :   ");

        }
        isCorrect=false;
        while (isUniqueData(email)) {
            System.out.println("ERROR!!! User with the same email already exists... Use new email or login using the previous credentials....");
            while (isCorrect==false) {
                System.out.print("Enter your email address :\t");
                email= read.nextLine();
                if(email.matches(".+@gmail.com")) {
                    isCorrect = true;
                }
                else
                    System.out.println("Incorrect form of Name,Please try again :   ");

            }
        }
        System.out.print("Enter your Password :\t");
        String password = read.nextLine();
        isCorrect=false;
        while (isCorrect==false)
        {
            System.out.print("Enter your Phone number :\t");
            phone = read.nextLine();
            if(phone.matches("[0-9]+"))
                isCorrect=true;
            else
                System.out.println("Incorrect form of Phone Number , Please try again :   ");
        }
        isCorrect=false;
        while (isCorrect==false)
        {
            System.out.print("Enter your address :\t");
            address= read.nextLine();
            if(address.matches("[a-zA-Z ]+"))
                isCorrect=true;
            else
                System.out.println("Incorrect form of Address , Please try again :    ");
        }
        isCorrect=false;
        while (isCorrect==false)
        {
             System.out.print("Enter your age :\t");
             String temp = read.nextLine();
             if(temp.matches("[0-9]+")) {
                 isCorrect = true;
                 age= Integer.parseInt(temp);
             }
             else
                 System.out.println("Incorrect form of Age , Pleaas try again :    ");
        }
        RandomGenerator random = new RandomGenerator();
        random.randomIDGen();
        String id=random.getRandomNumber();
        try {
            FileWriter writer=new FileWriter("data/CustomerList.csv",true);
            writer.append(randomIDDisplay(id)+","+name+","+age+","+email+","+address+","+phone+","+password+","+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        customerCollection.add(new Customer(id, name, email, password, phone, address, age));
    }

    /**
     * Returns String consisting of customers data(name, age, email etc...) for displaying.
     * randomIDDisplay() adds space between the userID for easy readability.
     *
     * @param i for serial numbers.
     * @return customers data in String
     */
    private String toString(int i) {
        return String.format("%10s| %-10d | %-10s | %-32s | %-7s | %-27s | %-35s | %-23s |", "", i, randomIDDisplay(userID), name, age, email, address, phone);
    }

    /**
     * Searches for customer with the given ID and displays the customers' data if found.
     *
     * @param ID of the searching/required customer
     */
    public void searchUser(String ID) {
        boolean isFound = false;
        Customer customerWithTheID = customerCollection.get(0);
        for (Customer c : customerCollection) {
            if (ID.equals(c.getUserID())) {
                System.out.printf("%-50sCustomer Found...!!!Here is the Full Record...!!!\n\n\n", " ");
                displayHeader();
                isFound = true;
                customerWithTheID = c;
                break;
            }
        }
        if (isFound) {
            System.out.println(customerWithTheID.toString(1));
            System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }

    /**
     * Returns true if the given emailID is already registered, false otherwise
     *
     * @param emailID to be checked in the list
     */
    public boolean isUniqueData(String emailID) {
        boolean isUnique = false;
        for (Customer c : customerCollection) {
            if (emailID.equals(c.getEmail())) {
                isUnique = true;
                break;
            }
        }
        return isUnique;
    }

    public void editUserInfo(String ID) {
        boolean isFound = false;
        Scanner read = new Scanner(System.in);
        for (Customer c : customerCollection) {
            if (ID.equals(c.getUserID())) {
                isFound = true;
                String email=null;
                String name=null;
                String phone=null;
                String address=null;
                int age=0;
                boolean isCorrect=false;
                while (isCorrect==false)
                {
                    System.out.print("\nEnter the name of the Passenger:\t");
                    name = read.nextLine();
                    if(name.matches("[a-zA-Z]+")) {
                        isCorrect = true;
                        c.setName(name);
                    }
                    else
                        System.out.println("Incorrect form of Name,Please try again :   ");
                }
                isCorrect=false;
                while (isCorrect==false) {
                    System.out.print("Enter your email address :\t");
                    email= read.nextLine();
                    if(email.matches(".+@gmail.com")) {
                        isCorrect = true;
                    }
                    else
                        System.out.println("Incorrect form of Name,Please try again :   ");

                }
                isCorrect=false;
                while (isUniqueData(email)) {
                    System.out.println("ERROR!!! User with the same email already exists... Use new email or login using the previous credentials....");
                    while (isCorrect==false) {
                        System.out.print("Enter your email address :\t");
                        email= read.nextLine();
                        if(email.matches(".+@gmail.com")) {
                            isCorrect = true;
                        }
                        else
                            System.out.println("Incorrect form of Name,Please try again :   ");

                    }
                }
                c.setEmail(email);
                isCorrect=false;
                while (isCorrect==false)
                {
                    System.out.print("Enter your Phone number :\t");
                    phone = read.nextLine();
                    if(phone.matches("[0-9]+")) {
                        isCorrect = true;
                        c.setPhone(phone);
                    }
                    else
                        System.out.println("Incorrect form of Phone Number , Please try again :   ");
                }
                isCorrect=false;
                while (isCorrect==false)
                {
                    System.out.print("Enter your address :\t");
                    address= read.nextLine();
                    if(address.matches("[a-zA-Z ]+"))
                        isCorrect=true;
                    else
                        System.out.println("Incorrect form of Address , Please try again :    ");
                }
                c.setAddress(address);
                isCorrect=false;
                while (isCorrect==false)
                {
                    System.out.print("Enter your age :\t");
                    String temp = read.nextLine();
                    if(temp.matches("[0-9]+")) {
                        isCorrect = true;
                        age= Integer.parseInt(temp);
                    }
                    else
                        System.out.println("Incorrect form of Age , Pleaas try again :    ");
                }
                c.setAge(age);
                displayCustomersData(false);
                break;
            }
        }
        if (!isFound) {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }

    public void deleteUser(String ID) {
        boolean isFound = false;
        Iterator<Customer> iterator = customerCollection.iterator();
        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            if (ID.equals(customer.getUserID())) {
                isFound = true;
                break;
            }
        }
        if (isFound) {
            iterator.remove();
            System.out.printf("\n%-50sPrinting all  Customer's Data after deleting Customer with the ID %s.....!!!!\n", "", ID);
            displayCustomersData(false);
            try {
                FileWriter writer=new FileWriter("data/CustomerList.csv");
                writer.write("UserID,Name,Age,Email,Address,Phone Number,Password,"+"\n");
                for (Customer customer : customerCollection) {
                    writer.write(randomIDDisplay(customer.getUserID())+","+customer.getName()+","+customer.getAge()+","+customer.getEmail()+","+customer.getAddress()+","+customer.getPhone()+","+customer.getPassword()+","+"\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }

    /**
     * Shows the customers' data in formatted way.
     * @param showHeader to check if we want to print ascii art for the customers' data.
     */
    public void displayCustomersData(boolean showHeader) {

        displayHeader();
        Iterator<Customer> iterator = customerCollection.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            Customer c = iterator.next();
            System.out.println(c.toString(i));
            System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        }
    }

    /**
     * Shows the header for printing customers data
     */
    void displayHeader() {
        System.out.println();
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        System.out.printf("%10s| SerialNum  |   UserID   | Passenger Names                  | Age     | EmailID                      | Home Address                       | Phone Number            |%n", "");
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        System.out.println();

    }

    /**
     * Adds space between userID to increase its readability
     * <p>
     * Example:
     * </p>
     * <b>"920 191" is much more readable than "920191"</b>
     *
     * @param randomID id to add space
     * @return randomID with added space
     */
    public String randomIDDisplay(String randomID) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i <= randomID.length(); i++) {
            if (i == 3) {
                newString.append(" ").append(randomID.charAt(i));
            } else if (i < randomID.length()) {
                newString.append(randomID.charAt(i));
            }
        }
        return newString.toString();
    }

    /**
     * Associates a new flight with the specified customer
     *
     * @param f flight to associate
     */
    public void addNewFlightToCustomerList(Flight f) {
        this.flightsRegisteredByUser.add(f);
//        numOfFlights++;
    }

    /**
     * Adds numOfTickets to already booked flights
     * @param index at which flight is registered in the arraylist
     * @param numOfTickets how many tickets to add
     */
    public void addExistingFlightToCustomerList(int index, int numOfTickets) {
        int newNumOfTickets = numOfTicketsBookedByUser.get(index) + numOfTickets;
        this.numOfTicketsBookedByUser.set(index, newNumOfTickets);
    }


    //        ************************************************************ Setters & Getters ************************************************************

    public List<Flight> getFlightsRegisteredByUser() {
        return flightsRegisteredByUser;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getNumOfTicketsBookedByUser() {
        return numOfTicketsBookedByUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }
}