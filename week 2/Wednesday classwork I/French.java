public class French implements Language {

    @Override
    public String getLanguage() {
        return "French";
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getHello() {
        return "Bonjour";
    }

    @Override
    public String getBye() {
        return "Au Reviour";
    }
}
