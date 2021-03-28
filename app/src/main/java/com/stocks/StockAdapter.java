package com.stocks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
import java.util.List;

public class StockAdapter extends ArrayAdapter<Stock>
{
    private LayoutInflater inflater;
    private int layout;
    private List<Stock> Stocks;
 
    public StockAdapter(Context context, int resource, List<Stock> Stocks)
    {
        super(context, resource, Stocks);
        this.Stocks = Stocks;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = inflater.inflate(this.layout, parent, false);
 
        ImageView imgPicture = (ImageView)view.findViewById(R.id.imgPicture);
        TextView txtTicker = (TextView)view.findViewById(R.id.txtTicker);
        TextView txtName = (TextView)view.findViewById(R.id.txtName);
        TextView txtPrice = (TextView) view.findViewById(R.id.txtPrice);
        TextView txtDelta = (TextView) view.findViewById(R.id.txtDelta);
 
        Stock stock = Stocks.get(position);

        imgPicture.setImageResource(stock.getPictureResource());
        txtTicker.setText(stock.getTicker());
        txtName.setText(stock.getName());
        txtPrice.setText(Float.toString(stock.getPrice()));
        txtDelta.setText(Float.toString(stock.getDelta()));
 
        return view;
    }
}
