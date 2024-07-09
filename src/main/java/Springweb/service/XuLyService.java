/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Springweb.service;

import Springweb.entity.XuLy;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vi Hao
 */
@Service
public interface XuLyService {
    @Autowired
    Iterable<XuLy> findAll();
    List<XuLy> search(String term);
    XuLy add(XuLy tv);
    XuLy update(XuLy tv);
    Optional<XuLy> findById(Integer id);
    void deleteByMaTV(Integer id);
    int maxID();
}
