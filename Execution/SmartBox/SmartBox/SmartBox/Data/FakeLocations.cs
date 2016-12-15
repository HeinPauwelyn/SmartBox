using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SmartBox.Models;

namespace SmartBox.Data
{
    public class FakeLocations : ILocationData
    {
        public List<Location> GetLocations()
        {
            return new List<Location>()
            {
                new Location("50°49\"", "3°48\"", "0", "15:36:48"),
                new Location("50°48\"", "3°44\"", "0", "15:36:50"),
                new Location("50°47\"", "3°45\"", "0", "15:36:52"),
                new Location("50°46\"", "3°49\"", "0", "15:36:54"),
                new Location("50°46\"", "3°41\"", "0", "15:36:56")
            };
        }
    }
}
