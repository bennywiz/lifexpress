package life.example.xpress;

public class langue {
    int id;
    String langue;

    public langue(int id, String langue) {
        this.id = id;
        this.langue = langue;
    }

    public langue(String langue) {
        this.langue = langue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

}
