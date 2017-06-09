using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace Sve2.FhQuotes.Dao.EntityFramework {
  public class FhQuotesContext : DbContext {

    public FhQuotesContext(DbContextOptions<FhQuotesContext> options) : base(options) {
    }

    internal virtual DbSet<Quote> Quotes { get; set; }
    internal virtual DbSet<Stock> Stocks { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder) {
      modelBuilder.Entity<Quote>(entity => {
        entity.HasIndex(e => e.Time)
            .HasName("IX_Quote");
      });
    }
  }
}