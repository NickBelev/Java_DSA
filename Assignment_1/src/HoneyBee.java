public abstract class HoneyBee extends Insect {
    private int foodCost;
    public HoneyBee(Tile position, int hp, int cost) {
        super(position, hp);
        this.foodCost = cost;
    }
    public int getCost() {
        return foodCost;
    }
    @Override
    public boolean equals(Object input) {
        if ((super.equals(input)) && (this.foodCost == ((HoneyBee) input).foodCost)) {
            return true;
        }
        else {
            return false;
        }
    }
}
