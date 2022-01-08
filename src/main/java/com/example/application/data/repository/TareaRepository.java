/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.data.repository;

import com.example.application.data.entity.Tarea;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Leinier
 */
@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    //filtrar
    @Query("SELECT t from Tarea t JOIN Person p on t.id = p.id "
            + " WHERE lower(t.nombre) like lower(concat('%', :searchTerm, '%')) "
            + "or lower(t.descripcion) like lower(concat('%', :searchTerm, '%')) "
            + "or lower(t.duracion) like lower(concat('%', :searchTerm, '%')) "
            + "or lower(p.nombre) like lower(concat('%', :searchTerm, '%')) "
            + "or lower(p.apellidos) like lower(concat('%', :searchTerm, '%')) "
    )
    List<Tarea> search(@Param("searchTerm") String searchTerm);

    //filtrar por nombre 
    @Query("SELECT t from Tarea t JOIN Person p on t.id = p.id "
            + " WHERE lower(t.nombre) like lower(concat('%', :searchTerm, '%')) "
    )
    List<Tarea> searchByNombre(@Param("searchTerm") String searchTerm);
    
    //filtrar por descripcion 
    @Query("SELECT t from Tarea t JOIN Person p on t.id = p.id "
            + " WHERE lower(t.descripcion) like lower(concat('%', :searchTerm, '%')) "
    )
    List<Tarea> searchByDescripcion(@Param("searchTerm") String searchTerm);
    
    //filtrar por duracion 
    @Query("SELECT t from Tarea t JOIN Person p on t.id = p.id "
            + " WHERE lower(t.duracion) like lower(concat('%', :searchTerm, '%')) "
    )
    List<Tarea> searchByDuracion(@Param("searchTerm") String searchTerm);
    
    //filtrar por Estudiante
    @Query("SELECT t from Tarea t JOIN Person p on t.id = p.id "
            + " WHERE lower(p.nombre) like lower(concat('%', :searchTerm, '%')) "
            + "or lower(p.apellidos) like lower(concat('%', :searchTerm, '%')) "
    )
    List<Tarea> searchByEstudiante(@Param("searchTerm") String searchTerm);

}
