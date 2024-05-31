package com.example.adventuregame.Controller;


import com.example.adventuregame.Model.Item;
import com.example.adventuregame.Model.SuperMonster;
import com.example.adventuregame.Model.ScenarioConfig;
import com.example.adventuregame.View.GameScreen;
import com.example.adventuregame.Model.Character;

public abstract class ScenarioManager{

    protected SuperMonster monster;
    protected Character character;
    protected GameScreen gameScreen;
    protected ScenarioConfig scenarioConfig;
    protected ScenarioConfig.Scenario currentScenario;
    protected String npcName;
    protected String question;
    protected Item requiredItem;
    protected String forkId;
    public ScenarioConfig getScenarioConfig() {
        return scenarioConfig;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void displayScenarioWithOperation(ScenarioConfig.Scenario scenario, boolean textCondition, int operation) {
        String description;

        if (textCondition){
            description = scenario.getDescriptionDefault();
        }else{
            description = scenario.getDescriptionSecond();
        }
        String descriptionFormatted = descriptionFormatted(description).replace("{operation}", String.valueOf(operation));
        gameScreen.printText(gameScreen.getIndex(), descriptionFormatted);
    }

    public void displayScenario(ScenarioConfig.Scenario scenario, boolean textCondition) {
        String description;
        if (textCondition){
            description = scenario.getDescriptionDefault();
        }else{
            description = scenario.getDescriptionSecond();
        }
        String descriptionFormatted = descriptionFormatted(description);
        gameScreen.printText(gameScreen.getIndex(), descriptionFormatted);
    }

    public void updateButtons(ScenarioConfig.Scenario scenario, Boolean condition1, Boolean condition2, Boolean condition3) {
        for (int i = 0; i < 4; i++) {
            if (i < scenario.getButtons().size()) {
                ScenarioConfig.Scenario.ButtonDescription button = scenario.getButtons().get(i);
                switch (i) {
                    case 0:
                        gameScreen.getButton1().setText(condition1? descriptionFormatted(button.getTextDefault()): descriptionFormatted(button.getTextSecond()));
                        gameScreen.setChoice1(condition1 ? button.getChoiceDefault() : button.getChoiceSecond());
                        break;
                    case 1:
                        gameScreen.getButton2().setText(condition2? descriptionFormatted(button.getTextDefault()): descriptionFormatted(button.getTextSecond()));
                        gameScreen.setChoice2(condition2 ? button.getChoiceDefault() : button.getChoiceSecond());
                        break;
                    case 2:
                        gameScreen.getButton3().setText(condition3? descriptionFormatted(button.getTextDefault()):descriptionFormatted(button.getTextSecond()));
                        gameScreen.setChoice3(condition3 ? button.getChoiceDefault() : button.getChoiceSecond());
                        break;
                    case 3:
                        gameScreen.getButton4().setText(condition3? descriptionFormatted(button.getTextDefault()): descriptionFormatted(button.getTextSecond()));
                        gameScreen.setChoice4(condition3 ? button.getChoiceDefault() : button.getChoiceSecond());
                        break;
                }
            }
        }
    }

    public String descriptionFormatted(String description){
        return (description != null) ? description
                .replace("{numberOfFork}", (forkId != null && forkId.length() > 11) ? forkId.substring(11) : "")
                .replace("{questionLine}", question != null ? question : "")
                .replace("{npcName}", npcName != null ? npcName : "")
                .replace("{itemType}", requiredItem != null ? requiredItem.getType(): "")
                .replace("{monsterName}", monster != null ? monster.getName() : "")
                .replace("{monsterType}", monster != null ? monster.getType() : "")
                .replace("{receivedItem}", character != null && !character.getInventory().isEmpty() ? character.getInventory().get(character.getInventory().size()-1).getName() : "")
                .replace("{monsterDamage}", monster != null ?  String.valueOf(monster.getAttack()) : "")
                .replace("{monsterHp}", monster != null ? String.valueOf(monster.getHp()) : "")
                .replace("{characterHp}", character != null ? String.valueOf(character.getHp()) : "")
                .replace("{weaponName}", character != null && character.getWeapon() != null ? character.getWeapon().getName() : "")
                .replace("{weaponDamage}", character != null && character.getWeapon() != null ? String.valueOf(character.getWeapon().getDamage()) : "") : null;
    }



}
