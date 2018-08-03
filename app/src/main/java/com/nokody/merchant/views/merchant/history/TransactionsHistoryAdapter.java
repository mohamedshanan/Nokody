package com.nokody.merchant.views.merchant.history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nokody.merchant.R;
import com.nokody.merchant.data.models.Transaction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionsHistoryAdapter extends RecyclerView.Adapter<TransactionsHistoryAdapter.TransactionViewHolder> {

    private final Context context;
    private final List<Transaction> transactions;
    private Integer myId;

    TransactionsHistoryAdapter(Context context, List<Transaction> transactions, Integer myId) {
        this.context = context;
        this.transactions = transactions;
        this.myId = myId;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        if (transaction.isSuccessful()) {
            holder.state.setImageResource(R.drawable.ic_check);
        } else {
            holder.state.setImageResource(R.drawable.ic_failed);
        }

        int index = transaction.getDate().indexOf(".");
        String dateTime = transaction.getDate().substring(0, index).replace("T", " ");
        holder.time.setText(dateTime);

        if (transaction.getFromAccountId() == myId) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.colorGreen_500));
        }

        holder.amount.setText(String.valueOf(transaction.getAmount()) + " SAR");

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.state)
        ImageView state;

        @Nullable
        @BindView(R.id.time)
        TextView time;

        @Nullable
        @BindView(R.id.amount)
        TextView amount;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
