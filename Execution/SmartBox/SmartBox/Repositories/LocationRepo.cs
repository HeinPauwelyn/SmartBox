using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using SmartBox.Models;

namespace SmartBox.Repositories
{
    public class LocationRepo
    {
        public async Task<List<Location>> GetLocations()
        {
            List<Location> locations = new List<Location>();
            try
            {
                using (SqlConnection connection = new SqlConnection(Helpers.ConnectionString))
                {
                    SqlCommand command = new SqlCommand("select id, latitude, longitude, altitude, time, isOpen from locations", connection);
                    await connection.OpenAsync();

                    using (SqlDataReader reader = await command.ExecuteReaderAsync())
                    {
                        while (await reader.ReadAsync())
                        {
                            locations.Add(new Location() {
                                ID = Convert.ToInt32(reader["id"]),
                                Longitude = reader["longitude"].ToString(),
                                Latitude = reader["latitude"].ToString(),
                                Allitude = reader["altitude"].ToString(),
                                Time = reader["time"].ToString(),
                                IsOpen = (bool)reader["isOpen"]
                            });
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                return null;
            }

            return locations;
        }
    }
}
