package FlightOperation;

import java.util.Random;

public class RandomGenerator {

    //        ************************************************************ Fields ************************************************************

    private String randomNum;
    /*  City name is at the 0-index, its latitude is on the 1-index and longitude on the 2-index*/
    private static final String[][] destinations = {
            {"Tehran", "24.871940", "66.988060"}, {"Mashhad", "13.921430", "100.595337"}, {"Shiraz", "-6.174760", "106.827072"},
            {"Zahedan", "33.607587", "73.100316"}, {"Yazd", "40.642422", "-73.781749"}, {"Isfahan", "31.521139", "74.406519"},
            {"Tabriz", "35.919108", "74.332838"}, {"Kerman", "21.683647", "39.152862"}, {"Ahvaz", "24.977080", "46.688942"}, {"Rasht", "28.555764", "77.096520"},
            {"Karaj", "22.285005", "114.158339"}, {"Qom", "40.052121", "116.609609"}, {"Kermanshah", "35.550899", "139.780683"}, {"Bandar Abbas", "2.749914", "101.707160"},
            {"Zanjan", "-33.942028", "151.174304"}, {"Sanandaj", "-37.671812", "144.846079"}, {"Qazvin", "-33.968879", "18.596982"}, {"Khorramabad", "40.476938", "-3.569428"},
            {"Gorgan", "53.424077", "-6.256792"}, {"Sari", "25.936834", "27.925890"}
    };

    //        ************************************************************ Behaviours/Methods ************************************************************


    /* Generates Random ID for the Customers....*/
    public void randomIDGen() {
        Random rand = new Random();
        String randomID = Integer.toString(rand.nextInt(1000000));

        while (Integer.parseInt(randomID) < 20000) {
            randomID = Integer.toString(rand.nextInt(1000000));
        }
        setRandomNum(randomID);
    }

    /*This method sets the destinations for each of the flights from the above destinations randomly.....*/
    public String[][] randomDestinations() {
        Random rand = new Random();
        int randomCity1 = rand.nextInt(destinations.length);
        int randomCity2 = rand.nextInt(destinations.length);
        String fromWhichCity = destinations[randomCity1][0];
        String fromWhichCityLat = destinations[randomCity1][1];
        String fromWhichCityLong = destinations[randomCity1][2];
        while (randomCity2 == randomCity1) {
            randomCity2 = rand.nextInt(destinations.length);
        }
        String toWhichCity = destinations[randomCity2][0];
        String toWhichCityLat = destinations[randomCity2][1];
        String toWhichCityLong = destinations[randomCity2][2];
        String[][] chosenDestinations = new String[2][3];
        chosenDestinations[0][0] = fromWhichCity;
        chosenDestinations[0][1] = fromWhichCityLat;
        chosenDestinations[0][2] = fromWhichCityLong;
        chosenDestinations[1][0] = toWhichCity;
        chosenDestinations[1][1] = toWhichCityLat;
        chosenDestinations[1][2] = toWhichCityLong;
        return chosenDestinations;
    }

    /*Generates the Random Number of Seats for each flight*/
    public int randomNumOfSeats() {
        Random random = new Random();
        int numOfSeats = random.nextInt(500);
        while (numOfSeats < 75) {
            numOfSeats = random.nextInt(500);
        }
        return numOfSeats;
    }

    /*Generates the Unique Flight Number....*/
    public String randomFlightNumbGen(int uptoHowManyLettersRequired, int divisible) {
        Random random = new Random();
        StringBuilder randomAlphabets = new StringBuilder();
        for (int i = 0; i < uptoHowManyLettersRequired; i++) {
            randomAlphabets.append((char) (random.nextInt(26) + 'a'));
        }
        randomAlphabets.append("-").append(randomNumOfSeats() / divisible);
        return randomAlphabets.toString();
    }

    //        ************************************************************ Setters & Getters ************************************************************

    public void setRandomNum(String randomNum) {
        this.randomNum = randomNum;
    }

    public String getRandomNumber() {
        return randomNum;
    }
}
