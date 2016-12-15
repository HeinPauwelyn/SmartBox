using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Maps;

namespace SmartBox
{
    public partial class Map : ContentPage
    {
        public Map()
        {
            InitializeComponent();

            //Xamarin.Forms.Maps.Map map = new Xamarin.Forms.Maps.Map(MapSpan.FromCenterAndRadius(
            //    new Position(50.818285, 3.149985),
            //    Distance.FromKilometers(0.8)
            //))
            //{
            //    IsShowingUser = true,
            //    VerticalOptions = LayoutOptions.FillAndExpand
            //};
        }
    }
}
