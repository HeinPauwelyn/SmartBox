CREATE PROCEDURE insertLocation 
    @Latitude nvarchar(50),
    @Longitude nvarchar(50),
    @Altitude nvarchar(50),
    @Time nvarchar(50),  
    @IsOpen bit
AS   
    insert into Locations(latitude, longitude, altitude, time, isopen) values (@Latitude, @Longitude, @Altitude, @Time, @IsOpen);
GO