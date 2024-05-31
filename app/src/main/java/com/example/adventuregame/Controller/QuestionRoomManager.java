package com.example.adventuregame.Controller;


import com.example.adventuregame.Model.Character;
import com.example.adventuregame.Model.Item;
import com.example.adventuregame.Model.ScenarioConfig;
import com.example.adventuregame.View.GameScreen;

public class QuestionRoomManager extends ScenarioManager{

    private final Item reward;
    private Boolean questionRoomWasNoAttended = true;
    private final Boolean rightAnswer;
    private Boolean attemptWasNoMade = true;
    public Boolean getAttemptWasNoMade() {
        return attemptWasNoMade;
    }


    public QuestionRoomManager(String question, Character character, GameScreen gameScreen, ScenarioConfig scenarioConfig, Item reward, Boolean rightAnswer) {
        this.character = character;
        this.gameScreen = gameScreen;
        this.scenarioConfig = scenarioConfig;
        this.reward = reward;
        this.rightAnswer = rightAnswer;
        this.question = question;
    }

    public void comeQuestionRoom(){
        currentScenario = scenarioConfig.getScenarioById("comeQuestionRoom");
        displayScenario(currentScenario, questionRoomWasNoAttended);
        if(questionRoomWasNoAttended){
            questionRoomWasNoAttended = false;
        }
        updateButtons(currentScenario, true, true, true);
    }
    public void question(){
        if(attemptWasNoMade) {
            attemptWasNoMade = false;
        }
        currentScenario = scenarioConfig.getScenarioById("question");
        displayScenario(currentScenario, true);
        updateButtons(currentScenario, true, true, true);
    }
    public void checkAnswer(boolean assumeAnswer){
        currentScenario = scenarioConfig.getScenarioById("checkAnswer");
        if(rightAnswer == assumeAnswer){
            displayScenario(currentScenario, true);
            updateButtons(currentScenario, true, true, true);
        } else{
            displayScenario(currentScenario, false);
            updateButtons(currentScenario, false, true, true);
        }
    }
    public void itemAnswer(){
        currentScenario = scenarioConfig.getScenarioById("itemAnswer");
        character.getInventory().add(reward);
        displayScenario(currentScenario, true);
        updateButtons(currentScenario, true, true, true);
    }
    public void afterItemRoom(){
        currentScenario = scenarioConfig.getScenarioById("afterItemRoom");
        displayScenario(currentScenario, true);
        updateButtons(currentScenario, true, true, true);
    }

}



