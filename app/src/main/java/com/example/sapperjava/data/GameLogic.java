package com.example.sapperjava.data;

import com.example.sapperjava.domain.CellUnit;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {

    private BombGrid mineGrid;
    private boolean gameOver;
    private boolean flagMode;
    private boolean clearMode;
    private int flagCount;
    private int numberBombs;
    private boolean timeExpired;

    public GameLogic(int size, int numberBombs) {
        this.gameOver = false;
        this.flagMode = false;
        this.clearMode = true;
        this.timeExpired = false;
        this.flagCount = 0;
        this.numberBombs = numberBombs;
        mineGrid = new BombGrid(size);
        mineGrid.generateGrid(numberBombs);
    }

    public void handleCellClick(CellUnit cell) {
        if (!gameOver && !isGameWon() && !timeExpired && !cell.isRevealed()) {
            if (clearMode) {
                clear(cell);
            } else if (flagMode) {
                flag(cell);
            }
        }
    }

    public void clear(CellUnit cell) {
        int index = getMineGrid().getCells().indexOf(cell);
        getMineGrid().getCells().get(index).setRevealed(true);

        if (cell.getValue() == CellUnit.BOMB) {
            gameOver = true;
        } else if (cell.getValue() == CellUnit.BLANK) {
            List<CellUnit> toClear = new ArrayList<>();
            List<CellUnit> toCheckAdjacents = new ArrayList<>();

            toCheckAdjacents.add(cell);

            while (toCheckAdjacents.size() > 0) {
                CellUnit c = toCheckAdjacents.get(0);
                int cellIndex = getMineGrid().getCells().indexOf(c);
                int[] cellPos = getMineGrid().toXY(cellIndex);
                for (CellUnit adjacent: getMineGrid().adjacentCells(cellPos[0], cellPos[1])) {
                    if (adjacent.getValue() == CellUnit.BLANK) {
                        if (!toClear.contains(adjacent)) {
                            if (!toCheckAdjacents.contains(adjacent)) {
                                toCheckAdjacents.add(adjacent);
                            }
                        }
                    } else {
                        if (!toClear.contains(adjacent)) {
                            toClear.add(adjacent);
                        }
                    }
                }
                toCheckAdjacents.remove(c);
                toClear.add(c);
            }

            for (CellUnit c: toClear) {
                c.setRevealed(true);
            }
        }
    }

    public void flag(CellUnit cell) {
        cell.setFlagged(!cell.isFlagged());
        int count = 0;
        for (CellUnit c: getMineGrid().getCells()) {
            if (c.isFlagged()) {
                count++;
            }
        }
        flagCount = count;
    }

    public boolean isGameWon() {
        int numbersUnrevealed = 0;
        for (CellUnit c: getMineGrid().getCells()) {
            if (c.getValue() != CellUnit.BOMB && c.getValue() != CellUnit.BLANK && !c.isRevealed()) {
                numbersUnrevealed++;
            }
        }

        if (numbersUnrevealed == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void toggleMode() {
        clearMode = !clearMode;
        flagMode = !flagMode;
    }

    public void outOfTime() {
        timeExpired = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public BombGrid getMineGrid() {
        return mineGrid;
    }

    public boolean isFlagMode() {
        return flagMode;
    }

    public boolean isClearMode() {
        return clearMode;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public int getNumberBombs() {
        return numberBombs;
    }
}
