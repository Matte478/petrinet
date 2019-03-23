public class Place extends Vertex {
    private int token;

    public Place(long id, String name, int token) throws IllegalArgumentException {
        super(id, name);

        if (token < 0) {
            throw new IllegalArgumentException("Illegal number of tokens!");
        }
        this.token = token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Place ID: " + this.getId() + ", name: " + this.getName() + ", token: " + this.getToken();
    }
}
