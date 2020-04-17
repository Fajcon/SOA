package pl.edu.agh.soa.lab1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pl.edu.agh.soa.lab1.model.LegoPack;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@ToString
public class LegoSet {
    private Long legoSetNumber;
    private String name;
    private String boxGraphicBase64;
    private List<LegoPack> legoPacks;

    @XmlElementWrapper(name = "legoPacks")
    @XmlElement(name = "legoPack")
    public List<LegoPack> getLegoPacks() {
        return legoPacks;
    }

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
