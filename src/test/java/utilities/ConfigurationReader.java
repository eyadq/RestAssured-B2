package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ConfigurationReader {

    private static Properties allProperties;
    static private final String configuration = "src/test/resources/configuration/configuration.properties";
    static private final String secrets = "src/test/resources/configuration/secrets.properties";


    static {
        try {
            List<String> paths = new ArrayList<>();
            paths.add(configuration);
            paths.add(secrets);

            List<Properties> propertiesList = new ArrayList<>();
            for(String path: paths){
                Properties properties = new Properties();
                FileInputStream input = new FileInputStream(path);
                properties.load(input);
                input.close();
                propertiesList.add(properties);
            }

            allProperties = new Properties();
            for(Properties properties: propertiesList){
                allProperties.putAll(properties);
            }

        } catch(FileNotFoundException e){
            if(e.getMessage().startsWith("secrets")){
                System.out.println("*** " + e.getMessage() + " remember to add file to .gitIgnore" + " ***");
            } else {
                System.out.println("*** " + e.getMessage() + " ADD SENSITIVE DATA TO \"secrets.properties ONLY\"" + " ***");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " not found");
        }
    }

    public static String getProperty(String keyName){
        return allProperties.getProperty(keyName);
    }

    private static Set<String> getAllProperties(){
        return allProperties.stringPropertyNames();
    }
}