import java.util.List;

public class MovieHall {
    private int id;
    private int capacity;
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public MovieHall(int id, int capacity) {
        this.capacity = capacity;
        this.id = id;
    }
}
