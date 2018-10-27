package com.agyapong.effectiveflashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean isShowingAnswers = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // this is a wrong answer so set the background to RED when user clicks this option
        findViewById(R.id.multiple_choice_GitLab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.multiple_choice_GitLab).setBackgroundColor(getResources().getColor(R.color.my_red_color));
            }
        });
        // this is the correct answer so set the background to GREEN when user clicks this option
        findViewById(R.id.multiple_choice_GitHub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.multiple_choice_GitHub).setBackgroundColor(getResources().getColor(R.color.my_green_color));
            }
        });
        // this is a wrong answer so set the background to RED when user clicks this option
        findViewById(R.id.multiple_choice_Bitbucket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.multiple_choice_Bitbucket).setBackgroundColor(getResources().getColor(R.color.my_red_color));
            }
        });

        // reset the multiple choice colors back to it's original state when user clicks the rootview
        findViewById(R.id.rootview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.multiple_choice_GitLab).setBackgroundColor(getResources().getColor(R.color.multiple_choice_color));
                findViewById(R.id.multiple_choice_GitHub).setBackgroundColor(getResources().getColor(R.color.multiple_choice_color));
                findViewById(R.id.multiple_choice_Bitbucket).setBackgroundColor(getResources().getColor(R.color.multiple_choice_color));
            }
        });

        // toggle between showing the answers and hiding the answers when user clicks the hide/show "eye" button
        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (isShowingAnswers == false) {
                    //displays all the answer options
                    findViewById(R.id.multiple_choice_GitLab).setVisibility(View.VISIBLE);
                    findViewById(R.id.multiple_choice_GitHub).setVisibility(View.VISIBLE);
                    findViewById(R.id.multiple_choice_Bitbucket).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.hide_icon);
                    isShowingAnswers = true;
                } else{
                    //hide all answer options
                    findViewById(R.id.multiple_choice_GitLab).setVisibility(View.INVISIBLE);
                    findViewById(R.id.multiple_choice_GitHub).setVisibility(View.INVISIBLE);
                    findViewById(R.id.multiple_choice_Bitbucket).setVisibility(View.INVISIBLE);
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
                // startActivityForResult specifies that we want result(data) to be returned back to the Main Activity from the AddCardActivity
                MainActivity.this.startActivityForResult(intent, 100);

            }
        });


    }

    // this method is responsible for getting the data passed from the AddCardActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String string1 = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String string2 = data.getExtras().getString("string2");

            // sets the question and answer TextViews to the new question and answer data that user
            // inputted at the AddCardActivity and was passed to the main activity
            ((TextView)findViewById(R.id.flashcard_question)).setText(string1);
            ((TextView)findViewById(R.id.multiple_choice_GitHub)).setText(string2);


            // Hide the other multiple choice answers since they are not in the Required challenge
            // Add it when you are working on the Optional challenge later on
            findViewById(R.id.multiple_choice_GitLab).setVisibility(View.INVISIBLE);
            findViewById(R.id.multiple_choice_Bitbucket).setVisibility(View.INVISIBLE);


        }
    }
}