package com.agyapong.effectiveflashcards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    boolean isShowingAnswers = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this is a wrong answer so set the background to RED when user clicks this option
        findViewById(R.id.multiple_choice_GitLab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.multiple_choice_GitLab).setBackgroundColor(getResources().getColor(R.color.my_red_color));
            }
        });
        ////this is the correct answer so set the background to GREEN when user clicks this option
        findViewById(R.id.multiple_choice_GitHub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.multiple_choice_GitHub).setBackgroundColor(getResources().getColor(R.color.my_green_color));
            }
        });
        //this is a wrong answer so set the background to RED when user clicks this option
        findViewById(R.id.multiple_choice_Bitbucket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.multiple_choice_Bitbucket).setBackgroundColor(getResources().getColor(R.color.my_red_color));
            }
        });

        //Reset the multiple choice colors back to it's original state when user clicks the rootview
        findViewById(R.id.rootview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.multiple_choice_GitLab).setBackgroundColor(getResources().getColor(R.color.multiple_choice_color));
                findViewById(R.id.multiple_choice_GitHub).setBackgroundColor(getResources().getColor(R.color.multiple_choice_color));
                findViewById(R.id.multiple_choice_Bitbucket).setBackgroundColor(getResources().getColor(R.color.multiple_choice_color));
            }
        });

        //toggle between showing the answers and hiding the answers when user clicks the hide/show "eye" button
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


    }
}