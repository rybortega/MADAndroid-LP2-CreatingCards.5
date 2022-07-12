package com.example.lilliansflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView flashcardQuestion;
    TextView flashcardAnswer1;
    TextView flashcardAnswer2;
    TextView flashcardAnswer3;
    Flashcard flashcardToEdit;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;
    static final int ADD_CARD_REQUEST_CODE = 100;
    static final int EDIT_CARD_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        flashcardQuestion = findViewById(R.id.flashcard_question);
        flashcardAnswer1 = findViewById(R.id.flashcard_answer1);
        flashcardAnswer2 = findViewById(R.id.flashcard_answer2);
        flashcardAnswer3 = findViewById(R.id.flashcard_answer3);

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();
        //displays the first written flashcard when being launched
        if (allFlashcards != null && allFlashcards.size() > 0) {
            Flashcard flashcard = allFlashcards.get(0);
            flashcardQuestion.setText(flashcard.getQuestion());
            flashcardAnswer1.setText(flashcard.getWrongAnswer1());
            flashcardAnswer2.setText(flashcard.getWrongAnswer2());
            flashcardAnswer3.setText(flashcard.getAnswer());
        }

        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cx = flashcardAnswer3.getWidth() / 2;
                int cy = flashcardAnswer3.getHeight() / 2;

                float finalRadius = (float)Math.hypot(cx, cy);

                Animator anim = ViewAnimationUtils.createCircularReveal(flashcardAnswer3, cx, cy, 0f, finalRadius);

                flashcardAnswer1.setVisibility(View.INVISIBLE);
                flashcardAnswer2.setVisibility(View.INVISIBLE);
                flashcardAnswer3.setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();
            }
        });

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
                //Make other two answer choices visible after
                flashcardAnswer1.setVisibility(View.VISIBLE);
                flashcardAnswer2.setVisibility(View.VISIBLE);
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
                MainActivity.this.startActivityForResult(intent, ADD_CARD_REQUEST_CODE);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        //When users tap on the edit button for the flashcard
        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                allFlashcards = flashcardDatabase.getAllCards();
                for(int i = 0; i < allFlashcards.size(); i++) {
                    if(allFlashcards.get(i).getQuestion().equals(flashcardQuestion.getText().toString())) {
                        flashcardToEdit = allFlashcards.get(i);
                    }
                }
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("question edit", flashcardQuestion.getText().toString());
                intent.putExtra("answer 1 edit", flashcardAnswer1.getText().toString());
                intent.putExtra("answer 2 edit", flashcardAnswer2.getText().toString());
                intent.putExtra("answer 3 edit", flashcardAnswer3.getText().toString());
                MainActivity.this.startActivityForResult(intent, EDIT_CARD_REQUEST_CODE);
            }
        });


        //When users tap on the next button on the app
        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFlashcards.size() == 0) {
                    return;
                }
                allFlashcards = flashcardDatabase.getAllCards();
                int flashcardIndex = getRandomNumber(0, allFlashcards.size()-1);
                while(flashcardIndex == currentCardDisplayedIndex) {
                    flashcardIndex = getRandomNumber(0, allFlashcards.size()-1);
                }
                Flashcard flashcard = allFlashcards.get(flashcardIndex);

                flashcardQuestion.setText(flashcard.getQuestion());
                flashcardAnswer1.setText(flashcard.getWrongAnswer1());
                flashcardAnswer2.setText(flashcard.getWrongAnswer2());
                flashcardAnswer3.setText(flashcard.getAnswer());
                currentCardDisplayedIndex = flashcardIndex;

                final Animation leftOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing

                        flashcardQuestion.startAnimation(rightInAnim);
                        flashcardAnswer1.startAnimation(rightInAnim);
                        flashcardAnswer2.startAnimation(rightInAnim);
                        flashcardAnswer3.startAnimation(rightInAnim);

                        Flashcard currentCard = allFlashcards.get(currentCardDisplayedIndex);
                        flashcardQuestion.setText(currentCard.getQuestion());
                        flashcardAnswer1.setText(currentCard.getWrongAnswer1());
                        flashcardAnswer2.setText(currentCard.getWrongAnswer2());
                        flashcardAnswer3.setText(currentCard.getAnswer());
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                //animation to allow flashcard components to slide to the left
                flashcardQuestion.startAnimation(leftOutAnim);
                flashcardAnswer1.startAnimation(leftOutAnim);
                flashcardAnswer2.startAnimation(leftOutAnim);
                flashcardAnswer3.startAnimation(leftOutAnim);
            }
        });

        //When users tap on trash button on app
        findViewById(R.id.trash_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashcardDatabase.deleteCard(flashcardQuestion.getText().toString());
                allFlashcards = flashcardDatabase.getAllCards(); //update size of flashcard list
                currentCardDisplayedIndex--; //update current displayed card index
                //no cards present in the database after deletion
                if (allFlashcards.size() == 0) {
                    flashcardQuestion.setText("Add a new card!");
                    flashcardAnswer1.setVisibility(View.INVISIBLE);
                    flashcardAnswer2.setVisibility(View.INVISIBLE);
                    flashcardAnswer3.setVisibility(View.INVISIBLE);
                    currentCardDisplayedIndex = 0;
                //a card is still present in the data base, even though currentCardDisplayedIndex is negative
                } else if (currentCardDisplayedIndex < 0 && allFlashcards.size() > 0) {
                    Flashcard currentFlashcard = allFlashcards.get(0);

                    flashcardQuestion.setText(currentFlashcard.getQuestion());
                    flashcardAnswer1.setText(currentFlashcard.getWrongAnswer1());
                    flashcardAnswer2.setText(currentFlashcard.getWrongAnswer2());
                    flashcardAnswer3.setText(currentFlashcard.getAnswer());
                } else {
                    Flashcard currentFlashcard = allFlashcards.get(currentCardDisplayedIndex);

                    flashcardQuestion.setText(currentFlashcard.getQuestion());
                    flashcardAnswer1.setText(currentFlashcard.getWrongAnswer1());
                    flashcardAnswer2.setText(currentFlashcard.getWrongAnswer2());
                    flashcardAnswer3.setText(currentFlashcard.getAnswer());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String questionString = data.getExtras().getString("question");
                String answer1String = data.getExtras().getString("answer1");
                String answer2String = data.getExtras().getString("answer2");
                String answer3String = data.getExtras().getString("answer3");

                flashcardQuestion.setText(questionString);
                flashcardAnswer1.setText(answer1String);
                flashcardAnswer2.setText(answer2String);
                flashcardAnswer3.setText(answer3String);

                //save flashcard data into database (answer3String before other two strings to follow constructor parameters)
                //correct answer: answerString3
                //incorrect answers: answerString1 and answerString2
                flashcardDatabase.insertCard(new Flashcard(questionString, answer3String, answer1String,
                        answer2String));
                allFlashcards = flashcardDatabase.getAllCards();

                //make visible if empty state was displayed (from deleting)
                flashcardQuestion.setVisibility(View.VISIBLE);
                flashcardAnswer1.setVisibility(View.VISIBLE);
                flashcardAnswer2.setVisibility(View.VISIBLE);
                flashcardAnswer3.setVisibility(View.VISIBLE);

                Snackbar.make(findViewById(R.id.flashcard_question),
                        "Card successfully created.", Snackbar.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == EDIT_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String questionString = data.getExtras().getString("question");
                String answer1String = data.getExtras().getString("answer1");
                String answer2String = data.getExtras().getString("answer2");
                String answer3String = data.getExtras().getString("answer3");

                //update flashcard's question and answer choices using setter methods
                flashcardToEdit.setQuestion(questionString);
                flashcardToEdit.setAnswer(answer3String);
                flashcardToEdit.setWrongAnswer1(answer1String);
                flashcardToEdit.setWrongAnswer2(answer2String);

                //update questions and answers currently displayed on the screen
                flashcardQuestion.setText(questionString);
                flashcardAnswer1.setText(answer1String);
                flashcardAnswer2.setText(answer2String);
                flashcardAnswer3.setText(answer3String);

                flashcardDatabase.updateCard(flashcardToEdit);

                Snackbar.make(findViewById(R.id.flashcard_question),
                        "Card successfully edited.", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    // returns a random number between minNumber and maxNumber, inclusive
    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;

    }


}
