package com.fooyemeet2.Activities.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.fooyemeet2.Activities.LoginActivity;
import com.fooyemeet2.Activities.MainActivity;
import com.fooyemeet2.Activities.Questions;
import com.fooyemeet2.Activities.QuestionsAdapter;
import com.fooyemeet2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.faid on 15/06/2016.
 */
public class FragmentQuestions extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.questions_sign_up, container, false);

        Button button = (Button) rootView.findViewById(R.id.button_validate_question);
        ListView mListView = (ListView) rootView.findViewById(R.id.listView2);

        List<Questions> tweets = genererQuestions();

        QuestionsAdapter adapter = new QuestionsAdapter(getContext(), tweets);
        mListView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AsynctaskQuestions asynctaskQuestions = new AsynctaskQuestions(getActivity());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_login, new FragmentProfileTab()).commit();
            }
        });
        return rootView ;
    }

    private List<Questions> genererQuestions(){
        List<Questions> tweets = new ArrayList<>();
        tweets.add(new Questions("Florent", "Mon premier tweet !"));
        tweets.add(new Questions("Kevin", "C'est ici que ça se passe !"));
        tweets.add(new Questions("Logan", "Que c'est beau..."));
        tweets.add(new Questions("Mathieu", "Il est quelle heure ??"));
        tweets.add(new Questions("Willy", "On y est presque"));
        tweets.add(new Questions("Willy", "On y est presque"));
        tweets.add(new Questions("Willy", "On y est presque"));
        return tweets;
    }

}
