package com.sj.news35.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sj.news35.MainActivity;
import com.sj.news35.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sj on 2015/6/28 0028.
 */
public class MenuFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_view, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView list_View = (ListView) view.findViewById(R.id.list_view);
        List<String> lists = initData();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.test_list_item, android.R.id.text1, lists);
        list_View.setAdapter(arrayAdapter);
        list_View.setOnItemClickListener(this);

    }

    private List<String> initData() {
        List<String> lists = new ArrayList<String>();
        lists.add("item " + 1);
        lists.add("item " + 2);
        lists.add("item " + 3);
        return lists;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment = new Fragment1();
                break;
            case 2:
                fragment = new Fragment1();
                break;
        }
        ((MainActivity) getActivity()).switchFragment(fragment);
    }
}
