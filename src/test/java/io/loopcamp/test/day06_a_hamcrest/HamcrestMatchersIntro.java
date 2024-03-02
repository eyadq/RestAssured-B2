package io.loopcamp.test.day06_a_hamcrest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchersIntro {
    @Test
    public void numberTest(){
        assertThat(1+3, is(4));
        assertThat(1+3, equalTo(4));
        assertThat(1+3, is(equalTo(4)));
        assertThat(1+3, is(greaterThan(3))); //Junit: assertTrue(104 > 100)
    }

    @Test
    public void stringTest(){
        String word = "loopcamp";

        assertThat(word, is("loopcamp"));
        assertThat(word, equalTo("loopcamp"));
        assertThat(word, is(equalTo("loopcamp")));

        assertThat(word, startsWith("loop"));
        assertThat(word, startsWithIgnoringCase("LOOP"));

        //contains
        assertThat(word, containsString("pc"));

        //blank string
        String str = " ";
        assertThat(str, is(blankString()));
        assertThat(str.replace(" ", ""), is(emptyOrNullString()));
        assertThat(str.trim(), is(emptyOrNullString()));
    }

    @Test
    public void listsTest(){
        List<Integer> nums = Arrays.asList(5, 20, 1, 54, 0);
        List<String> words = Arrays.asList("java", "selenium", "git", "maven", "api");

        //size
        assertThat(nums, hasSize(5));
        assertThat(words, hasSize(5));

        //contains item
        assertThat(nums, hasItem(20));
        //assertThat(nums, hasItem(7)); //fails because nums that does not contain 7

        assertThat(words, hasItem("git"));
        assertThat(words, hasItems("git", "api"));
        //assertThat(words, hasItems("git", "db")); //list must contain all items or assertion fails

        //assertThat(nums, containsInAnyOrder(54, 20, 0, 5)); //failed b/c we left out the 1, we need same number of elements as list

        //every element
        assertThat(nums, everyItem(greaterThanOrEqualTo(0)));
        assertThat(words, everyItem(is(not(blankString()))));
    }
}
