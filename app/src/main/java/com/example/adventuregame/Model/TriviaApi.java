package com.example.adventuregame.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.apache.commons.text.StringEscapeUtils;

public class TriviaApi {
    private static final Random RANDOM = new Random();
    public static Object[][] loadTriviaQuestions() {
        Object[][] questionsAndAnswers = new Object[50][2];
        try {
            String apiUrl = "https://opentdb.com/api.php?amount=50&type=boolean";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray results = jsonObject.getAsJsonArray("results");

            for (int i = 0; i < results.size(); i++) {
                JsonObject item = results.get(i).getAsJsonObject();
                String question = StringEscapeUtils.unescapeHtml4(item.get("question").getAsString());
                boolean answer = Boolean.parseBoolean(StringEscapeUtils.unescapeHtml4(item.get("correct_answer").getAsString()));
                questionsAndAnswers[i][0] = question;
                questionsAndAnswers[i][1] = answer;

            }
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Object[] quest: questionsAndAnswers){
            Object[] newQuestion = RandomGenerator.getRandomQuestions()[RANDOM.nextInt(RandomGenerator.getRandomQuestions().length)];
            if (quest[0] == null){
                quest[0] = newQuestion[0];
                quest[1] = newQuestion[1];
            }
        }
        return questionsAndAnswers;
    }
}
