using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SmartBox.Models
{
    public class Location
    {
        public int ID { get; set; }
        public string Longitude { get; set; }
        public string Latitude { get; set; }
        public string Allitude { get; set; }
        public string Time { get; set; }
        public bool IsOpen { get; set; }

        public override string ToString()
        {
            return $"Long: {Longitude}, Lat: {Latitude}, time: {Time}, open: {IsOpen}";
        }
    }
}
