package utilities;

public class StringUtil {

    public static String replaceVariableInsideCurlyBraces(String path, Object targetVariable){
        String sub = path.substring(path.indexOf("{"));
        path = path.replace(sub, String.valueOf(targetVariable));
        return path;
    }
}
