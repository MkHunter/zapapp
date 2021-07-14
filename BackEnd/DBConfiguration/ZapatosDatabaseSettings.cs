using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ApiZapapp.DBConfiguration
{
    public class ZapatosDatabaseSettings:IZapatosDatabaseSettings
    {
        public List<string> Collections { get; set; }
        public string ConnectionString { get; set; }
        public string DatabaseName { get; set; }
    }

    public interface IZapatosDatabaseSettings
    {
        public List<String> Collections { get; set; }
        public string ConnectionString { get; set; }
        public string DatabaseName { get; set; }
    }
}
