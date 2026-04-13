package control.mode.challenge;

import java.util.Iterator;
import java.util.NoSuchElementException;

import entity.level.Difficulty;
import entity.level.Level;
import entity.level.DifficultySpawner;
import control.mode.LevelGenerator;

public class ChallengeLevelGenerator implements LevelGenerator {
    @Override
    public Iterator<Level> iterator() {
        return new Iterator<Level>() {
            private boolean delivered = false;

            @Override
            public boolean hasNext() {
                return !delivered;
            }

            @Override
            public Level next() {
                if (!hasNext()) throw new NoSuchElementException();
                delivered = true;
                return new Level(new DifficultySpawner(Difficulty.BOSS));
            }
        };
    }
}
