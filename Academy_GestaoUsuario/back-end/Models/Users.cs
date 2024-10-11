using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data;

namespace back_end.Models
{
    public class User
    {
        [Key]
        [Column("user_id")]
        public int UserId { get; set; }

        [Column("role_id")]
        public int RoleId { get; set; }

        [Required]
        [StringLength(255)]
        [Column("name")]
        public string Name { get; set; }

        [Required]
        [EmailAddress]
        [StringLength(255)]
        [Column("email")]
        public string Email { get; set; }

        [Required]
        [StringLength(255)]
        [Column("password")]
        public string Password { get; set; }

        [Column("creation_date")]
        public DateTime CreationDate { get; set; } = DateTime.UtcNow;

        [Column("isActive")]
        public bool IsActive { get; set; } = true;
    }
}
