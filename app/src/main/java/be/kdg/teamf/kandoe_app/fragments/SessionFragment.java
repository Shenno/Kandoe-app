package be.kdg.teamf.kandoe_app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;

import be.kdg.teamf.kandoe_app.R;
import be.kdg.teamf.kandoe_app.adapter.RipAdapter;
import be.kdg.teamf.kandoe_app.adapter.Student;

/**
 * Created by admin on 11/03/2016.
 */
public class SessionFragment extends Fragment {

    private ArrayList<Student> mDataSet;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_session, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        // Layout Managers:
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDataSet = new ArrayList<Student>();
        loadData();
        mRecyclerView.setVisibility(View.VISIBLE);
        // Creating Adapter object
        RipAdapter mAdapter = new RipAdapter(getActivity(), mDataSet);


        // Setting Mode to Single to reveal bottom View for one item in List
        // Setting Mode to Mutliple to reveal bottom Views for multile items in List
        ((RipAdapter) mAdapter).setMode(Attributes.Mode.Single);

        mRecyclerView.setAdapter(mAdapter);

        /* Scroll Listeners */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return rootView;
    }


    // load initial data
    public void loadData() {

        for (int i = 0; i <= 20; i++) {
            mDataSet.add(new Student("Student " + i, "androidstudent" + i + "@gmail.com"));

        }


    }
}
