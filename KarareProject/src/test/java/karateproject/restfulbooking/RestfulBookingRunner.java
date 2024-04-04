package karateproject.restfulbooking;
import com.intuit.karate.junit5.Karate;

public class RestfulBookingRunner {

    @Karate.Test
    Karate testRestfulBooking() {
        return Karate.run("restfulbooking").relativeTo(getClass());
    }

}
