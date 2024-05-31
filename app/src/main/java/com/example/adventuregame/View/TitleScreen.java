package com.example.adventuregame.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adventuregame.R;
import com.example.adventuregame.Model.RandomGenerator;
import com.example.adventuregame.Model.TriviaApi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TitleScreen extends AppCompatActivity {
    private static Object[][] triviaQuestions; // NEW
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_title_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadTriviaQuestions();
    }
    public void gameScreen(View view){
        Intent gameScreen = new Intent(this, GameScreen.class);
        startActivity(gameScreen);
    }
    private void loadTriviaQuestions() {
        Future<Object[][]> future = executorService.submit(TriviaApi::loadTriviaQuestions);
        executorService.execute(() -> {
            try {
                triviaQuestions = future.get();
                runOnUiThread(() -> Toast.makeText(this, "Trivia questions loaded", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                Log.e("TriviaQuestions", "Failed to load trivia questions", e);
                triviaQuestions = RandomGenerator.getRandomQuestions();
                runOnUiThread(() -> Toast.makeText(this, "Failed to load trivia questions", Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
    public static Object[][] getTriviaQuestions() {
        return triviaQuestions;
    }

}