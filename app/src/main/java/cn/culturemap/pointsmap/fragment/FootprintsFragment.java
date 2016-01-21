package cn.culturemap.pointsmap.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.culturemap.pointsmap.R;

/**
 * 足迹 Fragment
 * Created by XP on 2016/1/18.
 */
public class FootprintsFragment extends Fragment {

    public FootprintsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_footprints,container,false);
    }
}
