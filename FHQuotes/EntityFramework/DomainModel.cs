using System;
using System.Collections.Generic;

namespace Sve2.FhQuotes.Dao.EntityFramework
{
    internal class Stock
    {
        public string Symbol { get; set; }
        public string Name { get; set; }
        public string Currency { get; set; }

        public virtual ICollection<Quote> Quotes { get; set; } = new List<Quote>();
    }

    internal class Quote
    {
        public int Id { get; set; }
        public decimal Ask { get; set; }
        public decimal Bid { get; set; }
        public decimal Price { get; set; }
        public DateTime Time { get; set; }

        public virtual Stock Stock { get; set; }
    }
}