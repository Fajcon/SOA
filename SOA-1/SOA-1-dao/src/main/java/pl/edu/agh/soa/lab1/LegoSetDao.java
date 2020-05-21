package pl.edu.agh.soa.lab1;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class LegoSetDao{
    @PersistenceContext(unitName = "Lego")
    EntityManager entityManager;

    public static LegoSetEntity legoSetToEntityMapper(LegoSet legoSet){
        LegoSetEntity legoSetEntity = new LegoSetEntity();
        legoSetEntity.setName(legoSet.getName());
        legoSetEntity.setImgPath(legoSet.getImgPath());
        List<LegoPackEntity> legoPacks = legoSet.getLegoPacks()
                .stream()
                .map(LegoPackDao::legoPackToEntityMapper)
                .collect(Collectors.toList());
        legoSetEntity.setLegoPacksEntity(legoPacks);
        return legoSetEntity;
    }

    public static LegoSet entityToLegoSetMapper(LegoSetEntity legoSetEntity){
        LegoSet legoSet = new LegoSet();
        legoSet.setLegoSetNumber(legoSetEntity.getLegoSetNumber());
        legoSet.setName(legoSetEntity.getName());
        legoSet.setImgPath(legoSetEntity.getImgPath());
        List<LegoPack> legoPacks = legoSetEntity.getLegoPacksEntity()
                .stream()
                .map(LegoPackDao::entityToLegoPackMapper)
                .collect(Collectors.toList());
        legoSet.setLegoPacks(legoPacks);
        return legoSet;
    }

    public void save(LegoSet legoSet) {
        entityManager.persist(LegoSetDao.legoSetToEntityMapper(legoSet));
    }

    public List<LegoSet> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LegoSetEntity> query = cb.createQuery(LegoSetEntity.class);
        Root<LegoSetEntity> legoSetEntityRoot = query.from(LegoSetEntity.class);
        query.select(legoSetEntityRoot);

        return entityManager
                .createQuery(query)
                .getResultList()
                .stream()
                .map(LegoSetDao::entityToLegoSetMapper)
                .collect(Collectors.toList());
    }

    public LegoSet find(Long id) throws Exception {
        return Optional.ofNullable(entityManager.find(LegoSetEntity.class, id))
                .map(LegoSetDao::entityToLegoSetMapper)
                .orElseThrow(Exception::new);
    }

    public void update(LegoSet legoSet){
        LegoSetEntity legoSetEntity = LegoSetDao.legoSetToEntityMapper(legoSet);
        legoSetEntity.setLegoSetNumber(legoSet.getLegoSetNumber());
        entityManager.merge(legoSetEntity);
    }
}