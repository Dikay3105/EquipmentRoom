/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Springweb.service;

import Springweb.entity.ThanhVien;
import Springweb.repository.ThanhVienRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vi Hao
 */
@Service
public interface ThanhVienService {
    @Autowired
    Iterable<ThanhVien> findAll();

    List<ThanhVien> listForSearch();

    ThanhVien add(ThanhVien tv);

    ThanhVien update(ThanhVien tv);

    Optional<ThanhVien> findById(Integer id);

    ThanhVien findByMaTV(Integer maTV);

    void deleteAllById(Integer id);


}
