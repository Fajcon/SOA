package pl.edu.agh.soa.lab1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LegoBlock {
    private String color;
    private long partNumber;

    @JsonCreator
    public LegoBlock(@JsonProperty(value = "color", required = true)String color,
                     @JsonProperty(value = "partNumber", required = true)long partNumber,
                     @JsonProperty(value = "name", required = true)String name) {
        this.color = color;
        this.partNumber = partNumber;
        this.name = name;
    }

    private String name;
}
