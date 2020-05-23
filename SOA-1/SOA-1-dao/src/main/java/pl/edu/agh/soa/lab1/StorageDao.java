package pl.edu.agh.soa.lab1;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class StorageDao {
    @PersistenceContext(unitName = "Lego")
    EntityManager entityManager;

    public static Storage entityToStorageMapper(StorageEntity storageEntity){
        Storage storage = new Storage();
        storage.setName(storageEntity.getName());
        storage.setLegoSets(storageEntity
                .getLegoSetEntities()
                .stream()
                .map(LegoSetDao::entityToLegoSetMapper)
                .collect(Collectors.toList()));
        storage.setStorageId(storageEntity.getStorageId());
        return storage;
    }

    public static StorageEntity storageToEntityMapper(Storage storage){
        StorageEntity storageEntity = new StorageEntity();
        storageEntity.setName(storage.getName());
        return storageEntity;
    }

    public void save(Storage storage){
        entityManager.persist(StorageDao.storageToEntityMapper(storage));
    }

    public Storage find(Long id) throws Exception {
        return Optional.ofNullable(entityManager.find(StorageEntity.class, id))
                .map(StorageDao::entityToStorageMapper)
                .orElseThrow(Exception::new);
    }

    public void update(Long storageId, Long legoSetId){
        StorageEntity storageEntity = entityManager.find(StorageEntity.class, storageId);
        LegoSetEntity legoSetEntity = entityManager.find(LegoSetEntity.class, legoSetId);
        storageEntity.getLegoSetEntities().add(legoSetEntity);
        entityManager.merge(storageEntity);
    }

    public List<Storage> findByName(String name){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StorageEntity> query = cb.createQuery(StorageEntity.class);
        Root<StorageEntity> storageEntityRoot = query.from(StorageEntity.class);
        query.select(storageEntityRoot).where(cb.equal(storageEntityRoot.get("name"), name));

        return entityManager
                .createQuery(query)
                .getResultList()
                .stream()
                .map(StorageDao::entityToStorageMapper)
                .collect(Collectors.toList());
    }
}
