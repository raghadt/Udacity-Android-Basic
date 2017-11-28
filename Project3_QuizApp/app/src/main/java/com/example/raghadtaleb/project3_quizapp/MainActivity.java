package com.example.raghadtaleb.project3_quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import static com.example.raghadtaleb.project3_quizapp.R.id.answer_venus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitAnswers(View view) {

        RadioButton answer_8_planets = (RadioButton) findViewById(R.id.planets);
        boolean question1_planets = answer_8_planets.isChecked();

        CheckBox answerVenus = (CheckBox) findViewById(answer_venus);
        boolean question2_venus = answerVenus.isChecked();

        EditText answer_pluto = (EditText) findViewById(R.id.pluto);
        boolean question3_pluto=false;

        if(answer_pluto.getText().toString().toLowerCase().equals("pluto")){
            question3_pluto = true;
        }

        RadioButton star1 = (RadioButton) findViewById(R.id.star1);
        RadioButton star2 = (RadioButton) findViewById(R.id.star2);
        boolean question4_star1 = star1.isChecked();
        boolean question4_star2 = star2.isChecked();
        TextView results = (TextView) findViewById(R.id.results);

        if (question2_venus && question1_planets && (question4_star1 || question4_star2) && question3_pluto) {
            results.setText("You are right!");
        } else {
            if(!question1_planets){
                results.setText("count them again!");
            }
            if(!question2_venus){
                results.setText("hmm checkout question 2, it is also called the Earth sister");
            }

            if(!question3_pluto){
                results.setText("it starts with p and ends with an o");
            }
        }

    }
}
