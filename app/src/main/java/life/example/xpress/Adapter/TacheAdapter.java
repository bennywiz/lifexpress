package life.example.xpress.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import life.example.xpress.DataBase.DataHelper;
import life.example.xpress.MainActivity;
import life.example.xpress.Tache;
import life.example.lifexpress.R;
import life.example.xpress.mysqlhelper;
import life.example.xpress.ui.Links;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TacheAdapter extends RecyclerView.Adapter<TacheAdapter.TacheVH> implements Filterable {

    ArrayList<Tache> taches;
    List<Tache> listTache;
    Context context;

    public TacheAdapter(ArrayList<Tache> taches, Context context) {
        this.taches = taches;
        this.context = context;
        this.listTache = new ArrayList<>(taches);
    }

    @NonNull
    @Override
    public TacheVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.taskrow,parent,false);

        TacheVH tvh = new TacheVH(view);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TacheVH holder, int position) {

        Tache t = taches.get(position);

        holder.dateTache.setText(t.getDateTache());
        holder.client.setText(t.getNom());
        holder.contact.setText(t.getContact());
        holder.service.setText(t.getDomaine()+" : "+t.getService());

        holder.renvoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String message ="Nom de l'entreprise/client :"+t.getNom()+" \n" +
                        "Service :"+t.getService()+"\n"+
                        "Contact :"+t.getContact()+
                       "Domaine :"+t.getDomaine();
                String subject = "Demande d\'un Service";
                saveToAppserver(message);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("suppression");
                builder.setMessage("Supprimer ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataHelper dataHelper = new DataHelper(context);
                        boolean result= dataHelper.deleteOneTache(t);
                        if (result){
                            Toast.makeText(context,"Supprimée",Toast.LENGTH_LONG).show();
                            taches.remove(t);
                            notifyDataSetChanged();
                        }
                        else {Toast.makeText(context,"failed",Toast.LENGTH_LONG).show();}
                    }
                });
                builder.setNegativeButton("Non",null);
                builder.show();


            }
        });



    }

    @Override
    public int getItemCount() {

        return taches.size();
    }
    private void saveToAppserver(String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("En cours d'envoi...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Links.sendservice, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(" Reponse json *******************");
                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");

                    if (Response.equals("OK")) {
                        Toast.makeText(context, "Envoyé", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();


                    } else {
                        Toast.makeText(context, "Echoué", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                } catch (JSONException e) {
                    Log.e("anyText", response);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Echoué", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("message",msg);
                return params;
            }
        };
        mysqlhelper.getInstance(context).addToRequestQueue(stringRequest);
    }
    @Override
    public Filter getFilter() {

        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> fiteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()){
                for (Tache t:listTache){
                    fiteredList.add(t.getDateTache());
                }
               // fiteredList.addAll(listTache.get);
            }
            else {
                for (Tache move:listTache){
                    if (move.getDateTache().contains(constraint.toString().toLowerCase())){
                        fiteredList.add(move.getDateTache());
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterResults;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listTache.clear();
            listTache.add((Tache) results.values);
            notifyDataSetChanged();

        }
    };

    class TacheVH extends RecyclerView.ViewHolder{

        TextView dateTache,service,client,contact;
        //,,
        Button delete,renvoi;
       // Layout layout;

        public TacheVH(@NonNull View v) {
            super(v);
            dateTache = (TextView) v.findViewById(R.id.date_tache);
           client = (TextView) v.findViewById(R.id.nom_client);
           contact = (TextView) v.findViewById(R.id.contact_client);
            service = (TextView) v.findViewById(R.id.service_client);
            renvoi = (Button) v.findViewById(R.id.btn_renvoie);
            delete = (Button) v.findViewById(R.id.btn_delete);
        }
    }
}
