/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.domain.PrestamoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoItemRepository extends JpaRepository<PrestamoItem, Long> {

}

