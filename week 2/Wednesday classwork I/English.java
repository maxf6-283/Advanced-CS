public class English implements Language{
    @Override
    public String getLanguage() {
        return "English";
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getHello() {
        return "Hello";
    }

    @Override
    public String getBye() {
        return "Bye";
    }
    
}
