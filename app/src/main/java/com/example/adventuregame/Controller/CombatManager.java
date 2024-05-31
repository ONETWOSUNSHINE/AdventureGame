package com.example.adventuregame.Controller;


import com.example.adventuregame.Model.Character;
import com.example.adventuregame.Model.Item;
import com.example.adventuregame.Model.SuperMonster;
import com.example.adventuregame.Model.ScenarioConfig;
import com.example.adventuregame.View.GameScreen;

public class CombatManager extends ScenarioManager {

    private boolean monsterWasNoAttacked = true;
    private boolean isFirstMonsterAlive = true;
    private Boolean treasureRoomWasNoAttend = true;
    private final Item item;
    private final String endContext;
    public boolean isFirstMonsterAlive() {
        return isFirstMonsterAlive;
    }

    public String getEndContext() {
        return endContext;
    }


    public CombatManager(SuperMonster monster, Character character, GameScreen gameScreen, ScenarioConfig scenarioConfig,
                         String endContext, Item item) {
        this.character = character;
        this.gameScreen = gameScreen;
        this.scenarioConfig = scenarioConfig;
        this.monster = monster;
        this.endContext = endContext;
        this.item = item;

    }

    public void monster(){
        currentScenario = scenarioConfig.getScenarioById("monster");
        displayScenario(currentScenario, monsterWasNoAttacked);
        updateButtons(currentScenario, true, true, true);
    }

    public void characterAttack(){
        currentScenario = scenarioConfig.getScenarioById("characterAttack");
        int characterDamage = new java.util.Random().nextInt(character.getWeapon().getDamage());
        monster.setHp(monster.getHp()-characterDamage);
        if(monsterWasNoAttacked){
            monsterWasNoAttacked = false;
        }
        displayScenarioWithOperation(currentScenario, true, characterDamage);
        updateButtons(currentScenario, monster.getHp() < 1, monster.getHp() < 1, monster.getHp() < 1);
    }

    public void monsterAttack(){
        currentScenario = scenarioConfig.getScenarioById("monsterAttack");
        int monsterDamage = new java.util.Random().nextInt(monster.getAttack());
        character.setHp(character.getHp()-monsterDamage);
        displayScenarioWithOperation(currentScenario, true, monsterDamage);
        updateButtons(currentScenario, character.getHp() < 1, character.getHp() < 1, character.getHp() < 1);

    }

    public void runAway(){
        currentScenario = scenarioConfig.getScenarioById("runAway");
        int monsterDamage = new java.util.Random().nextInt(monster.getAttack());
        displayScenarioWithOperation(currentScenario, true, monsterDamage);
        character.setHp(character.getHp()-monsterDamage);
        updateButtons(currentScenario, character.getHp() < 1, character.getHp() < 1, character.getHp() < 1);

    }

    public void fight(){
        currentScenario = scenarioConfig.getScenarioById("fight");
        displayScenario(currentScenario, monsterWasNoAttacked);
        updateButtons(currentScenario, true, true, true);

    }

    public void victory(){
        currentScenario = scenarioConfig.getScenarioById("victory");
        displayScenario(currentScenario, true);
        isFirstMonsterAlive = false;
        updateButtons(currentScenario, true, true, true);

    }

    public void death(){
        currentScenario = scenarioConfig.getScenarioById("death");
        displayScenario(currentScenario, true);
        updateButtons(currentScenario, true, true, true);

    }

    public void treasureRoom(){
        currentScenario = scenarioConfig.getScenarioById("treasureRoom");
        if (treasureRoomWasNoAttend){
            character.addItemToInventory(item);
            character.setHp(character.getHp() + new java.util.Random().nextInt(character.getHp() * 2));
            displayScenario(currentScenario, true);
            treasureRoomWasNoAttend = false;
        }else{
            displayScenario(currentScenario, false);
        }
        updateButtons(currentScenario, true, true, true);
    }



}
