package io.loopcamp.pojo;

import lombok.Data;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

@Data
public class MinionSearch {

    private List<Minion> content;
    private int totalElement;
}
