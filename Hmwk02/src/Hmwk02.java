import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

    /*
    @AUTHOR: Elliot Fortney
    This program takes a zip code and a distance in miles from a user. It then returns all the cities that are within the given distance of the specified zip code.
    The program will then sort the cities that are returned based on the population of each city, allowing the user to quickly see the most populated areas.

    DIFFERENCES FROM ASSIGNMENT
    This program will likely result in some flakey totals for population. This is due to the faulty data given in the zips2 database. Instead of re-writing the program to
    handle a new database, I modified my SQL using group by and Distinct modifiers. This is currently affecting any city that has multiple zip-codes.
    */


public class Hmwk02 {

    //=====GLOBAL CONNECTION=====//
    static String host = "jdbc:mysql://turing.cs.missouriwestern.edu/misc";
    static String user = "csc254";
    static String password = "age126";
    int port = 3306;

    static Connection con;
    static Statement st;
    static ResultSet rs;


    public static void main(String[] args) {
        //scanner variable for main
        Scanner scan = new Scanner(System.in);
        City primaryCity = new City();
        City close = new City();
        close.setDistance(Double.MAX_VALUE);
        ArrayList<City>allCities = new ArrayList<>();
        ArrayList<City>citiesInRange = new ArrayList<>();// create a place to store all cities that are in the given radius
        //grab the zip-code and then the distance from the user
        int userZip = getUserZipCode(scan);
        int userDistance = findDistance(scan);
        double userLat = setUserLat(userZip);
        double userLong = setUserLong(userZip);
        primaryCity.lat = userLat;
        primaryCity.longitude = userLong;

        buildCities(allCities);                          // build cities from the query result
        removeOutliers(allCities,citiesInRange,primaryCity,userDistance);   // add all cities to the array that are within the given distance
        sortResults(citiesInRange);                     // sort the cities based on their population

        // find the closest city
        for(int i = 0 ; i < citiesInRange.size(); i++) {
            if(citiesInRange.get(i).getDistance() < close.getDistance()) {
                close = citiesInRange.get(i);
            }
        }

        //====== Print a summary of important details to the user =====/
        System.out.println("||========== SUMMARY ==========||");
        // tell the user how many cities are at risk
        System.out.printf("There are %d cities at risk \n", citiesInRange.size());
        // tell the user which city is the most populated
        System.out.println(citiesInRange.get(0).cityName + " is the city with the largest population at " + citiesInRange.get(0).getPop());
        // tell the user which city is the closest
        System.out.printf("%s is the closest city, %f miles from origin city. It has a population of %f\n",close.getName(),close.getDistance(),close.getPop());
        System.out.println();
        System.out.println("||==========MASS DATA==========||");
        for(City city : citiesInRange) {
            double distanceM = distance(primaryCity.lat,primaryCity.longitude,city.lat,city.longitude,"M"); // get the distance in miles
            double distanceK = distance(primaryCity.lat,primaryCity.longitude,city.lat,city.longitude,"K"); // get the distance in kilometers
            city.printData();
            System.out.printf("The distance between %s and the starting location is %f miles (%f kilometers)\n", city.getName(),distanceM,distanceK);
        }


    } // end of main


    // sort the arraylist and return cities from largest to smallest based on pop
    public static void sortResults(ArrayList<City> cities) {
        City smallest;
        int swap = 1;      // set this var to 1 to make sure that the while loop runs at least one time
        while(swap == 1) {
            swap = 0;      // now set it to zero, perform comparison between students
            for(int i = 0; i < cities.size() - 1; i++) {
                switch (cities.get(i).compareTo(cities.get(i+1))) {
                    // the cities are in the right order, do nothing
                    case 1:
                        break;
                    // the city has a larger pop
                    case -1:
                        smallest = cities.get(i);
                        cities.set(i, cities.get(i+1));
                        cities.set(i+1, smallest);
                        swap = 1;
                        break;

                    default:
                        // equal cities require no action
                }
            }
        }
    }// end of sortLarge




    /*
 * Gets the longitude of the city requested, based on zipcode.
 * params:
 *  zip: int, the zip of the given city
 */
    public static double setUserLong(int zip) {
        double result = 0;
        try {
            try {
                con = (Connection) DriverManager.getConnection(host, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String queryString = String.format("Select `long` from zips2 WHERE zipcode = %d ",zip);
            st = (Statement) con.createStatement();
            rs = st.executeQuery(queryString);
            if(rs.next()){
                result = rs.getDouble(1) ;
            }else{
                result = 0.00;
                System.out.println("zipcode wasnt found");
            }
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    /*
* Gets the latitude of the city requested, based on zipcode.
* params:
*  zip: int, the zip of the given city
*/
    public static double setUserLat(int zip) {
        double result = 0;
        try {
            try {
                con = (Connection) DriverManager.getConnection(host, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String queryString = String.format("Select `lat` from zips2 WHERE zipcode = %d ", zip);
            st = (Statement) con.createStatement();
            rs = st.executeQuery(queryString);
            if (rs.next()) {
                result = rs.getDouble(1);
            } else {
                result = 0.00;
                System.out.println("zipcode wasnt found");
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /*
     * calculate the distance between the two cities, if the distance is > the radius given by user, delete them from the list
     */
    static void removeOutliers(ArrayList<City> cities,ArrayList<City> newList, City primary, int radius) {
        for(City city : cities) {
            double distance = distance(primary.lat,primary.longitude,city.lat,city.longitude,"M");
            if(distance <= radius) {
                city.setDistance(distance);
                newList.add(city);
            }
        }
    }

    /*
    build the cities and add them to an array list
     */
    static void buildCities(ArrayList<City> cities) {
        City result;
        try {
            try {
                con = (Connection) DriverManager.getConnection(host, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String queryString = ("Select city, lat, `long`, `taxreturnsfiled`, `estimatedpopulation`,state,zipcode  from zips2 where country = 'US' and estimatedpopulation > 0 and `locationtype` = 'PRIMARY' group by state, city order by city;");
            st = (Statement) con.createStatement();
            rs = st.executeQuery(queryString);
            while(rs.next()){
                result = new City();
                result.setCityName(rs.getString(1));
                result.setLat(rs.getDouble(2));
                result.setLongitude(rs.getDouble(3));
                result.setHousing(rs.getInt(4));
                result.setPop(rs.getInt(5));
                result.setState(rs.getString(6));
                cities.add(result);
            }
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // calculate the distance between the two cities
    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    // functions to convert from radians
    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    public static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    // get the user entered zip code and welcome them to the program, return as an int
    private static int getUserZipCode(Scanner scan) {
        System.out.println("Please enter a valid 5 digit zip code");
        int zip;
        //prevent the user from entering any value that is longer or greater than 5
        do {
            zip = 0;
            zip = scan.nextInt();
            if (String.valueOf(zip).length() != 5) {
                System.out.printf("Please enter a valid 5 digit zip code ");
            }
        }while(String.valueOf(zip).length() != 5);

        return zip;
    }

    /*
     * Gets the distance that the user wants to check from the set city
     *  params:
     *      scan: scanner object to read from the user input
     */
    private static int findDistance(Scanner scan) {
        int distance;
        distance = 0;
        //prevent the user from entering a negative value for distance
        do {
            try {
                System.out.println("Please enter the radius as a whole, positive integer.");
                distance = scan.nextInt(); //attempt to get the distance, if there is a String or a negative number, force user to reenter data
            }catch(InputMismatchException e) {
                System.out.println("Invalid input");
            }
            scan.nextLine(); //clear the buffer
        }while(distance <= 0);

        return distance;
    }

}
