public class Tile {
    private int foodHere;
    private boolean beehivePresent;
    private boolean hornetNestPresent;
    private boolean onPathFroNestToHive;
    private Tile nextOnNestToHivePath = null;
    private Tile nextOnHiveToNestPath = null;
    private HoneyBee beeOnTile;
    private SwarmOfHornets hornetsOnTile;
    public Tile() {
        this.beehivePresent = false;
        this.hornetNestPresent = false;
        this.onPathFroNestToHive = false;
        this.foodHere = 0;
        this.beeOnTile = null;
        this.hornetsOnTile = new SwarmOfHornets();
    }
    public Tile(int foodHere, boolean beehivePresent, boolean hornetNestPresent, boolean onPathFroNestToHive, Tile nextOnNestToHivePath, Tile nextOnHiveToNestPath, HoneyBee beeOnTile, SwarmOfHornets hornetsOnTile) {
        this.foodHere = foodHere;
        this.beehivePresent = beehivePresent;
        this.hornetNestPresent = hornetNestPresent;
        this.onPathFroNestToHive = onPathFroNestToHive;
        this.nextOnNestToHivePath = nextOnNestToHivePath;
        this.nextOnHiveToNestPath = nextOnHiveToNestPath;
        this.beeOnTile = beeOnTile;
        this.hornetsOnTile = hornetsOnTile;
    }
    public boolean isHive() {
        if (beehivePresent) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isNest() {
        if (hornetNestPresent) {
            return true;
        }
        else {
            return false;
        }
    }
    public void buildHive() {
        beehivePresent = true;
    }
    public void buildNest() {
        hornetNestPresent = true;
    }
    public boolean isOnThePath() {
        return onPathFroNestToHive;
    }
    public Tile towardTheHive() {
        return nextOnNestToHivePath;
    }
    public Tile towardTheNest() {
        return nextOnHiveToNestPath;
    }
    public void createPath(Tile toHive, Tile toNest) {
        this.nextOnNestToHivePath = toHive;
        this.nextOnHiveToNestPath = toNest;
        this.onPathFroNestToHive = true;
    }
    public int collectFood() {
        int foodWasHere = this.foodHere;
        this.foodHere = 0;
        return foodWasHere;
    }
    public void storeFood(int appendFood) {
        this.foodHere += appendFood;
    }
    public HoneyBee getBee() {
        return this.beeOnTile;
    }
    public Hornet getHornet() {
        return hornetsOnTile.getFirstHornet();
    }
    public int getNumOfHornets() {
        return hornetsOnTile.sizeOfSwarm();
    }
    public boolean addInsect(Insect bug) {
        if (bug instanceof HoneyBee) {
            if (beeOnTile != null) {
                return false;
            }
            else if (isNest()) {
                return false;
            }
            else {
                beeOnTile = (HoneyBee) bug;
                bug.setPosition(this);
                return true;
            }
        }
        else if (bug instanceof Hornet) {
            if (!isNest() && !isHive() && !onPathFroNestToHive) {
                return false;
            }
            else {
                this.hornetsOnTile.addHornet((Hornet) bug);
                bug.setPosition(this);
                return true;
            }
        }
        else {
            return false;
        }
    }
    public boolean removeInsect(Insect bug) {
        if (bug instanceof HoneyBee) {
            if (bug == beeOnTile) {
                beeOnTile = null;
                bug.setPosition(null);
                return true;
            } else {
                return false;
            }
        }
        else if (bug instanceof Hornet) {
            for (int i = 0; i < hornetsOnTile.getHornets().length; i++) {
                if (hornetsOnTile.getHornets()[i] == bug) {
                    hornetsOnTile.removeHornet((Hornet) bug);
                    bug.setPosition(null);
                    return true;
                }
            }
            return false;
        }
        else {
            return false;
        }
    }
}