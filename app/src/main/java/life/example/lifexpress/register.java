package life.example.lifexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import life.example.xpress.Client;
import life.example.xpress.DataBase.DataHelper;
import life.example.xpress.MainActivity;
import life.example.xpress.SelectLangage;
import life.example.xpress.Session;
import life.example.xpress.langue;
import life.example.xpress.pays;

public class register extends AppCompatActivity {

    Button terminer;
    EditText nom,phone,email;
    DataHelper dataHelper;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        terminer = findViewById(R.id.btn_finish);
        dataHelper = new DataHelper(register.this);
        relativeLayout = findViewById(R.id.precedent1);

        nom = findViewById(R.id.editTextTextPersonName);
        phone = findViewById(R.id.editTextTextPersonName2);
        email = findViewById(R.id.editTextTextPersonName3);

        Session session = Session.getInstance(register.this);
        String langue = session.getData("langue");

        if (langue.equals("English")){
            nom.setHint("Your name");
            phone.setHint("Your Contact");
            email.setHint("your Email address");
            terminer.setText("Finish");
        }

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dataHelper.deletelangue(new langue(langue))){
                    Intent intent = new Intent(register.this, select_country.class);
                    startActivity(intent);
                    overridePendingTransition( R.layout.right_in, R.layout.right_out);
                }
            }
        });

        terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm = nom.getText().toString();
                String cnt = phone.getText().toString();
                String adressemail = email.getText().toString();

                if (nm.equalsIgnoreCase("")){
                    if (langue.equals("English")){
                        nom.setHint("Your name please!");//
                        nom.setError("Your name please!");//
                    }
                    else {
                        nom.setHint("le nom SVP!");//
                        nom.setError("le nom SVP!");//
                    }

                }
                else if (cnt.equalsIgnoreCase("")){
                    if(langue.equals("English")){
                        phone.setHint("Your contact please!");//
                        phone.setError("Your contact please!");//
                    }
                    else {
                        phone.setHint("contact SVP!");//
                        phone.setError("contact SVP!");//
                    }
                }
                else if (adressemail.equalsIgnoreCase("")){
                    if (langue.equals("English")){
                        email.setHint("Your email adress please!");//
                        email.setError("Your email adress please!");//
                    }
                    else {
                        email.setHint("adresse email SVP!");//
                        email.setError("adresse email SVP!");//
                    }

                }
                else if (checkEmail(adressemail)==false){
                    if (langue.equals("English")){
                        email.setHint("Your email adress is incorrect!");
                        email.setError("Your email adress is incorrect!");
                    }
                    else {
                        email.setHint("Votre adresse email est incorrect!");
                        email.setError("Votre adresse email est incorrect!");
                    }
                }
                else if (dataHelper.saveClient(new Client(nm,cnt,adressemail))){

                    Intent intent = new Intent(register.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition( R.layout.left_in, R.layout.left_out);
                    finish();
                }
            }
        });
    }
    public boolean checkEmail(String email){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}