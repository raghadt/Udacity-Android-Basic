package com.example.raghadtaleb.project3_quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

//import static com.example.raghadtaleb.project3_quizapp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int counter;

    //--------------------------- question one ----------------------------

    public void questionOne() {
        RadioButton questionOne = (RadioButton) findViewById(R.id.questionOneCorrect);
        boolean questionOneAnswer = questionOne.isChecked();
        if (questionOneAnswer)
            counter++;
    }

    //--------------------------- question two ----------------------------

    public void questionTwo() {
        CheckBox questionTwo1 = (CheckBox) findViewById(R.id.questionTwoCorrect1);
        CheckBox questionTwo2 = (CheckBox) findViewById(R.id.questionTwoCorrect2);
        CheckBox questionTwoWrong = (CheckBox) findViewById(R.id.questionTwoWrong);

        boolean questionTwoAnswer = (questionTwo1.isChecked()) && (questionTwo2.isChecked()) && (!questionTwoWrong.isChecked());

        if (questionTwoAnswer)
            counter++;
    }

    //--------------------------- question three ----------------------------

    public void questionThree() {
        EditText questionThree = (EditText) findViewById(R.id.questionThreeCorrect);

        if (questionThree.getText().toString().toLowerCase().equals("pluto")) {
            counter++;
        }

    }

    //--------------------------- question Four ----------------------------
    public void questionFour() {

        RadioButton star1 = (RadioButton) findViewById(R.id.questionFourCorrect1);
        boolean question4_star1 = star1.isChecked();

        if (question4_star1)
            counter++;
    }


    //--------------------------- Submit Answer ----------------------------

    public void submitAnswers(View view) {
        counter = 0;

        questionOne();
        questionTwo();
        questionThree();
        questionFour();

        if (counter == 4) {
            Toast.makeText(MainActivity.this, "You are right! Great job you answered "+counter+" questions correctly", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(MainActivity.this, "Try again you answered "+counter+" questions correctly", Toast.LENGTH_LONG).show();

        }
    }
}
