# Mogelijke oplossingen

Er zijn twee mogelijke oplossingen voor dit probleem. Een eerste oplossing staat op onderstaand schema.

## Oplossing één


De smart bow heeft een GPS station bij zich en stuurt de GPS lokatie naar een IoT hub. Deze wordt daarna uitgelezen door een webjob die de gegevens in de database zet. Indien de gebruiker de lokatie opvraagt via de applicatie, wordt er via een API de gegevens uitgelezen vanuit de database. 

De applicatie wordt gemaakt met Xamarin voor Android, Windows en iOS. De webapplicatie wordt gemaakt met ASP.NET MVC en zal enkel API's bevatten. De Xamarin app, ASP.NET app en webjob worden geprogrammeerd met de taal C#. De Smartbox bevat een Arduino met GPS ontvanger en een Raspberry Pi. De gebruikte talen hiervoor zijn Python en Arduino. De database zal een SQL database worden.

Hieronder zie je een schematische voorstelling.

[![][2]][2]

## Oplossing twee

Identiek als oplossing één maar zonder IoT Hub. Wordt eventueel iets moeilijker qua uitvoering.

[![][1]][1]


[1]: https://raw.githubusercontent.com/HeinPauwelyn/SmartBox/master/Schemas/Smartbox-01.png
[2]: https://raw.githubusercontent.com/HeinPauwelyn/SmartBox/master/Schemas/Smartbox-02.png
