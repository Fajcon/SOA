package pl.edu.agh.soa.lab1;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LegoSetDAO {
    @PersistenceContext(unitName = "Kanapka")
    EntityManager entityManager;
    public void addLegoSet(LegoSetDao legoSetDao){
        entityManager.persist(legoSetDao);
    }

    public void addKanapka(){
        Kanapka kanapka = new Kanapka();
        kanapka.setName("nazwa");
        entityManager.persist(kanapka);
    }
    public Kanapka getKanapka(){
        return entityManager.find(Kanapka.class, 1L);
    }
    public void removeKanapka(){
        Kanapka kanapka = new Kanapka();
        kanapka.setId(1l);
        kanapka.setName("nazwa");
        entityManager.remove(kanapka);
    }
}