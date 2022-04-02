package com.example.lilliansflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {
    EditText question;
    EditText answer1;
    EditText answer2;
    EditText answer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        question = findViewById(R.id.question_text);
        answer1 = findViewById(R.id.answer_text1);
        answer2 = findViewById(R.id.answer_text2);
        answer3 = findViewById(R.id.answer_text3);

        Intent currIntent = getIntent();
        if(currIntent != null) {
            String currentQuestion = getIntent().getStringExtra("question edit");
            String currentAnswer1 = getIntent().getStringExtra("answer 1 edit");
            String currentAnswer2 = getIntent().getStringExtra("answer 2 edit");
            String currentAnswer3 = getIntent().getStringExtra("answer 3 edit");
            question.setText(currentQuestion);
            answer1.setText(currentAnswer1);
            answer2.setText(currentAnswer2);
            answer3.setText(currentAnswer3);
        }

        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                String questionText = ((EditText) findViewById(R.id.question_text)).getText().toString();
                String answerText1 = ((EditText) findViewById(R.id.answer_text1)).getText().toString();
                String answerText2 = ((EditText) findViewById(R.id.answer_text2)).getText().toString();
                String answerText3 = ((EditText) findViewById(R.id.answer_text3)).getText().toString();
                //Displays an error message if not all EditText views are populated
                if(questionText.isEmpty() || answerText1.isEmpty() || answerText2.isEmpty() || answerText3.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Must enter all questions and answers!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                } else {
                    data.putExtra("question", questionText);
                    data.putExtra("answer1", answerText1);
                    data.putExtra("answer2", answerText2);
                    data.putExtra("answer3", answerText3);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }
}