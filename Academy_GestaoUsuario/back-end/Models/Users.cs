using System.ComponentModel.DataAnnotations;

namespace back_end.Models
{
    public class Users
    {
        [Required(ErrorMessage = "O nome é obrigatório.")]
        [StringLength(255, ErrorMessage = "O nome deve ter no máximo 255 caracteres.")]
        public required string Name { get; set; }

        [Required(ErrorMessage = "O registro é obrigatório.")]
        [StringLength(50, ErrorMessage = "O registro deve ter no máximo 50 caracteres.")]
        public required string Registro { get; set; }

        [StringLength(100, ErrorMessage = "O curso deve ter no máximo 100 caracteres.")]
        public string? Course { get; set; }

        public int EntryYear { get; set; }

        [StringLength(50, ErrorMessage = "A classe deve ter no máximo 50 caracteres.")]
        public string? Class { get; set; }

        [StringLength(50, ErrorMessage = "A classe deve ter no máximo 50 caracteres.")]
        public DateOnly Birthday { get; set; }
    }
}
