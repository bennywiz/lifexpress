package life.example.xpress;

public class Tache {

    private  int id;
    private  String nom;
    private  String contact;
    private  String service;
    private  String dateTache;
    private  String domaine;

    //constructeur

    public Tache(int id, String nom, String contact, String service,String dt,String domaine) {
        this.id = id;
        this.nom = nom;
        this.contact = contact;
        this.service = service;
        this.dateTache = dt;
        this.domaine = domaine;
    }


    public  Tache(String nom, String contact, String service, String dt, String domaine) {
        this.nom = nom;
        this.contact = contact;
        this.service = service;
        this.dateTache = dt;
        this.domaine = domaine;
    }

    public Tache() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDateTache() {
        return dateTache;
    }

    public void setDateTache(String dateTache) {
        this.dateTache = dateTache;
    }


    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getDomaine() {
        return domaine;
    }
}
