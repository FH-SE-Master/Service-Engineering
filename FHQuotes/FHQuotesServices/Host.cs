using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using Autofac;
using Autofac.Integration.Wcf;

namespace Sve2.FHQuotes.Services
{
    class Host
    {
        static void Main(string[] args)
        {
            using (IContainer container = AutofacConfig.CreateContainer())
            {
                using (ServiceHost quotesHost = new ServiceHost(typeof(FHQuotesService)))
                {
                    quotesHost.AddDependencyInjectionBehavior<FHQuotesService>(container);

                    quotesHost.Open();

                    Console.WriteLine("Service Host started");
                    Console.ReadLine();
                }
            }
        }
    }
}
