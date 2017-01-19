using SmartBox.Models;
using SmartBox.WebApp.Repo;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data.Entity;

namespace SmartBox.WebApp.Controllers
{
    [AllowAnonymous]
    public class LocationController : ApiController
    {
        private GenericRepo<Location> _locations;

        public LocationController()
        {
            _locations = new GenericRepo<Location>();
        }

        public IEnumerable<Location> Get()
        {
            return _locations.All();
        }
    }
}
