public class AngryBee extends HoneyBee {
    private int attackDMG;
    public AngryBee(Tile position, int damage) {
        super(position, 10, 1);
        this.attackDMG = damage;
    }
    @Override
    public boolean takeAction() {
        if (!this.getPosition().isOnThePath()) {
            return false;
        }
        else {
            boolean didSting = false;
            Tile posInit = this.getPosition();
            Tile posChange = this.getPosition();
            while (!posChange.isNest()) {
                if (posChange.getNumOfHornets() != 0) {
                    posChange.getHornet().takeDamage(attackDMG);
                    didSting = true;
                    break;
                }
                this.setPosition(posChange.towardTheNest());
            }
            this.setPosition(posInit);
            return didSting;
        }
    }
    @Override
    public boolean equals(Object input) {
        if ((super.equals(input)) && (((HoneyBee) input).getCost() == this.getCost()) && (((AngryBee) input).attackDMG == this.attackDMG)) {
            return true;
        }
        else {
            return false;
        }
    }
}
