package pl.edu.agh.soa.lab1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.File;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class LegoSet {
    private Long legoSetNumber;
    private String name;
    private String age;
    private File boxGraphic;
    private List<LegoPack> legoPacks;

    @XmlElementWrapper(name = "legoPacks")
    @XmlElement(name = "legoPack")
    public List<LegoPack> getLegoPacks() {
        return legoPacks;
    }

    public void addCourse(LegoPack legoPack){
        legoPacks.add(legoPack);
    }
}
