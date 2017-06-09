using System;
using System.Collections.Generic;
using System.Text;
using Sve2.FhQuotes.Dao.EntityFramework;
using Sve2.FhQuotes.Dao.Interfaces;

namespace Sve2.FhQuotes.Dao.EntityFramework {

  public class DbInitializer {

    private FhQuotesContext Context { get; set; }

    public DbInitializer(FhQuotesContext context) {
      this.Context = context;
    }

    public void Initialize() {

      Context.Database.EnsureDeleted();
      Context.Database.EnsureCreated();
 
      Context.Stocks.Add(new Stock { Symbol = "MSFT", Name = "Microsoft Corporation", Currency = "USD" });
      Context.Stocks.Add(new Stock { Symbol = "IBM", Name = "International Business Machines Corp.", Currency = "USD" });
      Context.Stocks.Add(new Stock { Symbol = "FB", Name = "Facebook, Inc.", Currency = "USD" });
      Context.Stocks.Add(new Stock { Symbol = "ORCL", Name = "Oracle Corporation", Currency = "USD" });
      Context.Stocks.Add(new Stock { Symbol = "VOE.VI", Name = "VOESTALPINE AG", Currency = "EUR" });
      Context.Stocks.Add(new Stock { Symbol = "VOW3.F", Name = "Volkswagen AG", Currency = "EUR" });
      Context.Stocks.Add(new Stock { Symbol = "BAS.DE", Name = "BASF SE", Currency = "EUR" });

      Context.Quotes.Add(new Quote { StockSymbol = "MSFT", Price = 66.5M, Ask = 66.5M, Bid = 66.5M, Time = new DateTime(2017, 4, 29) });
      Context.Quotes.Add(new Quote { StockSymbol = "MSFT", Price = 69.16M, Ask = 69.16M, Bid = 69.16M, Time = new DateTime(2017, 4, 30) });
      Context.Quotes.Add(new Quote { StockSymbol = "MSFT", Price = 71.93M, Ask = 71.93M, Bid = 71.93M, Time = new DateTime(2017, 5, 1) });
      Context.Quotes.Add(new Quote { StockSymbol = "MSFT", Price = 69.77M, Ask = 69.77M, Bid = 69.77M, Time = new DateTime(2017, 5, 2) });
      Context.Quotes.Add(new Quote { StockSymbol = "MSFT", Price = 67.68M, Ask = 67.68M, Bid = 67.68M, Time = new DateTime(2017, 5, 3) });
      Context.Quotes.Add(new Quote { StockSymbol = "MSFT", Price = 65.65M, Ask = 65.65M, Bid = 65.65M, Time = new DateTime(2017, 5, 4) });
      Context.Quotes.Add(new Quote { StockSymbol = "IBM", Price = 158M, Ask = 158M, Bid = 158M, Time = new DateTime(2017, 5, 1) });
      Context.Quotes.Add(new Quote { StockSymbol = "IBM", Price = 164.32M, Ask = 164.32M, Bid = 164.32M, Time = new DateTime(2017, 5, 2) });
      Context.Quotes.Add(new Quote { StockSymbol = "IBM", Price = 159.39M, Ask = 159.39M, Bid = 159.39M, Time = new DateTime(2017, 5, 3) });
      Context.Quotes.Add(new Quote { StockSymbol = "IBM", Price = 154.61M, Ask = 154.61M, Bid = 154.61M, Time = new DateTime(2017, 5, 4) });
      Context.Quotes.Add(new Quote { StockSymbol = "FB", Price = 150M, Ask = 150M, Bid = 150M, Time = new DateTime(2017, 5, 2) });
      Context.Quotes.Add(new Quote { StockSymbol = "FB", Price = 145.5M, Ask = 145.5M, Bid = 145.5M, Time = new DateTime(2017, 5, 3) });
      Context.Quotes.Add(new Quote { StockSymbol = "FB", Price = 152.78M, Ask = 152.78M, Bid = 152.78M, Time = new DateTime(2017, 5, 4) });
      Context.Quotes.Add(new Quote { StockSymbol = "ORCL", Price = 45.5M, Ask = 45.5M, Bid = 45.5M, Time = new DateTime(2017, 5, 3) });
      Context.Quotes.Add(new Quote { StockSymbol = "ORCL", Price = 44.14M, Ask = 44.14M, Bid = 44.14M, Time = new DateTime(2017, 5, 4) });
      Context.Quotes.Add(new Quote { StockSymbol = "VOE.VI", Price = 38.1M, Ask = 38.1M, Bid = 38.1M, Time = new DateTime(2017, 5, 3) });
      Context.Quotes.Add(new Quote { StockSymbol = "VOE.VI", Price = 36.96M, Ask = 36.96M, Bid = 36.96M, Time = new DateTime(2017, 5, 4) });
      Context.Quotes.Add(new Quote { StockSymbol = "VOW3.F", Price = 143.9M, Ask = 143.9M, Bid = 143.9M, Time = new DateTime(2017, 5, 4) });
      Context.Quotes.Add(new Quote { StockSymbol = "BAS.DE", Price = 90M, Ask = 90M, Bid = 90M, Time = new DateTime(2017, 5, 2) });
      Context.Quotes.Add(new Quote { StockSymbol = "BAS.DE", Price = 87.3M, Ask = 87.3M, Bid = 87.3M, Time = new DateTime(2017, 5, 3) });
      Context.Quotes.Add(new Quote { StockSymbol = "BAS.DE", Price = 84.68M, Ask = 84.68M, Bid = 84.68M, Time = new DateTime(2017, 5, 4) });

      Context.SaveChanges();
    }
  }
}
