using System;
using System.Collections.Generic;
using Sve2.FhQuotes.Dao.Interfaces;
using System.Linq;
using Microsoft.EntityFrameworkCore;
using System.Threading.Tasks;

namespace Sve2.FhQuotes.Dao.EntityFramework
{
    public class StockDao : IStockDao
    {
        private readonly FhQuotesContext context;

        public StockDao(FhQuotesContext context)
        {
            this.context = context;
        }

        public async Task<ICollection<Domain.Stock>> FindAll()
        {
            return await context.Stocks.ToAsyncEnumerable().Select(stock => stock.ToDomain()).ToList();
            // return context.Stocks.AsEnumerable().Select(stock => stock.ToDomain()).ToList();
        }

        public async Task<ICollection<string>> FindAllSymbols()
        {
            return await context.Stocks.Select(s => s.Symbol).ToListAsync();
        }

        public async Task<Domain.Stock> FindBySymbol(string stockSymbol)
        {
            return (await context.Stocks.FindAsync(stockSymbol)).ToDomain();
            //return Task.FromResult(context.Stocks.Find(stockSymbol).ToDomain());
        }

        public async Task<bool> Insert(Domain.Stock stock)
        {
            if (await context.Stocks.FindAsync(stock.Symbol) != null)
                return false;

            await context.Stocks.AddAsync(stock.ToEF());
            await context.SaveChangesAsync();
            return true;
        }
    }

    public class QuoteDao : IQuoteDao
    {
        private readonly FhQuotesContext context;

        public QuoteDao(FhQuotesContext context)
        {
            this.context = context;
        }

        public async Task<Domain.Quote> FindLastQuote(string stockSymbol)
        {
            var stock = await context.Stocks.FindAsync(stockSymbol);
            if (stock == null)
                return null;

            var query = (from q in context.Quotes
                where q.Stock.Symbol == stockSymbol
                orderby q.Time descending
                select q).Take(1);

            return (await query.FirstOrDefaultAsync()).ToDomain();
        }

        public async Task<ICollection<Domain.Quote>> FindQuotesInRange(string stockSymbol, DateTime fromTime,
            DateTime toTime)
        {
            var stock = await context.Stocks.FindAsync(stockSymbol);
            if (stock == null)
                return null;

            var query = from q in context.Quotes
                where q.Stock.Symbol == stockSymbol &&
                      fromTime <= q.Time && q.Time <= toTime
                orderby q.Time ascending
                select q;

            return await query.ToAsyncEnumerable().Select(q => q.ToDomain()).ToList();
        }

        public async Task<bool> Insert(string stockSymbol, Domain.Quote quote)
        {
            var stock = await context.Stocks.FindAsync(stockSymbol);
            if (stock == null)
                return false;

            Quote quoteEF = quote.ToEF();
            quoteEF.Stock = stock;

            await context.Quotes.AddAsync(quoteEF);
            await context.SaveChangesAsync();

            return true;
        }
    }
}