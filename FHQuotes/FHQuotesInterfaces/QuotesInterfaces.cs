using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using Sve2.FhQuotes.Domain;

namespace Sve2.FHQuotes.Interfaces
{
    [ServiceContract(Namespace = "http://Sve2.FHQuotes.Interfaces")]
    public interface IQuotes
    {
        [OperationContract]
        decimal FIndCurrentPrice(string stokSymbol);

        [OperationContract]
        IEnumerable<string> FindAllStockSymbols();

        [OperationContract]
        Stock FindStock(string stokSymbol);

        [OperationContract]
        Quote FindCurrentQuote(string stocksymbol);

        [OperationContract]
        IEnumerable<Quote> FIndQuoteInTImeRange(string stockSymbol, DateTime from, DateTime to);
    }
}
