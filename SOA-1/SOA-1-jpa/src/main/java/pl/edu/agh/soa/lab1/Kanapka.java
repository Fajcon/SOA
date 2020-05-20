package pl.edu.agh.soa.lab1;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "kanapka")
@Data
public class Kanapka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;

}
