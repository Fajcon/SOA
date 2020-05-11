package pl.edu.agh.soa.lab1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@ToString
public class LegoSet {
    @JsonProperty
    private Long legoSetNumber;
    @JsonProperty
    private String name;
    @JsonProperty
    private String boxGraphicBase64;
    @JsonProperty
    private List<LegoPack> legoPacks;

    @JsonCreator
    public LegoSet(@JsonProperty(value = "legoSetNumber", required = true)Long legoSetNumber,
                   @JsonProperty(value = "name", required = true)String name,
                   @JsonProperty(value = "boxGraphicBase64", required = true)String boxGraphicBase64,
                   @JsonProperty(value = "legoPacks")List<LegoPack> legoPacks) {
        this.legoSetNumber = legoSetNumber;
        this.name = name;
        this.boxGraphicBase64 = boxGraphicBase64;
        this.legoPacks = legoPacks;
    }
}
