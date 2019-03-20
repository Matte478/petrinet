public class Place extends Point {
    private int token;

    public Place(long id, String name, int token) throws IllegalArgumentException {
        super(id, name);

        if (token < 0) {
            throw new IllegalArgumentException("Illegal number of tokens!");
        } else {
            this.token = token;
        }
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getToken() {
        return token;
    }
}
