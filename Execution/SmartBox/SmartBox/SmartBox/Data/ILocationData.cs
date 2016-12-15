using SmartBox.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SmartBox.Data
{
    interface ILocationData
    {
        List<Location> GetLocations();
    }
}
