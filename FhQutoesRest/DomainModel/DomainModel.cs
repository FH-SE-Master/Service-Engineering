using System;
using System.Collections.Generic;
using System.Runtime.Serialization;
using System.Text;

[assembly: CLSCompliant(true)]

namespace Sve2.FhQuotes.Domain {

  [DataContract]
  public class Stock {

    public Stock() {
    }

    public Stock(string symbol, string name, string currency) {
      this.Symbol = symbol;
      this.Name = name;
      this.Currency = currency;
    }

    [DataMember]
    public string Symbol { get; set; }

    [DataMember]
    public string Name { get; set; }

    [DataMember]
    public string Currency { get; set; }
  }

  [DataContract]
  public class Quote {

    public Quote() {
    }

    public Quote(Stock stock, decimal price, decimal ask, decimal bid, DateTime time) {
      this.Stock = stock;
      this.Price = price;
      this.Ask = ask;
      this.Bid = bid;
      this.Time = time;
    }

    [DataMember]
    public Stock Stock { get; set; }

    [DataMember]
    public decimal? Price { get; set; }

    [DataMember]
    public decimal? Ask { get; set; }

    [DataMember]
    public decimal? Bid { get; set; }

    [DataMember]
    public DateTime? Time { get; set; }
  }
}
