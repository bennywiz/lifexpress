package life.example.xpress;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import life.example.xpress.DataBase.DataHelper;
import life.example.lifexpress.R;
import life.example.xpress.ui.Links;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private AlertDialog.Builder dialoguebuilder,dialog_services;
    private EditText Nom, contact,nom_device,n_device,specification,email;
    private AlertDialog dialog,alert_services;
    Button btn_valider;
    Spinner spin1,spin2;
    String message="";

    ExtendedFloatingActionButton fopen,info,clim,gen,hand,elect,plomb;
    Animation fabopen, fabclose,fabclickwise,fabclickantwise;


    String nm = "";
    String cnt = "";
    String adressemail = "";
    String mg = "En cours d'envoi...";
    String val = "Requête Envoyé";
    String val1 = "Merci de nous choisir, Nous avons bien reçu votre requête";
    String val2 = "Notre équipe technique va vous contacter dans moin de 3 munites";

    String formattedDate;
    Session session;
    DataHelper dataHelper;

  private AppUpdateManager mAppupdatemanager;
    private static final int RC_APP_UPDATE = 100;
    String pays="";
    String lng = "";
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fopen = findViewById(R.id.fab);
        /*
        info = findViewById(R.id.fab_info);
        clim = findViewById(R.id.fab_clim);
        elect = findViewById(R.id.fab_ele);
        gen = findViewById(R.id.fab_group);
        hand = findViewById(R.id.fab_hand);
        plomb =findViewById(R.id.fab_plomb);*/

        fabopen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fabclose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.close_fab);
        fabclickwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clickwise);
        fabclickantwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_antclickwise);


        dataHelper = new DataHelper(MainActivity.this);

         lng = dataHelper.getlangue();
         pays = dataHelper.getpays();


        System.out.println("**************** µµµµµµµµµµµµ **********");

        System.out.println("La langue est :"+lng);
        System.out.println("Le pays est :"+pays);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View comandepopup = getLayoutInflater().inflate(R.layout.pop, null);
        specification = (EditText) comandepopup.findViewById(R.id.specification);
        spin1 = (Spinner) comandepopup.findViewById(R.id.spinner2);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formattedDate = df.format(c);
        // dt.getTime();


        //View btn_services = getLayoutInflater().inflate(R.layout.fragment_home,null,false);


        fopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && checkSelfPermission(Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
                else {

                    PermissionOncall();

                }

            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        if (pays.equals("Ouganda")){
            Menu menu = navigationView.getMenu();
            MenuItem pays_number = menu.findItem(R.id.pays_number);
            pays_number.setTitle("00760152809");
        }

        if (lng.equals("English")){

            Menu menu = navigationView.getMenu();
            MenuItem tache = menu.findItem(R.id.taches);
            tache.setTitle("Tasks");

            MenuItem about = menu.findItem(R.id.nav_slideshow);
            about.setTitle("About Us");

            MenuItem parametre = menu.findItem(R.id.settings);
            parametre.setTitle("Settings");

            if (pays.equals("Ouganda")){

                MenuItem pays_number = menu.findItem(R.id.pays_number);
                pays_number.setTitle("00760152809");
            }

            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.taches,R.id.settings)
                    .setDrawerLayout(drawer)
                    .build();

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

        }
        else {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.taches,R.id.settings)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        }

        //update functions

        mAppupdatemanager = AppUpdateManagerFactory.create(this);
        mAppupdatemanager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {

                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){

                    try {
                        mAppupdatemanager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE,MainActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        //mAppupdatemanager.registerListener(installStateUpdatedListener);

        // update functions
    }
    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState state) {
            if (state.installStatus() == InstallStatus.DOWNLOADED){

                showCompletedUpdate();
            }

        }
    };
    private void showCompletedUpdate(){

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"new app is ready",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("install", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppupdatemanager.completeUpdate();
            }
        });
        snackbar.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (requestCode == RC_APP_UPDATE && requestCode != RESULT_OK){
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onStop() {
        /*if (mAppupdatemanager != null)
           mAppupdatemanager.unregisterListener(installStateUpdatedListener);*/
        super.onStop();
    }

    protected void onResume(){


        super.onResume();
        mAppupdatemanager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {

                if (result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){

                    try {
                        mAppupdatemanager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE,MainActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void PermissionOncall(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
        else {
            if (pays.equals("Ouganda")){
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:00760152809"));
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:+25722281141"));
                startActivity(intent);
            }
        }
    }
    //update functions

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PermissionOncall();
            }
        } else {
            Toast.makeText(this, "Veillez donner la permission, essayer encore!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void saveToAppserver(String msg,String country) {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        System.out.println("******* SYSTEM pays *******");
        System.out.println(country);

        if(lng.equals("English")){
            mg = "Sending...";
            val = "Request sent";
            val1 = "\n" +
                    "Thank you, we have received your request ";
            val2 = "\n" +
                    "Our technical team will contact you in less than 3 munites";
        }
        progressDialog.setMessage(mg);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Links.sendservice, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");

                    if (Response.equals("OK")) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, val, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, val1, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, val2, Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Echoué", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    Log.e("anyText", response);
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Echoué", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("message",msg);
                params.put("country",country);
                return params;
            }
        };
        mysqlhelper.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }
    public void createnewcomande(View view) {
        String view_id = view.getResources().getResourceName(view.getId());
        String domaine ="";
        dialoguebuilder = new AlertDialog.Builder(MainActivity.this);
        final View comandepopup = getLayoutInflater().inflate(R.layout.pop, null);
        spin1 = (Spinner) comandepopup.findViewById(R.id.spinner2);
        spin2 = (Spinner) comandepopup.findViewById(R.id.spinner_divices);


        System.out.println("l'id du View  :"+view_id+"\n id du bouton");

        //charger le spinner des apareil si Dieu le veut

        if (view_id.equals("life.example.lifexpress:id/btn_informatique")){

            if (lng.equals("English")){
                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.devices_informatique_en));
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin2.setAdapter(dataAdapter1);
                domaine = "IT";

                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.service_informatique_en));
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin1.setAdapter(dataAdapter2);
            }
            else {
                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.devices_informatique));
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin2.setAdapter(dataAdapter1);
                domaine = "Informatique";

                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.service_informatique));
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin1.setAdapter(dataAdapter2);
            }

        }
        else if (view_id.equals("life.example.lifexpress:id/btn_electricite")){

            if (lng.equals("English")){
                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.devices_electricité_en));
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin2.setAdapter(dataAdapter1);
                domaine = "Electricity";
                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.service_electr_en));
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin1.setAdapter(dataAdapter2);
            }
            else {
                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.devices_electricité));
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin2.setAdapter(dataAdapter1);
                domaine = "Electricité";
                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.service_electr));
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin1.setAdapter(dataAdapter2);
            }

        }
        else if (view_id.equals("life.example.lifexpress:id/btn_plomberie")){

            if (lng.equals("English")){
                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.devices_plomberie_en));
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin2.setAdapter(dataAdapter1);
                domaine = "Plumbing";
                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.no_services_en));
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin1.setAdapter(dataAdapter2);
            }
            else
            {
                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.devices_plomberie));
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin2.setAdapter(dataAdapter1);
                domaine = "Plomberie";
                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.no_services));
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin1.setAdapter(dataAdapter2);
            }
        }
       else  if (view_id.equals("life.example.lifexpress:id/btn_handy_man")){

           if (lng.equals("English")){
               ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                       android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.devices_handyman_en));
               dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spin2.setAdapter(dataAdapter1);
               domaine = "Handy Man";
               ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                       android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.service_hanyman_en));
               dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spin1.setAdapter(dataAdapter2);
           }
            else {
               ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                       android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.devices_handyman));
               dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spin2.setAdapter(dataAdapter1);
               domaine = "Handy Man";
               ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                       android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.service_hanyman));
               dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spin1.setAdapter(dataAdapter2);
           }
        }
       else if (view_id.equals("life.example.lifexpress:id/btn_climatisation")){

           if (lng.equals("English")){
               ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                       android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.devices_climatisation_en));
               dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spin2.setAdapter(dataAdapter1);
               domaine = "Air conditioning";
               ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                       android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.service_climat_en));
               dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spin1.setAdapter(dataAdapter2);
           }
           else {
               ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                       android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.devices_climatisation));
               dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spin2.setAdapter(dataAdapter1);
               domaine = "Climatisation";
               ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                       android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.service_climat));
               dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spin1.setAdapter(dataAdapter2);
           }
        }
       else{

           if (lng.equals("English")){
               ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                       android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.devices_electrogene_en));
               dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spin2.setAdapter(dataAdapter1);
               domaine = "Generator";
               ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                       android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.service_electrg_en));
               dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spin1.setAdapter(dataAdapter2);
           }
           else {
               ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                       android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.devices_electrogene));
               dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spin2.setAdapter(dataAdapter1);
               domaine = "Groupe électrogene";
               ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                       android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.service_electrg));
               dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spin1.setAdapter(dataAdapter2);
           }

        }

        specification = (EditText) comandepopup.findViewById(R.id.specification);
        nom_device = (EditText) comandepopup.findViewById(R.id.autre_devices);
        n_device =(EditText) comandepopup.findViewById(R.id.n_devices);
        btn_valider = (Button) comandepopup.findViewById(R.id.btn_valider);

        if (lng.equals("English")){
            specification.setHint("Type your problem here");
            nom_device.setHint("Enter the material name");
            n_device.setHint("The number");
            btn_valider.setText("Validate");
        }


        dialoguebuilder.setView(comandepopup);
        dialog = dialoguebuilder.create();
        dialog.show();

        String finalDomaine = domaine;
        btn_valider.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ArrayList<Client> client = new ArrayList<>();
                client = dataHelper.extractClient();


                for (Client c : client)
                {
                    nm = c.getNom();
                    cnt = c.getPhone();
                    adressemail = c.getEmail();
                }

                 if (checkNetworkConnection()==false){
                    if (lng.equals("English")){
                        Toast.makeText(MainActivity.this, "There's no internet connection", Toast.LENGTH_LONG).show();
                    }
                   else {
                        Toast.makeText(MainActivity.this, "Connection internet SVP!", Toast.LENGTH_LONG).show();
                    }
                }
                else{

                    if (spin1.getSelectedItem().toString().equals("Fourniture de materiels")){

                        if(spin2.getSelectedItem().toString().equals("Autres")){

                            if (lng.equals("English")){
                                message ="company/client :"+nm+" \n" +
                                        "Service : Fourniture de materiels \n"+
                                        "Contact :"+cnt+"\n"
                                        +"Email :"+ adressemail+"\n"
                                        +"Domaine :"+ finalDomaine+"\n"
                                        +"Device(s) :"+nom_device.getText().toString()+"\n"
                                        +"Amount :"+ n_device.getText().toString();
                            }
                            else{
                                message ="Nom de l'entreprise/client :"+nm+" \n" +
                                        "Service : Fourniture de materiels \n"+
                                        "Contact :"+cnt+"\n"
                                        +"Email :"+ adressemail+"\n"
                                        +"Domaine :"+ finalDomaine+"\n"
                                        +"Equipement :"+nom_device.getText().toString()+"\n"
                                        +"Nombre :"+ n_device.getText().toString();
                            }

                        }
                        else {

                            if(lng.equals("English")){
                                message ="Company/client :"+nm+" \n" +
                                        "Service : Supply of materials \n"+
                                        "Contact :"+cnt+"\n"
                                        +"Email :"+ adressemail+"\n"
                                        +"Domain :"+ finalDomaine+"\n"
                                        +"Device :"+spin2.getSelectedItem().toString()+"\n"
                                        +"Amount :"+ n_device.getText().toString();
                            }
                            else{
                                message ="Nom de l'entreprise/client :"+nm+" \n" +
                                        "Service : Fourniture de materiels \n"+
                                        "Contact :"+cnt+"\n"
                                        +"Email :"+ adressemail+"\n"
                                        +"Domaine :"+ finalDomaine+"\n"
                                        +"Equipement :"+spin2.getSelectedItem().toString()+"\n"
                                        +"Nombre :"+ n_device.getText().toString();
                            }

                        }

                    }
                    else if (spin1.getSelectedItem().toString().equals("Autres")){
                        if (lng.equals("English")){
                            message ="Company/client :"+nm+" \n" +
                                    "Service :"+specification.getText().toString()+"\n"+
                                    "Contact :"+cnt+"\n"
                                    +"Email :"+ adressemail+"\n"
                                    +"Domain :"+ finalDomaine;
                        }
                        else {
                            message ="Nom de l'entreprise/client :"+nm+" \n" +
                                    "Service :"+specification.getText().toString()+"\n"+
                                    "Contact :"+cnt+"\n"
                                    +"Email :"+ adressemail+"\n"
                                    +"Domaine :"+ finalDomaine;
                        }
                    }
                    else{
                        if (lng.equals("English")){
                            message ="Company/client :"+nm+" \n" +
                                    "Service :"+spin1.getSelectedItem().toString()+"\n"+
                                    "Contact :"+cnt+"\n"
                                    +"Email :"+ adressemail+"\n"
                                    +"Domain :"+ finalDomaine;
                        }
                        else {
                            message ="Nom de l'entreprise/client :"+nm+" \n" +
                                    "Service :"+spin1.getSelectedItem().toString()+"\n"+
                                    "Contact :"+cnt+"\n"
                                    +"Email :"+ adressemail+"\n"
                                    +"Domaine :"+ finalDomaine;
                        }
                    }
                    System.out.println("********************************************");
                    System.out.println(message);
                    save(v,finalDomaine);
                    saveToAppserver(message,pays);
                    dialog.dismiss();

                }
            }
        });

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (lng.equals("English")){
                    if (spin1.getSelectedItem().toString().equals("Supply of materials")){
                        spin2.setVisibility(View.VISIBLE);
                        n_device.setVisibility(View.VISIBLE);
                        specification.setVisibility(View.GONE);
                    }
                    else if (spin1.getSelectedItem().toString().equals("Others")){

                        specification.setVisibility(View.VISIBLE);
                        spin2.setVisibility(View.GONE);
                        n_device.setVisibility(View.GONE);
                        nom_device.setVisibility(View.GONE);

                    }
                    else {
                        specification.setVisibility(View.GONE);
                        spin2.setVisibility(View.GONE);
                        n_device.setVisibility(View.GONE);
                        nom_device.setVisibility(View.GONE);
                    }
                }
                else{
                    if (spin1.getSelectedItem().toString().equals("Fourniture de materiels")){
                        spin2.setVisibility(View.VISIBLE);
                        n_device.setVisibility(View.VISIBLE);
                        specification.setVisibility(View.GONE);
                    }
                    else if (spin1.getSelectedItem().toString().equals("Autres")){

                        specification.setVisibility(View.VISIBLE);
                        spin2.setVisibility(View.GONE);
                        n_device.setVisibility(View.GONE);
                        nom_device.setVisibility(View.GONE);

                    }
                    else {
                        specification.setVisibility(View.GONE);
                        spin2.setVisibility(View.GONE);
                        n_device.setVisibility(View.GONE);
                        nom_device.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (lng.equals("English")){
                    if (spin2.getSelectedItem().toString().equals("Others")){
                        nom_device.setVisibility(View.VISIBLE);
                    }
                    else {
                        nom_device.setVisibility(View.GONE);
                    }
                }
                else {
                    if (spin2.getSelectedItem().toString().equals("Autres")){
                        nom_device.setVisibility(View.VISIBLE);
                    }
                    else {
                        nom_device.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return  (networkInfo!=null && networkInfo.isConnected());
    }
    public void save(View view,String domaine){

        ArrayList<Client> client = new ArrayList<>();
        client = dataHelper.extractClient();


        for (Client c : client)
        {
            nm = c.getNom();
            cnt = c.getPhone();
        }

       String name = nm;
       String contacts = cnt;
       String services = spin1.getSelectedItem().toString();
       String autres = specification.getText().toString();

       String equipement = spin2.getSelectedItem().toString();
       String n_eq = n_device.getText().toString();

       Date c = Calendar.getInstance().getTime();
       Tache t=new Tache();
       SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
       String dat = df.format(c);

       DataHelper databaseHelper = new DataHelper(this);


        if (spin1.getSelectedItem().toString().equals("Fourniture de materiels")){

            t = new Tache(name,contacts,"Fourniture de materiels :"+equipement+" N° :"+n_eq,dat,domaine);

        }
       else if (spin1.getSelectedItem().toString().equals("Autres")){

            t = new Tache(name,contacts,autres,dat,domaine);

       }
       else{

            t = new Tache(name,contacts,services,dat,domaine);
       }

        boolean resultat = databaseHelper.enregistrerTache(t);

        if (resultat==true){
            System.out.println("Succes");
        }
        else {
            System.out.println("Error");
        }
    }
}