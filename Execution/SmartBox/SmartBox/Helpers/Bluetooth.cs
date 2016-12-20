using System;
using System.Threading.Tasks;
using Windows.Devices.Bluetooth.Rfcomm;
using Windows.Devices.Enumeration;
using Windows.Networking.Sockets;
using Windows.Storage.Streams;
using System.Linq;

namespace SmartBox.Helpers
{
    public class Bluetooth
    {
        private StreamSocket _socket;
        private RfcommDeviceService _service;

        public DeviceInformationCollection Devices { get; set; }

        public Bluetooth()
        {
            LoadDevices();
        }

        private async Task LoadDevices()
        {
            Devices = await DeviceInformation.FindAllAsync(RfcommDeviceService.GetDeviceSelector(RfcommServiceId.SerialPort));
        }

        public async Task<string> Connect(string deviceName)
        {
            try
            {
                DeviceInformation device = Devices.Single(x => x.Name == deviceName);

                _service = await RfcommDeviceService.FromIdAsync(device.Id);
                _socket = new StreamSocket();

                await _socket.ConnectAsync(_service.ConnectionHostName, _service.ConnectionServiceName, SocketProtectionLevel.BluetoothEncryptionAllowNullAuthentication);

                return "";
            }
            catch (Exception ex)
            {
                return ex.Message;
            }
        }

        public async Task<uint> Send(string msg)
        {
            try
            {
                DataWriter writer = new DataWriter(_socket.OutputStream);

                writer.WriteString(msg);

                // Launch an async task to complete the write operation
                return await writer.StoreAsync().AsTask();
            }
            catch (Exception ex)
            {
                return 0;
            }
        }

        public async Task Disconnect()
        {
            if (_service != null && _socket != null)
            {
                await _socket.CancelIOAsync();
                _socket.Dispose();
                _socket = null;
                _service.Dispose();
                _service = null;
            }
        }
    }
}
