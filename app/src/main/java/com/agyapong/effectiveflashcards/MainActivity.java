package com.agyapong.effectiveflashcards;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView qstnView;

    boolean isShowingAnswers = false;
    private final static int EDIT_CARD_REQUEST_CODE = 200;
    private final static int ADD_CARD_REQUEST_CODE = 100;

    // to store the user input that was passed from the AddCardActivity to the Main Activity(main screen)
    String userQuestionInput, userCorrectAnsInput, userWrongAns1Input, userWrongAns2Input ;

    FlashcardDatabase flashcardDatabase; // instance of our database so we can read from/write to it
    List<Flashcard> allFlashcards; // to hold a list all the flashcards

    // to hold the current flashcard displayed on the screen which needs
    // to be updated/changed when the edit button is clicked
    Flashcard cardToEdit;


    int currentCardDisplayedIndex = 0; // keeps track of the index of the current card we're showing.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // just created it to access the root view in order to display the snackbar msgs
        qstnView = findViewById(R.id.flashcard_question);

        flashcardDatabase = new FlashcardDatabase(this);

        // reads or retrieves all the flashcards from the database
        // and stores them into the list of flashcards
        allFlashcards = flashcardDatabase.getAllCards();

        // if the list of flashcards saved in our database is not empty, display a saved flashcard
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.correct_choice)).setText(allFlashcards.get(0).getAnswer());
            ((TextView) findViewById(R.id.wrong_choice1)).setText(allFlashcards.get(0).getWrongAnswer1());
            ((TextView) findViewById(R.id.wrong_choice2)).setText(allFlashcards.get(0).getWrongAnswer2());
        }


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
                MainActivity.this.startActivityForResult(intent, ADD_CARD_REQUEST_CODE );
            }
        });

        // takes user from the MainActivity to AddCardActivity(Add Card Screen)
        // and also passes the current question and answers from the MainActivity (main screen)
        // to the Add Card Activity to be displayed on that screen, when the edit icon is clicked
        findViewById(R.id.edit_card_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get current question & answers displayed on the main screen
               String currentQuestion = ((TextView) findViewById(R.id.flashcard_question)).getText().toString();
               String currentWrongAns1 = ((TextView) findViewById(R.id.wrong_choice1)).getText().toString();
               String currentCorrectAns = ((TextView) findViewById(R.id.correct_choice)).getText().toString();
               String currentWrongAns2 = ((TextView) findViewById(R.id.wrong_choice2)).getText().toString();

                // if there are no flashcards in the database,
                // show an empty state by prompting the user to add a new card
                if (allFlashcards.isEmpty()) {
                    ((TextView) findViewById(R.id.flashcard_question)).setText("No content to edit. Add a new flashcard");

                    // call method to clear and hide the multiple choice answers TextViews from the screen
                    hideAndClearAnswersTextViews();
                }
                else {

                    // loop through the list of all cards (allFlashCards) to find the one
                    // that has the same question as the current Question being displayed.
                    for (int i = 0; i < allFlashcards.size()-1; i++) {
                        if (allFlashcards.get(i).getQuestion().equalsIgnoreCase(currentQuestion))
                            cardToEdit = allFlashcards.get(i); // then set cardToEdit to the matching Flashcard
                    }

                    // pass data to from mainActivity to AddCardActivity
                    Intent data = new Intent(MainActivity.this, AddCardActivity.class);
                    data.putExtra("questionKey", currentQuestion); //pass current qstn to Add Card Screen
                    data.putExtra("correctAnsKey", currentCorrectAns); //pass current correct ans to Add Card Screen
                    data.putExtra("wrongAns1Key", currentWrongAns1); //pass current wrong ans 1 to Add Card Screen
                    data.putExtra("wrongAns2Key", currentWrongAns2); //pass current wrong correct ans 2 to Add Card Screen
                    MainActivity.this.startActivityForResult(data, EDIT_CARD_REQUEST_CODE); // expecting result(data) from AddCardActivity
                }
            }
        });

        //takes the user to the next flashcard when the next button is clicked
        findViewById(R.id.next_card_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if there are no flashcards in the database,
                // show an empty state by prompting the user to add a new card
                if (allFlashcards.isEmpty()) {
                    ((TextView) findViewById(R.id.flashcard_question)).setText("Your flashcard is empty. Add a new flashcard");

                    // call method to clear and hide the multiple choice answers TextViews from the screen
                    hideAndClearAnswersTextViews();

                }else {
                    // to show flashcards in random order
                    int randomNumber = getRandomNumber(0, allFlashcards.size() - 1);

                    // set the question and multiple choice answers TextViews with data from
                    // the database and shows the flashcards in a random order with get(randomNumber)
                    ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(randomNumber).getQuestion());
                    ((TextView) findViewById(R.id.correct_choice)).setText(allFlashcards.get(randomNumber).getAnswer());
                    ((TextView) findViewById(R.id.wrong_choice1)).setText(allFlashcards.get(randomNumber).getWrongAnswer1());
                    ((TextView) findViewById(R.id.wrong_choice2)).setText(allFlashcards.get(randomNumber).getWrongAnswer2());
                }
            }
        });

        // deletes a card whenever the delete/trash button is clicked
        findViewById(R.id.delete_card_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // if there are no flashcards in the database,
                if (allFlashcards.isEmpty()) {

                    // show an empty state by prompting the user to add a new card
                    ((TextView) findViewById(R.id.flashcard_question)).setText("No content to delete. Add a new flashcard");

                    // call method to clear and hide the multiple choice answers TextViews from the screen
                    hideAndClearAnswersTextViews();

                }else{

                    // deletes the flashcard
                    flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());

                    // update our instance of list of cards otherwise, allFlashcards will be holding on to stale data!
                    allFlashcards = flashcardDatabase.getAllCards();

                    // show an empty state by prompting the user to add a new card
                    ((TextView) findViewById(R.id.flashcard_question)).setText("");

                    // call method to clear and hide the multiple choice answers TextViews from the screen
                    hideAndClearAnswersTextViews();

                    Snackbar.make(qstnView.getRootView(), "Card deleted! Add a new card or click next button to show the next flashcard", Snackbar.LENGTH_LONG).show();
                }
            }

        });
    }

    // this method is responsible for getting the data passed from the AddCardActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            // grab the data passed from AddCardActivity
            userQuestionInput = data.getExtras().getString("string1");
            userCorrectAnsInput = data.getExtras().getString("string2");
            userWrongAns1Input = data.getExtras().getString("string3");
            userWrongAns2Input = data.getExtras().getString("string4");

            // Congratulate the user on successfully adding a flashcard...lol
            Snackbar.make(qstnView.getRootView(), "Card successfully created", Snackbar.LENGTH_SHORT).show();

            // set the TextViews to show the new question and
            // multiple choice answers grabbed from AddCardActivity
            ((TextView) findViewById(R.id.flashcard_question)).setText(userQuestionInput);
            ((TextView) findViewById(R.id.correct_choice)).setText(userCorrectAnsInput);
            ((TextView) findViewById(R.id.wrong_choice1)).setText(userWrongAns1Input);
            ((TextView) findViewById(R.id.wrong_choice2)).setText(userWrongAns2Input);

            // inserts/saves/writes the flashcard retrieved from the AddCardActivity into the database
            flashcardDatabase.insertCard(new Flashcard(userQuestionInput, userCorrectAnsInput,
                    userWrongAns1Input, userWrongAns2Input));

            // update our list of flashcards with the new flashcard added/saved to the database
            // or reads/retrieves the new flashcard saved into the database to the list of flashcards
            allFlashcards = flashcardDatabase.getAllCards();

        } else if (requestCode == EDIT_CARD_REQUEST_CODE && resultCode == RESULT_OK) {

            // grab the data passed from AddCardActivity
            userQuestionInput = data.getExtras().getString("string1");
            userCorrectAnsInput = data.getExtras().getString("string2");
            userWrongAns1Input = data.getExtras().getString("string3");
            userWrongAns2Input = data.getExtras().getString("string4");

            // changes the content of the flashcard with the EDITED
            // question and multiple choice answers
            cardToEdit.setQuestion(userQuestionInput);
            cardToEdit.setAnswer(userCorrectAnsInput);
            cardToEdit.setWrongAnswer1(userWrongAns1Input);
            cardToEdit.setWrongAnswer2(userWrongAns2Input);

            // update the database with the edited flashcard
            flashcardDatabase.updateCard(cardToEdit);

            // update our list of flashcards with the new flashcard added/saved to the database
            // or reads/retrieves the new flashcard saved into the database to the list of flashcards
            allFlashcards = flashcardDatabase.getAllCards();

            // set the TextViews to show the EDITED question and multiple choice answers
            ((TextView) findViewById(R.id.flashcard_question)).setText(userQuestionInput);
            ((TextView) findViewById(R.id.correct_choice)).setText(userCorrectAnsInput);
            ((TextView) findViewById(R.id.wrong_choice1)).setText(userWrongAns1Input);
            ((TextView) findViewById(R.id.wrong_choice2)).setText(userWrongAns2Input);

            Snackbar.make(qstnView.getRootView(), "Card successfully edited", Snackbar.LENGTH_SHORT).show();


        }
    }

    // returns a random number between minNumber and maxNumber, inclusive.
    // for example, if i called getRandomNumber(1, 3), there's an equal chance of it returning either 1, 2, or 3.
    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

    // this method clears and hides the multiple choice answers TextViews
    public void hideAndClearAnswersTextViews(){

        // and clear the multiple choice answers TextViews
        ((TextView) findViewById(R.id.correct_choice)).setText("");
        ((TextView) findViewById(R.id.wrong_choice1)).setText("");
        ((TextView) findViewById(R.id.wrong_choice2)).setText("");

        // also hide the multiple choice answers TextViews
        findViewById(R.id.wrong_choice1).setVisibility(View.INVISIBLE);
        findViewById(R.id.correct_choice).setVisibility(View.INVISIBLE);
        findViewById(R.id.wrong_choice2).setVisibility(View.INVISIBLE);

        ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.show_icon);

    }
}