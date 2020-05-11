package pl.edu.agh.soa.lab1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@ToString
public class LegoPack {
    @JsonProperty
    private LegoBlock legoBlocks;
    @JsonProperty
    private Long numberOfBlocks;

    @JsonCreator
    public LegoPack(@JsonProperty(value = "legoBlocks", required = true)LegoBlock legoBlocks,
                    @JsonProperty(value = "numberOfBlocks", required = true)Long numberOfBlocks) {
        this.legoBlocks = legoBlocks;
        this.numberOfBlocks = numberOfBlocks;
    }
}
