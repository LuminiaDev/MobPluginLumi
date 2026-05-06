package nukkitcoders.mobplugin.dispenser;

import cn.nukkit.dispenser.DispenseBehavior;
import cn.nukkit.item.ItemNamespaceId;
import cn.nukkit.registry.Registries;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import nukkitcoders.mobplugin.dispenser.impl.ShearsDispenseBehaviour;

public class DispenserBehaviorRegistry {
    private static final Object2ObjectOpenHashMap<String, DispenseBehavior> BEHAVIORS = new Object2ObjectOpenHashMap<>();

    public DispenserBehaviorRegistry() {
        register(ItemNamespaceId.SHEARS, new ShearsDispenseBehaviour());
    }

    public void register(String id, DispenseBehavior behavior) {
        BEHAVIORS.put(id, behavior);
        Registries.DISPENSE_BEHAVIOR.register(id, behavior);
    }

    public DispenseBehavior get(String id) {
        return BEHAVIORS.get(id);
    }
}
