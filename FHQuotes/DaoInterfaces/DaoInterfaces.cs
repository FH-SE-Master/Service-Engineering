using System;
using System.Collections.Generic;
using Sve2.FhQuotes.Domain;

[assembly: CLSCompliant(true)]

namespace Sve2.FhQuotes.Dao.Interfaces
{

    public interface IStockDao
    {
        Stock FindBySymbol(string stockSymbol);
        ICollection<string> FindAllSymbols();
        ICollection<Stock> FindAll();
        bool Insert(Stock stock);
    }

    public interface IQuoteDao
    {
        Quote FindLastQuote(string stockSymbol);
        ICollection<Quote> FindQuotesInRange(string stockSymbol, DateTime from, DateTime to);
        bool Insert(string stockSymbol, Quote quote);
    }
}
