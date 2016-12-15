using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SmartBox.Models
{
    public class Location
    {
        public string Longitude { get; set; }
        public string Latitude { get; set; }
        public string Altitude { get; set; }
        public string Time { get; set; }

        public Location()
        { }

        public Location(string lng, string lat, string alt, string time)
        {
            Longitude = lng;
            Latitude = lat;
            Altitude = alt;
            Time = time;
        }

        public override string ToString()
        {
            return $"{Time}: Longitude = {Longitude}, Latitude = {Latitude}, Altitude = {Altitude}";
        }
    }
}
