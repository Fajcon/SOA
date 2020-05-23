package pl.edu.agh.soa.lab1;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "LegoSet")
@Data
public class LegoSetDao{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legoSetNumber;
    private String name;
    @OneToMany
    private List<LegoPackDao> legoPacksDao;
}
