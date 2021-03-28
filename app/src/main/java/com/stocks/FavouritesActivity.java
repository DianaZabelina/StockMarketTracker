package com.stocks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class FavouritesActivity extends AppCompatActivity implements View.OnClickListener
{
    private ListView lstStocks;
    private Button btnDelete;
    private Button btnMain;

    StockAdapter stockAdapter;

    private int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        lstStocks = (ListView) findViewById(R.id.lstStocks);
        stockAdapter = new StockAdapter(this, R.layout.list_item, MainActivity.FavouritesStocks);
        lstStocks.setAdapter(stockAdapter);

        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnMain = (Button)findViewById(R.id.btnMain);
        btnDelete.setOnClickListener(this);
        btnMain.setOnClickListener(this);

        lstStocks.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                selected = position;
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnDelete)
        {
            Stock stock = MainActivity.FavouritesStocks.get(selected);
            stockAdapter.remove(stock);
        }
        if (v.getId() == R.id.btnMain)
        {
            finish();
        }
    }
}