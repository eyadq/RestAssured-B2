package io.loopcamp.api.pet_store;


import lombok.Data;

import java.util.List;

@Data
public class NewPetPojo {

    int id;
    Category category;
    String name;
    List<String> photoUrls;
    List<Category> tags;
    String status;

    @Data
    public static class Category{
        int id;
        String name;
    }

}
