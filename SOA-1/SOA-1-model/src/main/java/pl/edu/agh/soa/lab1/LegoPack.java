package pl.edu.agh.soa.lab1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class LegoPack {
    @JsonProperty
    private LegoBlock legoBlock;
    @JsonProperty
    private Long numberOfBlocks;

    @JsonCreator
    public LegoPack(@JsonProperty(value = "legoBlocks", required = true)LegoBlock legoBlock,
                    @JsonProperty(value = "numberOfBlocks", required = true)Long numberOfBlocks) {
        this.legoBlock = legoBlock;
        this.numberOfBlocks = numberOfBlocks;
    }
}
