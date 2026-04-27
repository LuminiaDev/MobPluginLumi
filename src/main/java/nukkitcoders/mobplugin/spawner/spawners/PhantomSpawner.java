package nukkitcoders.mobplugin.spawner.spawners;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.entities.monster.flying.Phantom;
import nukkitcoders.mobplugin.spawner.AbstractEntitySpawner;
import nukkitcoders.mobplugin.spawner.EntitySpawnerTask;

public class PhantomSpawner extends AbstractEntitySpawner {

    public PhantomSpawner(EntitySpawnerTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        final int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);

        if (level.isMobSpawningAllowedByTime()) {
            if (pos.y < 130 && pos.y > 0 && biomeId != 14 && biomeId != 15 && level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z) == 0 && level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z) == 0) { // "Phantoms spawn if the player's Y-coordinate is between 1 and 129" - Minecraft Wiki
                Phantom phantom = (Phantom) this.spawnTask.createEntity("Phantom", pos);
                if (phantom != null) {
                    phantom.setTarget(player);
                }
            }
        }
    }

    @Override
    public final int getEntityNetworkId() {
        return Phantom.NETWORK_ID;
    }
}
