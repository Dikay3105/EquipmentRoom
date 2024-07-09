/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Springweb.service;

import Springweb.entity.ThietBi;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vi Hao
 */
@Service
public interface ThietBiService {
    @Autowired
    Iterable<ThietBi> findAll();
    List<ThietBi> listForSearch();
    List<ThietBi> search(String term);
    ThietBi add(ThietBi tb);
    ThietBi update(ThietBi tb);
    Optional<ThietBi> findById(Integer id);
    void deleteAllById(Integer id);
    int maxID();
}
