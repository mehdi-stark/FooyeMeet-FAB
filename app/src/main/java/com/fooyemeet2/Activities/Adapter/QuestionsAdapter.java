package com.fooyemeet2.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fooyemeet2.R;

import java.util.List;

/**
 * Created by m.faid on 15/06/2016.
 */
public class QuestionsAdapter extends ArrayAdapter<Questions> {

    private class QuestionsViewHolder{
        public TextView pseudo;
        public TextView text;
    }

    public QuestionsAdapter(Context context, List<Questions> questions) {
        super(context, 0, questions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.question, parent, false);
        }

        QuestionsViewHolder viewHolder = (QuestionsViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new QuestionsViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.question_theme);
            viewHolder.text = (TextView) convertView.findViewById(R.id.question_content);
            convertView.setTag(viewHolder);
        }

        Questions question_pos = getItem(position);

        viewHolder.pseudo.setText(question_pos.getPseudo());
        viewHolder.text.setText(question_pos.getText());

        return convertView;
    }
}
