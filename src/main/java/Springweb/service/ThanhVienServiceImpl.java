/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Springweb.service;

import Springweb.entity.ThanhVien;
import Springweb.repository.ThanhVienRepository;
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
public class ThanhVienServiceImpl implements ThanhVienService {
    @Autowired
    private ThanhVienRepository thanhVienRepository;
    @Autowired
    private XuLyRepository xuLyRepository;
    @Autowired
    private ThongTinSDRepository thongTinSDRepository;

    @Override
    public Iterable<ThanhVien> findAll() {
        return thanhVienRepository.findAll();
    }

    @Override
    public List<ThanhVien> listForSearch() {
        Iterable<ThanhVien> iterable = thanhVienRepository.findAll();

        List<ThanhVien> list = new ArrayList<>();
        iterable.forEach(list::add);

        return list;
    }

    @Override
    public ThanhVien add(ThanhVien tv) {
        return thanhVienRepository.save(tv);
    }

    @Override
    public ThanhVien update(ThanhVien tv) {
        return thanhVienRepository.save(tv);
    }

    @Override
    public Optional<ThanhVien> findById(Integer id) { // handle case if ThanhVien is not exist in database
        return thanhVienRepository.findById(id);
    }

    @Override
    public ThanhVien findByMaTV(Integer maTV) { // handle case if ThanhVien is not exist in database
        return thanhVienRepository.findByMaTV(maTV);
    }

    @Override
    @Transactional
    public void deleteAllById(Integer id) {
        System.out.println(id);
        xuLyRepository.deleteByMaTV(id);
        thongTinSDRepository.deleteByMaTV(id);
        thanhVienRepository.deleteById(id);
    }

}
