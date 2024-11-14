import java.util.ArrayList;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Language> languages = new ArrayList<>();
        languages.add(new English());
        languages.add(new Spanish());
        languages.add(new French());
        languages.add(new Dutch());

        for (Language lang : languages) {
            System.out.println(lang.getLanguage());
        }

        System.out.print("Select a language from the list: ");
        String langName = sc.nextLine();

        Language language = null;
        for (Language lang : languages) {
            if (lang.getLanguage().equals(langName)) {
                language = lang;
                break;
            }
        }
        while (language == null) {
            System.out.print("That language is not available; Enter again: ");
            langName = sc.nextLine();
            for (Language lang : languages) {
                if (lang.getLanguage().equals(langName)) {
                    language = lang;
                    break;
                }
            }
        }

        sc.close();

        System.out.printf("Language: %s, Author: %s%nHello: %s%nBye: %s%n", language.getLanguage(),
                language.getAuthor(), language.getHello(), language.getBye());

    }
}
