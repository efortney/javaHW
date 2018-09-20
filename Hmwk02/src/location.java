/*
Abstract class location. From it the class City inherits all methods, allowing the user to perform sorts and compare and contrast data of all
objects that extend location.
This could be a city, a business, a country, etc...
 */

abstract class Location {

    abstract double getLong();
    abstract double getLat();
    abstract int getHousing();
    abstract double getPop();

}
