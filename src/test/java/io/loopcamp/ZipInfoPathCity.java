package io.loopcamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.loopcamp.pojo.ZipInfo;
import lombok.Data;

import java.util.List;

@Data
public class ZipInfoPathCity {
    @JsonProperty("country abbreviation")
    private String countryAbbreviation;
    private List<Place> places;
    private String country;
    @JsonProperty("place name")
    private String placeName;
    private String state;
    @JsonProperty("state abbreviation")
    private String stateAbbreviation;

    @Data
    public static class Place {
        @JsonProperty("place name")
        private String placeName;
        private String longitude;
        @JsonProperty("post code")
        private String postCode;
        private String latitude;
    }
}
