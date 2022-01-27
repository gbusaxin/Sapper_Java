package com.example.sapperjava.data;

import com.example.sapperjava.domain.CellUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BombGrid {
    private List<CellUnit> cells;
    private int size;

    public BombGrid(int size) {
        this.size = size;
        this.cells = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            cells.add(new CellUnit(CellUnit.BLANK));
        }
    }

    public void generateGrid(int totalBombs) {
        int bombsPlaced = 0;
        while (bombsPlaced < totalBombs) {
            int x = new Random().nextInt(size);
            int y = new Random().nextInt(size);

            if (cellAt(x, y).getValue() == CellUnit.BLANK) {
                cells.set(x + (y*size), new CellUnit(CellUnit.BOMB));
                bombsPlaced++;
            }
        }

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (cellAt(x, y).getValue() != CellUnit.BOMB) {
                    List<CellUnit> adjacentCells = adjacentCells(x, y);
                    int countBombs = 0;
                    for (CellUnit cell: adjacentCells) {
                        if (cell.getValue() == CellUnit.BOMB) {
                            countBombs++;
                        }
                    }
                    if (countBombs > 0) {
                        cells.set(x + (y*size), new CellUnit(countBombs));
                    }
                }
            }
        }
    }

    public CellUnit cellAt(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return null;
        }
        return cells.get(x + (y*size));
    }

    public List<CellUnit> adjacentCells(int x, int y) {
        List<CellUnit> adjacentCells = new ArrayList<>();

        List<CellUnit> cellsList = new ArrayList<>();
        cellsList.add(cellAt(x-1, y));
        cellsList.add(cellAt(x+1, y));
        cellsList.add(cellAt(x-1, y-1));
        cellsList.add(cellAt(x, y-1));
        cellsList.add(cellAt(x+1, y-1));
        cellsList.add(cellAt(x-1, y+1));
        cellsList.add(cellAt(x, y+1));
        cellsList.add(cellAt(x+1, y+1));

        for (CellUnit cell: cellsList) {
            if (cell != null) {
                adjacentCells.add(cell);
            }
        }

        return adjacentCells;
    }

    public int toIndex(int x, int y) {
        return x + (y*size);
    }

    public int[] toXY(int index) {
        int y = index / size;
        int x = index - (y*size);
        return new int[]{x, y};
    }

    public void revealAllBombs() {
        for (CellUnit c: cells) {
            if (c.getValue() == CellUnit.BOMB) {
                c.setRevealed(true);
            }
        }
    }

    public List<CellUnit> getCells() {
        return cells;
    }
}
