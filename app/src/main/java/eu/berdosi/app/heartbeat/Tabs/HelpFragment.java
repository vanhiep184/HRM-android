package eu.berdosi.app.heartbeat.Tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import eu.berdosi.app.heartbeat.R;

public class HelpFragment extends Fragment {

    private View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView =  inflater.inflate(R.layout.fragment_help,container,false);
        return rootView;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Toast.makeText(this.getActivity(), "help resume", Toast.LENGTH_SHORT).show();
//    }
}
