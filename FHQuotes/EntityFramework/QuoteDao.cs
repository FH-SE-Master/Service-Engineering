using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Sve2.FhQuotes.Dao.Interfaces;
using Sve2.FhQuotes.Domain;

namespace Sve2.FhQuotes.Dao.EntityFramework
{
    public class QuoteDao : IQuoteDao
    {
        public Domain.Quote FindLastQuote(string stockSymbol)
        {
            using (FhQuotesContext ctx = new FhQuotesContext())
            {
                return ctx.Quotes.Where(quote => quote.Stock.Symbol.Equals(stockSymbol))
                    .OrderByDescending(q => q.Time)
                    .FirstOrDefault().ToDomain();
            }
        }

        public ICollection<Domain.Quote> FindQuotesInRange(string stockSymbol, DateTime from, DateTime to)
        {
            using (FhQuotesContext ctx = new FhQuotesContext())
            {
                return ctx.Quotes.Where(q => q.Stock.Symbol.Equals(stockSymbol))
                    .Where(q => q.Time.CompareTo(from) >= 0)
                    .Where(q => q.Time.CompareTo(to) <= 0)
                    .OrderByDescending(q => q.Time)
                    .AsEnumerable().Select(q => q.ToDomain())
                    .ToList();
            }
        }

        public bool Insert(string stockSymbol, Domain.Quote quote)
        {
            using (FhQuotesContext ctx = new FhQuotesContext())
            {
                Stock stock = ctx.Stocks.Find(stockSymbol);
                if (stock == null)
                {
                    return false;
                }

                var quoteEF = quote.ToEF();
                quoteEF.Stock = stock;
                ctx.Quotes.Add(quoteEF);
                ctx.SaveChanges();

                return true;
            }
        }
    }
}