public class TankyBee extends HoneyBee {
    private int attackDMG;
    private int armor;
    public TankyBee(Tile position, int damage, int armor) {
        super(position, 30, 3);
        this.attackDMG = damage;
        this.armor = armor;
    }
    @Override
    public boolean takeAction() {
        if (this.getPosition().getNumOfHornets() != 0) {
            this.getPosition().getHornet().takeDamage(this.attackDMG);
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public void takeDamage(int damage) {
        double multiplier = (double) 100/(100 + armor);
        int damageRecalc = (int) (damage * multiplier);
        super.takeDamage(damageRecalc);
    }
    @Override
    public boolean equals(Object input) {
        if ((super.equals(input)) && (((HoneyBee) input).getCost() == this.getCost()) && (((TankyBee) input).attackDMG == this.attackDMG) && (((TankyBee) input).armor == this.armor)) {
            return true;
        }
        else {
            return false;
        }
    }
}
