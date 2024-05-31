package com.example.adventuregame.Controller;


import com.example.adventuregame.Model.Character;
import com.example.adventuregame.Model.Item;
import com.example.adventuregame.Model.ScenarioConfig;
import com.example.adventuregame.View.GameScreen;

import java.util.Objects;

public class NPCRoomManager extends ScenarioManager {
    private final String endContext;
    private Boolean npcRoomWasNoAttended = true;
    private Boolean itemWasNoFound = true;
    private static int helpCounter = 0;

    public static int getHelpCounter() {
        return helpCounter;
    }

    public static void setHelpCounter(int helpCounter) {
        NPCRoomManager.helpCounter = helpCounter;
    }

    public Boolean getItemWasNoFound() {
        return itemWasNoFound;
    }
    public String getEndContext() {
        return endContext;
    }



    public NPCRoomManager(String npcName, Character character, GameScreen gameScreen, ScenarioConfig scenarioConfig,
                          String endContext, Item requiredItem) {
        this.npcName = npcName;
        this.character = character;
        this.gameScreen = gameScreen;
        this.scenarioConfig = scenarioConfig;
        this.endContext = endContext;
        this.requiredItem = requiredItem;
    }

    public void comeNPCRoom(){
        currentScenario = scenarioConfig.getScenarioById("comeNPCRoom");
        displayScenario(currentScenario, npcRoomWasNoAttended);
        updateButtons(currentScenario, npcRoomWasNoAttended, npcRoomWasNoAttended, true);
    }
    public void firstLine(){
        if(npcRoomWasNoAttended){
            npcRoomWasNoAttended = false;
        }
        currentScenario = scenarioConfig.getScenarioById("firstLine");
        displayScenario(currentScenario, true);
        updateButtons(currentScenario, true, true, true);
    }
    public void secondLine(){
        if(npcRoomWasNoAttended){
            npcRoomWasNoAttended = false;
        }
        currentScenario = scenarioConfig.getScenarioById("secondLine");
        displayScenario(currentScenario, true);
        updateButtons(currentScenario, true, true, true);
    }
    public void thirdLine(){
        currentScenario = scenarioConfig.getScenarioById("thirdLine");
        displayScenario(currentScenario, true);
        updateButtons(currentScenario, true, true, true);
    }
    public void forthLine(){
        currentScenario = scenarioConfig.getScenarioById("forthLine");
        displayScenario(currentScenario, true);
        updateButtons(currentScenario, true, true, true);
    }
    public void giveItem(){
        currentScenario = scenarioConfig.getScenarioById("giveItem");
        findItemInInventory();
        displayScenario(currentScenario, findItemInInventory());
        updateButtons(currentScenario, findItemInInventory(), true, true);
    }
    public Boolean findItemInInventory(){
        for (Item item: character.getInventory()){
            if(Objects.equals(item.getName(), requiredItem.getName())){
                return true;
            }
        }
        return false;
    }
    public void finalLine(){
        itemWasNoFound = false;
        for (Item item: character.getInventory()){
            if(Objects.equals(item.getName(), requiredItem.getName())){
                character.getInventory().remove(item);
                break;
            }
        }
        helpCounter++;
        currentScenario = scenarioConfig.getScenarioById("finalLine");
        displayScenario(currentScenario, true);
        updateButtons(currentScenario, true, true, true);
    }
    public void roomAfterItem(){
        currentScenario = scenarioConfig.getScenarioById("roomAfterItem");
        displayScenario(currentScenario, true);
        updateButtons(currentScenario, true, true, true);
    }
}
