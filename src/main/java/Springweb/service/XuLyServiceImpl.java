/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Springweb.service;

import Springweb.entity.XuLy;
import Springweb.repository.XuLyRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vi Hao
 */
@Service
public class XuLyServiceImpl implements XuLyService{
    @Autowired
    private XuLyRepository xuLyRepository;

    @Override
    public Iterable<XuLy> findAll() {
        return xuLyRepository.findAll();
    }

    @Override
    public List<XuLy> search(String term) {
        return null;
    }

    @Override
    public XuLy add(XuLy tv) {
        return xuLyRepository.save(tv);
    }

    @Override
    public XuLy update(XuLy tv) {
        return xuLyRepository.save(tv);
    }

    @Override
    public Optional<XuLy> findById(Integer id) { 
        return xuLyRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteByMaTV(Integer matv) {
        xuLyRepository.deleteByMaTV(matv);
    }
    @Override
    public int maxID() {
        return xuLyRepository.getNextAutoIncrementValue();
    }
}
