package nukkitcoders.mobplugin;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.*;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.item.Item;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.GameRule;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.network.protocol.TextPacket;

import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.entities.Tameable;
import nukkitcoders.mobplugin.entities.monster.WalkingMonster;
import nukkitcoders.mobplugin.entities.monster.walking.*;
import nukkitcoders.mobplugin.utils.FastMathLite;
import nukkitcoders.mobplugin.utils.Utils;

public class EventListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void EntityDeathEvent(EntityDeathEvent ev) {
        if (ev.getEntity() instanceof EntityCreature) {
            this.handleExperienceOrb(ev.getEntity());
            this.handleTamedEntityDeathMessage(ev.getEntity());
            this.handleAttackedEntityAngry(ev.getEntity());
            if (ev.getEntity() instanceof BaseEntity && ev.getEntity().getLevel().getGameRules().getBoolean(GameRule.DO_MOB_LOOT)) {
                BaseEntity baseEntity = (BaseEntity) ev.getEntity();
                if (!(baseEntity.getLastDamageCause() instanceof EntityDamageByEntityEvent)) {
                    return;
                }
                Entity damager = ((EntityDamageByEntityEvent) baseEntity.getLastDamageCause()).getDamager();
                if (damager instanceof Creeper && damager != baseEntity && baseEntity.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    if (((Creeper) damager).isPowered()) {
                        Item skull = Utils.getMobHead(baseEntity.getNetworkId());
                        if (skull != null) {
                            baseEntity.getLevel().dropItem(baseEntity, skull);
                        }
                    }
                } else if (baseEntity instanceof Creeper && (damager instanceof Skeleton || damager instanceof Stray) && baseEntity.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    baseEntity.getLevel().dropItem(baseEntity, Item.get(Utils.rand(500, 511), 0, 1));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void PlayerDeathEvent(PlayerDeathEvent ev) {
        this.handleAttackedEntityAngry(ev.getEntity());
    }

    private void handleExperienceOrb(Entity entity) {
        if (!(entity instanceof BaseEntity)) {
            return;
        }

        BaseEntity baseEntity = (BaseEntity) entity;

        if (!(baseEntity.getLastDamageCause() instanceof EntityDamageByEntityEvent)) {
            return;
        }

        Entity damager = ((EntityDamageByEntityEvent) baseEntity.getLastDamageCause()).getDamager();
        if (!(damager instanceof Player)) {
            return;
        }
        int killExperience = baseEntity.getKillExperience();
        if (killExperience > 0) {
            if (MobPlugin.getInstance().config.noXpOrbs) {
                ((Player) damager).addExperience(killExperience);
            } else {
                damager.getLevel().dropExpOrb(baseEntity, killExperience);
            }
        }
    }

    private void handleTamedEntityDeathMessage(Entity entity) {
        if (!(entity instanceof BaseEntity)) {
            return;
        }

        BaseEntity baseEntity = (BaseEntity) entity;

        if (baseEntity instanceof Tameable) {
            if (!((Tameable) baseEntity).hasOwner()) {
                return;
            }

            if (((Tameable) baseEntity).getOwner() == null) {
                return;
            }

            // TODO: More detailed death messages
            String killedEntity;
            if (baseEntity instanceof Wolf) {
                killedEntity = "%entity.wolf.name";
            } else {
                killedEntity = baseEntity.getName();
            }

            TranslationContainer deathMessage = new TranslationContainer("death.attack.generic", killedEntity);
            if (baseEntity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                Entity damageEntity = ((EntityDamageByEntityEvent) baseEntity.getLastDamageCause()).getDamager();
                if (damageEntity instanceof Player) {
                    deathMessage = new TranslationContainer("death.attack.player", killedEntity, damageEntity.getName());
                } else {
                    deathMessage = new TranslationContainer("death.attack.mob", killedEntity, damageEntity.getName());
                }
            }

            TextPacket tameDeathMessage = new TextPacket();
            tameDeathMessage.type = TextPacket.TYPE_TRANSLATION;
            tameDeathMessage.message = deathMessage.getText();
            tameDeathMessage.parameters = deathMessage.getParameters();
            tameDeathMessage.isLocalized = true;
            ((Tameable) baseEntity).getOwner().dataPacket(tameDeathMessage);
        }
    }

    private void handleAttackedEntityAngry(Entity entity) {
        if (!(entity.getLastDamageCause() instanceof EntityDamageByEntityEvent)) {
            return;
        }

        Entity damager = ((EntityDamageByEntityEvent) entity.getLastDamageCause()).getDamager();
        if (damager instanceof Wolf) {
            ((Wolf) damager).isAngryTo = -1L;
            ((Wolf) damager).setAngry(false);
        } else if (damager instanceof IronGolem || damager instanceof SnowGolem) {
            ((WalkingMonster) damager).isAngryTo = -1L;
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void PlayerMoveEvent(PlayerMoveEvent ev) {
        Player player = ev.getPlayer();
        if (player.ticksLived % 20 == 0) {
            AxisAlignedBB aab = new SimpleAxisAlignedBB(
                    player.getX() - 0.6f,
                    player.getY() + 1.45f,
                    player.getZ() - 0.6f,
                    player.getX() + 0.6f,
                    player.getY() + 2.9f,
                    player.getZ() + 0.6f
            );

            for (int i = 0; i < 8; i++) {
                aab.offset(-FastMathLite.sin(player.getYaw() * Math.PI / 180) * i, i * (Math.tan(player.getPitch() * -3.141592653589793 / 180)), FastMathLite.cos(player.getYaw() * Math.PI / 180) * i);
                Entity[] entities = player.getLevel().getCollidingEntities(aab);
                for (Entity e : entities) {
                    if (e instanceof Enderman) {
                        ((Enderman) e).stareToAngry();
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent ev) {
        if (!MobPlugin.getInstance().config.checkTamedEntityAttack) {
            return;
        }

        if (ev.getEntity() instanceof Player) {
            for (Entity entity : ev.getEntity().getLevel().getNearbyEntities(ev.getEntity().getBoundingBox().grow(17, 17, 17), ev.getEntity())) {
                if (entity instanceof Wolf) {
                    if (((Wolf) entity).hasOwner()) {
                        ((Wolf) entity).isAngryTo = ev.getDamager().getId();
                        ((Wolf) entity).setAngry(true);
                    }
                }
            }
        } else if (ev.getDamager() instanceof Player) {
            for (Entity entity : ev.getDamager().getLevel().getNearbyEntities(ev.getDamager().getBoundingBox().grow(17, 17, 17), ev.getDamager())) {
                if (entity.getId() == ev.getEntity().getId()) {
                    return;
                }

                if (entity instanceof Wolf) {
                    if (((Wolf) entity).hasOwner()) {
                        if (((Wolf) entity).getOwner().equals(ev.getDamager())) {
                            ((Wolf) entity).isAngryTo = ev.getEntity().getId();
                            ((Wolf) entity).setAngry(true);
                        }
                    }
                }
            }
        }
    }
}
