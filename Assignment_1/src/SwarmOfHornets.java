public class SwarmOfHornets {
    private Hornet[] swarmMembers;
    private int swarmSize;
    public SwarmOfHornets() {
        swarmMembers = new Hornet[0];
        swarmSize = 0;
    }
    public int sizeOfSwarm() {
        return swarmSize;
    }
    public Hornet[] getHornets() {
        return swarmMembers;
    }
    public Hornet getFirstHornet() {
        if (swarmMembers.length == 0) {
            return null;
        }
        else {
            return swarmMembers[0];
        }
    }
    public void addHornet(Hornet newMember) {
        boolean containsNull = false;
        int nullIndex = 0;
        for (int i = 0; i < swarmMembers.length; i++) {
            if (swarmMembers[i] == null) {
                containsNull = true;
                nullIndex = i;
                break;
            }
        }
        if (containsNull) {
            swarmMembers[nullIndex] = newMember;
            swarmSize++;
        }
        else {
            Hornet[] sizedUpSwarm = new Hornet[(swarmMembers.length + 1)];
            for (int i = 0; i < swarmMembers.length; i++) {
                sizedUpSwarm[i] = swarmMembers[i];
            }
            sizedUpSwarm[swarmMembers.length] = newMember;
            swarmMembers = sizedUpSwarm;
            swarmSize++;
        }
    }
    public boolean removeHornet(Hornet riddance) {
        boolean riddanceExists = false;
        int riddanceIndex = 0;
        for (int i = 0; i < swarmMembers.length; i++) {
            if (swarmMembers[i] == riddance) {
                riddanceExists = true;
                riddanceIndex = i;
                break;
            }
        }
        if (riddanceExists) {
            Hornet[] culledSwarm = new Hornet[swarmMembers.length - 1];
            for (int i = 0; i < riddanceIndex; i++) {
                culledSwarm[i] = swarmMembers[i];
            }
            for (int i = (riddanceIndex + 1); i < (culledSwarm.length); i++) {
                culledSwarm[i - 1] = swarmMembers[i];
            }
            swarmMembers = culledSwarm;
            swarmSize--;
        }
        return riddanceExists;
    }

}