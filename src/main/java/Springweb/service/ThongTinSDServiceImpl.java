/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Springweb.service;

import Springweb.entity.ThongTinSD;
import Springweb.repository.ThongTinSDRepository;
import java.util.ArrayList;
import java.util.Date;
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
public class ThongTinSDServiceImpl implements ThongTinSDService {
    @Autowired
    private ThongTinSDRepository thongTinSDRepository;

    @Override
    public Iterable<ThongTinSD> findAll() {
        return thongTinSDRepository.findAll();
    }

    @Override
    public List<ThongTinSD> search(String term) {
        return null;
    }

    @Override
    public ThongTinSD add(ThongTinSD tv) {
        return thongTinSDRepository.save(tv);
    }

    @Override
    public ThongTinSD update(ThongTinSD tv) {
        return thongTinSDRepository.save(tv);
    }

    @Override
    public Optional<ThongTinSD> findById(Integer id) { // handle case if ThongTinSD is not exist in database
        return thongTinSDRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteByMaTV(Integer matv) {
        thongTinSDRepository.deleteByMaTV(matv);
    }

    @Override
    public int getCountTVAll() {
        return thongTinSDRepository.getCountTVAll();
    }

    @Override
    public ArrayList<Object[]> getCountTVTheoKhoa() {
        return thongTinSDRepository.getCountTVTheoKhoa();
    }

    @Override
    public ArrayList<Object[]> getCountTVTheoNganh() {
        return thongTinSDRepository.getCountTVTheoNganh();
    }

    @Override
    public ArrayList<Object[]> getCountTVTheoNgay(Date date) {
        return thongTinSDRepository.getCountTVTheoNgay(date);
    }

    @Override
    public ArrayList<Object[]> getCountTVTheoThang(int month, int year) {
        return thongTinSDRepository.getCountTVThang(month, year);
    }

    @Override
    public ArrayList<Object[]> getCountTVTheoNam(int year) {
        return thongTinSDRepository.getCountTVTheoNam(year);
    }

    @Override
    public ArrayList<Object[]> getCountTVTheoKhoang(Date date1, Date date2) {
        return thongTinSDRepository.getCountTVTheoKhoang(date1, date2);
    }

    @Override
    public ArrayList<Object[]> getCountTBTheoKhoang(Date date1, Date date2) {
        return thongTinSDRepository.getCountTBTheoKhoang(date1, date2);
    }

    @Override
    public ArrayList<ThongTinSD> getThietBiDangMuon() {
        return thongTinSDRepository.getThietBiDangMuon();
    }

    @Override
    public ArrayList<ThongTinSD> getThietBiDangMuonTrongNgay(Date date) {
        return thongTinSDRepository.getThietBiDangMuonTrongNgay(date);
    }

    @Override
    public ArrayList<ThongTinSD> getThietBiDangMuonTrongKhoangThoiGian(Date startDate, Date endDate) {
        return thongTinSDRepository.getThietBiDangMuonTrongKhoangThoiGian(startDate, endDate);
    }

    @Override
    public ArrayList<ThongTinSD> getThietBiDangMuonTrongThang(int year, int month) {
        return thongTinSDRepository.getThietBiDangMuonTrongThang(year, month);
    }

    @Override
    public ArrayList<ThongTinSD> getThietBiDangMuonTrongNam(int year) {
        return thongTinSDRepository.getThietBiDangMuonTrongNam(year);
    }
}
