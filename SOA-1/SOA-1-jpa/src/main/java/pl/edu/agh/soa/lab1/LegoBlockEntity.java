package pl.edu.agh.soa.lab1;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "LegoBlock")
@Data
public class LegoBlockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legoBlockid;
    private String color;
}
