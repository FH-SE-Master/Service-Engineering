using System;
using System.Collections.Generic;
using System.Text;
using Sve2.FhQuotes.Domain;
using Sve2.FhQuotes.Dao.Interfaces;

namespace Sve2.FhQuotes.Dao
{
    public class DaoFactory
    {
        public static IStockDao StockDao
        {
            get { return new Sve2.FhQuotes.Dao.Simple.StockDao(); }
        }

        public static IQuoteDao QuoteDao
        {
            get { return new Sve2.FhQuotes.Dao.Simple.QuoteDao(); }
        }
    }
}
