package UI;
import FlightOperation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Flight extends FlightDistance {

    //        ************************************************************ Fields ************************************************************

    private final String flightSchedule;
    private final String flightNumber;
    private final String origin;
    private final String arrivalTime;
    private final String gate;
    private final String destination;
    private double distanceInMiles;
    private double distanceInKm;
    private String flightTime;
    private int numOfSeatsInTheFlight;
    private List<Customer> listOfRegisteredCustomersInAFlight;
    private int customerIndex;
    private static int nextFlightDay = 0;
    private static final List<Flight> flightList = new ArrayList<>();

    //        ************************************************************ Behaviours/Methods ************************************************************

    public Flight() {
        this.flightSchedule = null;
        this.flightNumber = null;
        this.arrivalTime=null;
        this.numOfSeatsInTheFlight = 0;
        origin = null;
        destination = null;
        this.gate = null;
    }

    /**
     * Creates new random flight from the specified arguments.
     *
     * @param flightSchedule           includes departure date and time of flight
     * @param flightNumber             unique identifier of each flight
     * @param numOfSeatsInTheFlight    available seats in the flight
     * @param origin       consists of origin airports(cities)
     * @param destination       consists of destination airports(cities)
     * @param distanceBetweenTheCities gives the distance between the airports both in miles and kilometers
     * @param gate                     from where passengers will board to the aircraft
     */
    Flight(String flightSchedule, String flightNumber, int numOfSeatsInTheFlight, String origin,String destination, String distanceBetweenTheCities, String gate) {
        this.flightSchedule = flightSchedule;
        this.flightNumber = flightNumber;
        this.numOfSeatsInTheFlight = numOfSeatsInTheFlight;
        this.origin = origin;
        this.destination = destination;
        this.distanceInMiles = Double.parseDouble(distanceBetweenTheCities);
        this.flightTime = calculateFlightTime(distanceInMiles);
        this.listOfRegisteredCustomersInAFlight = new ArrayList<>();
        this.gate = gate;
        this.arrivalTime=null;
    }
    Flight(String flightSchedule, String flightNumber, int numOfSeatsInTheFlight, String origin,String destination, String distance, String gate,String arrivalTime)
    {
        this.flightSchedule = flightSchedule;
        this.flightNumber = flightNumber;
        this.numOfSeatsInTheFlight = numOfSeatsInTheFlight;
        this.origin = origin;
        this.destination = destination;
        this.distanceInMiles = Double.parseDouble(distance);
        this.arrivalTime = arrivalTime;
        this.listOfRegisteredCustomersInAFlight = new ArrayList<>();
        this.gate = gate;
    }

    /**
     * Creates Flight Schedule. All methods of this class are collaborating with each other
     * to create flight schedule of the said length in this method.
     */
    public void flightScheduler() {
        int numOfFlights = 15;              // decides how many unique flights to be included/display in scheduler
        RandomGenerator r1 = new RandomGenerator();
        for (int i = 0; i < numOfFlights; i++) {
            String[][] chosenDestinations = r1.randomDestinations();
            String[] distanceBetweenTheCities = calculateDistance(Double.parseDouble(chosenDestinations[0][1]), Double.parseDouble(chosenDestinations[0][2]), Double.parseDouble(chosenDestinations[1][1]), Double.parseDouble(chosenDestinations[1][2]));
            String flightSchedule = createNewFlightsAndTime();
            String flightNumber = r1.randomFlightNumbGen(2, 1).toUpperCase();
            int numOfSeatsInTheFlight = r1.randomNumOfSeats();
            String gate = r1.randomFlightNumbGen(1, 30);
            flightList.add(new Flight(flightSchedule, flightNumber, numOfSeatsInTheFlight, chosenDestinations[0][0],chosenDestinations[1][0], distanceBetweenTheCities[0], gate.toUpperCase()));
        }



    }

    /**
     * Registers new Customer in this Flight.
     *
     * @param customer customer to be registered
     */
    public void addNewCustomerToFlight(Customer customer) {
        this.listOfRegisteredCustomersInAFlight.add(customer);
    }

    /**
     * Adds numOfTickets to existing customer's tickets for the this flight.
     *
     * @param customer     customer in which tickets are to be added
     * @param numOfTickets number of tickets to add
     */
    void addTicketsToExistingCustomer(Customer customer, int numOfTickets) {
        customer.addExistingFlightToCustomerList(customerIndex, numOfTickets);
    }

    /***
     * Checks if the specified customer is already registered in the FLight's array list
     * @param customersList of the flight
     * @param customer specified customer to be checked
     * @return true if the customer is already registered in the said flight, false otherwise
     */
    public boolean isCustomerAlreadyAdded(List<Customer> customersList, Customer customer) {
        boolean isAdded = false;
        for (Customer customer1 : customersList) {
            if (customer1.getUserID().equals(customer.getUserID())) {
                isAdded = true;
                customerIndex = customersList.indexOf(customer1);
                break;
            }
        }
        return isAdded;
    }

    /**
     * Calculates the flight time, using avg. ground speed of 450 knots.
     *
     * @param distanceBetweenTheCities distance between the cities/airports in miles
     * @return formatted flight time
     */
    public String calculateFlightTime(double distanceBetweenTheCities) {
        double groundSpeed = 450;
        double time = (distanceBetweenTheCities / groundSpeed);
        String timeInString = String.format("%.4s", time);
        String[] timeArray = timeInString.replace('.', ':').split(":");
        int hours = Integer.parseInt(timeArray[0]);
        int minutes = Integer.parseInt(timeArray[1]);
        int modulus = minutes % 5;
        // Changing flight time to make minutes near/divisible to 5.
        if (modulus < 3) {
            minutes -= modulus;
        } else {
            minutes += 5 - modulus;
        }
        if (minutes >= 60) {
            minutes -= 60;
            hours++;
        }
        if (hours <= 9 && Integer.toString(minutes).length() == 1) {
            return String.format("0%s:%s0", hours, minutes);
        } else if (hours <= 9 && Integer.toString(minutes).length() > 1) {
            return String.format("0%s:%s", hours, minutes);
        } else if (hours > 9 && Integer.toString(minutes).length() == 1) {
            return String.format("%s:%s0", hours, minutes);
        } else {
            return String.format("%s:%s", hours, minutes);
        }
    }

    /**
     * Creates flight arrival time by adding flight time to flight departure time
     *
     * @return flight arrival time
     */
    public String fetchArrivalTime() {
        /*These lines convert the String of flightSchedule to LocalDateTIme and add the arrivalTime to it....*/
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a ");
        LocalDateTime departureDateTime = LocalDateTime.parse(flightSchedule, formatter);

        /*Getting the Flight Time, plane was in air*/
        String[] flightTime = getFlightTime().split(":");
        int hours = Integer.parseInt(flightTime[0]);
        int minutes = Integer.parseInt(flightTime[1]);


        LocalDateTime arrivalTime;

        arrivalTime = departureDateTime.plusHours(hours).plusMinutes(minutes);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("EE  dd-MM-yyyy HH:mm a");
        return arrivalTime.format(formatter1);

    }

    void deleteFlight(String flightNumber) {
        boolean isFound = false;
        Iterator<Flight> list = flightList.iterator();
        while (list.hasNext()) {
            Flight flight = list.next();
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                isFound = true;
                break;
            }
        }
        if (isFound) {
            list.remove();
            try {
                FileWriter writer=new FileWriter("data/FlightList.csv");
                writer.write("Day,Date,Hour,FLIGHT NO,Available Seats,FROM,TO,ARRIVAL TIME,GATE,DISTANCE(MILES),"+"\n");
                for (Flight flight : flightList) {
                    if(flight.arrivalTime!=null)
                      writer.write(flight.toString()+"\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Flight with given Number not found...");
        }
        displayFlightSchedule();
    }
    void addFlight(String day,String date,String hour,String flightNumber,int seats,String origin,String destination,String arrivalTime,String gate,String distance)
    {
       flightList.add(new Flight(day+" , "+date+" , "+hour,flightNumber,seats,origin,destination,distance,gate,arrivalTime));
    }
    void addNewFlight(String origin , String destination,int seats,String flightSchedule,String gate,String distance,String arrivalTime) {
        boolean isFound = false;
        Iterator<Flight> list = flightList.iterator();
        while (list.hasNext()) {
            Flight flight = list.next();
            if (flight.getDestination().equalsIgnoreCase(destination)&&flight.getOrigin().equalsIgnoreCase(origin)&&flight.getSeats()==seats&&flight.getFlightSchedule().equalsIgnoreCase(flightSchedule)&&flight.getGate().equalsIgnoreCase(gate)) {
                isFound = true;
                break;
            }
        }
        if (isFound) {
            System.out.println("This flight already existed");
        } else {
            RandomGenerator r1 = new RandomGenerator();
            String flightNumber = r1.randomFlightNumbGen(2, 1).toUpperCase();
            Flight temp = new Flight(flightSchedule, flightNumber, seats, origin, destination, distance, gate, arrivalTime);
            flightList.add(temp);
            try {
                FileWriter writer=new FileWriter("data/FlightList.csv",true);
                writer.append(temp.toString()+"\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Calculates the distance between the cities/airports based on their lat longs.
     *
     * @param lat1 origin city/airport latitude
     * @param lon1 origin city/airport longitude
     * @param lat2 destination city/airport latitude
     * @param lon2 destination city/airport longitude
     * @return distance both in miles and km between the cities/airports
     */
    @Override
    public String[] calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double distance = Math.sin(degreeToRadian(lat1)) * Math.sin(degreeToRadian(lat2)) + Math.cos(degreeToRadian(lat1)) * Math.cos(degreeToRadian(lat2)) * Math.cos(degreeToRadian(theta));
        distance = Math.acos(distance);
        distance = radianToDegree(distance);
        distance = distance * 60 * 1.1515;
        /* On the Zero-Index, distance will be in Miles, on 1st-index, distance will be in KM and on the 2nd index distance will be in KNOTS*/
        String[] distanceString = new String[3];
        distanceString[0] = String.format("%.2f", distance * 0.8684);
        distanceString[1] = String.format("%.2f", distance * 1.609344);
        distanceString[2] = Double.toString(Math.round(distance * 100.0) / 100.0);
        return distanceString;
    }

    private double degreeToRadian(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double radianToDegree(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void displayFlightSchedule() {
        Iterator<Flight> flightIterator = flightList.iterator();
        System.out.println();
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+---------------------------------+\n");
        System.out.printf("| Num  | FLIGHT SCHEDULE                           | FLIGHT NO | Available Seats  |   FROM ====>>         |   ====>> TO            |  ARRIVAL TIME             |  GATE       |   DISTANCE(MILES/KMS)           |%n");
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+---------------------------------+\n");
        int i = 0;
        while (flightIterator.hasNext()) {
            i++;
            Flight f1 = flightIterator.next();
            System.out.println(f1.toString(i));
             System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+---------------------------------+\n");
        }
    }

    @Override
    public String toString(int i) {
        if(arrivalTime==null)
            return String.format("| %-5d| %-41s | %-9s |  %-15s | %-21s | %-22s | %-10s  |  %-9s  |  %-30s |", i, flightSchedule, flightNumber, numOfSeatsInTheFlight, origin, destination, fetchArrivalTime(), gate, distanceInMiles);
        else
            return String.format("| %-5d| %-41s | %-9s |  %-15s | %-21s | %-22s | %-10s  |  %-9s  |  %-30s |", i, flightSchedule, flightNumber, numOfSeatsInTheFlight, origin, destination, arrivalTime, gate, distanceInMiles);
    }
    @Override
    public String toString()
    {
        return String.format(flightSchedule+","+flightNumber+","+numOfSeatsInTheFlight+","+origin+","+destination+","+arrivalTime+","+gate+","+distanceInMiles+",");
    }

    /**
     * Creates new random flight schedule
     *
     * @return newly created flight schedule
     */
    public String createNewFlightsAndTime() {

        Calendar c = Calendar.getInstance();
        // Incrementing nextFlightDay, so that next scheduled flight would be in the future, not in the present
        nextFlightDay += Math.random() * 7;
        c.add(Calendar.DATE, nextFlightDay);
        c.add(Calendar.HOUR, nextFlightDay);
        c.set(Calendar.MINUTE, ((c.get(Calendar.MINUTE) * 3) - (int) (Math.random() * 45)));
        Date myDateObj = c.getTime();
        LocalDateTime date = Instant.ofEpochMilli(myDateObj.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        date = getNearestHourQuarter(date);
        return date.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a "));
    }

    /**
     * Formats flight schedule, so that the minutes would be to the nearest quarter.
     *
     * @param datetime to be formatting
     * @return formatted LocalDateTime with minutes close to the nearest hour quarter
     */
    public LocalDateTime getNearestHourQuarter(LocalDateTime datetime) {
        int minutes = datetime.getMinute();
        int mod = minutes % 15;
        LocalDateTime newDatetime;
        if (mod < 8) {
            newDatetime = datetime.minusMinutes(mod);
        } else {
            newDatetime = datetime.plusMinutes(15 - mod);
        }
        newDatetime = newDatetime.truncatedTo(ChronoUnit.MINUTES);
        return newDatetime;
    }


    //        ************************************************************ Setters & Getters ************************************************************

    public int getNoOfSeats() {
        return numOfSeatsInTheFlight;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setNoOfSeatsInTheFlight(int numOfSeatsInTheFlight) {
        this.numOfSeatsInTheFlight = numOfSeatsInTheFlight;
    }

    public void setFlightTime(String flightTime){this.flightTime=flightTime;}

    public String getFlightTime() {
        return flightTime;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public List<Customer> getListOfRegisteredCustomersInAFlight() {
        return listOfRegisteredCustomersInAFlight;
    }

    public String getFlightSchedule() {
        return flightSchedule;
    }

    public String getOrigin() {
        return origin;
    }

    public int getSeats() {
        return numOfSeatsInTheFlight;
    }

    public String getGate() {
        return gate;
    }

    public String getDestination() {
        return destination;
    }

}