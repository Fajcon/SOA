package pl.edu.agh.soa.lab1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class LegoBlock {
    @JsonProperty
    private String color;
    @JsonProperty
    private Long legoBlockid;

    @JsonCreator
    public LegoBlock(@JsonProperty(value = "color", required = true)String color,
                     @JsonProperty(value = "legoBlockid", required = true)Long legoBlockid) {
        this.color = color;
        this.legoBlockid = legoBlockid;
    }
}
