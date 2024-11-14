import java.util.ArrayList;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Animal> animals = new ArrayList<>();

        animals.add(new Bird("Tweety"));
        animals.add(new Cat("Mr. Whiskers"));
        animals.add(new Dog("Fido"));

        for (Animal anim : animals) {
            anim.printInfo();
        }

        System.out.println();

        for (Animal anim : animals) {
            System.out.println(anim.getName());
        }

        System.out.print("Search for the animal: ");
        String animName = sc.nextLine();

        Animal animal = null;
        for (Animal anim : animals) {
            try {
                if (anim.getName().substring(anim.getName().length() - animName.length()).equals(animName)) {
                    animal = anim;
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
            }
        }

        while (animal == null) {
            System.out.print("Invalid name, enter again: ");
            animName = sc.nextLine();

            for (Animal anim : animals) {
                try {
                    if (anim.getName().substring(anim.getName().length() - animName.length()).equals(animName)) {
                        animal = anim;
                        break;
                    }
                } catch (IndexOutOfBoundsException e) {
                }
            }
        }

        animal.printInfo();

        System.out.print("Remove the animal: ");
        animName = sc.nextLine();

        animal = null;
        for (Animal anim : animals) {
            try {
                if (anim.getName().substring(anim.getName().length() - animName.length()).equals(animName)) {
                    animal = anim;
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
            }
        }

        while (animal == null) {
            System.out.print("Invalid name, enter again: ");
            animName = sc.nextLine();

            for (Animal anim : animals) {
                try {
                    if (anim.getName().substring(anim.getName().length() - animName.length()).equals(animName)) {
                        animal = anim;
                        break;
                    }
                } catch (IndexOutOfBoundsException e) {
                }
            }
        }

        animals.remove(animal);

        for (Animal anim : animals) {
            System.out.println(anim.getName());
        }

        sc.close();
    }
}