package io.loopcamp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Region {


    @JsonProperty("region_id")
    private int regionId;
    @JsonProperty("region_name")
    private String regionName;
    private List<link> links;
    @Data
    public static class link {
        @JsonProperty("rel")
        private String rel;
        @JsonProperty("href")
        private String href;

        @Override
        public String toString() {
            return "link{" +
                    "rel='" + rel + '\'' +
                    ", href='" + href + '\'' +
                    '}';
        }
    }
}
