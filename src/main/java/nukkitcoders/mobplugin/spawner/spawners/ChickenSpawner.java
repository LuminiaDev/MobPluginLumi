package nukkitcoders.mobplugin.spawner.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import nukkitcoders.mobplugin.entities.BaseEntity;
import cn.nukkit.entity.passive.EntityChicken;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.spawner.AbstractEntitySpawner;
import nukkitcoders.mobplugin.spawner.EntitySpawnerTask;
import cn.nukkit.utils.Utils;

public class ChickenSpawner extends AbstractEntitySpawner {

    public ChickenSpawner(EntitySpawnerTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        if (Utils.rand(1, 3) != 1) {
            return;
        }
        if (level.isAnimalSpawningAllowedByTime()) {
            int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
            if (blockId == Block.GRASS || blockId == Block.SNOW_LAYER) {
                for (int i = 0; i < Utils.rand(2, 4); i++) {
                    BaseEntity entity = this.spawnTask.createEntity("Chicken", pos.add(0.5, 1, 0.5));
                    if (entity == null) return;
                    if (Utils.rand(1, 20) == 1) {
                        entity.setBaby(true);
                    }
                }
            }
        }
    }

    @Override
    public final int getEntityNetworkId() {
        return EntityChicken.NETWORK_ID;
    }
}
