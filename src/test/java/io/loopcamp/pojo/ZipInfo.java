package io.loopcamp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ZipInfo {
    @JsonProperty("post code")
    private String postCode;
    private String country;
    @JsonProperty("country abbreviation")
    private String countryAbbreviation;
    private List<Place> places;

    @Data
    public static class Place {
        @JsonProperty("place name")
        private String placeName;
        private String longitude;
        private String state;
        @JsonProperty("state abbreviation")
        private String stateAbbreviation;
        private String latitude;
    }

    public void prettyPrint(){
        System.out.println(
                "post code: " + postCode + "\n" +
                "country: " + country + "\n" +
                "country abbreviation: " + countryAbbreviation);
        for (int i = 0; i < places.size(); i++) {
            String comma = (i == getPlaces().size()-1) ? "" : ",";
            System.out.println(
                    "{" +
                    "\n\tplace name: " + places.get(i).getPlaceName() +
                    "\n\tlongitude: " + places.get(i).getLongitude() +
                    "\n\tstate: " + places.get(i).getState() +
                    "\n\tstate abbreviation: " + places.get(i).getStateAbbreviation() +
                    "\n\tlatitude: " + places.get(i).getLatitude() +
                    "\n}" + comma
            );
        }
    }


}
