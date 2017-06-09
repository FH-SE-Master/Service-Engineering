using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Sve2.FhQuotes.Domain;
using Sve2.FhQuotes.Dao.Interfaces;
using Swashbuckle.AspNetCore.SwaggerGen;

namespace Sve2.FhQuotesServices.Controllers
{
    // [Route("api/[controller]")] usage would cause case sensitivity within the path 
    [Route("api/stocks")]
    public class StocksController : Controller
    {
        private IStockDao _stockDao;
        private IQuoteDao _quoteDao;
        private ILogger<StocksController> _logger;

        public StocksController(IStockDao stockdao, IQuoteDao quoteDao, ILogger<StocksController> logger)
        {
            _stockDao = stockdao;
            _quoteDao = quoteDao;
            _logger = logger;
        }

        [HttpGet]
        [Produces(typeof(Stock))]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [SwaggerOperation(nameof(GetStockBySmybol))]
        public async Task<IEnumerable<Stock>> GetAllStocks()
        {
            return await _stockDao.FindAll();
        }

        [HttpGet("{symbol}")]
        [Produces(typeof(Stock))]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [SwaggerOperation(nameof(GetStockBySmybol))]
        public async Task<IActionResult> GetStockBySmybol(string symbol)
        {
            Stock stock = await _stockDao.FindBySymbol(symbol);
            if (stock == null)
            {
                return NotFound($"Stock for symbol {symbol} not found");
            }

            return Ok(stock);
        }
    }
}