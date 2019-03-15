public class Place {
    private long id;
    private final String name;
    private int token;

    public Place(long id, String name, int token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getToken() {
        return token;
    }
}
