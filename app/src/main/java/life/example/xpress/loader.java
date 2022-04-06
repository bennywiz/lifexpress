package life.example.xpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

import life.example.lifexpress.R;
import life.example.xpress.DataBase.DataHelper;

public class loader extends AppCompatActivity {
    private static int loader_time = 3000;
    DataHelper dataHelper;
    langue l;
    ArrayList<langue> listlangue = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        dataHelper = new DataHelper(this);
        listlangue = dataHelper.extractlangue();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dataHelper.checklangue()){
                    for (langue l:listlangue){
                       // Toast.makeText(loader.this, l.getLangue(), Toast.LENGTH_SHORT).show();
                        if(l.getLangue().equals("fr")){
                            Session session = Session.getInstance(loader.this);
                            session.saveData("langue","fr");

                            Intent loaderintent = new Intent(loader.this, MainActivity.class);
                            startActivity(loaderintent);
                            finish();
                        }
                        else {
                            Session session = new Session(loader.this);
                            session.saveData("langue","en");

                            Intent loaderintent = new Intent(loader.this, MainActivity.class);
                            startActivity(loaderintent);
                            finish();
                        }
                    }

                }
                else {
                    Intent loaderintent = new Intent(loader.this, SelectLangage.class);
                    startActivity(loaderintent);
                    finish();
                }
            }
        },loader_time);
    }
}