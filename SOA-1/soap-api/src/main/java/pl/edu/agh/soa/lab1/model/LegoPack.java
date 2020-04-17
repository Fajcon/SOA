package pl.edu.agh.soa.lab1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class LegoPack {
    private LegoBlock legoBlocks;
    private Long numberOfBlocks;

    @JsonCreator
    public LegoPack(@JsonProperty(value = "legoBlocks", required = true)LegoBlock legoBlocks,
                    @JsonProperty(value = "numberOfBlocks", required = true)Long numberOfBlocks) {
        this.legoBlocks = legoBlocks;
        this.numberOfBlocks = numberOfBlocks;
    }
}