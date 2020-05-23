package pl.edu.agh.soa.lab1;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@ToString
public class Storage {
    private Long storageId;
    private String name;
    List<LegoSet> legoSets;

    public Storage(Long storageId, String name, List<LegoSet> legoSets) {
        this.storageId = storageId;
        this.name = name;
        this.legoSets = legoSets;
    }
}
