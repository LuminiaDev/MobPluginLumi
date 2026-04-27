package nukkitcoders.mobplugin.spawner.spawners;

import cn.nukkit.Player;
import nukkitcoders.mobplugin.entities.BaseEntity;
import cn.nukkit.entity.mob.EntityZombiePigman;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.spawner.AbstractEntitySpawner;
import nukkitcoders.mobplugin.spawner.EntitySpawnerTask;
import cn.nukkit.utils.Utils;

public class ZombiePigmanSpawner extends AbstractEntitySpawner {

    public ZombiePigmanSpawner(EntitySpawnerTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        if (level.getBlockLightAt((int) pos.x, (int) pos.y + 1, (int) pos.z) <= 7) {
            for (int i = 0; i < Utils.rand(2, 4); i++) {
                BaseEntity entity = this.spawnTask.createEntity("ZombiePigman", pos.add(0.5, 1, 0.5));
                if (entity == null) return;
                if (Utils.rand(1, 20) == 1) {
                    entity.setBaby(true);
                }
            }
        }
    }

    @Override
    public final int getEntityNetworkId() {
        return EntityZombiePigman.NETWORK_ID;
    }
}
