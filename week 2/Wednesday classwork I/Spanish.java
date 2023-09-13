public class Spanish implements Language {
    @Override
    public String getLanguage() {
        return "Spanish";
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getHello() {
        return "Hola";
    }

    @Override
    public String getBye() {
        return "Adios";
    }
    
}
