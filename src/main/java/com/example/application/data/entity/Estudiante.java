/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Leinier
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Estudiantes")
public class Estudiante extends Person {

    @EqualsAndHashCode.Include
    @ToString.Include

    @NotNull(message = "campo vacio")
    @Column(name = "anno_repitencia" , nullable = false)
    private Integer anno_repitencia;

    @NotNull(message = "campo vacio")
    @Column(name = "cantidad_asignaturas" , nullable = false)
    private Integer cantidad_asignaturas;

    @NotNull(message = "debe elegir un campo")
    @ManyToOne
    private Area area;

    @NotNull(message = "debe elegir un campo")
    @ManyToOne
    private Grupo grupo;

    @OneToMany(mappedBy = "estudiante")
    private List<Evaluacion> evaluaciones;

    @OneToMany(mappedBy = "e")
    private List<Tarea> tareas;

    public Estudiante(String nombre, String apellidos, String email, String solapin, Integer anno_repitencia, Integer cantidad_asignaturas, Area area) {
        super(nombre, apellidos, email, solapin);
        this.anno_repitencia = anno_repitencia;
        this.cantidad_asignaturas = cantidad_asignaturas;
        this.area = area;
    }

    public String getStringNombreApellidos() {
        return getNombre() + " " + getApellidos();
    }

}
