/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Springweb.controller;

import Springweb.entity.ThietBi;
import Springweb.entity.ThongTinSD;
import Springweb.entity.XuLy;
import Springweb.service.ThietBiService;
import Springweb.service.ThongTinSDService;
import Springweb.service.XuLyService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Vi Hao
 */
@Controller
public class ThietBiController {
    @Autowired
    private ThietBiService thietBiService;
    @Autowired
    private ThongTinSDService thongTinSdService;
    @Autowired
    private XuLyService xuLyService;

    public boolean KiemTraThanhVienViPham(int matv) {
        List<XuLy> list = (List) xuLyService.findAll();
        for (XuLy xl : list) {
            if (xl.getMaTV() != null)
                if (xl.getMaTV() == matv && xl.getTrangThaiXL() == 0)
                    return true;
        }
        return false;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public boolean KiemTraThietBiChuaMuon(int matb) {
        List<ThongTinSD> list = (List) thongTinSdService.findAll();
        for (ThongTinSD tt : list) {
            if (tt.getMaTB() != null)
                if (tt.getMaTB() == matb
                        && ((tt.getTGTra() == null && tt.getTGMuon() != null)
                                || (tt.getTGDatcho() != null
                                        && isSameDay(tt.getTGDatcho(), new Date()))))
                    return false;
        }
        return true;
    }

    public boolean KiemTraThanhVienDaDatChoThietBi(int matv, int matb) {
        List<ThongTinSD> list = (List) thongTinSdService.findAll();
        for (ThongTinSD tt : list) {
            if (tt.getMaTB() != null)
                if (tt.getMaTB() == matb && tt.getMaTV() == matv && tt.getTGDatcho() != null
                        && tt.getTGDatcho().after(new Date()))
                    return true;
        }
        return false;
    }

    public boolean KiemtraThietBiDaMuon(int matv, int matb) {
        List<ThongTinSD> list = (List) thongTinSdService.findAll();
        for (ThongTinSD tt : list) {
            if (tt.getMaTB() != null)
                if (tt.getMaTB() == matb && tt.getMaTV() == matv && tt.getTGMuon() != null && tt.getTGTra() == null)
                    return true;
        }
        return false;
    }

    @GetMapping("/api/get-devices")
    public ResponseEntity<ArrayList<ThietBi>> getDevices(@RequestParam String status, @RequestParam String matv,
            @RequestParam String search) {
        Iterable<ThietBi> devices = thietBiService.findAll();
        ArrayList<ThietBi> results = new ArrayList<ThietBi>();
        if ("muon".equals(status)) {
            for (ThietBi tb : devices) {
                if (KiemTraThietBiChuaMuon(tb.getMaTB()) && !KiemTraThanhVienViPham(Integer.parseInt(matv)))
                    if (tb.getTenTB().toLowerCase().contains(search.toLowerCase()) ||
                            tb.getMaTB().toString().toLowerCase().contains(search.toLowerCase()))
                        results.add(tb);
            }
        } else if ("datcho".equals(status)) {
            for (ThietBi tb : devices) {
                if (!KiemTraThanhVienDaDatChoThietBi(Integer.parseInt(matv), tb.getMaTB())
                        && KiemTraThietBiChuaMuon(tb.getMaTB()) && !KiemTraThanhVienViPham(Integer.parseInt(matv)))
                    if (tb.getTenTB().toLowerCase().contains(search.toLowerCase()) ||
                            tb.getMaTB().toString().toLowerCase().contains(search.toLowerCase()))
                        results.add(tb);
            }
        } else {
            for (ThietBi tb : devices) {
                if (KiemtraThietBiDaMuon(Integer.parseInt(matv), tb.getMaTB()))
                    if (tb.getTenTB().toLowerCase().contains(search.toLowerCase()) ||
                            tb.getMaTB().toString().toLowerCase().contains(search.toLowerCase()))
                        results.add(tb);
            }
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping("/api/get-devices-damuon")
    public ResponseEntity<ArrayList<ThongTinSD>> getUserViPham(@RequestParam Integer maTV) {
        Iterable<ThongTinSD> thongtin = thongTinSdService.findAll();
        ArrayList<ThongTinSD> results = new ArrayList<ThongTinSD>();

        for (ThongTinSD tt : thongtin) {
            if (tt.getMaTV().equals(maTV) && tt.getMaTB() != null && tt.getTGMuon() != null && tt.getTGTra() == null) {
                results.add(tt);
            }
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping("/api/get-all-devices")
    @ResponseBody
    public ArrayList<ThietBi> getAllDevices() {
        Iterable<ThietBi> devices = thietBiService.findAll();
        ArrayList<ThietBi> results = new ArrayList<ThietBi>();
        for (ThietBi tb : devices) {
            results.add(tb);
        }
        return results;
    }

    @GetMapping("/thietbi")
    public String showAllThietBi(Model m) {
        Iterable<ThietBi> list = thietBiService.findAll();
        String item_id = "";
        for (ThietBi tb : list) {
            String id = Integer.toString(tb.getMaTB()).substring(1, 3);
            item_id = id;
            break;
        }
        m.addAttribute("item_id", item_id);
        m.addAttribute("data", list);
        return "thietbi";
    }

    @GetMapping(value = { "thietbi/delete/{id}" })
    public String delete(@PathVariable("id") int id) {
        thietBiService.deleteAllById(id);
        return "redirect:/thietbi";
    }

    @GetMapping("thietbi/edit/{id}")
    public String editThietBi(@PathVariable Integer id, Model model) {
        Optional<ThietBi> optionalThietBi = thietBiService.findById(id);
        optionalThietBi.ifPresent(thietBi -> model.addAttribute("thietbi", thietBi));
        return "thietbi_edit";
    }

    @GetMapping("thietbi/search")
    public ResponseEntity<List<ThietBi>> searchThietBi() {
        List<ThietBi> results = thietBiService.listForSearch();
        return ResponseEntity.ok(results);
    }

    @GetMapping("thietbi/add")
    public String addThietbi(Model model) {

        return "thietbi_add";
    }

    @PostMapping("thietbi/update")
    public ResponseEntity<String> updateThietBi(@RequestBody ThietBi thietbi) {
        try {
            thietBiService.update(thietbi);
            return new ResponseEntity<>("redirect:/thietbi", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lưu dữ liệu không thành công: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("thietbi/save")
    public ResponseEntity<String> saveThietB(@RequestBody ThietBi thietbi) {
        try {
            thietBiService.add(thietbi);
            return new ResponseEntity<>("redirect:/thietbi", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lưu dữ liệu không thành công: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("thietbi/saveExcel")
    public ResponseEntity<String> saveThietBi(@RequestBody ThietBi thietbi) {
        System.out.println(thietbi.getMaTB());
        Iterable<ThietBi> list = thietBiService.findAll();
        for (ThietBi tb : list) {
            if (tb.getMaTB().equals(thietbi.getMaTB())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Mã thiết bị trùng !");
            }
        }
        try {
            thietBiService.add(thietbi);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Thêm thiết bị thành công !");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi máy chủ !");
        }
    }

}
