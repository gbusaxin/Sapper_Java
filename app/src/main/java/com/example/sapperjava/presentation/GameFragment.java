package com.example.sapperjava.presentation;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sapperjava.R;
import com.example.sapperjava.data.GameLogic;
import com.example.sapperjava.data.OnCellUnitClickListener;
import com.example.sapperjava.databinding.FragmentGameBinding;
import com.example.sapperjava.domain.CellUnit;

public class GameFragment extends Fragment implements OnCellUnitClickListener {

    private FragmentGameBinding binding;
    public static final long TIMER_LENGTH = 999000L;    // 999 seconds in milliseconds
    public static final long TIMER_INTERVAL = 1000;    // 999 seconds in milliseconds
    public static final int BOMB_COUNT = 10;
    public static final int GRID_SIZE = 10;

    private AdapterRvGame adapterRvGame;
    private RecyclerView rvGame;
    private TextView play, timer, flag, flagsLeft;
    private GameLogic gameLogic;
    private CountDownTimer countDownTimer;
    private int secondsElapsed;
    private boolean timerStarted;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvGame = binding.recyclerViewGame;
        timer = binding.textViewGameTimer;
        flagsLeft = binding.textViewFlagsCount;
        play = binding.textViewPlay;
        flag = binding.textViewGameFlag;

        rvGame.setLayoutManager(new GridLayoutManager(requireContext(),GRID_SIZE));
        timerStarted = false;
        countDownTimer = new CountDownTimer(TIMER_LENGTH,TIMER_INTERVAL) {

            @Override
            public void onTick(long l) {
                secondsElapsed += 1;
                timer.setText(String.format("%03d", secondsElapsed));
            }

            @Override
            public void onFinish() {
                gameLogic.outOfTime();
                Toast.makeText(getContext(), "Game Over: Timer Expired", Toast.LENGTH_SHORT).show();
                gameLogic.getMineGrid().revealAllBombs();
                adapterRvGame.setList(gameLogic.getMineGrid().getCells());
            }
        };

        gameLogic = new GameLogic(GRID_SIZE, BOMB_COUNT);
        flagsLeft.setText(String.format("%03d", gameLogic.getNumberBombs() - gameLogic.getFlagCount()));
        adapterRvGame = new AdapterRvGame(gameLogic.getMineGrid().getCells(), this);
        rvGame.setAdapter(adapterRvGame);

        play.setOnClickListener(view1 -> {
            gameLogic = new GameLogic(GRID_SIZE, BOMB_COUNT);
            adapterRvGame.setList(gameLogic.getMineGrid().getCells());
            timerStarted = false;
            countDownTimer.cancel();
            secondsElapsed = 0;
            String defValue = getResources().getString(R.string.default_value);
            timer.setText(defValue);
            flagsLeft.setText(String.format("%03d", gameLogic.getNumberBombs() - gameLogic.getFlagCount()));
        });

        flag.setOnClickListener(view12 -> {
            gameLogic.toggleMode();
            if (gameLogic.isFlagMode()) {
                GradientDrawable border = new GradientDrawable();
                border.setColor(0xFFFFFFFF);
                border.setStroke(1, 0xFF000000);
                flag.setBackground(border);
            } else {
                GradientDrawable border = new GradientDrawable();
                border.setColor(0xFFFFFFFF);
                flag.setBackground(border);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onCellUnitClick(CellUnit cell) {
        gameLogic.handleCellClick(cell);

        flagsLeft.setText(String.format("%03d", gameLogic.getNumberBombs() - gameLogic.getFlagCount()));

        if (!timerStarted) {
            countDownTimer.start();
            timerStarted = true;
        }

        if (gameLogic.isGameOver()) {
            countDownTimer.cancel();
            Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
            gameLogic.getMineGrid().revealAllBombs();
        }

        if (gameLogic.isGameWon()) {
            countDownTimer.cancel();
            Toast.makeText(getContext(), "Game Won", Toast.LENGTH_SHORT).show();
            gameLogic.getMineGrid().revealAllBombs();
        }

        adapterRvGame.setList(gameLogic.getMineGrid().getCells());
    }
}