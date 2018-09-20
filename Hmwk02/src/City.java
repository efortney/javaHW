/*
City object. Determined from the database and query results.
Creating cities as an object allows for us to perform comparisons on cities in order to
make decisions based on the available data.
 */
public class City{

    //=====VARS=====//
    String cityName;  //name of the city
    double lat;       //latitude of the city
    double longitude; //longitude of the city
    int pop;       //population of the city
    int housing;   //the total amount of housing units in the zip
    double distance;            // the distance from original city
    int zipCode;
    String state;


    //=====CONSTRUCTOR=====//
    public City(){

    }


    String getName() {
        return cityName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setHousing(int housing) {
        this.housing = housing;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public void setZip(int zipCode){this.zipCode = zipCode;}

    public void setDistance(double distance) {
        this.distance = distance;
    }

    double getLong() {
        return this.longitude;
    }

    double getLat() {
        return this.lat;
    }

    int getHousing() {
        return this.housing;
    }

    double getPop() {
        return this.pop;
    }

    int getZipCode(){
        return this.zipCode;
    }

    String getState() {
        return this.state;
    }

    public double getDistance() {
        return this.distance;
    }

    public void printData() {
        System.out.printf("%s, %s has a population of %d. Its longitude is %f, latitude is %f. The total number of housing units is %d\n",cityName, state, pop, longitude, lat, housing);
    }

    // sort cities on population from largest to smallest
    public int compareTo(City o) {
        if(pop > o.pop){
            return 1;
        }
        else if(pop < o.pop){
            return -1;
        }
        else{
            return 0;
        }
    }


} //end of city
