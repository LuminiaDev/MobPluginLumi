package nukkitcoders.mobplugin;

import cn.nukkit.event.Listener;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.registry.Registries;
import nukkitcoders.mobplugin.spawner.EntitySpawnerTask;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.entities.animal.flying.Allay;
import nukkitcoders.mobplugin.entities.animal.flying.Bat;
import nukkitcoders.mobplugin.entities.animal.flying.Bee;
import nukkitcoders.mobplugin.entities.animal.flying.Parrot;
import nukkitcoders.mobplugin.entities.animal.jumping.Frog;
import nukkitcoders.mobplugin.entities.animal.jumping.Rabbit;
import nukkitcoders.mobplugin.entities.animal.swimming.*;
import nukkitcoders.mobplugin.entities.animal.walking.*;
import nukkitcoders.mobplugin.entities.monster.flying.*;
import nukkitcoders.mobplugin.entities.monster.jumping.MagmaCube;
import nukkitcoders.mobplugin.entities.monster.jumping.Slime;
import nukkitcoders.mobplugin.entities.monster.swimming.ElderGuardian;
import nukkitcoders.mobplugin.entities.monster.swimming.Guardian;
import nukkitcoders.mobplugin.entities.monster.walking.*;

public class MobPlugin extends PluginBase implements Listener {

    private static MobPlugin INSTANCE;
    public Config config;
    private EntitySpawnerTask spawnerTask;

    public MobPlugin() {
        INSTANCE = this;
    }

    public static MobPlugin getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        config = new Config(this);

        if (!config.init(this)) {
            return;
        }

        this.registerEntities();
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        spawnerTask = new EntitySpawnerTask();
        int spawnerTicks = Math.max(getServer().getSettings().world().entity().ticksPerEntitySpawns(), 2) >> 1; // Run the spawner on 2x speed but spawn only either monsters or animals
        getServer().getScheduler().scheduleDelayedRepeatingTask(this, this.spawnerTask, spawnerTicks, spawnerTicks);
    }

    @Override
    public void onDisable() {
        RouteFinderThreadPool.shutDownNow();
    }

    private void registerEntities() {

        Registries.ENTITY.register(Bat.class.getSimpleName(), Bat.class);
        Registries.ENTITY.register(Bee.class.getSimpleName(), Bee.class);
        Registries.ENTITY.register(Cat.class.getSimpleName(), Cat.class);
        Registries.ENTITY.register(Chicken.class.getSimpleName(), Chicken.class);
        Registries.ENTITY.register(Cod.class.getSimpleName(), Cod.class);
        Registries.ENTITY.register(Cow.class.getSimpleName(), Cow.class);
        Registries.ENTITY.register(Dolphin.class.getSimpleName(), Dolphin.class);
        Registries.ENTITY.register(Donkey.class.getSimpleName(), Donkey.class);
        Registries.ENTITY.register(Fox.class.getSimpleName(), Fox.class);
        Registries.ENTITY.register(Horse.class.getSimpleName(), Horse.class);
        Registries.ENTITY.register(MagmaCube.class.getSimpleName(), MagmaCube.class);
        Registries.ENTITY.register(Llama.class.getSimpleName(), Llama.class);
        Registries.ENTITY.register(Mooshroom.class.getSimpleName(), Mooshroom.class);
        Registries.ENTITY.register(Mule.class.getSimpleName(), Mule.class);
        Registries.ENTITY.register(Ocelot.class.getSimpleName(), Ocelot.class);
        Registries.ENTITY.register(Panda.class.getSimpleName(), Panda.class);
        Registries.ENTITY.register(Parrot.class.getSimpleName(), Parrot.class);
        Registries.ENTITY.register(Pig.class.getSimpleName(), Pig.class);
        Registries.ENTITY.register(PolarBear.class.getSimpleName(), PolarBear.class);
        Registries.ENTITY.register(Pufferfish.class.getSimpleName(), Pufferfish.class);
        Registries.ENTITY.register(Rabbit.class.getSimpleName(), Rabbit.class);
        Registries.ENTITY.register(Salmon.class.getSimpleName(), Salmon.class);
        Registries.ENTITY.register(SkeletonHorse.class.getSimpleName(), SkeletonHorse.class);
        Registries.ENTITY.register(Sheep.class.getSimpleName(), Sheep.class);
        Registries.ENTITY.register(Squid.class.getSimpleName(), Squid.class);
        Registries.ENTITY.register(TropicalFish.class.getSimpleName(), TropicalFish.class);
        Registries.ENTITY.register(Turtle.class.getSimpleName(), Turtle.class);
        Registries.ENTITY.register("VillagerV1", Villager.class);
        Registries.ENTITY.register("Villager", VillagerV2.class);
        Registries.ENTITY.register(ZombieHorse.class.getSimpleName(), ZombieHorse.class);
        Registries.ENTITY.register(WanderingTrader.class.getSimpleName(), WanderingTrader.class);
        Registries.ENTITY.register(Strider.class.getSimpleName(), Strider.class);
        Registries.ENTITY.register(GlowSquid.class.getSimpleName(), GlowSquid.class);
        Registries.ENTITY.register(Goat.class.getSimpleName(), Goat.class);
        Registries.ENTITY.register(Axolotl.class.getSimpleName(), Axolotl.class);
        Registries.ENTITY.register(Allay.class.getSimpleName(), Allay.class);
        Registries.ENTITY.register(Frog.class.getSimpleName(), Frog.class);
        Registries.ENTITY.register(Tadpole.class.getSimpleName(), Tadpole.class);
        Registries.ENTITY.register(Camel.class.getSimpleName(), Camel.class);

        Registries.ENTITY.register(Blaze.class.getSimpleName(), Blaze.class);
        Registries.ENTITY.register(Ghast.class.getSimpleName(), Ghast.class);
        Registries.ENTITY.register(CaveSpider.class.getSimpleName(), CaveSpider.class);
        Registries.ENTITY.register(WitherSkeleton.class.getSimpleName(), WitherSkeleton.class);
        Registries.ENTITY.register(Creeper.class.getSimpleName(), Creeper.class);
        Registries.ENTITY.register(Drowned.class.getSimpleName(), Drowned.class);
        Registries.ENTITY.register(ElderGuardian.class.getSimpleName(), ElderGuardian.class);
        Registries.ENTITY.register(EnderDragon.class.getSimpleName(), EnderDragon.class);
        Registries.ENTITY.register(Enderman.class.getSimpleName(), Enderman.class);
        Registries.ENTITY.register(Endermite.class.getSimpleName(), Endermite.class);
        Registries.ENTITY.register(Evoker.class.getSimpleName(), Evoker.class);
        Registries.ENTITY.register(Guardian.class.getSimpleName(), Guardian.class);
        Registries.ENTITY.register(Husk.class.getSimpleName(), Husk.class);
        Registries.ENTITY.register(IronGolem.class.getSimpleName(), IronGolem.class);
        Registries.ENTITY.register(Phantom.class.getSimpleName(), Phantom.class);
        Registries.ENTITY.register(ZombiePigman.class.getSimpleName(), ZombiePigman.class);
        Registries.ENTITY.register(Shulker.class.getSimpleName(), Shulker.class);
        Registries.ENTITY.register(Silverfish.class.getSimpleName(), Silverfish.class);
        Registries.ENTITY.register(Skeleton.class.getSimpleName(), Skeleton.class);
        Registries.ENTITY.register(Slime.class.getSimpleName(), Slime.class);
        Registries.ENTITY.register(SnowGolem.class.getSimpleName(), SnowGolem.class);
        Registries.ENTITY.register(Spider.class.getSimpleName(), Spider.class);
        Registries.ENTITY.register(Stray.class.getSimpleName(), Stray.class);
        Registries.ENTITY.register(Vex.class.getSimpleName(), Vex.class);
        Registries.ENTITY.register(Vindicator.class.getSimpleName(), Vindicator.class);
        Registries.ENTITY.register(Witch.class.getSimpleName(), Witch.class);
        Registries.ENTITY.register(Wither.class.getSimpleName(), Wither.class);
        Registries.ENTITY.register(Wolf.class.getSimpleName(), Wolf.class);
        Registries.ENTITY.register(Zombie.class.getSimpleName(), Zombie.class);
        Registries.ENTITY.register("ZombieVillagerV1", ZombieVillager.class);
        Registries.ENTITY.register("ZombieVillager", ZombieVillagerV2.class);
        Registries.ENTITY.register(Pillager.class.getSimpleName(), Pillager.class);
        Registries.ENTITY.register(Ravager.class.getSimpleName(), Ravager.class);
        Registries.ENTITY.register(Hoglin.class.getSimpleName(), Hoglin.class);
        Registries.ENTITY.register(Piglin.class.getSimpleName(), Piglin.class);
        Registries.ENTITY.register(Zoglin.class.getSimpleName(), Zoglin.class);
        Registries.ENTITY.register(PiglinBrute.class.getSimpleName(), PiglinBrute.class);
        Registries.ENTITY.register(Warden.class.getSimpleName(), Warden.class);
    }

    public static boolean shouldMobBurn(Level level, BaseEntity entity) {
        int time = level.getTime() % Level.TIME_FULL;
        return !entity.isOnFire() && !level.isRaining() && (time < 12567 || time > 23450) && !entity.isInsideOfWater() && level.canBlockSeeSky(entity);
    }
}
