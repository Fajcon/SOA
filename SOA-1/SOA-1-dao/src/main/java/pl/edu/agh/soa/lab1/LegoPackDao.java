package pl.edu.agh.soa.lab1;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class LegoPackDao {

    public static LegoPackEntity legoPackToEntityMapper(LegoPack legoPack){
        LegoPackEntity legoPackEntity = new LegoPackEntity();
        legoPackEntity.setNumberOfBlocks(legoPack.getNumberOfBlocks());
        legoPackEntity.setLegoBlockEntity(LegoBlockDao.legoBlockToEntityMapper(legoPack.getLegoBlock()));
        return  legoPackEntity;
    }

    public static LegoPack entityToLegoPackMapper(LegoPackEntity legoPackEntity) {
        LegoPack legoPack = new LegoPack();
        legoPack.setNumberOfBlocks(legoPackEntity.getNumberOfBlocks());
        legoPack.setLegoBlock(LegoBlockDao.entityToLegoBlockMapper(legoPackEntity.getLegoBlockEntity()));
        return legoPack;
    }
}
