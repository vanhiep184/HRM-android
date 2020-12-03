package eu.berdosi.app.heartbeat.Tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import eu.berdosi.app.heartbeat.R;
import eu.berdosi.app.heartbeat.Utils.CustomListAdapter;
import eu.berdosi.app.heartbeat.Utils.HistoryMeasure;
import eu.berdosi.app.heartbeat.Utils.SQLiteHelper;

public class HistoryMeasureFragment extends Fragment {

    List<HistoryMeasure> historyMeasureList;

    private View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_history_measurement,container,false);

        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            ListView historyList = rootView.findViewById(R.id.history_list);
            SQLiteHelper db = new SQLiteHelper(getContext());
            historyMeasureList = db.get();
            historyList.setAdapter(new CustomListAdapter(historyMeasureList,getActivity()));
        }
    }
}
