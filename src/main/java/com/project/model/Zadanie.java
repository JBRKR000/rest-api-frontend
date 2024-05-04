package com.project.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Data
@Setter
@Getter
public class Zadanie {
    private int zadanieId;
    private int projektId;
    @NotBlank(message = "Pole nazwa nie może być puste!")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierać od {min} do {max} znaków!")
    private String nazwa;
    private int kolejnosc;
    private String opis;
    private LocalDateTime dataCzasOddania;
    @JsonIgnoreProperties({"projekt_id"})
    private Projekt projekt;
    public Zadanie(){
    }
    public Zadanie(String nazwa, String opis, int kolejnosc){
        this.nazwa = nazwa;
        this.opis = opis;
        this.kolejnosc = kolejnosc;
    }

    public Integer getZadanieId() {
        return zadanieId;
    }
}
