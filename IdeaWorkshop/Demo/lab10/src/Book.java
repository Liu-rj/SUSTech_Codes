public enum Book {
    JAVA("Java",2020),
    CPP("C++",2009),
    DATABASE("Database about postgres",2015),
    DATA_STRUCTURE("Data structure in Java",2000);

    private final String name;
    private final int year;

    Book(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }
}
