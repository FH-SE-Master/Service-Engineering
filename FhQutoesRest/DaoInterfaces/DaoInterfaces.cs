using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Sve2.FhQuotes.Domain;

[assembly: CLSCompliant(true)]

namespace Sve2.FhQuotes.Dao.Interfaces {

  public interface IStockDao {
    Task<Stock> FindBySymbol(string stockSymbol);
    Task<ICollection<string>> FindAllSymbols();
    Task<ICollection<Domain.Stock>> FindAll();
    Task<bool> Insert(Stock stock);
  }

  public interface IQuoteDao {
    Task<Quote> FindLastQuote(string stockSymbol);
    Task<ICollection<Quote>> FindQuotesInRange(string stockSymbol, DateTime from, DateTime to);
    Task<bool> Insert(string stockSymbol, Quote quote);
  }
}
