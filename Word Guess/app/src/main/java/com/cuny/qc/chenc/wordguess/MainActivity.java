package com.cuny.qc.chenc.wordguess;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int WORD_LENGTH = 4;
    private static final int WORD_MAXLENGTH = 7;
    private TrieNode root;
    private LinearLayout wordLinearLayout;
    private Random random = new Random();
    private Boolean touchable = false;
    private String wordForGuess = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();
        root = new TrieNode();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = in.readLine()) != null) {
                String word = line.trim();
                if (word.length() >= WORD_LENGTH && word.length()<=WORD_MAXLENGTH) {
                    root.add(word);
                }
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_SHORT);
            toast.show();
        }
        wordLinearLayout = (LinearLayout) findViewById(R.id.guessword);
        onStartGame();

        Button reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartGame();
            }
        });

        Button guess = (Button)findViewById(R.id.guessit);
        guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGuess();
            }
        });

        Button ans = (Button)findViewById(R.id.showAns);
        ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAns();
            }
        });
    }

    public void onStartGame(){
        wordForGuess = root.getAnyWordStartingWith(null);
        EditText userAns = (EditText)findViewById(R.id.userguess);
        userAns.setText("");
        wordLinearLayout.removeAllViews();
        for (char c : wordForGuess.toCharArray()){
            touchable = random.nextBoolean();
            LetterTile letter = new LetterTile(this,c,touchable);
            letter.addtoView(wordLinearLayout);
        }
        Log.i("TAG",wordForGuess);
    }

    public void onGuess(){
        EditText userAns = (EditText)findViewById(R.id.userguess);
        String ans = userAns.getText().toString();
        if (ans.equalsIgnoreCase(wordForGuess)){
            Toast.makeText(this,"Congratulations",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Try Again",Toast.LENGTH_SHORT).show();
        }
    }

    private void showAns(){
        for(int i=0;i<wordLinearLayout.getChildCount();i++){
            ((LetterTile)wordLinearLayout.getChildAt(i)).showAnswer();
        }
    }
}
