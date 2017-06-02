using System;
using System.Collections.Generic;
using System.Text;
using Sve2.FhQuotes.Dao.Interfaces;
using Sve2.FhQuotes.Domain;
using System.Diagnostics;

namespace Sve2.FhQuotes.Dao.Simple
{
    internal class Sync
    {
        public static object Root = new object();
    }


    public class StockDao : IStockDao, IDisposable
    {

        public void Dispose()
        {
            // Console.WriteLine("StockDao.Dispose()");
        }

        private static IDictionary<string, Stock> stocks;

        private static void InitStocks()
        {
            stocks.Add("MSFT", new Stock("MSFT", "Microsoft Corporation", "USD"));
            stocks.Add("IBM", new Stock("IBM", "International Business Machines Corp.", "USD"));
            stocks.Add("FB", new Stock("FB", "Facebook, Inc.", "USD"));
            stocks.Add("ORCL", new Stock("ORCL", "Oracle Corporation", "USD"));
            stocks.Add("VOE.VI", new Stock("VOE.VI", "VOESTALPINE AG", "EUR"));
            stocks.Add("VOW3.F", new Stock("VOW3.F", "Volkswagen AG", "EUR"));
            stocks.Add("BAS.DE", new Stock("BAS.DE", "BASF SE", "EUR"));
        }

        static StockDao()
        {
            stocks = new SortedDictionary<string, Stock>();
            InitStocks();
        }

        public Stock FindBySymbol(string stockSymbol)
        {
            lock (Sync.Root)
            {
                Stock stock;
                if (stocks.TryGetValue(stockSymbol, out stock))
                    return stock;
                else
                    return null;
            }
        }

        public ICollection<Stock> FindAll()
        {
            lock (Sync.Root)
            {
                return new List<Stock>(stocks.Values);
            }
        }

        public ICollection<string> FindAllSymbols()
        {
            lock (Sync.Root)
            {
                List<string> allSymbols = new List<string>();
                foreach (Stock stock in this.FindAll())
                    allSymbols.Add(stock.Symbol);

                return allSymbols;
            }
        }

        public bool Insert(Stock stock)
        {
            lock (Sync.Root)
            {
                if (!stocks.ContainsKey(stock.Symbol))
                {
                    stocks.Add(stock.Symbol, stock);
                    return true;
                }
                else
                    return false;
            }
        }
    }

    public class QuoteDao : IQuoteDao
    {
        private static IDictionary<string, SortedSet<Quote>> quotes;
        private static ByTimeComparer quotesComparer = new ByTimeComparer();

        private StockDao stockDao;

        private class ByTimeComparer : IComparer<Quote>
        {

            public int Compare(Quote q1, Quote q2)
            {
                return q1.Time.CompareTo(q2.Time);
            }
        }

        private IStockDao StockDao
        {
            get
            {
                if (stockDao == null)
                    stockDao = new StockDao();
                return stockDao;
            }
        }

        private static void InitQuotes()
        {
            StockDao stockDao = new StockDao();
            Stock stock = null;

            stock = stockDao.FindBySymbol("MSFT");
            AddQuote(stock, 66.5M, 66.5M, 66.5M, new DateTime(2017, 4, 29));
            AddQuote(stock, 69.16M, 69.16M, 69.16M, new DateTime(2017, 4, 30));
            AddQuote(stock, 71.93M, 71.93M, 71.93M, new DateTime(2017, 5, 1));
            AddQuote(stock, 69.77M, 69.77M, 69.77M, new DateTime(2017, 5, 2));
            AddQuote(stock, 67.68M, 67.68M, 67.68M, new DateTime(2017, 5, 3));
            AddQuote(stock, 65.65M, 65.65M, 65.65M, new DateTime(2017, 5, 4));

            stock = stockDao.FindBySymbol("IBM");
            AddQuote(stock, 158M, 158M, 158M, new DateTime(2017, 5, 1));
            AddQuote(stock, 164.32M, 164.32M, 164.32M, new DateTime(2017, 5, 2));
            AddQuote(stock, 159.39M, 159.39M, 159.39M, new DateTime(2017, 5, 3));
            AddQuote(stock, 154.61M, 154.61M, 154.61M, new DateTime(2017, 5, 4));

            stock = stockDao.FindBySymbol("FB");
            AddQuote(stock, 150M, 150M, 150M, new DateTime(2017, 5, 2));
            AddQuote(stock, 145.5M, 145.5M, 145.5M, new DateTime(2017, 5, 3));
            AddQuote(stock, 152.78M, 152.78M, 152.78M, new DateTime(2017, 5, 4));

            stock = stockDao.FindBySymbol("ORCL");
            AddQuote(stock, 45.5M, 45.5M, 45.5M, new DateTime(2017, 5, 3));
            AddQuote(stock, 44.14M, 44.14M, 44.14M, new DateTime(2017, 5, 4));

            stock = stockDao.FindBySymbol("VOE.VI");
            AddQuote(stock, 38.1M, 38.1M, 38.1M, new DateTime(2017, 5, 3));
            AddQuote(stock, 36.96M, 36.96M, 36.96M, new DateTime(2017, 5, 4));

            stock = stockDao.FindBySymbol("VOW3.F");
            AddQuote(stock, 143.9M, 143.9M, 143.9M, new DateTime(2017, 5, 4));

            stock = stockDao.FindBySymbol("BAS.DE");
            AddQuote(stock, 90M, 90M, 90M, new DateTime(2017, 5, 2));
            AddQuote(stock, 87.3M, 87.3M, 87.3M, new DateTime(2017, 5, 3));
            AddQuote(stock, 84.68M, 84.68M, 84.68M, new DateTime(2017, 5, 4));
        }

        private static void AddQuote(Stock stock, decimal price, decimal ask, decimal bid, DateTime time)
        {
            Debug.Assert(stock != null);

            lock (Sync.Root)
            {
                SortedSet<Quote> quoteSet;
                if (!quotes.TryGetValue(stock.Symbol, out quoteSet))
                    quotes.Add(stock.Symbol, quoteSet = new SortedSet<Quote>(quotesComparer));
                quoteSet.Add(new Quote(stock, price, ask, bid, time));
            }
        }

        static QuoteDao()
        {
            quotes = new SortedDictionary<string, SortedSet<Quote>>();
            InitQuotes();
        }

        public QuoteDao()
        {
        }

        public Quote FindLastQuote(string stockSymbol)
        {
            if (StockDao.FindBySymbol(stockSymbol) == null)
                return null;

            lock (Sync.Root)
            {
                SortedSet<Quote> quoteSet;
                if (quotes.TryGetValue(stockSymbol, out quoteSet))
                    return quoteSet.Max;
                else
                    return null;
            }
        }

        public ICollection<Quote> FindQuotesInRange(string stockSymbol, DateTime from, DateTime to)
        {
            if (StockDao.FindBySymbol(stockSymbol) == null)
                return null;

            lock (Sync.Root)
            {
                List<Quote> quotesInRange = new List<Quote>();

                SortedSet<Quote> quoteSet;
                if (quotes.TryGetValue(stockSymbol, out quoteSet))
                {
                    foreach (Quote quote in quoteSet)
                        if (from <= quote.Time && quote.Time <= to)
                            quotesInRange.Add(quote);

                    return quotesInRange;
                }
                else
                    return null;
            }
        }

        public bool Insert(string stockSymbol, Quote quote)
        {
            Stock stock = StockDao.FindBySymbol(stockSymbol);
            if (stock == null)
                return false;

            lock (Sync.Root)
            {
                SortedSet<Quote> quoteSet;
                if (!quotes.TryGetValue(stockSymbol, out quoteSet))
                    quotes.Add(stockSymbol, quoteSet = new SortedSet<Quote>(quotesComparer));

                quote.Stock = stock;
                quoteSet.Add(quote);

                return true;
            }
        }
    }
}
