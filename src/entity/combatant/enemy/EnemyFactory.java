package entity.combatant.enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.HashMap;

public class EnemyFactory {
    private static final Map<Class<? extends Enemy>, Supplier<Enemy>> registry = new HashMap<>();

    static {
        register(Goblin.class, Goblin::new);
        register(Wolf.class, Wolf::new);
        register(Dragon.class, Dragon::new);
    }

    public static <T extends Enemy> void register(Class<T> type, Supplier<T> supplier) {
        registry.put(type, () -> supplier.get());
    }

    public static <T extends Enemy> T create(Class<T> type) {
        Supplier<Enemy> supplier = registry.get(type);
        if (supplier == null) throw new IllegalArgumentException("Unknown enemy class: " + type.getName());
        return type.cast(supplier.get());
    }

    public static List<Enemy> createGroup(Map<Class<? extends Enemy>, Integer> group) {
        List<Enemy> enemies = new ArrayList<>();
        for (Map.Entry<Class<? extends Enemy>, Integer> entry : group.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                enemies.add(create(entry.getKey()));
            }
        }
        return enemies;
    }
}
