package pl.edu.agh.soa.lab1;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "LegoSet")
@Data
public class LegoSetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legoSetNumber;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<LegoPackEntity> legoPacksEntity;
    private String imgPath;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "legoSetEntities")
    List<StorageEntity> storageEntities;
}
