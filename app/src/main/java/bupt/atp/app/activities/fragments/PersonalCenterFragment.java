package bupt.atp.app.activities.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bupt.atp.app.R;


/**
 * Created by tangye on 2018/6/26.
 */

public class PersonalCenterFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pc_layout, container, false);
        return view;
    }
}
