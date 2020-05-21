package pl.edu.agh.soa.lab1;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "LegoPack")
@Data
public class LegoPackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legoPackId;
    @OneToOne(cascade = CascadeType.MERGE)
    private LegoBlockEntity legoBlockEntity;
    private Long numberOfBlocks;
}
