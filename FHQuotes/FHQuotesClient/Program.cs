using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using Sve2.FhQuotes.Domain;
using Sve2.FhQuotes.Faults;
using Sve2.FHQuotes.Interfaces;

namespace Sve2.FHQuotes.Client
{
    class Program
    {
        const string SECURE_HTTP_QUOTES_ENDPOINT = "FHQuotesService_SecureHttpEndpoint";
        private const string UNSECURE_HTTP_QUOTES_ENDPOINT = "FHQuotesService_UnsecureHttpEndpoint";
        private const string SECURE_TCP_QUOTES_ENDPOINT = "FHQuotesService_UnsecureTcpEndpoint";
        private const string UNSECURE_TCP_QUOTES_ENDPOINT = "FHQuotesService_UnsecureTcpEndpoint";
        private const string SECURE_PIPE_QUOTES_ENDPOINT = "FHQuotesService_SecurePipeEndpoint";
        private const string UNSECURE_PIPE_QUOTES_ENDPOINT = "FHQuotesService_UnsecurePipeEndpoint";

        private const int ITERAITONS = 100;

        static void Main(string[] args)
        {
            //TestErrorHandling();
            TestQuotes();
            /**DoTimings(SECURE_HTTP_QUOTES_ENDPOINT);
            DoTimings(UNSECURE_HTTP_QUOTES_ENDPOINT);
            DoTimings(SECURE_TCP_QUOTES_ENDPOINT);
            DoTimings(UNSECURE_TCP_QUOTES_ENDPOINT);
            DoTimings(SECURE_PIPE_QUOTES_ENDPOINT);
            DoTimings(UNSECURE_PIPE_QUOTES_ENDPOINT);
    */
            Console.ReadLine();
        }

        private static void DoTimings(string endpointName)
        {
            using (ChannelFactory<IQuotes> quotesFactory = new ChannelFactory<IQuotes>(endpointName))
            {
                IQuotes proxy = quotesFactory.CreateChannel();
                Stopwatch watch = new Stopwatch();

                // Warmup
                proxy.FindAllStockSymbols();

                watch.Start();
                for (int i = 0; i < ITERAITONS; i++)
                {
                    proxy.FindCurrentQuote("MSFT");
                }

                watch.Stop();

                double time = watch.Elapsed.TotalSeconds / ITERAITONS;
                int callsPerSecond = (int) (1 / time);
                Console.WriteLine($"Enpoint '{endpointName}': time/call={time} / calls/Second={callsPerSecond}");
            }
        }

        private static void TestQuotes()
        {
            WcfHelper.Execute<IQuotes>(UNSECURE_HTTP_QUOTES_ENDPOINT, (proxy) =>
            {
                foreach (var stock in proxy.FindAllStockSymbols())
                {
                    try
                    {
                        Quote quote = proxy.FindCurrentQuote(stock);
                        Console.WriteLine($"stock '{stock}': / pricce: '{quote.Price}' / time: {quote.Time}");
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine($"Excxeption: {e}");
                    }
                }
            });
        }

        private static void TestErrorHandling()
        {
            WcfHelper.Execute<IQuotes>(UNSECURE_HTTP_QUOTES_ENDPOINT, proxy => proxy.FindCurrentQuote("TSLA"));
        }
    }
}