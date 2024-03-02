package io.loopcamp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;

@Data
public class Minion {

    private int id;
    private String gender;
    private String name;
    private String phone;
    //private String lastName; // If you have extra than what you need, it will not give an issue.
    // the instance variable names have to match what we have as KEY in our JSON Response Body
    // The number of the KEYS have to also match
    public void prettyPrint(){
        System.out.println(name +
                "\n\tId " + id +
                "\n\tGender " + gender +
                "\n\tPhone " + phone
        );
    }
}
