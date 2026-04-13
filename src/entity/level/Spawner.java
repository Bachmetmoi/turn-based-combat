package entity.level;

import java.util.Iterator;
import java.util.List;
import entity.combatant.enemy.Enemy;

public interface Spawner {
    Iterator<List<Enemy>> getWaves();
}
