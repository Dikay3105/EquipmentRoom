/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Springweb.service;

import Springweb.entity.ThietBi;
import Springweb.repository.ThietBiRepository;
import Springweb.repository.ThongTinSDRepository;
import Springweb.repository.XuLyRepository;
import java.util.ArrayList;
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
public class ThietBiServiceImpl implements ThietBiService{
    @Autowired
    private ThietBiRepository ThietBiRepository;
    @Autowired
    private XuLyRepository xuLyRepository; 
    @Autowired
    private ThongTinSDRepository thongTinSDRepository;
    
    @Override
    public Iterable<ThietBi> findAll() {
        return ThietBiRepository.findAll();
    }

    @Override
    public List<ThietBi> search(String term) {
        return null;
    }
    
     @Override
    public List<ThietBi> listForSearch() {
        Iterable<ThietBi> iterable = ThietBiRepository.findAll();

        List<ThietBi> list = new ArrayList<>();
        iterable.forEach(list::add);

        return list;
    }
    @Override
    public ThietBi add(ThietBi tv) {
        return ThietBiRepository.save(tv);
    }

    @Override
    public ThietBi update(ThietBi tv) {
        return ThietBiRepository.save(tv);
    }

    @Override
    public Optional<ThietBi> findById(Integer id) { // handle case if ThietBi is not exist in database
        return ThietBiRepository.findById(id);
    }

    @Override
    public int maxID() {
        return ThietBiRepository.getNextAutoIncrementValue();
    }
    
    
    @Override
    @Transactional
    public void deleteAllById(Integer id) {
        System.out.println(id);
        xuLyRepository.deleteByMaTV(id);
        thongTinSDRepository.deleteByMaTV(id);
        ThietBiRepository.deleteById(id);
    }
    
  
}
