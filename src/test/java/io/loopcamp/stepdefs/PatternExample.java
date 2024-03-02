package io.loopcamp.stepdefs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternExample {

    public static void main(String[] args) {

        String path = "/api/minions/{minionId}";

        Pattern pattern = Pattern.compile("\\{*\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(path);
        if(matcher.find())
            System.out.println(matcher.group(1));

        System.out.println(matcher.matches());
    }
}
