package com.dam.adp.fitness360proyecto3eval.DAO;

import java.util.List;

/**
 * Interfaz genérica para los objetos de acceso a datos (DAO)
 * Define los métodos comunes que deben implementar todos los DAOs
 * 
 * @param <T> Tipo de entidad que maneja el DAO
 */
public interface GenericDAO<T> {
    
    /**
     * Obtiene todos los registros de la entidad
     * 
     * @return Lista de todas las entidades
     */
    List<T> getAll();
    
    /**
     * Obtiene una entidad por su identificador
     * 
     * @param id Identificador de la entidad
     * @return La entidad encontrada o null si no existe
     */
    T getById(int id);
    
    /**
     * Inserta una nueva entidad en la base de datos
     * 
     * @param entity Entidad a insertar
     * @return La entidad insertada con su ID generado
     */
    T insert(T entity);
    
    /**
     * Actualiza una entidad existente en la base de datos
     * 
     * @param entity Entidad con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    boolean update(T entity);
    
    /**
     * Elimina una entidad de la base de datos
     * 
     * @param entity Entidad a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    boolean delete(T entity);
}