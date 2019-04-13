package petrinet;

public abstract class Vertex {
    private final long id;
    private final String name;

    public Vertex(long id, String name) {
        if(name == null || name.length() < 1) {
            throw new IllegalArgumentException("\nName must be string of length > 0!\n");
        }
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
