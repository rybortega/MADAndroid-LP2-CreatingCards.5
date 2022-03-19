package com.example.lilliansflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {
    EditText question;
    EditText answer1;
    EditText answer2;
    EditText answer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        String currentQuestion = getIntent().getStringExtra("question edit");
        String currentAnswer1 = getIntent().getStringExtra("answer 1 edit");
        String currentAnswer2 = getIntent().getStringExtra("answer 1 edit");
        String currentAnswer3 = getIntent().getStringExtra("answer 1 edit");



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
                data.putExtra("question", questionText);
                data.putExtra("answer1", answerText1);
                data.putExtra("answer2", answerText2);
                data.putExtra("answer3", answerText3);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}