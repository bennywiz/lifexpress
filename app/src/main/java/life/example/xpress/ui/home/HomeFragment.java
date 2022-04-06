package life.example.xpress.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import life.example.lifexpress.R;
import life.example.xpress.DataBase.DataHelper;
import life.example.xpress.MainActivity;
import life.example.xpress.Session;

public class HomeFragment extends Fragment {

    TextView info ,plomb,elect,clim,group;
    DataHelper dataHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        dataHelper = new DataHelper(getContext());
        String lng = dataHelper.getlangue();

        if (lng.equals("English")){
            info = root.findViewById(R.id.text_info);
            plomb = root.findViewById(R.id.text_plo);
            elect = root.findViewById(R.id.text_elect);
            clim = root.findViewById(R.id.text_clim);
            group = root.findViewById(R.id.text_gen);

            info.setText("IT");
            plomb.setText("Plumbing");
            elect.setText("Electricity");
            clim.setText("Air conditioning");
            group.setText("Generator");
        }


        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}