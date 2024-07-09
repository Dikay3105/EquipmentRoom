/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Springweb.service;

import Springweb.entity.ThongTinSD;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vi Hao
 */
@Service
public interface ThongTinSDService {
    @Autowired
    Iterable<ThongTinSD> findAll();
    List<ThongTinSD> search(String term);
    ThongTinSD add(ThongTinSD tv);
    ThongTinSD update(ThongTinSD tv);
    Optional<ThongTinSD> findById(Integer id);
    void deleteByMaTV(Integer id);
    public int getCountTVAll();
    public ArrayList<Object[]> getCountTVTheoKhoa();
    public ArrayList<Object[]> getCountTVTheoNganh();
    public ArrayList<Object[]> getCountTVTheoNgay(Date date);
    public ArrayList<Object[]> getCountTVTheoThang(int month, int year);
    public ArrayList<Object[]> getCountTVTheoNam(int year);
    public ArrayList<Object[]> getCountTVTheoKhoang(Date date1, Date date2);
    public ArrayList<Object[]> getCountTBTheoKhoang(Date date1, Date date2);
    public ArrayList<ThongTinSD> getThietBiDangMuon();
    public ArrayList<ThongTinSD> getThietBiDangMuonTrongNgay(Date date);
    public ArrayList<ThongTinSD> getThietBiDangMuonTrongKhoangThoiGian(Date startDate, Date endDate);
    public ArrayList<ThongTinSD> getThietBiDangMuonTrongThang(int year, int month);
    public ArrayList<ThongTinSD> getThietBiDangMuonTrongNam(int year);

}
