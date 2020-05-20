package pl.edu.agh.soa.lab1;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LegoSetDAO {
    @PersistenceContext(unitName = "Kanapka")
    EntityManager entityManager;
//    public void addLegoSet(LegoSet legoSet){
//        entityManager.persist(legoSet);
//    }

    public void addKanapka(){
        Kanapka kanapka = new Kanapka();
        kanapka.setName("nazwa");
        entityManager.persist(kanapka);
        entityManager.getTransaction().commit();
    }
    public void getKanapka(){
        Kanapka kanapka =  entityManager.find(Kanapka.class, 1);
        System.out.println(kanapka.getId());
        System.out.println(kanapka.getName());
    }
}