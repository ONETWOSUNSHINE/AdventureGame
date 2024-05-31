package com.example.adventuregame.Controller;

import android.content.Context;


import com.example.adventuregame.Model.Character;
import com.example.adventuregame.Model.Item;
import com.example.adventuregame.Model.SuperWeapon;
import com.example.adventuregame.Model.SuperMonster;
import com.example.adventuregame.Model.ScenarioConfig;
import com.example.adventuregame.Model.RandomGenerator;
import com.example.adventuregame.View.GameScreen;
import com.example.adventuregame.View.TitleScreen;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Scenario {
    protected GameScreen gameScreen;
    protected Character character;
    protected SuperMonster monster;
    Item item;
    SuperWeapon weapon;
    protected ScenarioConfig scenarioBattleConfig;
    protected ScenarioConfig scenarioForkConfig;
    protected ScenarioConfig scenarioNPCConfig;
    protected ScenarioConfig scenarioQuestionConfig;
    private ForkManager forkManager;
    private final ArrayList<ForkManager> listOfForks = new ArrayList<>();
    Random RANDOM = new Random();
    private int forkCounter = 1;




    public Scenario(GameScreen gameScreen, Context context) {
        this.gameScreen = gameScreen;
        this.scenarioBattleConfig = ScenarioConfig.loadFromAsset(context, "scenario_battle_config.json");
        this.scenarioForkConfig = ScenarioConfig.loadFromAsset(context, "scenario_fork_config.json");
        this.scenarioNPCConfig = ScenarioConfig.loadFromAsset(context, "scenario_npc_config.json");
        this.scenarioQuestionConfig = ScenarioConfig.loadFromAsset(context, "scenario_question_config.json");

    }



    public void selectChoice(String choice){
        switch (choice){
            case "monster": forkManager.getCombatManager().monster(); break;
            case "fight": forkManager.getCombatManager().fight();break;
            case "leaveCombat": forkManager.leaveCombat();break;
            case "runAway":forkManager.getCombatManager().runAway();break;
            case "death":forkManager.getCombatManager().death();break;
            case "characterAttack":forkManager.getCombatManager().characterAttack();break;
            case "monsterAttack":forkManager.getCombatManager().monsterAttack();break;
            case "victory":forkManager.getCombatManager().victory();break;
            case "treasureRoom":forkManager.getCombatManager().treasureRoom();break;
            case "inventory": gameScreen.openInventory(character);break;
            case "combatIsLeaf": forkManager.combatIsLeaf();break;
            case "forkMakerFromCombat": forkMakerFromCombat();break;
            case "goToPreviousFork": goToPreviousFork();break;

            case "comeNPCRoom": forkManager.getNpcRoomManager().comeNPCRoom();break;
            case "firstLine": forkManager.getNpcRoomManager().firstLine();break;
            case "secondLine": forkManager.getNpcRoomManager().secondLine();break;
            case "thirdLine": forkManager.getNpcRoomManager().thirdLine();break;
            case "forthLine": forkManager.getNpcRoomManager().forthLine();break;
            case "giveItem": forkManager.getNpcRoomManager().giveItem();break;
            case "finalLine": forkManager.getNpcRoomManager().finalLine();break;
            case "roomAfterItem": forkManager.getNpcRoomManager().roomAfterItem();break;
            case "forkMakerFromNPC": forkMakerFromNPC();break;
            case "npcRoomIsLeaf": forkManager.npcRoomIsLeaf();break;

            case "comeQuestionRoom": forkManager.getQuestionRoomManager().comeQuestionRoom();break;
            case "question": forkManager.getQuestionRoomManager().question();break;
            case "checkAnswerYes": forkManager.getQuestionRoomManager().checkAnswer(true);break;
            case "checkAnswerNo": forkManager.getQuestionRoomManager().checkAnswer(false);break;
            case "itemAnswer":forkManager.getQuestionRoomManager().itemAnswer();break;
            case "afterItemRoom": forkManager.getQuestionRoomManager().afterItemRoom();break;
            case "title screen":
                gameScreen.titleScreen(); break;
        }
    }

    public void startingInScPoint() {
        character = new Character(80, new SuperWeapon("Fists", 5), new ArrayList<>(), gameScreen);

        forkMaker("startingFork", 0);
    }
    public void forkMakerFromCombat(){
        forkMaker(forkManager.getCombatManager().getEndContext(), 1);

    }
    public void forkMakerFromNPC(){
        forkMaker(forkManager.getNpcRoomManager().getEndContext(), 2);

    }

    public void forkMaker(String startContext, int previousForkFrom){
        ForkManager previousForkManager = forkManager;
        for (ForkManager fork: listOfForks){
            if (Objects.equals(startContext, fork.getForkId())){
                forkManager = fork;
                fork.startingFork();
                return;
            }
        }
        Object[] newMonster = RandomGenerator.getRandomMonster();
        String newItem = RandomGenerator.generateRandomItem();
        String newName = RandomGenerator.generateRandomName();
        Object[] newWeapon = RandomGenerator.getRandomWeapon();
        Object[] newQuestion = TitleScreen.getTriviaQuestions()[RANDOM.nextInt(TitleScreen.getTriviaQuestions().length)];
        String endContextCombat = "anotherFork" + forkCounter++;
        String endContextNPC = "anotherFork" + forkCounter++;

        monster = new SuperMonster((String) newMonster[0], "Monster", (int) newMonster[1],(int) newMonster[2]);
        item = new Item(newName + "'s " + newItem, newItem);
        weapon = new SuperWeapon((String) newWeapon[0], (int) newWeapon[1]);
        QuestionRoomManager questionRoomManager = new QuestionRoomManager((String) newQuestion[0], character, gameScreen, scenarioQuestionConfig, weapon, (Boolean) newQuestion[1]);

        CombatManager combatManager = new CombatManager(monster, character, gameScreen, scenarioBattleConfig, endContextCombat, item);
        NPCRoomManager npcRoomManager = new NPCRoomManager(newName, character, gameScreen, scenarioNPCConfig, endContextNPC, item);
        forkManager = new ForkManager((previousForkManager != null) ? previousForkManager.getForkId() : "title screen", startContext, previousForkFrom,
                combatManager, npcRoomManager, questionRoomManager,
                scenarioForkConfig);
        listOfForks.add(forkManager);
        forkManager.startingFork();
    }
    public void goToPreviousFork(){
        int i = forkManager.getPreviousForkFrom();
        for (ForkManager fork: listOfForks){
            if(Objects.equals(fork.getForkId(), forkManager.getPreviousForkId())){
                forkManager = fork;
                break;
            }
        }
        switch (i){
            case 0:  gameScreen.titleScreen();break;
            case 1: forkManager.getCombatManager().treasureRoom();break;
            case 2: forkManager.getNpcRoomManager().roomAfterItem();break;
        }

    }


    public static int getEndCounter() {
        return 5;
    }
}
