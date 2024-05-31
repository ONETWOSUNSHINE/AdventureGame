package com.example.adventuregame.Model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ScenarioConfig {
    private final List<Scenario> scenarios;

    public ScenarioConfig(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }


    public static class Scenario{
        private final String id;
        private final String descriptionDefault;
        private final String descriptionSecond;
        private final List<ButtonDescription> buttons;

        public Scenario(String id, String descriptionDefault, String descriptionSecond, List<ButtonDescription> buttons) {
            this.id = id;
            this.descriptionDefault = descriptionDefault;
            this.descriptionSecond = descriptionSecond;
            this.buttons = buttons;
        }

        public String getId() {
            return id;
        }

        public String getDescriptionDefault() {
            return descriptionDefault;
        }

        public String getDescriptionSecond() {
            return descriptionSecond;
        }

        public List<ButtonDescription> getButtons() {
            return buttons;
        }

        public static class ButtonDescription{
            private final String textDefault;
            private final String textSecond;
            private final String choiceDefault;
            private final String choiceSecond;

            public ButtonDescription(String textDefault, String textSecond, String choiceDefault, String choiceSecond) {
                this.textDefault = textDefault;
                this.textSecond = textSecond;
                this.choiceDefault = choiceDefault;
                this.choiceSecond = choiceSecond;
            }

            public String getTextDefault() {
                return textDefault;
            }

            public String getTextSecond() {
                return textSecond;
            }

            public String getChoiceDefault() {
                return choiceDefault;
            }

            public String getChoiceSecond() {
                return choiceSecond;
            }
        }
    }
    public Scenario getScenarioById(String id) {
        for (Scenario scenario : scenarios) {
            if (scenario.getId().equals(id)) {
                return scenario;
            }
        }
        return getScenarioById("");
    }
    public static ScenarioConfig loadFromAsset(Context context, String fileName) {
        Gson gson = new Gson();
        try (InputStream is = context.getAssets().open(fileName)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            int bytesRead = is.read(buffer);
            if (bytesRead != size) {
                throw new IOException("Failed to read the entire file: " + fileName);
            }
            String json = new String(buffer, StandardCharsets.UTF_8);
            Type type = new TypeToken<ScenarioConfig>() {}.getType();
            return gson.fromJson(json, type);
        } catch (IOException e) {
            throw new RuntimeException("Error loading JSON from asset: " + fileName, e);
        }
    }

}
