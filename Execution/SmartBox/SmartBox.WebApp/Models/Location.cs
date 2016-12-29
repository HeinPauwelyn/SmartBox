using System.ComponentModel.DataAnnotations;

namespace SmartBox.WebApp.Models
{
    public class Location
    {
        [Key]
        public int ID { get; set; }
        public string Longitude { get; set; }
        public string Latitude { get; set; }
        public string Altitude { get; set; }
        public string Time { get; set; }
        public bool IsOpen { get; set; }
    }
}