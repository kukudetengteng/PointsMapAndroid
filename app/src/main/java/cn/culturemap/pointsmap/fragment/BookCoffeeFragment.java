package cn.culturemap.pointsmap.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.culturemap.pointsmap.R;

/**
 * 图书/Coffee Fragment
 * Created by XP on 2016/1/18.
 */
public class BookCoffeeFragment extends Fragment {

    public BookCoffeeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_coffee,container,false);
    }
}
