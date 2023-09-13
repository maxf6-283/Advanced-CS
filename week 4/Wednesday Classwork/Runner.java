import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        MyArrayList<Task> tasks = new MyArrayList<>();
        
        boolean doLoop = true;
        mainloop: while (doLoop) {
            System.out.println(
                    """
                            Select one option
                            1.View Task List.
                            2.Add a Task.
                            3.Remove a Task by Name and Rank.
                            4.Add a Task given a location.
                            5.Remove a Task by index location.
                            6.Replace a Task by location.
                            7.Sort by Rank. 1 is the highest priority followed by subsequent numbers.
                            8.Quit
                            """);

            int ans = sc.nextInt();
            sc.nextLine();

            System.out.println();

            switch(ans) {
                case 1 -> {
                    for(int i = 0; i < tasks.size(); i++) {
                        System.out.println(tasks.get(i));
                    }
                }
                case 2 -> {
                    System.out.println("What task would you like to add: ");
                    String taskName = sc.nextLine();
                    
                    System.out.println("What rank would you like it to have: ");
                    int taskRank = sc.nextInt();
                    sc.nextLine();

                    Task task = new Task(taskName, taskRank);

                    for(int i = 0; i < tasks.size(); i++) {
                        if(tasks.get(i).name().equals(taskName)) {
                            tasks.set(i, task);
                            continue mainloop;
                        }
                    }

                    tasks.add(task);
                }
                case 3 -> {
                    System.out.println("What task would you like to remove: ");
                    String taskName = sc.nextLine();

                    System.out.println("What rank would you like to remove it from: ");
                    int taskRank = sc.nextInt();
                    sc.nextLine();
                    
                    Task task = new Task(taskName, taskRank);
                    tasks.remove(task);
                }
                case 4 -> {
                    System.out.println("What task would you like to add: ");
                    String taskName = sc.nextLine();
                    
                    System.out.println("What rank would you like it to have: ");
                    int taskRank = sc.nextInt();
                    sc.nextLine();

                    System.out.println("What index would you like to place it?: ");
                    int index = sc.nextInt();
                    sc.nextLine();
                    
                    Task task = new Task(taskName, taskRank);

                    for(int i = 0; i < tasks.size(); i++) {
                        if(tasks.get(i).name().equals(taskName)) {
                            tasks.set(i, task);
                            continue mainloop;
                        }
                    }

                    tasks.add(index, task);
                }
                case 5 -> {
                    System.out.println("What index does the task that you'd like to remove have: ");
                    int index = sc.nextInt();
                    sc.nextLine();
                    
                    tasks.remove(index);

                }
                case 6 -> {
                    System.out.println("What is the index of the task you'd like to replace: ");
                    int index = sc.nextInt();
                    sc.nextLine();

                    System.out.println("What task would you like to replace it with: ");
                    String taskName = sc.nextLine();
                    
                    System.out.println("What rank would you like it to have: ");
                    int taskRank = sc.nextInt();
                    sc.nextLine();

                    tasks.set(index, new Task(taskName, taskRank));
                }
                case 7 -> {
                    for(int i = 0; i < tasks.size()-1; i++) {
                        int low = i;
                        for(int j = i+1; j < tasks.size(); j++) {
                            if(tasks.get(j).rank() < tasks.get(low).rank()) {
                                low = j;
                            }
                        }
                        Task temp = tasks.get(i);
                        tasks.set(i, tasks.get(low));
                        tasks.set(low, temp);
                    }
                }
                case 8 -> {
                    doLoop = false;
                }
                default -> {
                    System.out.println("Invalid input");
                }
            }
        }

        sc.close();
    }
}
