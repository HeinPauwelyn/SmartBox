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
        private StreamSocket _socket;
        private RfcommDeviceService _service;
        private DeviceInformationCollection _devices;

        public MainPage()
        {
            InitializeComponent();
            Loaded += MainPage_Loaded;
        }

        private async void MainPage_Loaded(object sender, RoutedEventArgs e)
        {
            btnDisconnect.IsEnabled = false;
            _devices = await DeviceInformation.FindAllAsync(RfcommDeviceService.GetDeviceSelector(RfcommServiceId.SerialPort));

            foreach (DeviceInformation d in _devices)
            {
                cboDevices.Items.Add(d.Name);
            }
        }

        private async void btnSend_Click(object sender, RoutedEventArgs e)
        {
            int dummy;

            if (!int.TryParse(tbInput.Text, out dummy))
            {
                tbError.Text = "Invalid input";
            }

            uint noOfCharsSent = await Send(tbInput.Text);

            if (noOfCharsSent != 0)
            {
                tbError.Text = noOfCharsSent.ToString();
            }
        }
        private async Task<uint> Send(string msg)
        {
            tbError.Text = string.Empty;

            try
            {
                DataWriter writer = new DataWriter(_socket.OutputStream);

                writer.WriteString(msg);

                // Launch an async task to complete the write operation
                return await writer.StoreAsync().AsTask();
            }
            catch (Exception ex)
            {
                tbError.Text = ex.Message;
                return 0;
            }
        }

        private async void btnConnect_Click(object sender, RoutedEventArgs e)
        {
            await Connect();
        }

        private async void cboDevices_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            await Connect();
        }

        private async Task Connect()
        {
            tbError.Text = string.Empty;

            try
            {
                DeviceInformation device = _devices.Single(x => x.Name == cboDevices.SelectedItem.ToString());

                _service = await RfcommDeviceService.FromIdAsync(device.Id);
                _socket = new StreamSocket();

                await _socket.ConnectAsync(_service.ConnectionHostName, _service.ConnectionServiceName, SocketProtectionLevel.BluetoothEncryptionAllowNullAuthentication);
                btnConnect.IsEnabled = false;
                btnDisconnect.IsEnabled = true;
                cboDevices.IsEnabled = false;
            }
            catch (Exception ex)
            {
                tbError.Text = ex.Message;
            }
        }

        private async void btnDisconnect_Click(object sender, RoutedEventArgs e)
        {
            tbError.Text = string.Empty;

            try
            {
                await _socket.CancelIOAsync();
                _socket.Dispose();
                _socket = null;
                _service.Dispose();
                _service = null;

                btnDisconnect.IsEnabled = false;
                btnConnect.IsEnabled = true;
                cboDevices.IsEnabled = true;
            }
            catch (Exception ex)
            {
                tbError.Text = ex.Message;
            }
        }
    }
}
