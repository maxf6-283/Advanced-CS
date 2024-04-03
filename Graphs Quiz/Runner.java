public class Runner {
    public static void main(String[] args) {
        Graph<Student> graph = new Graph<>();
        Student allen = new Student("Allen", 1512);
        Student heather = new Student("Heather", 1411);
        Student jose = new Student("Jose", 1111);
        Student maria = new Student("Maria", 1513);
        
        graph.add(allen);
        graph.add(heather);
        graph.add(jose);
        graph.add(maria);

        graph.addEdge(allen, heather);
        graph.addEdge(allen, jose);
        graph.addEdge(allen, maria);
        graph.addEdge(maria, heather);

        System.out.println(graph);

        graph.remove(heather);
        
        System.out.println();
        System.out.println(graph);
    }
}
