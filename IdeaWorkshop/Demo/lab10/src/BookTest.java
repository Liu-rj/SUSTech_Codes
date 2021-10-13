import java.util.EnumSet;

public class BookTest {
    public static void main(String[] args) {
        Book book = Book.CPP;
        System.out.println(book.getName());
        Book[] books = Book.values();
        for (Book b : books) {
            System.out.println(b);
        }
        for (Book b : EnumSet.range(Book.CPP,Book.DATA_STRUCTURE)) {
            System.out.println(b.getName());
            System.out.println(b.getYear());
        }
    }
}
