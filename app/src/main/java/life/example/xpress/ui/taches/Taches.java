package life.example.xpress.ui.taches;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import life.example.xpress.Adapter.TacheAdapter;
import life.example.xpress.DataBase.DataHelper;
import life.example.xpress.MainActivity;
import life.example.xpress.Tache;
import life.example.lifexpress.R;

import java.util.ArrayList;


public class Taches extends Fragment {

    ArrayList<Tache> listTache;
    RecyclerView recyclerView;

    TacheAdapter tacheAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_taches, container, false);
        DataHelper dataHelper = new DataHelper(getActivity());
        if (dataHelper.getlangue().equals("English")) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Tasks");
        }


        listTache = dataHelper.getAllTask();

        System.out.println("*************************tache *************************");
        for (Tache t : listTache) {
            System.out.println("tache :" + t.getNom());
        }

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);

        tacheAdapter = new TacheAdapter(listTache, getActivity());
        recyclerView.setAdapter(tacheAdapter);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(lm);


        return root;
    }
}