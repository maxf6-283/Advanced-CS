public class ToDo {
    private String task;
    private int rank;

    public ToDo(String myTask, int myRank) {
        task = myTask;
        rank = myRank;
    }

    @Override
    public String toString() {
        return rank + " - " + task;
    }
}