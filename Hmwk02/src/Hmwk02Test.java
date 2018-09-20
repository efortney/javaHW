import org.junit.Test;

import static org.junit.Assert.*;

public class Hmwk02Test {

    //=====TEST VARS=====//
    static City gower = new City();
    static City pBurg = new City();

    @Test
   public void testDistance() {
        gower.setLongitude(-94.59);
        gower.setLat(39.61);
        pBurg.setLat(39.56);
        pBurg.setLongitude(-94.46);
        // test miles
        assertEquals(7.736141,Hmwk02.distance(gower.lat,gower.longitude,pBurg.lat,pBurg.longitude, "M"),0.00001);
        // test kilometers
        assertEquals(12.450112,Hmwk02.distance(gower.lat,gower.longitude,pBurg.lat,pBurg.longitude, "K"),0.00001);
    }






}