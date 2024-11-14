public class Dutch implements Language {
    @Override
    public String getLanguage() {
        return "Dutch";
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getHello() {
        return "Hallo";
    }

    @Override
    public String getBye() {
        return "Doei";
    }
    
}
