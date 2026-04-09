package nukkitcoders.mobplugin;

import java.util.*;

public class Config {

    final cn.nukkit.utils.Config pluginConfig;

    public int spawnDelay;
    public int despawnTicks;
    public int spawnNoSpawningArea;
    public int endEndermanSpawnRate;
    public boolean noXpOrbs;
    public boolean killOnDespawn;
    public boolean checkTamedEntityAttack;
    public boolean creeperExplodeBlocks;
    public boolean allowBreeding;
    public boolean showBossBar;
    public Set<String> mobSpawningDisabledWorlds;
    public Set<String> mobCreationDisabledWorlds;

    Config(MobPlugin plugin) {
        plugin.saveDefaultConfig();
        pluginConfig = plugin.getConfig();
    }

    boolean init(MobPlugin plugin) {
        //entities
        spawnDelay = pluginConfig.getInt("entities.autospawn-ticks") >> 1; // The task runs double the speed but spawns only either monsters or animals
        despawnTicks = pluginConfig.getInt("entities.despawn-ticks");
        mobSpawningDisabledWorlds = loadStringListAsSet("entities.worlds-spawning-disabled");

        //other
        noXpOrbs = pluginConfig.getBoolean("other.use-no-xp-orbs");
        spawnNoSpawningArea = pluginConfig.getInt("other.spawn-no-spawning-area");
        killOnDespawn = pluginConfig.getBoolean("other.kill-mobs-on-despawn");
        endEndermanSpawnRate = pluginConfig.getInt("other.end-enderman-spawning");
        checkTamedEntityAttack = pluginConfig.getBoolean("other.check-tamed-entity-attack");
        creeperExplodeBlocks = pluginConfig.getBoolean("other.creeper-explode-blocks");
        mobCreationDisabledWorlds = loadStringListAsSet("other.worlds-entity-creation-disabled");
        allowBreeding = pluginConfig.getBoolean("other.allow-breeding");
        showBossBar = pluginConfig.getBoolean("other.show-boss-bar");
        return true;
    }

    private Set<String> loadStringListAsSet(String key) {
        Set<String> list = new HashSet<>();
        String input = pluginConfig.getString(key).toLowerCase();
        if (!input.trim().isEmpty()) {
            StringTokenizer tokenizer = new StringTokenizer(input, ", ");
            while (tokenizer.hasMoreTokens()) {
                list.add(tokenizer.nextToken());
            }
        }
        return list;
    }
}
