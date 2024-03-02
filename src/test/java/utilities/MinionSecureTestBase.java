package utilities;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public class MinionSecureTestBase {


    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("minions.secure.api.url");
    }
}
