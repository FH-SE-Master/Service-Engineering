using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Rest;
using Sve2.FhQuotes.Client.Proxy;
using Sve2.FhQuotes.Domain;

namespace ConsoleClient
{
    public class Program
    {
        private const string QUOTES_SERVICE_HOST_PORT = "http://localhost:5001";
        private static readonly string QUOTES_SERVICE_BASE_ADDR = $"{QUOTES_SERVICE_HOST_PORT}/api";

        private static readonly MediaTypeWithQualityHeaderValue MEDIA_TYPE_XML =
            new MediaTypeWithQualityHeaderValue("application/xml");

        private static readonly MediaTypeWithQualityHeaderValue MEDIA_TYPE_JSON =
            new MediaTypeWithQualityHeaderValue("application/json");

        private static async Task HttpRestClient()
        {
            using (HttpClient client = new HttpClient())
            {
                Console.WriteLine("-------------------------------- Stock List ----------------------------------");
                // set default accept header
                client.DefaultRequestHeaders.Accept.Add(MEDIA_TYPE_JSON);
                HttpResponseMessage message = await client.GetAsync($"{QUOTES_SERVICE_BASE_ADDR}/stocks");
                message.EnsureSuccessStatusCode();

                // log raw result
                Console.WriteLine($"Content: {message.Content.AsString()}");

                // log parsed response
                var parsedResponse = await message.Content.ReadAsAsync<IEnumerable<Stock>>();
                Console.WriteLine($"Parsed response : {String.Join(", ", parsedResponse)}");

                Console.WriteLine("------------------------------- Error Handling -------------------------------");
                foreach (string symbol in new[] {"MSFT", "XXX"})
                {
                    message = await client.GetAsync($"{QUOTES_SERVICE_BASE_ADDR}/stocks/{symbol}");
                    if (message.IsSuccessStatusCode)
                    {
                        Console.WriteLine($"Content for symbol '{symbol}': {message.Content.AsString()}");
                    }
                    else
                    {
                        Console.WriteLine(
                            $"Error occurred: status: {message.StatusCode} / request message: {message.RequestMessage.Content} / uri: {message.RequestMessage.RequestUri}");
                    }
                }

                Console.WriteLine("--------------------------------- Add Quote ----------------------------------");
                string stockSymbol = "MSFT";
                Quote newQuote = new Quote
                {
                    Price = 10,
                    Ask = 9,
                    Bid = 11,
                    Time = DateTime.Now
                };
            }
        }

        private static async Task AutoRestClient()
        {
            Console.WriteLine("-------------------------------- Stock List ----------------------------------");
            using (IQuotesClient quotesClient = new QuotesClient(new Uri(QUOTES_SERVICE_BASE_ADDR)))
            {
                Console.WriteLine("------------------------------- Error Handling -------------------------------");
                foreach (string symbol in new[] {"BAS.DE", "XXX"})
                {
                    var message = await quotesClient.GetStockBySmybol1WithHttpMessagesAsync(symbol);
                    if (message.Response.IsSuccessStatusCode)
                    {
                        Console.WriteLine($"Parsed content: {message.Body}");
                    }
                    else
                    {
                        Console.WriteLine(
                            $"Error occurred: status: {message.Response.StatusCode} / request message: {message.Request.Content} / uri: {message.Request.RequestUri}");
                    }
                }

                Console.WriteLine("--------------------------------- Add Quote ----------------------------------");
                string stockSymbol = "MSFT";
                Quote newQuote = new Quote
                {
                    Price = 10,
                    Ask = 9,
                    Bid = 11,
                    Time = DateTime.Now
                };
            }
        }

        public static void Main(string[] args)
        {
            Console.WriteLine("=============================== HttpRestClient ===============================");
            HttpRestClient().Wait();

            Console.WriteLine("=============================== AutoRestClient ===============================");
            AutoRestClient().Wait();
        }
    }
}