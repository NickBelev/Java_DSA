public abstract class Insect {
    private Tile position;
    private int hp;
    public Insect(Tile position, int hp) {
        this.position = position;
        this.hp = hp;
        boolean didItWork = position.addInsect(this);
        if (!didItWork) {
            throw new IllegalArgumentException("Could not create insect on specified tile.");
        }
    }
    public final Tile getPosition() {
        return position;
    }
    public final int getHealth() {
        return hp;
    }
    public void setPosition(Tile newPosition) {
        this.position = newPosition;
    }
    public void takeDamage(int amount) {
        Tile currentPos = this.getPosition();
        if ((this instanceof HoneyBee) && (this.getPosition().isHive())) {
            hp -= (int) (0.9 * (double) amount);
        }
        else {
            hp -= amount;
        }
        if (hp <= 0) {
            currentPos.removeInsect(this);
        }
    }
    public abstract boolean takeAction();
    public boolean equals(Object input) {
        if ((this.getClass() == input.getClass()) && (this.getPosition() == ((Insect) input).getPosition()) && (this.getHealth() == ((Insect) input).getHealth())) {
            return true;
        }
        else {
            return false;
        }
    }
}
