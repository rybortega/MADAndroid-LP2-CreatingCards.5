package com.example.lilliansflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

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
        /*
         Intent intent = new Intent(AddCardActivity.this, MainActivity.class);
          AddCardActivity.this.startActivityForResult(intent, 100);
         */

    }


}