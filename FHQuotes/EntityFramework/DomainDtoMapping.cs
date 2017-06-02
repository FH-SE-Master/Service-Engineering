
namespace Sve2.FhQuotes.Dao.EntityFramework
{

	internal static class DomainDtoMapping {
		public static Domain.Stock ToDomain(this Stock s) {
			if (s == null)
				return null;
			return new Domain.Stock { Symbol = s.Symbol, Name = s.Name, Currency = s.Currency };
		}

		public static Domain.Quote ToDomain(this Quote q) {
			if (q == null)
				return null;
			return new Domain.Quote {
				Ask = q.Ask,
				Bid = q.Bid,
				Price = q.Price,
				Time = q.Time,
				Stock = q.Stock == null ? null : q.Stock.ToDomain()
			};
		}

		public static Stock ToEF(this Domain.Stock s) {
			if (s == null)
				return null;
			return new Stock { Symbol = s.Symbol, Name = s.Name, Currency = s.Currency };
		}

		public static Quote ToEF(this Domain.Quote q) {
			if (q == null)
				return null;
			return new Quote {
				Ask = q.Ask,
				Bid = q.Bid,
				Price = q.Price,
				Time = q.Time,
				Stock = q.Stock == null ? null : q.Stock.ToEF()
			};
		}
	}

}
