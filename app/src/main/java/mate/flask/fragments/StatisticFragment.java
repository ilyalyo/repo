package mate.flask.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mate.flask.AnalyticRecord;
import mate.flask.DataRecord;
import mate.flask.DrawGraph;
import mate.flask.DrawStatistic;
import mate.flask.MyActivity;
import mate.flask.R;
import mate.flask.dbInteraction;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatisticFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class StatisticFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private int victimID;


    private OnFragmentInteractionListener mListener;

    public StatisticFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            victimID = getArguments().getInt(ARG_PARAM1 , 0);//поставить id текущего user!!!!
            Log.e("log_tag", "lol lolo"+ mParam1 );
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //реализация 2х закладок
        TabHost tabs = (TabHost) view.findViewById(R.id.tabHost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");

        spec.setContent(R.id.tab1);
        spec.setIndicator("Статистика");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Аналитика");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        //рисуем график аналитики на странице Аналитики
        ArrayList<DataRecord> dataRecords = MyActivity.db.getDataInterval(victimID);

        DrawGraph drawGraph = new DrawGraph(view.getContext(),dataRecords);
        drawGraph.setBackgroundColor(Color.WHITE);

        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView_statistic);
        scrollView.addView(drawGraph);

        // строим содержимое вкладки Статистика
        LinearLayout scrollView2 = (LinearLayout) view.findViewById(R.id.tab2);
        ArrayList<AnalyticRecord> dataAnalytic= MyActivity.db.getAnalytic(victimID);

        DrawStatistic drawStat = new DrawStatistic(view.getContext(),dataAnalytic);
        drawStat.setBackgroundColor(Color.WHITE);
        scrollView2.addView(drawStat);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
