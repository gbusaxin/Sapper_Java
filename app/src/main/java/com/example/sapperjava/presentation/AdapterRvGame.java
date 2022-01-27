package com.example.sapperjava.presentation;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sapperjava.R;
import com.example.sapperjava.data.OnCellUnitClickListener;
import com.example.sapperjava.domain.CellUnit;

import java.util.List;

public class AdapterRvGame extends RecyclerView.Adapter<AdapterRvGame.ViewHolderRvGame> {

    private List<CellUnit> list;
    private OnCellUnitClickListener listener;

    public AdapterRvGame(List<CellUnit> list, OnCellUnitClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterRvGame.ViewHolderRvGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
        return new ViewHolderRvGame(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRvGame.ViewHolderRvGame holder, int position) {
        holder.bind(list.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CellUnit> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolderRvGame extends RecyclerView.ViewHolder {

        TextView tvCell;

        public ViewHolderRvGame(@NonNull View itemView) {
            super(itemView);
            tvCell = itemView.findViewById(R.id.item_cell);
        }

        public void bind(final CellUnit cell) {
            itemView.setBackgroundColor(Color.GRAY);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCellUnitClick(cell);
                }
            });

            if (cell.isRevealed()) {
                if (cell.getValue() == CellUnit.BOMB) {
                    tvCell.setText(R.string.mine);
                } else if (cell.getValue() == CellUnit.BLANK) {
                    tvCell.setText("");
                    itemView.setBackgroundColor(Color.WHITE);
                } else {
                    tvCell.setText(String.valueOf(cell.getValue()));
                    if (cell.getValue() == 1) {
                        tvCell.setTextColor(Color.BLUE);
                    } else if (cell.getValue() == 2) {
                        tvCell.setTextColor(Color.GREEN);
                    } else if (cell.getValue() == 3) {
                        tvCell.setTextColor(Color.RED);
                    }
                }
            } else if (cell.isFlagged()) {
                tvCell.setText(R.string.flag);
            }
        }
    }
}
