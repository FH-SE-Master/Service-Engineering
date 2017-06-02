using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using Sve2.FhQuotes.Faults;
using Sve2.FHQuotes.Interfaces;

namespace Sve2.FHQuotes.Client
{
    class WcfHelper
    {
        public static void Execute<T>(string endpoint, Action<T> action)
        {
            string result = WcfHelper.Execute<string, T>(endpoint, proxy => {
                action(proxy);
                return "done";
            });
        }

    public static R Execute<R, T>(string endpoint, Func<T, R> function)
        {
            using (ChannelFactory<T> quotesFactory = new ChannelFactory<T>(endpoint))
            {
                R result = default(R);
                T proxy = default(T);
                try
                {
                    proxy = quotesFactory.CreateChannel();
                    result = function(proxy);
                }
                finally
                {
                    AbortDefaultedChannel(proxy);
                }

                return result;
            }
        }

        private static void AbortDefaultedChannel<T>(T proxy)
        {
            if (proxy != null && proxy is ICommunicationObject)
            {
                ICommunicationObject comObj = (ICommunicationObject)proxy;
                if (comObj.State == CommunicationState.Faulted)
                {
                    comObj.Abort();
                }
            }
        }
    }
}
