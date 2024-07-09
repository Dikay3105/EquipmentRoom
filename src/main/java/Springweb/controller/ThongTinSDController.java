/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Springweb.controller;

import Springweb.entity.ThanhVien;
import Springweb.entity.ThietBi;
import Springweb.entity.ThongTinSD;
import Springweb.service.ThanhVienService;
import Springweb.service.ThietBiService;
import Springweb.service.ThongTinSDService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Vi Hao
 */
@Controller
public class ThongTinSDController {
    @Autowired
    private ThanhVienService thanhVienService;
    @Autowired
    private ThietBiService thietBiService;

    @Autowired
    private ThongTinSDService thongTinSDService;

    // private static final Logger logger =
    // LoggerFactory.getLogger(ThongTinSDController.class);

    @GetMapping("/user/datcho")
    public String datCho_user() {
        return "datcho_user";
    }

    @PostMapping("/user/datcho/save")
    public ModelAndView handleMuonThietBiDatCho_user(
            @RequestParam("matv") String matv,
            @RequestParam("ngaydatcho") String ngaydatcho,
            @RequestParam(value = "thietBiCheckbox", required = false) List<String> danhSachThietBiIds) {
        System.out.println(ngaydatcho);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        ThanhVien thanhVien = null;
        for (ThanhVien tv : thanhVienService.findAll()) {
            if (tv.getMaTV() == Integer.parseInt(matv)) {
                thanhVien = tv;
                break;
            }
        }
        for (String thietBiId : danhSachThietBiIds) {
            ThietBi thietBi = null;
            for (ThietBi tb : thietBiService.findAll()) {
                if (tb.getMaTB() == Integer.parseInt(thietBiId)) {
                    thietBi = tb;
                    break;
                }
            }
            ThongTinSD ttsd = new ThongTinSD();
            ttsd.setThanhVien(thanhVien);
            ttsd.setThietBi(thietBi);
            ttsd.setTGVao(new Date());
            ttsd.setTGMuon(null);
            ttsd.setTGTra(null);

            try {
                ttsd.setTGDatcho(dateFormat.parse(ngaydatcho));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            thongTinSDService.add(ttsd);
        }
        return new ModelAndView("redirect:/user/datcho");

    }

    @GetMapping("/quanlydatcho")
    public String danhSachDatCho(Model m) {
        Iterable<ThongTinSD> list = thongTinSDService.findAll();
        ArrayList<ThongTinSD> results = new ArrayList<ThongTinSD>();
        for (ThongTinSD tt : list)
            if (tt.getTGMuon() == null && tt.getMaTB() != null && tt.getTGDatcho() != null)
                results.add(tt);
        m.addAttribute("data", results);
        return "datcho_admin";
    }

    @GetMapping("/user/danhsachdatcho")
    public String datCho(Model m, @RequestParam String matv) {
        Iterable<ThongTinSD> list = thongTinSDService.findAll();
        ArrayList<ThongTinSD> results = new ArrayList<ThongTinSD>();
        for (ThongTinSD tt : list)
            if (tt.getTGMuon() == null && tt.getMaTB() != null && tt.getTGDatcho() != null
                    && tt.getMaTV() == Integer.parseInt(matv))
                results.add(tt);
        m.addAttribute("data", results);
        return "danhsachdatcho";
    }

    @GetMapping("/quanlydatcho/save")
    public ModelAndView handleMuonThietBiDatCho(
            @RequestParam("matv") String matv,
            @RequestParam("matb") String matb) {
        for (ThongTinSD tt : thongTinSDService.findAll()) {
            if (tt.getMaTB() != null)
                if (tt.getMaTV() == Integer.parseInt(matv)
                        && tt.getMaTB() == Integer.parseInt(matb)) {
                    tt.setTGMuon(new Date());
                    thongTinSDService.update(tt);
                }
        }
        return new ModelAndView("redirect:/quanlydatcho");
    }

    public void xoaDatCho(String matv, String matb) {
        for (ThongTinSD tt : thongTinSDService.findAll()) {
            if (tt.getMaTB() != null)
                if (tt.getMaTV() == Integer.parseInt(matv)
                        && tt.getMaTB() == Integer.parseInt(matb)) {
                    tt.setTGDatcho(null);
                    thongTinSDService.update(tt);
                }
        }
    }

    @GetMapping("/quanlydatcho/delete")
    public ModelAndView handleXoaThietBiDatCho_Admin(
            @RequestParam("matv") String matv,
            @RequestParam("matb") String matb) {
        xoaDatCho(matv, matb);
        return new ModelAndView("redirect:/quanlydatcho");
    }

    @GetMapping("/user/datcho/delete")
    public String handleXoaThietBiDatCho_User(
            @RequestParam("matv") String matv,
            @RequestParam("matb") String matb) {
        xoaDatCho(matv, matb);
        return "redirect:/user/danhsachdatcho?matv=" + matv;
    }

    @GetMapping("/muontra")
    public String muonTra(Model m) {
        ArrayList<ThanhVien> tvList = (ArrayList) thanhVienService.findAll();
        m.addAttribute("tvList", tvList);
        return "muontra";
    }

    @GetMapping("/danhsachmuontra")
    public String danhSachMuonTra(Model m) {
        return "danhsachmuontra";
    }

    @GetMapping("/api/search-thongtinsd")
    public ResponseEntity<ArrayList<ThongTinSD>> getDevices(@RequestParam String search) {
        Iterable<ThongTinSD> list = thongTinSDService.findAll();
        ArrayList<ThongTinSD> results = new ArrayList<ThongTinSD>();
        for (ThongTinSD tt : list) {
            if (tt.getTGMuon() != null) {
                if (tt.getMaTT().toString().contains(search.toLowerCase()) ||
                        tt.getMaTB().toString().contains(search.toLowerCase()) ||
                        tt.getMaTV().toString().contains(search.toLowerCase()) ||
                        tt.getThanhVien().getHoten().toLowerCase().contains(search.toLowerCase()) ||
                        tt.getThietBi().getTenTB().toLowerCase().contains(search.toLowerCase()))
                    results.add(tt);
            }
        }
        return ResponseEntity.ok(results);
    }

    @PostMapping("/muontra/save")
    public ModelAndView handleMuonTra(
            @RequestParam("inlineRadioOptions") String action,
            @RequestParam("tv-select") String maThanhVien,
            @RequestParam(value = "thietBiCheckbox", required = false) List<String> danhSachThietBiIds) {
        if ("muon".equals(action)) {
            if (danhSachThietBiIds != null) {
                ThanhVien thanhVien = null;
                for (ThanhVien tv : thanhVienService.findAll()) {
                    if (tv.getMaTV() == Integer.parseInt(maThanhVien)) {
                        thanhVien = tv;
                        break;
                    }
                }
                for (String thietBiId : danhSachThietBiIds) {
                    // Xử lý từng thiết bị được chọn
                    ThietBi thietBi = null;
                    for (ThietBi tb : thietBiService.findAll()) {
                        if (tb.getMaTB() == Integer.parseInt(thietBiId)) {
                            thietBi = tb;
                            break;
                        }
                    }
                    ThongTinSD ttsd = new ThongTinSD();
                    ttsd.setThanhVien(thanhVien);
                    ttsd.setThietBi(thietBi);
                    ttsd.setTGVao(new Date());
                    ttsd.setTGMuon(new Date());
                    ttsd.setTGTra(null);
                    ttsd.setTGDatcho(null);
                    thongTinSDService.add(ttsd);
                }
            }
        } else if ("tra".equals(action)) {
            for (ThongTinSD tt : thongTinSDService.findAll()) {
                if (tt.getMaTB() != null)
                    for (String thietBiId : danhSachThietBiIds)
                        if (tt.getMaTV() == Integer.parseInt(maThanhVien)
                                && tt.getMaTB() == Integer.parseInt(thietBiId)) {
                            tt.setTGTra(new Date());
                            thongTinSDService.update(tt);
                        }
            }

        }
        return new ModelAndView("redirect:/muontra");
    }

    @GetMapping("/thong-ke/thanh-vien/getCountAll")
    @ResponseBody
    public int getCountTVAll() {
        return thongTinSDService.getCountTVAll();
    }

    @GetMapping("/thong-ke/thanh-vien/khoa")
    @ResponseBody
    public ArrayList<Object[]> getCountTVTheoKhoa() {
        return thongTinSDService.getCountTVTheoKhoa();
    }

    @GetMapping("/thong-ke/thanh-vien/nganh")
    @ResponseBody
    public ArrayList<Object[]> getCountTVTheoNganh() {
        return thongTinSDService.getCountTVTheoNganh();
    }

    @GetMapping(value = { "thong-ke/thanh-vien/day" })
    @ResponseBody
    public ArrayList<Object[]> getCountTVTheoNgay(@RequestParam("date") String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateString);
            return thongTinSDService.getCountTVTheoNgay(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @GetMapping(value = { "thong-ke/thiet-bi/khoang" })
    @ResponseBody
    public ArrayList<Object[]> getCountTVTheoNgay(@RequestParam("date1") String date1,
            @RequestParam("date2") String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(date1);
            Date dateClone = sdf.parse(date2);
            return thongTinSDService.getCountTBTheoKhoang(date, dateClone);
        } catch (ParseException e) {
            // Xử lý khi không thể chuyển đổi chuỗi thành ngày
            e.printStackTrace();
            return new ArrayList<>(); // hoặc trả về một giá trị mặc định khác nếu cần
        }
    }

    @GetMapping("/thong-ke/thanh-vien/month")
    @ResponseBody
    public ArrayList<Object[]> getCountTVTheoThang(@RequestParam("month") int month,
            @RequestParam("year") int year) {
        return thongTinSDService.getCountTVTheoThang(month, year);
    }

    @GetMapping("/thong-ke/thanh-vien/year")
    @ResponseBody
    public ArrayList<Object[]> getCountTVTheoNam(@RequestParam("year") int year) {
        return thongTinSDService.getCountTVTheoNam(year);
    }

    @GetMapping(value = { "thong-ke/thanh-vien/khoang" })
    @ResponseBody
    public ArrayList<Object[]> getCountTVTheoKhoang(@RequestParam("date1") String date1,
            @RequestParam("date2") String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(date1);
            Date dateClone = sdf.parse(date2);
            return thongTinSDService.getCountTVTheoKhoang(date, dateClone);
        } catch (ParseException e) {
            // Xử lý khi không thể chuyển đổi chuỗi thành ngày
            e.printStackTrace();
            return new ArrayList<>(); // hoặc trả về một giá trị mặc định khác nếu cần
        }
    }

    @GetMapping("/thong-ke/thiet-bi/getCountByID")
    @ResponseBody
    public Optional<ThongTinSD> getCountTBByID(@RequestParam("month") int ID) {
        return thongTinSDService.findById(ID);
    }

    @GetMapping("/thong-ke")
    public String getCountByHour() {
        return "thongke";
    }

    @GetMapping("/thong-ke/thietbidangmuon/all")
    @ResponseBody
    public Map<String, Object> getThietBiDangMuon() {
        Map<String, Object> response = new HashMap<>();

        List<ThongTinSD> thongTinSDList = thongTinSDService.getThietBiDangMuon();
        List<ThietBi> thietBiList = (List<ThietBi>) thietBiService.findAll();

        response.put("thongTinSDList", thongTinSDList);
        response.put("thietBiList", thietBiList);

        return response;
    }

    @GetMapping("/thong-ke/thietbidangmuon/day")
    @ResponseBody
    public Map<String, Object> getThietBiDangMuonTrongNgay(
            @RequestParam("ngay") int ngay,
            @RequestParam("thang") int thang,
            @RequestParam("nam") int nam) {
        Map<String, Object> response = new HashMap<>();
        String dateString = String.format("%04d-%02d-%02d 00:00:00", nam, thang, ngay);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(dateString); // Chuyển chuỗi thành đối tượng Date
            List<ThongTinSD> thongTinSDList = thongTinSDService.getThietBiDangMuonTrongNgay(date);
            List<ThietBi> thietBiList = (List<ThietBi>) thietBiService.findAll();

            response.put("thongTinSDList", thongTinSDList);
            response.put("thietBiList", thietBiList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("/thong-ke/thietbidangmuon/month")
    @ResponseBody
    public Map<String, Object> getThietBiDangMuonTrongThang(
            @RequestParam("thang") int thang,
            @RequestParam("nam") int nam) {
        Map<String, Object> response = new HashMap<>();
        List<ThongTinSD> thongTinSDList = thongTinSDService.getThietBiDangMuonTrongThang(nam, thang);
        List<ThietBi> thietBiList = (List<ThietBi>) thietBiService.findAll();

        response.put("thongTinSDList", thongTinSDList);
        response.put("thietBiList", thietBiList);
        return response;
    }

    @GetMapping("/thong-ke/thietbidangmuon/year")
    @ResponseBody
    public Map<String, Object> getThietBiDangMuonTrongNam(
            @RequestParam("nam") int nam) {
        Map<String, Object> response = new HashMap<>();
        List<ThongTinSD> thongTinSDList = thongTinSDService.getThietBiDangMuonTrongNam(nam);
        List<ThietBi> thietBiList = (List<ThietBi>) thietBiService.findAll();
        response.put("thongTinSDList", thongTinSDList);
        response.put("thietBiList", thietBiList);
        return response;
    }

    @GetMapping("/thong-ke/thietbidangmuon/fromto")
    @ResponseBody
    public Map<String, Object> getThietBiDangMuonTrongKhoangThoiGian(
            @RequestParam("from") String fromDate,
            @RequestParam("to") String toDate) {
        Map<String, Object> response = new HashMap<>();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fromDateObj = sdf.parse(fromDate);
            Date toDateObj = sdf.parse(toDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(toDateObj);
            calendar.add(Calendar.DATE, 1);
            Date endDate = calendar.getTime();
            List<ThongTinSD> thongTinSDList = thongTinSDService.getThietBiDangMuonTrongKhoangThoiGian(fromDateObj,
                    endDate);
            List<ThietBi> thietBiList = (List<ThietBi>) thietBiService.findAll();

            response.put("thongTinSDList", thongTinSDList);
            response.put("thietBiList", thietBiList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

}
