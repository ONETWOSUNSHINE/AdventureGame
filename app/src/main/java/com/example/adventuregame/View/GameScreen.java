package com.example.adventuregame.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adventuregame.Controller.Scenario;
import com.example.adventuregame.Model.Character;
import com.example.adventuregame.R;


public class GameScreen extends AppCompatActivity {
    private TextView gameScreenText, weaponName;
    private EditText healthBar;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private String choice1, choice2, choice3, choice4;

    private final int[] index = {0}; // for printText()


    public void setShowFullText(boolean showFullText) {
        isShowFullText = showFullText;
    }

    private boolean isShowFullText = false;
    public int[] getIndex() {
        return index;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public Button getButton1() {
        return button1;
    }


    public Button getButton2() {
        return button2;
    }


    public Button getButton3() {
        return button3;
    }

    public Button getButton4() {
        return button4;
    }

    Scenario inSc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inSc = new Scenario(this, this);
        gameScreenText = findViewById(R.id.gameScreenText);
        healthBar = findViewById(R.id.HPStatus);
        weaponName = findViewById(R.id.WeaponName);
        button1 = findViewById(R.id.choiceButton1);
        button2 = findViewById(R.id.choiceButton2);
        button3 = findViewById(R.id.choiceButton3);
        button4 = findViewById(R.id.choiceButton4);
        Button inventoryButton = findViewById(R.id.inventoryButton);
        button1.setOnClickListener(v -> handleButtonClick(getChoice1()));
        button2.setOnClickListener(v -> handleButtonClick(getChoice2()));
        button3.setOnClickListener(v -> handleButtonClick(getChoice3()));
        button4.setOnClickListener(v -> handleButtonClick(getChoice4()));
        inventoryButton.setOnClickListener(v -> inSc.selectChoice("inventory"));
        inSc.startingInScPoint();

    }






    private void handleButtonClick(String choice) {
        if (isShowFullText) {
            isShowFullText = false;
            inSc.selectChoice(choice);
        } else {
            setShowFullText(true);
        }
    }

    public void titleScreen() {
        Intent titleScreen = new Intent(this, TitleScreen.class);
        startActivity(titleScreen);
    }
    public void printText(final int[] ind, String textToPrint){
        if(ind[0]==0){
            gameScreenText.setText("");
        }
        if(isShowFullText){
            showFullText(textToPrint);
            ind[0]=0;
            return;
        }
        if (ind[0] < textToPrint.length()) {
            gameScreenText.append(String.valueOf(textToPrint.charAt(ind[0])));
            ind[0]++;
            new Handler().postDelayed(() -> printText(ind, textToPrint), 80);
        }
        else{
            ind[0]=0;
            isShowFullText = true;
        }
    }
    public void showFullText(String textToPrint){
        gameScreenText.setText(textToPrint);
    }
    public void openInventory(Character character){
        InventoryDialogFragment dialogFragment = InventoryDialogFragment.newInstance(character);
        dialogFragment.show(getSupportFragmentManager(), "inventory_dialog");
    }

    public TextView getHealthBar() {
        return healthBar;
    }

    public TextView getWeaponName() {
        return weaponName;
    }



}