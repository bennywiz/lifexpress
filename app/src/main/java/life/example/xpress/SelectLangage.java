package life.example.xpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.IdentityHashMap;

import life.example.lifexpress.R;
import life.example.lifexpress.select_country;
import life.example.xpress.DataBase.DataHelper;

public class SelectLangage extends AppCompatActivity {

    Spinner lang ;
    RelativeLayout btn_next;
    DataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_langage);
        dataHelper = new DataHelper(this);

        lang = findViewById(R.id.language_spinner);
        btn_next = findViewById(R.id.btn_next);

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(SelectLangage.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.lqngue_aray));
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lang.setAdapter(dataAdapter1);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String la = lang.getSelectedItem().toString();

                if (dataHelper.savelangue(new langue(la)))
                {
                    Intent intent = new Intent(SelectLangage.this, select_country.class);
                    startActivity(intent);
                    Session session = new Session(SelectLangage.this);
                    session.saveData("langue",la);
                    overridePendingTransition( R.layout.left_in, R.layout.left_out);
                    finish();
                }
            }
        });
    }
}