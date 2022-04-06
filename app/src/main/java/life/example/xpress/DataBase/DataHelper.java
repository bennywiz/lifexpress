package life.example.xpress.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import life.example.xpress.Client;
import life.example.xpress.Tache;
import life.example.xpress.langue;
import life.example.xpress.pays;

import java.util.ArrayList;

public class DataHelper extends SQLiteOpenHelper {


    public DataHelper(@Nullable Context context) {
        super(context, "EXPRESS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS  tache (ID INTEGER PRIMARY KEY AUTOINCREMENT,client TEXT,contact TEXT,service TEXT,datetache " +
                "TEXT,domaine TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS  langue (ID INTEGER PRIMARY KEY AUTOINCREMENT,langue TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS  pays (ID INTEGER PRIMARY KEY AUTOINCREMENT,pays TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS  client (ID INTEGER PRIMARY KEY AUTOINCREMENT,nom TEXT,phone TEXT,email TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

      db.execSQL("DROP TABLE IF EXISTS tache");
      db.execSQL("DROP TABLE IF EXISTS langue");
      db.execSQL("DROP TABLE IF EXISTS pays");
      db.execSQL("DROP TABLE IF EXISTS client");
      onCreate(db);
    }
    public void updateTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("alter TABLE tache ADD domaine TEXT");
    }

    public  boolean savelangue(langue l){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("langue",l.getLangue());
        long result = db.insert("langue",null,values);

        if (result !=-1){
            return true;
        }
        else return false;
    }
    public  boolean saveClient(Client c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom",c.getNom());
        values.put("phone",c.getPhone());
        values.put("email",c.getEmail());
        long result = db.insert("client",null,values);

        if (result !=-1){
            return true;
        }
        else return false;
    }
    public  boolean savepays(pays p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pays",p.getPays());
        long result = db.insert("pays",null,values);

        if (result !=-1){
            return true;
        }
        else return false;
    }

    public ArrayList<langue> extractlangue(){
        ArrayList<langue> listlangue = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.rawQuery("SELECT *FROM langue",null);

        if(cursor.moveToFirst()){
             do{
                 int id = Integer.parseInt(cursor.getString(0));
                 String langue = cursor.getString(1);
                 listlangue.add(new langue(id,langue));
             }while(cursor.moveToNext());
         }
        return listlangue;
    }
    public ArrayList<Client> extractClient(){
        ArrayList<Client> listclient = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT *FROM client",null);

        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String nom = cursor.getString(1);
                String phone = cursor.getString(2);
                String email = cursor.getString(3);
                listclient.add(new Client(id,nom,phone,email));
            }while(cursor.moveToNext());
        }
        return listclient;
    }

    public boolean deletelangue(langue l){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "langue=?";
        String whereArgs[] = {l.getLangue()};
        long i = db.delete("langue", whereClause, whereArgs);

        if(i!=-1){
            return true;
        }
        else return false;
    }
    public boolean deletep(pays p){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "pays=?";
        String whereArgs[] = {p.getPays()};
        long i = db.delete("pays", whereClause, whereArgs);

        if(i!=-1){
            return true;
        }
        else return false;
    }
    public boolean deleteOnepays(pays p){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "pays=?";
        String whereArgs[] = {p.getPays()};
        long i = db.delete("pays", whereClause, whereArgs);

        if(i!=-1){
            return true;
        }
        else return false;
    }
    public String getpays(){
        String pays = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT *FROM pays",null);

        if(cursor.moveToFirst()){
            do{
               pays = cursor.getString(1);
            }while(cursor.moveToNext());
        }
        return pays;
    }
    public String getlangue(){
        String lang = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT *FROM langue",null);

        if(cursor.moveToFirst()){
            do{
                lang = cursor.getString(1);
            }while(cursor.moveToNext());
        }
        return lang;
    }
    public ArrayList<pays> extractpays(){
        ArrayList<pays> listpays = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT *FROM pays",null);

        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String pays = cursor.getString(1);
                listpays.add(new pays(id,pays));
            }while(cursor.moveToNext());
        }
        return listpays;
    }

    public boolean checklangue(){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT *FROM langue",null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count > 0){
            return true;
        }
        else return false;
    }
    public boolean checkPays(){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT *FROM pays",null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count > 0){
            return true;
        }
        else return false;
    }


    public boolean enregistrerTache(Tache tache){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("client", tache.getNom());
        values.put("contact", tache.getContact());
        values.put("service", tache.getService());
        values.put("datetache", tache.getDateTache());
        values.put("domaine",tache.getDomaine());

        long result = db.insert("tache", null, values);
        //db.close();

        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Tache> getAllTask(){
        ArrayList<Tache> t = new ArrayList<>();
        String request = "SELECT *FROM tache";
        SQLiteDatabase db = getWritableDatabase();
         Cursor c = db.rawQuery(request,null);
         if (c.moveToFirst()){
             do {
                 int id = c.getInt(0);
                 String client = c.getString(1);
                 String contact = c.getString(2);
                 String service = c.getString(3);
                 String dt = c.getString(4);
                 String dm = c.getString(5);
                 Tache tache = new Tache(id,client,contact,service,dt,dm);
                 t.add(tache);
             }while (c.moveToNext());
         }
        return t;
    }


    public void deleteTache() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tache",null,null);
        db.close();
    }
    public boolean deleteOneTache(Tache tache){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "ID=?";
        String whereArgs[] = {String.valueOf(tache.getId())};
        long i = db.delete("tache", whereClause, whereArgs);

        if(i!=-1){
            return true;
        }
        else return false;
    }
    public boolean deletepays(pays pays){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "ID=?";
        String whereArgs[] = {String.valueOf(pays.getId())};
        long i = db.delete("pays", whereClause, whereArgs);

        if(i!=-1){
            return true;
        }
        else return false;
    }
    public boolean deleteLangue(langue langue){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "ID=?";
        String whereArgs[] = {String.valueOf(langue.getId())};
        long i = db.delete("langue", whereClause, whereArgs);

        if(i!=-1){
            return true;
        }
        else return false;
    }
    public boolean updatelangue(langue lang) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",String.valueOf(lang.getId()));
        contentValues.put("langue",lang.getLangue());

        long rep = db.update("langue", contentValues,"ID=?", new String[]{String.valueOf(lang.getId())});

        if (rep!=-1){
            return true;
        }
        else return false;
    }
    public boolean updateClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",String.valueOf(client.getId()));
        contentValues.put("nom",client.getNom());
        contentValues.put("phone",client.getPhone());
        contentValues.put("email",client.getEmail());
        long rep = db.update("client", contentValues,"ID=?", new String[]{String.valueOf(client.getId())});
        if (rep!=-1){
            return true;
        }
        else return false;
    }
}
