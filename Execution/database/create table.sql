drop table Locations;

create table Locations(
    id int primary key identity,
    latitude nvarchar(20),
    longitude nvarchar(20),
    altitude nvarchar(20),
    time nvarchar(20),
    isOpen bit
);

select id, latitude, longitude, altitude, time, isOpen from Locations;