package com.stocks;
 
public class Stock
{
    private int Picture;                         // picture resource
    private String Ticker;                       // ticker
    private String Name;                         // name
    private float Price;                         // current price
    private float Delta;                         // delta price

    public Stock()
    {
        Picture = 0;
        Ticker = "";
        Name = "";
        Price = 0;
        Delta = 0;
    }

    public Stock(int Picture, String Ticker, String Name, float Price)
    {
        this.Picture = Picture;
        this.Ticker = Ticker;
        this.Name = Name;
        this.Price = Price;
    }

    public int getPictureResource()
    {
        return Picture;
    }

    public void setPictureResource(int Picture)
    {
        this.Picture = Picture;
    }

    public String getTicker()
    {
        return Ticker;
    }

    public void setTicker(String Ticker)
    {
        this.Ticker = Ticker;
    }
 
    public String getName()
    {
        return Name;
    }
 
    public void setName(String Name)
    {
        this.Name = Name;
    }
 
    public float getPrice()
    {
        return Price;
    }
 
    public void setPrice(float Price)
    {
        this.Price = Price;
    }

    public float getDelta()
    {
        return Delta;
    }

    public void setDelta(float Delta)
    {
        this.Delta = Delta;
    }
}
