package entity.effect.base;


public abstract class PermanentEffect extends StatusEffect {
    public PermanentEffect(String name) { super(name); }

    public boolean isExpired() { return false; }
}
