
package biblioteca.biblioteca.domain;

import jakarta.persistence.*; import lombok.*;

@Data @Entity @Table(name="libro")
public class Libro {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  @Column(nullable=false,length=200) private String titulo;
  private String isbn;
  private String tipo;
  private Double precio;
  private Integer stock;
  private Boolean activo=true;
  private String portadaUrl;
  private String urlImagenLibro;
  @ManyToOne 
  @JoinColumn(name="id_genero")
  private Genero genero;

  @ManyToOne 
  @JoinColumn(name="id_autor")
  private Autor autor;
}

