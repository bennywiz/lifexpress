package life.example.lifexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import life.example.xpress.DataBase.DataHelper;
import life.example.xpress.MainActivity;
import life.example.xpress.SelectLangage;
import life.example.xpress.Session;
import life.example.xpress.langue;
import life.example.xpress.pays;

public class select_country extends AppCompatActivity {

    TextView fr,en;
    RelativeLayout precedent;
    Spinner country;
    DataHelper dataHelper;
    ImageView suivant;


    String p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);
        dataHelper = new DataHelper(select_country.this);

        fr = findViewById(R.id.french_text);
        en = findViewById(R.id.english_text);
        precedent = findViewById(R.id.precedent);
        country = findViewById(R.id.contry);
        suivant = findViewById(R.id.suivant);


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(select_country.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.counrty_aray));
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(dataAdapter1);




        Session session = Session.getInstance(select_country.this);
        String langue = session.getData("langue");

        if(langue.equals("Francais"))
        {
            en.setVisibility(View.GONE);
            fr.setVisibility(View.VISIBLE);
           // terminer.setText("Terminer");
        }
        else {
            en.setVisibility(View.VISIBLE);
            fr.setVisibility(View.GONE);
            //terminer.setText("Finish");
        }


        precedent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dataHelper.deletelangue(new langue(langue))){
                    Intent intent = new Intent(select_country.this, SelectLangage.class);
                    startActivity(intent);
                    overridePendingTransition( R.layout.right_in, R.layout.right_out);
                }
            }
        });

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                p = country.getSelectedItem().toString();

                if (dataHelper.savepays(new pays(p))){
                    //    session.saveData("pays",p);

                    Intent intent = new Intent(select_country.this, register.class);
                    startActivity(intent);
                    overridePendingTransition( R.layout.left_in, R.layout.left_out);
                   // finish();
                }

            }
        });


    }

    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }*/
}