using Plugin.Geolocator;
using Plugin.Geolocator.Abstractions;
using SmartBox.Data;
// using SmartBox.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace SmartBox
{
    public partial class MainPage : ContentPage
    {
        private ILocationData locationData = new FakeLocations();

        //public List<Location> Locations { get; set; }

        public MainPage()
        {
            InitializeComponent();

        }

        public async void GetGPS(object sender, EventArgs e)
        {
            IGeolocator locator = CrossGeolocator.Current;
            locator.DesiredAccuracy = 50;

            Position position = await locator.GetPositionAsync(timeoutMilliseconds: 10000);

            lng.Text = position.Longitude.ToString();
            lat.Text = position.Latitude.ToString();
        }
    }
}
