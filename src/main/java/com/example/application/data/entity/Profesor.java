/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;

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
@Table(name = "Profesores")
public class Profesor extends AbstractEntity {

    @EqualsAndHashCode.Include
    @ToString.Include

    @NotEmpty
    @NotBlank(message = "El campo no debe estar vacío")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z\\s]*$" ,message = "Solo letras") //0 combinaciones de letras 0 o mas veces incluyendo espacios
    @Size(message = "Mínimo 2 caracteres y máximo 100",min=2,max = 100)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotEmpty
    @NotBlank(message = "El campo no debe estar vacío")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z\\s]*$" ,message = "Solo letras") //0 combinaciones de letras 0 o mas veces incluyendo espacios
    @Size(message = "Mínimo 3 caracteres y máximo 100",min=3,max = 100)
    @Column(name = "apellidos" ,nullable = false)
    private String apellidos;

    @Email
    @NotEmpty
    @NotBlank(message = "El campo no debe estar vacío")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_\\.][a-zA-Z0-9]+(@uci\\.cu)$" , message = "Por favor escriba un correo válido" )
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotEmpty
    @NotBlank(message = "El campo no debe estar vacío")
    @Pattern(regexp = "^[A-Z][0-9]+$" ,message = "Solo letras y numeros")
    @Size(message = "Mínimo 7 caracteres y máximo 7 ", min=7, max = 7)
    @Column(name = "solapin", nullable = false, unique = true)
    private String solapin;


    @NotNull(message = "debe elegir un campo")
    @JoinColumn(name = "a_id")
    @ManyToOne
    private Area a;

    @NotNull(message = "campo vacío")
    @OneToOne()
    private User user;

    public String getStringNombreApellidos() {
        return getNombre() + " " + getApellidos();
    }

}
