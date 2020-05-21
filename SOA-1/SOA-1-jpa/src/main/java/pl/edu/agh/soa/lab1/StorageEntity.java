package pl.edu.agh.soa.lab1;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Storage")
@Data
public class StorageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storageId;
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    List<LegoSetEntity> legoSetEntities;
}
