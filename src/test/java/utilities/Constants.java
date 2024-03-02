package utilities;

public class Constants {
     public static final String MINIONS_BASE_URL = ConfigurationReader.getProperty("minions.api.url");
     public static final String HR_BASE_URL = ConfigurationReader.getProperty("hr.api.url");
     public static final String JSON_PLACE_HOLDER_POSTS_BASE = "https://jsonplaceholder.typicode.com/posts";
     public static final String JSON_PLACE_HOLDER_COMMENTS_BASE = "https://jsonplaceholder.typicode.com/comments";
}
