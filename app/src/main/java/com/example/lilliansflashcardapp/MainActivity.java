package com.example.lilliansflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView flashcardAnswer1 = findViewById(R.id.flashcard_answer1);
        TextView flashcardAnswer2 = findViewById(R.id.flashcard_answer2);
        TextView flashcardAnswer3 = findViewById(R.id.flashcard_answer3);

        flashcardAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.incorrect_answer));
                flashcardAnswer1.setTextColor(getResources().getColor(R.color.white));
            }
        });

        flashcardAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.incorrect_answer));
                flashcardAnswer2.setTextColor(getResources().getColor(R.color.white));
            }
        });

        flashcardAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.correct_answer));
                flashcardAnswer3.setTextColor(getResources().getColor(R.color.white));
            }
        });

        findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Revert changes made after selecting buttons
                flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.original_answer_background));
                flashcardAnswer1.setTextColor(getResources().getColor(R.color.original_text));
                flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.original_answer_background));
                flashcardAnswer2.setTextColor(getResources().getColor(R.color.original_text));
                flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.original_answer_background));
                flashcardAnswer3.setTextColor(getResources().getColor(R.color.original_text));
            }
        });

        final boolean[] isShowingAnswers = {true};
        ImageView toggleVisibility = findViewById(R.id.toggle_choices_visibility);

        toggleVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowingAnswers[0]) {
                    ((ImageView) toggleVisibility).setImageResource(R.drawable.do_not_see_icon_foreground);
                    flashcardAnswer1.setVisibility(View.VISIBLE);
                    flashcardAnswer2.setVisibility(View.VISIBLE);
                    flashcardAnswer3.setVisibility(View.VISIBLE);
                    isShowingAnswers[0] = false;
                } else {
                    ((ImageView) toggleVisibility).setImageResource(R.drawable.see_icon_foreground);
                    flashcardAnswer1.setVisibility(View.INVISIBLE);
                    flashcardAnswer2.setVisibility(View.INVISIBLE);
                    flashcardAnswer3.setVisibility(View.INVISIBLE);
                    isShowingAnswers[0] = true;
                }
            }
        });


    }
}