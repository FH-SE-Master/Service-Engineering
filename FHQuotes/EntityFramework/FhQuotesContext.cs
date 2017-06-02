using System.Data.Entity;

namespace Sve2.FhQuotes.Dao.EntityFramework
{
	internal class FhQuotesContext : DbContext {

		public FhQuotesContext()
			: base("Name=FhQuotesDb") {
		}

		public DbSet<Quote> Quotes { get; set; }
		public DbSet<Stock> Stocks { get; set; }

		protected override void OnModelCreating(DbModelBuilder modelBuilder) {
			modelBuilder.Configurations.Add(new StockMap());
			modelBuilder.Configurations.Add(new QuoteMap());
		}
	}
}
