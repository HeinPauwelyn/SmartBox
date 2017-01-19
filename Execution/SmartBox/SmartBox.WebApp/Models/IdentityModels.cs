using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity.Owin;
using System.Data.Entity;
using SmartBox.Models;

namespace SmartBox.WebApp.Models
{
    public class ApplicationDbContext: IdentityDbContext
    {
        public DbSet<Location> Locations { get; set; }

        public ApplicationDbContext()
            : base("DefaultConnection")
        {
        }
        
        public static ApplicationDbContext Create()
        {
            return new ApplicationDbContext();
        }
    }
}