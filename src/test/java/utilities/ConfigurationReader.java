package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Properties;
import java.util.Set;

public class ConfigurationReader {

    private static Properties properties = new Properties();
    static private final String[] paths = {
            "src/test/resources/configuration/configuration.properties",
            "src/test/resources/configuration/secrets.properties"
    };

    static {
        try {
            for(String path: paths){
                Properties propertiesFromEachConfigFile = new Properties();
                FileInputStream input = new FileInputStream(path);
                propertiesFromEachConfigFile.load(input);
                input.close();
                properties.putAll(propertiesFromEachConfigFile);
            }

        } catch(FileNotFoundException e){

            System.out.println("Configuration Reader FILES NOT FOUND ERROR" +
                    "\n\tGeneral config: \"src/test/resources/configuration/configuration.properties\"" +
                    "\n\tSecrets config: \"src/test/resources/configuration/secrets.properties\"" +
                    "\n\t\tNOTE: Add \"secrets.properties\" to gitIgnore in order to avoid committing passwords");

        } catch (IOException e) {
            System.out.println(e.getMessage() + " not found");
        }
    }

    public static String getProperty(String keyName){
        return properties.getProperty(keyName);
    }
}