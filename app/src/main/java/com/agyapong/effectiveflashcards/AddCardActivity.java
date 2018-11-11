package com.agyapong.effectiveflashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {
    String question, correctAnswer, wrongAnswer1, wrongAnswer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        // dismisses or closes the AddCardActivity(Add Card Screen) and takes the user back
        // to the Main Activity (Main Screen) when the cancel button is clicked by the user
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

                // grabs the question from user and stores into a cquestion string variable
                question = ((EditText) findViewById(R.id.questionTextField)).getText().toString();// gets question from user

                // grabs the correct answer from user and stores into a correct answer string variable
                correctAnswer = ((EditText) findViewById(R.id.correctAnsTextField)).getText().toString();

                // grabs the 1st wrong answer from user and stores into a cwrong answer 1 string variable
                wrongAnswer1 = ((EditText) findViewById(R.id.wrongAns1_TextField)).getText().toString();

                // grabs the 2nd wrong answer from user and stores into a cwrong answer 2 string variable
                wrongAnswer2 = ((EditText) findViewById(R.id.wrongAns2_TextField)).getText().toString();

                //Toast Message to show an error message to the user if they didn't populate all the textfields
                if (question.equalsIgnoreCase("") || correctAnswer.equalsIgnoreCase("")
                    || wrongAnswer1.equalsIgnoreCase("") || wrongAnswer2.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Must enter text in all fields", Toast.LENGTH_SHORT).show();

                }else {
                    Intent data = new Intent(); // create a new Intent, this is where we will put our data

                    // puts the question data collected from the user into the Intent,
                    // with the key as 'string1' and sends it to the MainActivity
                    data.putExtra("string1", question);
                    // puts the correct answer data collected from the user into the Intent,
                    // with the key as 'string2 and sends it to the MainActivity
                    data.putExtra("string2", correctAnswer);
                    // puts the first wrong answer data collected from the user into the Intent,
                    // with the key as 'string3'and sends it to the MainActivity
                    data.putExtra("string3", wrongAnswer1);
                    // puts the second wrong answer data collected from the user into the Intent,
                    // with the key as 'string4'and sends it to the MainActivity
                    data.putExtra("string4", wrongAnswer2);

                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes this activity and pass data to the original activity that launched this activity
                }
            }
        });

        // data received from MainActivity (main screen) stored into these strings with
        // keys matching what was passed from the main screen
        String question = getIntent().getStringExtra("questionKey");
        String correctAns = getIntent().getStringExtra("correctAnsKey");
        String wrongAns1 = getIntent().getStringExtra("wrongAns1Key");
        String wrongAns2 = getIntent().getStringExtra("wrongAns2Key");

        // displays the data received from main screen (i.e. currently displayed question and answers)
        // onto the Add Card Screen Textfields for user to see and edit
        ((EditText) findViewById(R.id.questionTextField)).setText(question);
        ((EditText) findViewById(R.id.correctAnsTextField)).setText(correctAns);
        ((EditText) findViewById(R.id.wrongAns1_TextField)).setText(wrongAns1);
        ((EditText) findViewById(R.id.wrongAns2_TextField)).setText(wrongAns2);
    }
}
