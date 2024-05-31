package com.example.adventuregame.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adventuregame.Model.Character;
import com.example.adventuregame.R;

import java.util.Objects;

public class InventoryDialogFragment extends DialogFragment {
    TextView inventoryName;
    Button closeButton;
    private static final String ARG_CHARACTER = "character";
    private Character character;

    public static InventoryDialogFragment newInstance(Character character){
        InventoryDialogFragment fragment = new InventoryDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHARACTER, character);
        fragment.setArguments(args);
        return fragment;
    }
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.inventory_dialog_fragment, container, false);
        inventoryName = rootView.findViewById(R.id.inventoryName);
        closeButton = rootView.findViewById(R.id.closeButton);
        inventoryName.setText("Your inventory");
        closeButton.setOnClickListener(v -> Objects.requireNonNull(getDialog()).dismiss());

        if (getArguments() != null) {
            character = (Character) getArguments().getSerializable(ARG_CHARACTER);
        }
        RecyclerView recyclerView = rootView.findViewById(R.id.mRecyclerView);
        Inventory_RecyclerViewAdapter adapter = new Inventory_RecyclerViewAdapter(requireContext(), character);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        return rootView;
    }
}
