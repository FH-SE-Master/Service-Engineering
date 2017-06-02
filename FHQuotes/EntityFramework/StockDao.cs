using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Sve2.FhQuotes.Dao.Interfaces;
using Sve2.FhQuotes.Domain;

namespace Sve2.FhQuotes.Dao.EntityFramework
{
    public class StockDao : IStockDao
    {
        static StockDao()
        {
            Console.WriteLine($"Assembly: {typeof(StockDao).Assembly.FullName}");
        }

        public ICollection<Domain.Stock> FindAll()
        {
            using (FhQuotesContext ctx = new FhQuotesContext())
            {
                // First materialize then convert to doamin model !!!!
                return ctx.Stocks.AsEnumerable().Select(stock => stock.ToDomain()).ToList();
            }
        }

        public ICollection<string> FindAllSymbols()
        {
            using (FhQuotesContext ctx = new FhQuotesContext())
            {
                return ctx.Stocks.Select(stock => stock.Symbol).ToList();
            }
        }

        public Domain.Stock FindBySymbol(string stockSymbol)
        {
            using (FhQuotesContext ctx = new FhQuotesContext())
            {
                return ctx.Stocks.Find(stockSymbol).ToDomain();
            }
        }

        public bool Insert(Domain.Stock stock)
        {
            using (FhQuotesContext ctx = new FhQuotesContext())
            {
                if (ctx.Stocks.Find(stock.Symbol) == null)
                {
                    ctx.Stocks.Add(stock.ToEF());
                    ctx.SaveChanges();
                    return true;
                }

                return false;
            }
        }
    }
}
