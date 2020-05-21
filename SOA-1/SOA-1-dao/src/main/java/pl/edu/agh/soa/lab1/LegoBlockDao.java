package pl.edu.agh.soa.lab1;

import javax.ejb.Stateless;

@Stateless
public class LegoBlockDao{

    public static LegoBlockEntity legoBlockToEntityMapper(LegoBlock legoBlock){
        LegoBlockEntity legoBlockEntity = new LegoBlockEntity();
        legoBlockEntity.setColor(legoBlock.getColor());
        legoBlockEntity.setLegoBlockid(legoBlock.getLegoBlockid());
        return legoBlockEntity;
    }

    public static LegoBlock entityToLegoBlockMapper(LegoBlockEntity legoBlockEntity){
        LegoBlock legoBlock = new LegoBlock();
        legoBlock.setColor(legoBlockEntity.getColor());
        legoBlock.setLegoBlockid(legoBlockEntity.getLegoBlockid());
        return legoBlock;
    }
}
