package life.example.xpress;

public class Client {

    private  int id;
    private  String nom,email,phone;

    public Client() {
    }

    public Client(int id, String nom, String phone , String email) {
        this.id = id;
        this.nom = nom;
        this.phone = phone;
        this.email = email;
    }
    public Client(String nom, String phone , String email) {
        this.nom = nom;
        this.phone = phone;
        this.email = email;
    }


    public  int getId() {
        return id;
    }

    public  void setId(int id) {
        this.id = id;
    }

    public  String getNom() {
        return nom;
    }

    public  void setNom(String nom) {
        this.nom = nom;
    }

    public  String getEmail() {
        return email;
    }

    public  void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public  void setPhone(String phone) {
        this.phone = phone;
    }
}
