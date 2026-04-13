package control.mode;

import java.util.Iterator;
import entity.level.Level;

public interface LevelGenerator extends Iterable<Level> {
    @Override
    Iterator<Level> iterator();
}
