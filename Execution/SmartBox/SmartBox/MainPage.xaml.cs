using SmartBox.Helpers;
using System;
using System.Linq;
using System.Threading.Tasks;
using Windows.Devices.Bluetooth.Rfcomm;
using Windows.Devices.Enumeration;
using Windows.Networking.Sockets;
using Windows.Storage.Streams;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;

namespace SmartBox
{
    public sealed partial class MainPage : Page
    {
        private Bluetooth _bluetooth;

        public MainPage()
        {
            InitializeComponent();
            Loaded += MainPage_Loaded;
        }

        private void MainPage_Loaded(object sender, RoutedEventArgs e)
        {
            // btnDisconnect.IsEnabled = false;
            _bluetooth = new Bluetooth();

            foreach (DeviceInformation device in _bluetooth.Devices)
            {
                cboDevices.Items.Add(device.Name);
            }
        }

        private async void btnSend_Click(object sender, RoutedEventArgs e)
        {
            uint noOfCharsSent = await _bluetooth.Send(tbInput.Text);

            if (noOfCharsSent != 0)
            {
                tbError.Text = noOfCharsSent.ToString();
            }
        }


        private async void btnConnect_Click(object sender, RoutedEventArgs e)
        {
            await Connect();
        }

        private async Task Connect()
        {
            if (await _bluetooth.Connect(cboDevices.SelectedItem.ToString()))
            {
                tbError.Text = "Connected";
            }
            else
            {
                tbError.Text = "Error while connecting";
            }
        }

        private async void cboDevices_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            await Connect();
        }

        private async void btnDisconnect_Click(object sender, RoutedEventArgs e)
        {
            tbError.Text = string.Empty;

            await _bluetooth.Disconnect();
        }
    }
}
