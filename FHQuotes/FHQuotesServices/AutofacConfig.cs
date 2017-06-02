using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Autofac;
using Sve2.FhQuotes.Dao.Interfaces;
using Sve2.FhQuotes.Dao.Simple;
using Sve2.FHQuotes.Interfaces;

namespace Sve2.FHQuotes.Services
{
    public class AutofacConfig
    {
        public static IContainer CreateContainer()
        {
            ContainerBuilder builder = new ContainerBuilder();
            // register DAOs
            builder.RegisterType<Sve2.FhQuotes.Dao.Simple.StockDao>()
                .As<IStockDao>()
                .InstancePerLifetimeScope();

            builder.RegisterType<Sve2.FhQuotes.Dao.Simple.QuoteDao>()
                .As<IQuoteDao>()
                .InstancePerLifetimeScope();

            // register services
            builder.RegisterType<FHQuotesService>();

            return builder.Build();
        }
    }
}
