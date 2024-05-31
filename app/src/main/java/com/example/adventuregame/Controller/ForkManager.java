package com.example.adventuregame.Controller;


import com.example.adventuregame.Model.ScenarioConfig;

import java.util.Objects;

public class ForkManager extends  ScenarioManager{
    private final String previousForkId;
    private Boolean forkWasNoAttended = true;
    private final CombatManager combatManager;
    private final NPCRoomManager npcRoomManager;
    private final QuestionRoomManager questionRoomManager;
    private ScenarioConfig.Scenario currentScenario;
    private final ScenarioConfig scenarioForkConfig;
    private final int previousForkFrom;
    public QuestionRoomManager getQuestionRoomManager() {
        return questionRoomManager;
    }
    public CombatManager getCombatManager() {
        return combatManager;
    }

    public String getForkId() {
        return forkId;
    }

    public String getPreviousForkId() {
        return previousForkId;
    }

    public int getPreviousForkFrom() {
        return previousForkFrom;
    }

    public NPCRoomManager getNpcRoomManager() {
        return npcRoomManager;
    }

    public ForkManager(String previousForkId, String forkId, int previousForkFrom, CombatManager combatManager, NPCRoomManager npcRoomManager, QuestionRoomManager questionRoomManager,
                       ScenarioConfig scenarioForkConfig) {
        this.previousForkId = previousForkId;
        this.npcRoomManager = npcRoomManager;
        this.questionRoomManager = questionRoomManager;
        this.forkId = forkId;
        this.combatManager = combatManager;
        this.scenarioForkConfig = scenarioForkConfig;
        this.previousForkFrom = previousForkFrom;
    }

    public void startingFork() {
        gameScreen = combatManager.getGameScreen();
        if(Objects.equals(forkId, "startingFork")){
            currentScenario = scenarioForkConfig.getScenarioById(forkId);
        }else{
            currentScenario = scenarioForkConfig.getScenarioById(forkId.substring(0, 11));
        }
        if(forkWasNoAttended){
            if (NPCRoomManager.getHelpCounter() >= Scenario.getEndCounter()){
                endGame();
                return;
            } else {
                displayScenario(currentScenario, true);
                forkWasNoAttended = false;
            }
        } else {
            if (NPCRoomManager.getHelpCounter() >= Scenario.getEndCounter()){
                endGame();
                return;
            } else {
                displayScenario(currentScenario, false);
            }
        }
        updateButtons(currentScenario, combatManager.isFirstMonsterAlive(), npcRoomManager.getItemWasNoFound(), questionRoomManager.getAttemptWasNoMade());
    }

    private void endGame() {
        NPCRoomManager.setHelpCounter(0);
        currentScenario = scenarioForkConfig.getScenarioById("endGame");
        displayScenario(currentScenario, true);
        updateButtons(currentScenario, true, true, true);

    }

    public void leaveCombat(){
        startingFork();
    }
    public void combatIsLeaf(){
        currentScenario = combatManager.getScenarioConfig().getScenarioById("combatIsLeaf");
        combatManager.displayScenario(currentScenario, Objects.equals(combatManager.getEndContext(), "leaf"));
        combatManager.updateButtons(currentScenario, Objects.equals(combatManager.getEndContext(), "leaf"), true, true);
    }
    public void npcRoomIsLeaf(){
        currentScenario = npcRoomManager.getScenarioConfig().getScenarioById("npcRoomIsLeaf");
        npcRoomManager.displayScenario(currentScenario, Objects.equals(npcRoomManager.getEndContext(), "leaf"));
        npcRoomManager.updateButtons(currentScenario, Objects.equals(npcRoomManager.getEndContext(), "leaf"), true, true);
    }


}
