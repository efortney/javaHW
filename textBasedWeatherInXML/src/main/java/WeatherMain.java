import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import java.io.IOException;

public class WeatherMain {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        //get the user city
        String city = getUserCity(scan);
        //get the user state
        String state = getUserState(scan);
        //create a url to search database
        String url = Credentials.getUrl() + state + "/" + city + ".xml";
        //connect to a web page and then connect to the database
        Document webpage = Jsoup.connect(url).get();
        Document doc = Jsoup.parse(webpage.toString(), "", Parser.xmlParser());
        checkValidity(doc);
        //create an arraylist to store the elemental results from the doc
        List<Element> results = new ArrayList<Element>();

        //grab the elements from the document and add them to the list
        results.add(getTemp(doc));
        results.add(getSkyCondition(doc));
        results.add(getWind(doc));
        results.add(getPrecip(doc));
        results.add(getFullState(doc));

        printData((ArrayList<Element>) results);
    }

    /**
     * Prints the data out to the end user
     * @param list: arraylist {Element}
     */
    private static void printData(ArrayList<Element> list) {
        System.out.println("Requested city: " + list.get(4).text());
        System.out.println("The current temperature is: " + list.get(0).text() + " fahrenheit");
        System.out.println("Sky conditions look to be " + list.get(1).text());
        System.out.println("Wind is " + list.get(2).text());
        System.out.println("The total rain accumulated today is "  + list.get(3).text());
    }

    /**
     * Get the full state and city out of the returned document.
     * @param doc document parsed
     * @return result: Jsoup element
     */
    private static Element getFullState(Document doc) {
        Elements full = doc.select("full");
        Element result;
        result = full.get(0);
        //System.out.println(result.text());

        return result;
    }


    /**
     * Get the rain conditions out of the returned document.
     * @param doc document parsed
     * @return result: Jsoup element
     */
    private static Element getPrecip(Document doc) {
        Elements precip = doc.select("precip_today_in");
        Element result;
        result = precip.get(0);
        //System.out.println(result.text());

        return result;
    }

    /**
     * Get the wind conditions out of the returned document.
     * @param doc document parsed
     * @return result: Jsoup element
     */
    private static Element getWind(Document doc) {
        Elements wind = doc.select("wind_string");
        Element result;
        result = wind.get(0);
        //System.out.println(result.text());

        return result;
    }


    /**
     * Get the sky conditions out of the returned document.
     * @param doc document parsed
     * @return result: Jsoup element
     */
    private static Element getSkyCondition(Document doc) {
        Elements sky = doc.select("weather");
        Element result;
        result = sky.get(0);
       //System.out.println(result.text());

        return result;
    }

    /**
     * Checks to make sure that a user entered a correct query. If not, it will exit the program
     * and prompt the user to restart.
     * @param doc document being parsed
     * @return boolean isValid
     */
    private static boolean checkValidity(Document doc) {
        boolean isValid = false;
        Elements temp = doc.select("temp_f");
        if(temp.size() >= 1){
            isValid = true;
        }
        else{
            System.out.println("Oops! It looks like the city you're searching for doesn't exist. Check your spelling and try again.");
            System.exit(1);
        }

        return isValid;
    }

    /**
     * Get the temperature out of the returned document.
     * @param doc document parsed
     * @return result: Jsoup element
     */
    private static Element getTemp(Document doc) {
        Elements temp = doc.select("temp_f");
        Element result;
        result = temp.get(0);
        //System.out.println(result.text());

        return result;
    }

    /**
     * Retrieves the users desired city to search for
     * @param scan scanner
     * @return result
     */
    private static String getUserCity(Scanner scan) {
        System.out.println("Please enter the city you would like to search for: ");
        String result = scan.nextLine().toLowerCase();
        result = changeSpace(result);
        return result;
    }


    /**
     * Retrieves the users desired state to search for
     * @param scan scanner
     * @return result
     */
    private static String getUserState(Scanner scan) {
        System.out.println("Please enter the state ABBREVIATION you would like to search for (MO for Missouri, CA for California): ");
        String result = scan.next().toLowerCase();
        return result;
    }


    /**
     * Removes a white space from a string and converts it to an underscore: _
     * @param stringToChange: {String}
     */
    private static String changeSpace(String stringToChange) {
        String result;
        result = stringToChange.replaceAll(" ","_");
        return result;
    }


}
