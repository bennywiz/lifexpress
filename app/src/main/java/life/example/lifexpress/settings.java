package life.example.lifexpress;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import life.example.xpress.Client;
import life.example.xpress.DataBase.DataHelper;
import life.example.xpress.MainActivity;
import life.example.xpress.langue;
import life.example.xpress.pays;

public class settings extends Fragment {

    TextView titre_lang,titre_pays,text_francais,pays_name,counrty_title,langue_title,nom_cl,tel_cl,mail_cl;
    AlertDialog.Builder dialoguebuilder;
    CheckBox fr,en,burundi,ouganda;
    DataHelper dataHelper;
    AlertDialog dialog;
    LinearLayout languecontainer,payscontainer,contacts;
    String pays;
    EditText noms,tel,email;
    Button okay ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        titre_lang = view.findViewById(R.id.titre_langue);
        titre_pays = view.findViewById(R.id.titre_pays);
        text_francais = view.findViewById(R.id.text_francais);
        languecontainer = view.findViewById(R.id.langue_container);
        payscontainer = view.findViewById(R.id.pays_container);
        pays_name = view.findViewById(R.id.pays_name);
        contacts = view.findViewById(R.id.contacts_);
        nom_cl = view.findViewById(R.id.nom_clients);
        tel_cl = view.findViewById(R.id.tel_clients);
        mail_cl = view.findViewById(R.id.email_clients);
        dataHelper = new DataHelper(getActivity());

        ArrayList<Client> client= dataHelper.extractClient();
        for (Client c : client){

            nom_cl.setText("Client : "+c.getNom());
            tel_cl.setText("Tél    : "+c.getPhone());
            mail_cl.setText("Email : "+c.getEmail());

        }

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popConatct();
            }
        });


        pays = dataHelper.getpays();
        if (pays.equals("Ouganda")){
           pays_name.setText("Ouganda");
        }

        if (dataHelper.getlangue().equals("English")) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Settings");
            titre_lang.setText("Language");
            titre_pays.setText("Country");
            text_francais.setText("French");
        }

        languecontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poplangue();
            }
        });
        payscontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poppays();
            }
        });

        return view;
    }
    public void popConatct() {

        dialoguebuilder = new AlertDialog.Builder(getContext());
        final View comandepopup = getLayoutInflater().inflate(R.layout.pop_contact, null);

        noms = comandepopup.findViewById(R.id.text_name);
        tel = comandepopup.findViewById(R.id.text_tele);
        email = comandepopup.findViewById(R.id.text_email);
        okay = comandepopup.findViewById(R.id.btn_ok);

        DataHelper hp = new DataHelper(getContext());
        ArrayList<Client> client= hp.extractClient();

        if (hp.getlangue().equals("English")) {

            noms.setHint("Name");
            tel.setHint("Phone Number");
            email.setHint("Email adress");
        }

        for (Client c : client){
            noms.setText(c.getNom());
            tel.setText(c.getPhone());
            email.setText(c.getEmail());
        }


        dialoguebuilder.setView(comandepopup);
        dialog = dialoguebuilder.create();
        dialog.show();

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nm = noms.getText().toString();
                String t = tel.getText().toString();
                String e = email.getText().toString();

                if (nm.equalsIgnoreCase("")){
                    if (hp.getlangue().equals("English")){
                        noms.setHint("Your name please!");//
                        noms.setError("Your name please!");//
                    }
                    else {
                        noms.setHint("le nom SVP!");//
                        noms.setError("le nom SVP!");//
                    }
                }
                else if (t.equalsIgnoreCase("")){
                    if(hp.getlangue().equals("English")){
                        tel.setHint("Your contact please!");//
                        tel.setError("Your contact please!");//
                    }
                    else {
                        tel.setHint("contact SVP!");//
                        tel.setError("contact SVP!");//
                    }
                }
                else if (e.equalsIgnoreCase("")){
                    if (hp.getlangue().equals("English")){
                        email.setHint("Your email adress please!");//
                        email.setError("Your email adress please!");//
                    }
                    else {
                        email.setHint("adresse email SVP!");//
                        email.setError("adresse email SVP!");//
                    }

                }
                else if (checkEmail(e)==false){
                    if (hp.getlangue().equals("English")){
                        email.setHint("Your email adress is incorrect!");
                        email.setError("Your email adress is incorrect!");
                    }
                    else {
                        email.setHint("Votre adresse email est incorrect!");
                        email.setError("Votre adresse email est incorrect!");
                    }
                }
                else if(!nm.equalsIgnoreCase("") || !t.equalsIgnoreCase("") || e.equalsIgnoreCase("")){
                    Client c = new Client(1,nm,t,e);
                    if (hp.updateClient(c)){
                       // dialog.dismiss();
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });

    }
    public void poplangue() {

        String domaine ="";
        dialoguebuilder = new AlertDialog.Builder(getContext());
        final View comandepopup = getLayoutInflater().inflate(R.layout.pop_langue, null);
        fr = (CheckBox) comandepopup.findViewById(R.id.check_francais);
        en = (CheckBox) comandepopup.findViewById(R.id.check_anglais);
        langue_title = (TextView) comandepopup.findViewById(R.id.langue_title);
        if (dataHelper.getlangue().equals("English")){
           langue_title.setText("Choose your language");
        }

        dialoguebuilder.setView(comandepopup);
        dialog = dialoguebuilder.create();
        dialog.show();

        if (dataHelper.getlangue().equals("English")){

            fr.setChecked(false);
        }
        else {
            en.setChecked(false);
        }
        fr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (fr.isChecked()){
                    if(dataHelper.deletelangue(new langue("Francais"))){
                        text_francais.setText("Francais");
                        dataHelper.savelangue(new langue("Francais"));
                        System.out.println("$$$$$$$$$$$$$$$$mmmmmmmmm$$$$$$$$$$$");
                        System.out.println("La langue actuel est "+dataHelper.getlangue());
                        dialog.dismiss();

                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Appliquer les modification?")
                                .setContentText("Les modifications prendront effet au prochain redémarrage.")
                                .setConfirmText("maintenant!")
                                .setCancelText("Non")
                                .showCancelButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                       // sDialog.dismissWithAnimation();
                                        Intent intent = new Intent(getActivity(),MainActivity.class);
                                        startActivity(intent);

                                    }
                                })
                                .show();

                    }
                }
            }
        });
        en.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (en.isChecked()){
                    if(dataHelper.deletelangue(new langue(1,"English"))){
                        text_francais.setText("English");
                        dataHelper.savelangue(new langue("English"));
                        System.out.println("$$$$$$$$$$$$$$$$mmmmmmmmm$$$$$$$$$$$");
                        System.out.println("La langue actuel est "+dataHelper.getlangue());
                        dialog.dismiss();

                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Comfirm modifications?")
                                .setContentText("Modifications will take effect at the next restarting.")
                                .setConfirmText("Yes,Restart now!")
                                .setCancelText("Not now")
                                .showCancelButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                       // sDialog.dismissWithAnimation();
                                        Intent intent = new Intent(getActivity(),MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                }
            }
        });

    }
    public void poppays() {

        dialoguebuilder = new AlertDialog.Builder(getContext());
        final View comandepopup = getLayoutInflater().inflate(R.layout.pop_pays, null);
        ouganda = (CheckBox) comandepopup.findViewById(R.id.check_ougnada);
        burundi = (CheckBox) comandepopup.findViewById(R.id.check_burundi);
        counrty_title = (TextView) comandepopup.findViewById(R.id.county_title);

        if (dataHelper.getlangue().equals("English")){
            counrty_title.setText("Choose your country");
        }

        dialoguebuilder.setView(comandepopup);
        dialog = dialoguebuilder.create();
        dialog.show();

        if (dataHelper.getpays().equals("Burundi")){
            ouganda.setChecked(false);
        }
        else {
            burundi.setChecked(false);
        }
        burundi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (burundi.isChecked()){
                    if(dataHelper.deletep(new pays("Burundi"))){
                        pays_name.setText("Burundi");
                        dataHelper.savepays(new pays("Burundi"));
                        dialog.dismiss();

                        if (dataHelper.getlangue().equals("English")){
                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Comfirm modifications?")
                                    .setContentText("Modifications will take effect at the next restarting.")
                                    .setConfirmText("Yes,Restart now!")
                                    .setCancelText("Not now")
                                    .showCancelButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // sDialog.dismissWithAnimation();
                                            Intent intent = new Intent(getActivity(),MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                        }
                        else {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Appliquer les modification?")
                                    .setContentText("Les modifications prendront effet au prochain redémarrage.")
                                    .setConfirmText("maintenant!")
                                    .setCancelText("Non")
                                    .showCancelButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // sDialog.dismissWithAnimation();
                                            Intent intent = new Intent(getActivity(),MainActivity.class);
                                            startActivity(intent);

                                        }
                                    })
                                    .show();
                        }
                    }
                }
            }
        });
        ouganda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ouganda.isChecked()){
                    if(dataHelper.deletep(new pays("Ouganda"))){
                        pays_name.setText("Ouganda");
                        dataHelper.savepays(new pays("Ouganda"));
                        dialog.dismiss();

                        if (dataHelper.getlangue().equals("English")){
                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Comfirm modifications?")
                                    .setContentText("Modifications will take effect at the next restarting.")
                                    .setConfirmText("Yes,Restart now!")
                                    .setCancelText("Not now")
                                    .showCancelButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // sDialog.dismissWithAnimation();
                                            Intent intent = new Intent(getActivity(),MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                        }
                        else {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Appliquer les modification?")
                                    .setContentText("Les modifications prendront effet au prochain redémarrage.")
                                    .setConfirmText("maintenant!")
                                    .setCancelText("Non")
                                    .showCancelButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // sDialog.dismissWithAnimation();
                                            Intent intent = new Intent(getActivity(),MainActivity.class);
                                            startActivity(intent);

                                        }
                                    })
                                    .show();
                        }
                    }
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
