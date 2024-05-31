package com.example.adventuregame.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adventuregame.Model.Character;
import com.example.adventuregame.Model.Item;
import com.example.adventuregame.Model.SuperWeapon;
import com.example.adventuregame.R;

public class Inventory_RecyclerViewAdapter extends RecyclerView.Adapter<Inventory_RecyclerViewAdapter.MyViewHolder> {
    private final Context context;
    private final Character character;

    public Inventory_RecyclerViewAdapter(Context context, Character character) {
        this.context = context;
        this.character = character;
    }

    @NonNull
    @Override
    public Inventory_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_rows, parent, false);
        return new Inventory_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Inventory_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textName.setText(character.getInventory().get(position).getName());
        if(character.getInventory().get(position) instanceof SuperWeapon){
            String textToView = "Damage: "+ ((SuperWeapon) character.getInventory().get(position)).getDamage();
            holder.textProper.setText(textToView);
        } else {
            holder.textProper.setText("");
        }
        holder.itemCard.setOnClickListener(v -> handleButtonClick(character.getInventory().get(position)));

    }
    private void handleButtonClick(Item item) {
        if(item instanceof SuperWeapon){
            character.setWeapon((SuperWeapon) item);
        }

    }

    @Override
    public int getItemCount() {
        return character.getInventory().size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textName, textProper;
        CardView itemCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textProper = itemView.findViewById(R.id.textProper);
            itemCard = itemView.findViewById(R.id.itemCard);
        }
    }
}
