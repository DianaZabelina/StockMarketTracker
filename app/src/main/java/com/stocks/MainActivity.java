package com.stocks;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private ListView lstStocks;
    private Button btnAdd;
    private Button btnFavourites;
    private Button btnFind;
    private EditText edtFind;

    private ArrayList<Stock> Stocks = new ArrayList();
    public static ArrayList<Stock> FavouritesStocks = new ArrayList();

    private StockAdapter stockAdapter;

    private int[] StockIndexes = new int[0];  // indexes for favourites
    private int selected;                     // selected index

    private Timer timer;
    private TimerTask mTimerTask;

    StockIO stockIO = new StockIO(this, "favourites.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInitialData();

        stockIO.ReadStockIndexes(StockIndexes);
        for (int index : StockIndexes)
        {
            Stock stock = Stocks.get(index);
            FavouritesStocks.add(stock);
        }

        lstStocks = (ListView)findViewById(R.id.lstStocks);
        stockAdapter = new StockAdapter(this, R.layout.list_item, Stocks);
        lstStocks.setAdapter(stockAdapter);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnFavourites = (Button)findViewById(R.id.btnFavourites);
        btnFind = (Button)findViewById(R.id.btnFind);
        btnAdd.setOnClickListener(this);
        btnFavourites.setOnClickListener(this);
        btnFind.setOnClickListener(this);
        edtFind = (EditText)findViewById(R.id.edtFind);

        lstStocks.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                selected = position;
            }
        });

        lstStocks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
            {
                selected = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        timer = new Timer();
        mTimerTask = new MyTimerTask();
        timer.schedule(mTimerTask, 0, 60000);
    }

    @Override
    protected void onDestroy()
    {
        stockIO.SaveStockIndexes(StockIndexes);
        super.onDestroy();
    }

    private int FindItem(String search)
    {
        int i = 0;
        for (Stock stock : Stocks)
        {
            //if (stock.getTicker().equals(search) || stock.getName().equals(search))
            if (stock.getTicker().equalsIgnoreCase(search) || stock.getName().equalsIgnoreCase(search))
                return i;
            i++;
        }

        return -1;
    }



    @Override
    public void onClick(View v)
    {
      if (v.getId() == R.id.btnAdd)
      {
        //int selected = lstStocks.getSelectedItemPosition(); // !!! doesn't work if focus is lost !!!
        Stock stock = Stocks.get(selected);
        FavouritesStocks.add(stock);
        Toast.makeText(this, Integer.toString(selected), Toast.LENGTH_SHORT).show();
      }
      else if (v.getId() == R.id.btnFavourites)
      {
        Intent intent = new Intent(this, FavouritesActivity.class);
        startActivity(intent);
      }
      else if (v.getId() == R.id.btnFind)
      {
          int index = FindItem(edtFind.getText().toString());
          lstStocks.requestFocusFromTouch(); // !!! needs for setSelection
          lstStocks.setSelection(index);
          Toast.makeText(this, Integer.toString(index), Toast.LENGTH_SHORT).show();
      }
    }

    public static String Ticker(int i)
    {
        switch (i)
        {
            case 0:
                return "BTC-USD";  // Bitcoin
            case 1:
                return "ADBE";     // Adobe Systems Inc.
            case 2:
                return "AAPL";     // Apple Inc.
            case 3:
                return "AMZN";     // Amazon.com Inc.
            case 4:
                return "BA";      // Boeing Co.
            case 5:
                return "AMD";     // Advanced Micro Devices Inc.
            case 6:
                return "T";       // AT&T, Inc.
            case 7:
                return "MSFT";       // Microsoft Corporation
            case 8:
                return "MCHP";       // Microchip Technology Inc.
            case 9:
                return "MOT";       // Motorola Inc.
            case 10:
                return "ADI";       // Analog Devices Inc.
            case 11:
                return "FB";       // Facebook Inc
            case 12:
                return "INTC";       // Intel Corporation
            case 13:
                return "HPQ";       // Hewlett-Packard Company
            case 14:
                return "JAVA";       // Sun Microsystems Inc.
            case 15:
                return "NVDA";       // NVIDIA Corporation
            case 16:
                return "ORCL";       // Oracle Corp.
            case 17:
                return "TXN";       // Texas Instruments Inc.
            case 18:
                return "GM";       //  General Motors Co
            case 19:
                return "KO";       //  The Coca-Cola Company
            case 20:
                return "NSANY";       //  Nissan Motor Co Ltd




        }
        return "";
    }

    private void setInitialData()
    {

        Collections.addAll(Stocks,
        new Stock(R.drawable.bitcoin, Ticker(0), "Bitcoin", 0),
        new Stock(R.drawable.adobe, Ticker(1), "Adobe Systems Inc.", 0),
        new Stock(R.drawable.apple, Ticker(2), " Apple Inc.", 0),
        new Stock(R.drawable.amazon, Ticker(3), "Amazon.com Inc.", 0),
        new Stock(R.drawable.boeing, Ticker(4), "Boeing Co.", 0),
        new Stock(R.drawable.amd, Ticker(5), "Advanced Micro Devices Inc.", 0),
        new Stock(R.drawable.att, Ticker(6), "AT&T, Inc.", 0),
        new Stock(R.drawable.microsoft, Ticker(7), "Microsoft Corporation", 0),
        new Stock(R.drawable.microchip, Ticker(8), "Microchip Technology Inc.", 0),
        new Stock(R.drawable.motorola, Ticker(9), "Motorola Inc.", 0),
        new Stock(R.drawable.analogdevices, Ticker(10), "Analog Devices Inc.", 0),
        new Stock(R.drawable.facebook, Ticker(11), "Facebook Inc", 0),
        new Stock(R.drawable.intel, Ticker(12), "Intel Corporation", 0),
        new Stock(R.drawable.hewlettpackard, Ticker(13), "Hewlett-Packard Company", 0),
        new Stock(R.drawable.sun, Ticker(14), "Sun Microsystems Inc.", 0),
        new Stock(R.drawable.nvidia, Ticker(15), "NVIDIA Corporation", 0),
        new Stock(R.drawable.oracle, Ticker(16), "Oracle Corp.", 0),
        new Stock(R.drawable.texasinstruments, Ticker(17), "Texas Instruments Inc.", 0),
        new Stock(R.drawable.generalmotors, Ticker(18), "General Motors Co", 0),
        new Stock(R.drawable.cocacola, Ticker(19), "The Coca-Cola Company", 0),
        new Stock(R.drawable.nissan, Ticker(20), "Nissan Motor Co Ltd", 0)



        );
    }


    class MyTimerTask extends TimerTask
    {
        private String getContent(String path) throws IOException
        {
            HttpsURLConnection connection = null;
            InputStream stream = null;
            BufferedReader reader = null;
            try
            {
                URL url = new URL(path);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);
                connection.connect();
                stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buf = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    buf.append(line).append("\n");
                return (buf.toString());
            }
            finally
            {
                if (reader != null)
                    reader.close();
                if (stream != null)
                    stream.close();
                if (connection != null)
                    connection.disconnect();
            }
        }

        // Parse string s (responce from server) to price & delta price
        private Stock ParseResponce(String s)
        {
          Stock stc = new Stock();

            String subs[];
            String delim = ",";
            subs = s.split(delim);
            String pс = subs[0].substring(5, subs[0].length()); // current
            String ph = subs[1].substring(4, subs[1].length()); // high
            String pl = subs[2].substring(4, subs[2].length()); // low
            stc.setPrice(Float.parseFloat(pс));
            stc.setDelta(Float.parseFloat(ph) - Float.parseFloat(pl));

          return stc;
        }


        private void UpdateStocks()
        {
          try
          {
              int i = 0;
              for (Stock stock : Stocks)
              {
                  String s = getContent("https://finnhub.io/api/v1/quote?symbol=" + Ticker(i++) +
                          "&token=c11as2v48v6tj8r9dtog");
                  Stock prs = ParseResponce(s);
                  stock.setPrice(prs.getPrice());
                  stock.setDelta(prs.getDelta());
              }
          }
          catch (IOException e)
          {
          }
        }


        @Override
        public void run()
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                        UpdateStocks();
                        lstStocks.post(new Runnable()
                        {
                            public void run()
                            {
                                stockAdapter.notifyDataSetChanged();
                            }
                        });
                }
            }).start();
        }
    }
}
