public class Hornet extends Insect {
    private int attackDMG;
    public Hornet(Tile position, int hp, int damage) {
        super(position, hp);
        this.attackDMG = damage;
    }
    @Override
    public boolean takeAction() {
        Tile currPos = this.getPosition();
        if (currPos != null) {
            if (currPos.getBee() != null) {
                this.getPosition().getBee().takeDamage(attackDMG);
                return true;
            }
            else if (!this.getPosition().isHive()) {
                Tile nextPos = getPosition().towardTheHive();
                getPosition().removeInsect(this);
                setPosition(nextPos);
                getPosition().addInsect(this);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    @Override
    public boolean equals(Object input) {
        if ((super.equals(input)) && (this.attackDMG == ((Hornet) input).attackDMG)) {
            return true;
        }
        else {
            return false;
        }
    }
}
