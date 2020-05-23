package pl.edu.agh.soa.lab1;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "LegoPack")
@Data
public class LegoPackDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legoBlockId;
    @OneToOne
    private LegoBlockDao legoBlocksDao;
    private Long numberOfBlocks;
}
