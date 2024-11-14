public enum Age {
    CHILD, ADULT;

    @Override
    public String toString() {
        switch (this) {
            case ADULT:
                return "Adult";
            case CHILD:
                return "Child";
            default:
                return "N/A";
        }
    }
}
