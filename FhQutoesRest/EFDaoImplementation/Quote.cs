using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Sve2.FhQuotes.Dao.EntityFramework {

  [Table("Quote")]
  internal class Quote {

    [Key]
    public int Id { get; set; }

    [Required]
    public string StockSymbol { get; set; }

    public decimal? Ask { get; set; }

    public decimal? Bid { get; set; }

    public decimal? Price { get; set; }

    public DateTime? Time { get; set; }

    [ForeignKey("StockSymbol")]
    [InverseProperty("Quote")]
    public virtual Stock Stock { get; set; }
  }
}
