public class Main3 {
    public static void main(String[] args) {
        Cinema cinema = new ConcreteCinema();
        cinema.addMovie("ne zha", 90, 1, 70.5, 0, new Time(1, 15));
        cinema.addMovie("name2", 125, 1, 60, 0, new Time(6, 25));
        cinema.addMovie("name3", 125, 3, 58.5, 1, new Time(16, 15));
        cinema.addMovie("name3", 125, 3, 58.5, 1, new Time(16, 15));
        cinema.addMovie("name3", 125, 3, 58.5, 1, new Time(16, 15));
        cinema.addMovie("name3", 125, 3, 58.5, 1, new Time(16, 15));
        cinema.addMovie("name3", 125, 3, 58.5, 1, new Time(16, 15));
        cinema.addMovie("name3", 125, 3, 58.5, 1, new Time(16, 15));
        cinema.addMovie("name3", 125, 3, 58.5, 1, new Time(16, 15));
        cinema.getAllMovies().forEach(System.out::println);
        System.out.println();
    }
}
