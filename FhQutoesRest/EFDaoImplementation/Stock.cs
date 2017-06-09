using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Sve2.FhQuotes.Dao.EntityFramework {

  [Table("Stock")]
  internal class Stock {

    public Stock() {
      Quote = new HashSet<Quote>();
    }

    [Key]
    public string Symbol { get; set; }

    [Required]
    public string Name { get; set; }

    [Required]
    public string Currency { get; set; }

    [InverseProperty("Stock")]
    public virtual ICollection<Quote> Quote { get; set; }
  }
}
