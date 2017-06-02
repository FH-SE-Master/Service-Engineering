using Sve2.FHQuotes.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using Sve2.FhQuotes.Dao;
using Sve2.FhQuotes.Dao.Interfaces;
using Sve2.FhQuotes.Domain;

namespace Sve2.FHQuotes.Services
{
    // Without PerCall it would be a Satefull service
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.PerCall)]
    public class FHQuotesService : IQuotes
    {

        private IStockDao StockDao { get; set; }
        private IQuoteDao QuoteDao { get; set; }

        public FHQuotesService(IStockDao stockDao, IQuoteDao quoteDao)
        {
            this.StockDao = StockDao;
            this.QuoteDao = QuoteDao;
        }

        public decimal FIndCurrentPrice(string stokSymbol)
        {
            EnsureStockexists(stokSymbol);
            Quote quote = QuoteDao.FindLastQuote(stokSymbol);
            if (quote == null)
            {
                throw new FaultException($"No quote exists for stock with symbol '{stokSymbol}'");
            }
            return quote.Price;
        }

        public IEnumerable<string> FindAllStockSymbols()
        {
            return StockDao.FindAllSymbols();
        }

        public Stock FindStock(string stokSymbol)
        {
            return EnsureStockexists(stokSymbol);
        }

        public Quote FindCurrentQuote(string stockSymbol)
        {
            EnsureStockexists(stockSymbol);
            Quote quote = QuoteDao.FindLastQuote(stockSymbol);
            if (quote == null)
            {
                throw new FaultException($"No quotes for stock with symbol '{stockSymbol}'");
            }
            return quote;
        }

        public IEnumerable<Quote> FIndQuoteInTImeRange(string stockSymbol, DateTime from, DateTime to)
        {
            EnsureStockexists(stockSymbol);
            return QuoteDao.FindQuotesInRange(stockSymbol, from, to);
        }

        private Stock EnsureStockexists(string stockSymbol)
        {
            Stock stock = StockDao.FindBySymbol(stockSymbol);
            if (stock == null)
            {
                throw new FaultException($"Stock does not exist with smybol '{stockSymbol}'");
            }
            return stock;
        }
    }
}
