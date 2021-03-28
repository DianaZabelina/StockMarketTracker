package com.stocks;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import android.widget.Toast;

public class StockIO
{
  private Context context;
  private String name;

  StockIO(Context context, String name)
  {
    this.context = context;
    this.name = name;
  }

  public static final byte[] intToByteArray(int value)
  {
    return new byte[] {
            (byte)(value >> 24),
            (byte)(value >> 16),
            (byte)(value >> 8),
            (byte)value};
  }

  public static final int fromByteArray(byte[] bytes)
  {
    return ((bytes[0] & 0xFF) << 24) |
            ((bytes[1] & 0xFF) << 16) |
            ((bytes[2] & 0xFF) << 8) |
            ((bytes[3] & 0xFF) << 0);
  }

   // Save to file
  public void SaveStockIndexes(int[] StockIndexes)
  {
     FileOutputStream fos = null;
     try
     {
       fos = context.openFileOutput(name, context.MODE_PRIVATE);
       int i = 0;
       for (int index : StockIndexes)
         fos.write(intToByteArray(StockIndexes[i++]));
     }
     catch(IOException ex)
     {
       Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
     }
     finally
     {
       try
       {
         if (fos != null)
           fos.close();
       }
       catch(IOException ex)
       {
         Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
       }
     }
   }

   // Read
   public void ReadStockIndexes(int[] StockIndexes)
   {
     FileInputStream fin = null;
     try
     {
       fin = context.openFileInput(name);
       byte[] bytes = new byte[fin.available()];
       fin.read(bytes);

       Toast.makeText(context, new String(bytes), Toast.LENGTH_SHORT).show();
       int i1 = 0;
       byte index[] = new byte[4];
       for (int i=0; i<bytes.length; i+=4)
       {
         System.arraycopy(bytes, i, index, 0, 4);
         StockIndexes = Arrays.copyOf(StockIndexes, i1 + 1);
         StockIndexes[i1++] = fromByteArray(index);
       }
     }
     catch(IOException ex)
     {
       Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
     }
     finally
     {
       try
       {
         if (fin != null)
           fin.close();
       }
       catch(IOException ex)
       {
         Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
       }
     }
   }
}
