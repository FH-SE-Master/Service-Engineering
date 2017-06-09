using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc.Formatters;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Sve2.FhQuotes.Dao.EntityFramework;
using Sve2.FhQuotes.Dao.Interfaces;
using Swashbuckle.AspNetCore.Swagger;

namespace Sve2.FhQuotesServices.Configuration
{
    public class Startup
    {
        public Startup(IHostingEnvironment env)
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(env.ContentRootPath)
                .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
                .AddJsonFile($"appsettings.{env.EnvironmentName}.json", optional: true)
                .AddEnvironmentVariables();
            Configuration = builder.Build();
        }

        public IConfigurationRoot Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            // Add framework services.
            services.AddMvc(options =>
            {
                options.InputFormatters.Add(new XmlDataContractSerializerInputFormatter());
                options.OutputFormatters.Add(new XmlDataContractSerializerOutputFormatter());
                options.ReturnHttpNotAcceptable = true;
            });
            // Add swagger api generation
            services.AddSwaggerGen(option =>
            {
                option.SwaggerDoc("v1", new Info {Title = "FhQuotes API", Version = "v1"});
            });

            // Defined injectable types
            services.AddScoped<IStockDao, StockDao>();
            services.AddScoped<IQuoteDao, QuoteDao>();

            services.AddEntityFramework()
                .AddDbContext<Sve2.FhQuotes.Dao.EntityFramework.FhQuotesContext>(options => {
                    options.UseSqlServer(Configuration.GetConnectionString("DefaultConnection"));
                    // options.UseSqlServer(Configuration.GetConnectionString("InMemoryConnection"));
                });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env, ILoggerFactory loggerFactory)
        {
            loggerFactory.AddConsole(Configuration.GetSection("Logging"));
            loggerFactory.AddDebug();

            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            // App uses swagger middleware 
            app.UseSwagger();
            app.UseSwaggerUI(option => { option.SwaggerEndpoint("/swagger/v1/swagger.json", "FhQuotes API"); });

            app.UseStaticFiles();
            app.UseMvc();
        }
    }
}