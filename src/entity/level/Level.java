package entity.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entity.combatant.enemy.Enemy;

public class Level {
    private final Iterator<List<Enemy>> waveIterator;

    public Level(Spawner spawner) {
        this.waveIterator = spawner.getWaves();
    }

    public List<Enemy> getInitialEnemies() {
        if (waveIterator.hasNext()) {
            return new ArrayList<>(waveIterator.next());
        }
        return new ArrayList<>();
    }

    public boolean isNextWaveAvailable() {
        return waveIterator.hasNext();
    }

    public List<Enemy> getNextWave() {
        if (waveIterator.hasNext()) {
            return new ArrayList<>(waveIterator.next());
        }
        return new ArrayList<>();
    }
}
