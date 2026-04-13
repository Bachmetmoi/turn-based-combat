package control.mode.story;

import java.util.Iterator;
import java.util.NoSuchElementException;

import entity.level.Difficulty;
import entity.level.Level;
import entity.level.DifficultySpawner;
import control.mode.LevelGenerator;

public class StoryLevelGenerator implements LevelGenerator {
    @Override
    public Iterator<Level> iterator() {
        return new Iterator<Level>() {
            private final Difficulty[] difficulties = Difficulty.values();
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < difficulties.length;
            }

            @Override
            public Level next() {
                if (!hasNext()) throw new NoSuchElementException();
                return new Level(new DifficultySpawner(difficulties[current++]));
            }
        };
    }
}
