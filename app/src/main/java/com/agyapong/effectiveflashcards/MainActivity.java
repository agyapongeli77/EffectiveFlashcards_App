package com.agyapong.effectiveflashcards;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean isShowingAnswers = false;
    TextView mainScreenQstn, mainScreenCorrectAns, mainScreenWrongAns1, mainScreenWrongAns2;

    // strings to store to current qstn & answers from the main screen textviews
    String currentQuestion, currentCorrectAns, currentWrongAns1, currentWrongAns2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // this is a wrong answer(@ position 1 on main screen) so set the background to RED when user clicks this option
        findViewById(R.id.wrong_choice1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.wrong_choice1).setBackgroundColor(getResources().getColor(R.color.my_red_color));
            }
        });
        // this is the correct answer(@ position 1 on main screen) so set the background to GREEN when user clicks this option
        findViewById(R.id.correct_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.correct_choice).setBackgroundColor(getResources().getColor(R.color.my_green_color));
            }
        });
        // this is a another wrong answer(@ position 3 on main screen) so set the background to RED when user clicks this option
        findViewById(R.id.wrong_choice2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.wrong_choice2).setBackgroundColor(getResources().getColor(R.color.my_red_color));
            }
        });

        // reset the multiple choice colors back to it's original state when user clicks the rootview
        findViewById(R.id.rootview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.wrong_choice1).setBackgroundColor(getResources().getColor(R.color.my_orange_color));
                findViewById(R.id.correct_choice).setBackgroundColor(getResources().getColor(R.color.my_orange_color));
                findViewById(R.id.wrong_choice2).setBackgroundColor(getResources().getColor(R.color.my_orange_color));
            }
        });

        // toggle between showing the answers and hiding the answers when user clicks the hide/show "eye" button
        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // if isShowingAnswers = false, show all the answer options and
                // change the background of the view to be the icon for hiding answers
                if (isShowingAnswers == false) {

                    findViewById(R.id.wrong_choice1).setVisibility(View.VISIBLE);
                    findViewById(R.id.correct_choice).setVisibility(View.VISIBLE);
                    findViewById(R.id.wrong_choice2).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.hide_icon);
                    isShowingAnswers = true;

                    // if isShowingAnswers = true, hide all the answer options and
                    // change background of view to be icon for showing answers
                } else{
                    //hide all answer options
                    findViewById(R.id.wrong_choice1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.correct_choice).setVisibility(View.INVISIBLE);
                    findViewById(R.id.wrong_choice2).setVisibility(View.INVISIBLE);
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.show_icon);
                    isShowingAnswers = false;
                }
            }
        });

        // takes user to the AddCardActivity(Add Card Screen) when the add icon is clicked from the Main Activity(Main Screen)
        findViewById(R.id.add_card_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);

                // startActivityForResult specifies that we expect result(data) to be returned back
                // from the AddCardActivity to the Main Activity
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        // takes user from the MainActivity to AddCardActivity(Add Card Screen)
        // and also passes the current question and answers on the MainActivity (main screen)
        // to the be displayed on the on the Add Card Screen, when the edit icon is clicked
        findViewById(R.id.edit_card_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestion = ((TextView)findViewById(R.id.flashcard_question)).getText().toString();
                currentWrongAns1 = ((TextView)findViewById(R.id.wrong_choice1)).getText().toString();//
                currentCorrectAns = ((TextView)findViewById(R.id.correct_choice)).getText().toString();//
                currentWrongAns2 = ((TextView)findViewById(R.id.wrong_choice2)).getText().toString();//
                Intent data = new Intent(MainActivity.this, AddCardActivity.class);
                data.putExtra("questionKey", currentQuestion); //pass current qstn to Add Card Screen
                data.putExtra("correctAnsKey", currentCorrectAns); //pass current correct ans to Add Card Screen
                data.putExtra("wrongAns1Key", currentWrongAns1); //pass current wrong ans 1 to Add Card Screen
                data.putExtra("wrongAns2Key", currentWrongAns2); //pass current wrong correct ans 2 to Add Card Screen
                MainActivity.this.startActivityForResult(data, 100);
            }
        });
    }

    // this method is responsible for getting the data passed from the AddCardActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!

            // data received from AddCardActivity (add card screen) stored into these strings with
            // keys matching what was passed from the add card screen
            String userQuestionInput = data.getExtras().getString("string1");
            String userCorrectAnsInput = data.getExtras().getString("string2");
            String userWrongAns1Input = data.getExtras().getString("string3");
            String userWrongAns2Input = data.getExtras().getString("string4");

            // sets the main screen's question and answer TextViews to the add screen's question and answer data
            // that user inputted at the AddCardActivity and was passed to the main activity
            mainScreenQstn = findViewById(R.id.flashcard_question);
            mainScreenQstn.setText(userQuestionInput);
            mainScreenCorrectAns = findViewById(R.id.correct_choice);
            mainScreenCorrectAns.setText(userCorrectAnsInput);
            mainScreenWrongAns1 = findViewById(R.id.wrong_choice1);
            mainScreenWrongAns1.setText(userWrongAns1Input);
            mainScreenWrongAns2 = findViewById(R.id.wrong_choice2);
            mainScreenWrongAns2.setText(userWrongAns2Input);

            /*Snackbar.make(parentView, R.string.snackbar_text, Snackbar.LENGTH_LONG)
            //parentView is the top-most container layout if the root layout is not CoordinatorLayout or FrameLayout
            //the text to display and
            //the length you want the snackbar display text to stay */
            Snackbar.make(mainScreenQstn.getRootView(),"Card successfully created",Snackbar.LENGTH_SHORT).show();
        }
    }


}