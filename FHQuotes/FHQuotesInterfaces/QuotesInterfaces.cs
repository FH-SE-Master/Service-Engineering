using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using Sve2.FhQuotes.Domain;
using Sve2.FhQuotes.Faults;

namespace Sve2.FHQuotes.Interfaces
{
    [ServiceContract(Namespace = "http://Sve2.FHQuotes.Interfaces")]
    public interface IQuotes
    {
        [OperationContract]
        [FaultContract(typeof(StockNotFoundFault))]
        decimal FIndCurrentPrice(string stokSymbol);

        [OperationContract]
        IEnumerable<string> FindAllStockSymbols();

        [OperationContract]
        [FaultContract(typeof(StockNotFoundFault))]
        Stock FindStock(string stokSymbol);

        [OperationContract]
        [FaultContract(typeof(StockNotFoundFault))]
        Quote FindCurrentQuote(string stocksymbol);

        [OperationContract]
        [FaultContract(typeof(NoQuotesException))]
        IEnumerable<Quote> FIndQuoteInTImeRange(string stockSymbol, DateTime from, DateTime to);
    }
}
