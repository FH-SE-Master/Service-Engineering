using System.Data.Entity.ModelConfiguration;

namespace Sve2.FhQuotes.Dao.EntityFramework
{

	// advanced OR mapping with the EF fluent API
	internal class StockMap : EntityTypeConfiguration<Stock> {
		public StockMap() {
			// table name
			this.ToTable("Stock");

			// primary key
			this.HasKey(t => t.Symbol);
		}
	}

	internal class QuoteMap : EntityTypeConfiguration<Quote> {
		public QuoteMap() {
			// table name
			this.ToTable("Quote");

			// primary Key
			this.HasKey(t => t.Id);

			// mapping of relationships
			this.HasRequired(t => t.Stock)
					.WithMany(s => s.Quotes)
					.Map(d => d.MapKey("StockSymbol")); 
			    // foreign key name (not exposed in object model)
		}
	}
}
