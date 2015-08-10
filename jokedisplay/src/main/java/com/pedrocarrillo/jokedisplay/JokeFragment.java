package com.pedrocarrillo.jokedisplay;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JokeFragment extends Fragment {

    public static final String JOKE_KEY = "_joke";

    private TextView tvJoke;
    private String mJoke;

    public static JokeFragment newInstance(String joke){
        JokeFragment jokeFragment = new JokeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(JOKE_KEY, joke);
        jokeFragment.setArguments(bundle);
        return jokeFragment;
    }

    public JokeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_joke, container, false);
        tvJoke = (TextView)rootView.findViewById(R.id.tv_joke);
        if( getArguments() != null ) {
            mJoke = getArguments().getString(JOKE_KEY);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvJoke.setText(mJoke);
    }
}
