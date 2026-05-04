package nukkitcoders.mobplugin;

import java.util.*;

public class Config {

    final cn.nukkit.utils.Config pluginConfig;

    public int spawnDelay;
    public int despawnTicks;
    public boolean spawnMobs;
    public boolean spawnAnimals;
    public Set<String> mobSpawningDisabledWorlds;

    Config(MobPlugin plugin) {
        plugin.saveDefaultConfig();
        pluginConfig = plugin.getConfig();
    }

    boolean init(MobPlugin plugin) {
        //entities
        spawnDelay = pluginConfig.getInt("autospawn-ticks") >> 1; // The task runs double the speed but spawns only either monsters or animals
        despawnTicks = pluginConfig.getInt("despawn-ticks");
        spawnMobs = pluginConfig.getBoolean("spawn-mobs");
        spawnAnimals = pluginConfig.getBoolean("spawn-animals");
        mobSpawningDisabledWorlds = loadStringListAsSet("worlds-spawning-disabled");

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
