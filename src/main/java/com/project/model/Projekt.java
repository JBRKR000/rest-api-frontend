package com.project.model;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Data
@Setter
@Getter
@JsonIgnoreProperties
public class Projekt {
    private Integer projektId;
    @NotBlank(message = "Pole nazwa nie może być puste!")
    @Size(min = 3, max = 50, message = "Pole nazwa musi zawierać od {min} do {max} znaków!")
    private String nazwa;
    private String opis;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dataCzasUtworzenia;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dataCzasModyfikacji;
    @JsonIgnoreProperties({"projekt"})
    private List<Zadanie> zadania;
    private Set<Student> studenci;
    public Projekt() {}
    public Projekt(String nazwa, String opis) {
        this.nazwa = nazwa;
        this.opis = opis;
    }
    public Projekt(String nazwa, String opis, LocalDate dataUtworzenia) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataCzasUtworzenia = dataUtworzenia.atStartOfDay();
        this.dataCzasModyfikacji = LocalDateTime.now();
    }
    public Projekt(Integer projektId, String nazwa, String opis, LocalDate dataUtworzenia) {
        this.projektId = projektId;
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataCzasUtworzenia = dataUtworzenia.atStartOfDay();
        this.dataCzasModyfikacji = LocalDateTime.now();
    }
    public Projekt(int i, String nazwa2, String opis2, LocalDateTime now, LocalDate of) {
    }

}
