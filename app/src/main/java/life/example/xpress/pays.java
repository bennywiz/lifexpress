package life.example.xpress;

public class pays {
    int id;
    String pays;

    public pays(int id, String pays) {
        this.id = id;
        this.pays = pays;
    }
    public pays (){}

    public pays(String pays) {
        this.pays = pays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

}
