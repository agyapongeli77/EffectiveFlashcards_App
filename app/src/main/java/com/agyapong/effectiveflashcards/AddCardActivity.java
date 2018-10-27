package com.agyapong.effectiveflashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        // dismisses or closes the AddCardActivity(Add Card Screen) and takes the user back
        // to the Main Activitity(Main Screen) when the cancel button is clicked by the user
        findViewById(R.id.cancel_action_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCardActivity.this, MainActivity.class);
                AddCardActivity.this.startActivity(intent);
                finish();
            }
        });

        // collect user input and pass it back to the Main Activity to be displayed when the save button is clicked
        findViewById(R.id.save_data_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String question = ((EditText) findViewById(R.id.questionTextField)).getText().toString();
                String answer = ((EditText) findViewById(R.id.answerTextField)).getText().toString();

                Intent data = new Intent(); // create a new Intent, this is where we will put our data
                data.putExtra("string1", question); // puts the question data collected from the user into the Intent, with the key as 'string1'
                data.putExtra("string2", answer); // puts the answer data collected from the user into the Intent, with the key as 'string2
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes this activity and pass data to the original activity that launched this activity

            }
        });
    }
}
