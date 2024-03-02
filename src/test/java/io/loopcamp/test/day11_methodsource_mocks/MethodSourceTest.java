package io.loopcamp.test.day11_methodsource_mocks;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class MethodSourceTest {

    public static List<String> getCountries(){
        List<String> countries = Arrays.asList("Canada", "USA", "France", "Azerbaijan");
        return countries;
    }

    @ParameterizedTest
    @MethodSource("getCountries")
    public void countriesTest(String eachCountry){
        System.out.println("eachCountry = " + eachCountry);
        System.out.println("Number of letters of country = " + eachCountry.length());
    }
}
