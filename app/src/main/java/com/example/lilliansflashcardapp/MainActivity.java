package com.example.lilliansflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView question;
    TextView answer1;
    TextView answer2;
    TextView answer3;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        TextView flashcardQuestion = findViewById(R.id.flashcard_question);
        TextView flashcardAnswer1 = findViewById(R.id.flashcard_answer1);
        TextView flashcardAnswer2 = findViewById(R.id.flashcard_answer2);
        TextView flashcardAnswer3 = findViewById(R.id.flashcard_answer3);

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            flashcardQuestion.setText(allFlashcards.get(0).getQuestion());
            flashcardAnswer1.setText(allFlashcards.get(0).getWrongAnswer1());
            flashcardAnswer2.setText(allFlashcards.get(0).getWrongAnswer2());
            flashcardAnswer3.setText(allFlashcards.get(0).getAnswer());
        }

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
                if (isShowingAnswers[0]) {
                    toggleVisibility.setImageResource(R.drawable.do_not_see_icon_foreground);
                    flashcardAnswer1.setVisibility(View.VISIBLE);
                    flashcardAnswer2.setVisibility(View.VISIBLE);
                    flashcardAnswer3.setVisibility(View.VISIBLE);
                    isShowingAnswers[0] = false;
                } else {
                    toggleVisibility.setImageResource(R.drawable.see_icon_foreground);
                    flashcardAnswer1.setVisibility(View.INVISIBLE);
                    flashcardAnswer2.setVisibility(View.INVISIBLE);
                    flashcardAnswer3.setVisibility(View.INVISIBLE);
                    isShowingAnswers[0] = true;
                }
            }
        });

        //When users tap on the add button for the flash card
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        //When users tap on the edit button for the flashcard
        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("question edit", flashcardQuestion.getText().toString());
                intent.putExtra("answer 1 edit", flashcardAnswer1.getText().toString());
                intent.putExtra("answer 2 edit", flashcardAnswer2.getText().toString());
                intent.putExtra("answer 3 edit", flashcardAnswer3.getText().toString());
                System.out.println("we passed by here me thinks");
                MainActivity.this.startActivityForResult(intent, 200);
            }
        });


        //When users tap on the next button on the app
        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allFlashcards.size() == 0) {
                    return;
                }
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;
                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if(currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(view,
                            "You've reached the end of the cards, going back to start.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardDisplayedIndex = 0;
                }
                // set the question and answer TextViews with data from the database
                allFlashcards = flashcardDatabase.getAllCards();
                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                flashcardQuestion.setText(flashcard.getQuestion());
                flashcardAnswer1.setText(flashcard.getWrongAnswer1());
                flashcardAnswer2.setText(flashcard.getWrongAnswer2());
                flashcardAnswer3.setText(flashcard.getAnswer());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        question = findViewById(R.id.flashcard_question);
        answer1 = findViewById(R.id.flashcard_answer1);
        answer2 = findViewById(R.id.flashcard_answer2);
        answer3 = findViewById(R.id.flashcard_answer3);

        if (requestCode == 100) {
            if (data != null) {
                String questionString = data.getExtras().getString("question");
                String answer1String = data.getExtras().getString("answer1");
                String answer2String = data.getExtras().getString("answer2");
                String answer3String = data.getExtras().getString("answer3");

                question.setText(questionString);
                answer1.setText(answer1String);
                answer2.setText(answer2String);
                answer3.setText(answer3String);

                //save flashcard data into database
                flashcardDatabase.insertCard(new Flashcard(questionString, answer1String,
                        answer2String, answer3String));
                allFlashcards = flashcardDatabase.getAllCards();
                System.out.println("add logic used here!");
                Snackbar.make(findViewById(R.id.flashcard_question),
                        "Card successfully created.", Snackbar.LENGTH_SHORT).show();
            }
        }
        if(requestCode == 200) {
            if (data != null) {
                String questionString = data.getExtras().getString("question");
                String answer1String = data.getExtras().getString("answer1");
                String answer2String = data.getExtras().getString("answer2");
                String answer3String = data.getExtras().getString("answer3");

                question.setText(questionString);
                answer1.setText(answer1String);
                answer2.setText(answer2String);
                answer3.setText(answer3String);

                Snackbar.make(findViewById(R.id.flashcard_question),
                        "Card successfully edited.", Snackbar.LENGTH_SHORT).show();
            }
        }
    }


}