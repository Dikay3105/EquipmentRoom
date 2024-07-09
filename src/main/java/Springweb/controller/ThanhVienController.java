/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Springweb.controller;

import Springweb.entity.ThanhVien;
import Springweb.entity.ThongTinSD;
import Springweb.entity.XuLy;
import Springweb.service.ThanhVienService;
import Springweb.service.ThongTinSDService;
import Springweb.service.XuLyService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vi Hao
 */
@Controller
public class ThanhVienController {
    @Autowired
    private ThanhVienService thanhVienService;
    @Autowired
    private ThongTinSDService thongTinSDService;
    @Autowired
    private XuLyService xuLyService;

    @GetMapping("/thanh-vien/add")
    public String register_admin(Model m) {
        return "thanhvien_add";

    }

    @GetMapping("/thanh-vien/forgot-password")
    public String forgotPassword(Model m) {
        return "forgotpassword";
    }

    @GetMapping("/user/login")
    public String login(Model m) {
        return "login";
    }

    @GetMapping("/user/signup")
    public String register(Model m) {
        return "signup";

    }

    @PostMapping("/thanh-vien/forgot-password")
    public ResponseEntity<?> forgotPassword(Model m, @RequestBody ThanhVien tv) {
        boolean isExist = false;
        ThanhVien userRequest = null;
        Iterable<ThanhVien> list = thanhVienService.findAll();
        for (ThanhVien tmp : list) {
            if (tv.getEmail().equals(tmp.getEmail())) {
                userRequest = tmp;
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email không tồn tại");
        }
        // Recipient's email ID needs to be mentioned.
        String to = tv.getEmail();

        // Sender's email ID needs to be mentioned
        String from = "hybridcnpm@gmail.com";

        // Assuming you are sending email from localhost
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("hybridcnpm@gmail.com", "hvqswllsukieudcv");
            }
        });

        try {
            Random random = new Random();
            int randomNumber = random.nextInt(90000000) + 10000000;
            userRequest.setPassword(String.valueOf(randomNumber));
            thanhVienService.update(userRequest);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Quan ly thanh vien !");
            String htmlContent = "<h2>Hi " + userRequest.getHoten()
                    + "</h2><h3>Chúng tôi nhận được yêu cầu thay đổi mật khẩu từ bạn " + tv.getEmail()
                    + "</h3><p>Mật khẩu mới của bạn là: <strong> " + randomNumber + "</strong></p>";
            message.setContent(htmlContent, "text/html;charset=utf-8");

            Transport.send(message);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Gửi mail thành công !");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi máy chủ !");

        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<ThanhVien> login(@RequestBody ThanhVien tv) {
        Iterable<ThanhVien> listTv = thanhVienService.findAll();
        for (ThanhVien tmp : listTv) {

            System.out.println(tmp.getMaTV() + " " + tmp.getPassword());
            if (tv.getMaTV().equals(tmp.getMaTV()) && tv.getPassword().equals(tmp.getPassword())) {
                System.out.println(tmp.getMaTV() + " " + tmp.getPassword());
                return ResponseEntity.ok(tmp);

            }

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/thanh-vien/save")
    public ResponseEntity<?> saveThanhvien(@RequestBody ThanhVien tv) {
        Iterable<ThanhVien> list = thanhVienService.findAll();
        for (ThanhVien tmp : list) {
            if (tv.getEmail().equalsIgnoreCase(tmp.getEmail())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST) // or HttpStatus.BAD_REQUEST if it's due to client
                        .body("Email đã tồn tại");
            }
            if (tv.getMaTV().equals(tmp.getMaTV())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST) // or HttpStatus.BAD_REQUEST if it's due to client
                        .body("Mã thành viên đã tồn tại");
            }
        }
        ThanhVien t = new ThanhVien();
        t.setMaTV(tv.getMaTV());
        t.setHoten(tv.getHoten());
        t.setKhoa(tv.getKhoa());
        t.setNganh(tv.getNganh());
        t.setSDT(tv.getSDT());
        t.setEmail(tv.getEmail());
        t.setPassword(tv.getPassword());
        try {
            thanhVienService.add(t);
            return ResponseEntity.ok().body("Redirect:/thanh-vien/add");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // or HttpStatus.BAD_REQUEST if it's due to client error
                    .body("Thêm thất bại: " + e.getMessage());
        }

    }

    @PostMapping("/thanh-vien/update")
    public String update(Model model, @RequestBody ThanhVien thanhVien) {
        // Optional<ThanhVien> thanhvien = thanhVienService.findById(tv.getMaTV());
        // ThanhVien t;
        // t = thanhvien.get();
        // t.setEmail(tv.getEmail());
        // t.setHoTen(tv.getHoTen());
        // t.setKhoa(tv.getKhoa());
        // t.setNganh(tv.getNganh());
        // t.setSDT(tv.getSDT());
        // t.setPassword(tv.getPassword());
        thanhVienService.update(thanhVien);
        return "redirect:/thanh-vien/all";

    }

    @GetMapping("/thanh-vien/all")
    public String showAllThanhVien(Model m) {
        Iterable<ThanhVien> list = thanhVienService.findAll();
        String item_id = "";
        for (ThanhVien tv : list) {
            String id = Integer.toString(tv.getMaTV()).substring(1, 3);
            item_id = id;
            break;
        }
        m.addAttribute("item_id", item_id);
        m.addAttribute("data", list);
        return "thanhvien_all";

    }

    @GetMapping(value = { "thanh-vien/edit/{id}" })
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<ThanhVien> tv = thanhVienService.findById(id);
        tv.ifPresent(thanhvien -> model.addAttribute("thanhvien", thanhvien));
        // model.addAttribute("customer", cus);
        return "thanhvien_edit";
    }

    @GetMapping(value = { "thanh-vien/delete/{id}" })
    public String delete(@PathVariable("id") int id) {
        thanhVienService.deleteAllById(id);
        return "redirect:/thanh-vien/all";
    }

    @GetMapping(value = "/thanh-vien/delete-with-condition/{selectedValue}")
    public String deleteWithCondition(Model m, @PathVariable("selectedValue") String selectedValue) {
        Iterable<ThanhVien> list = thanhVienService.findAll(); // Get all ThanhVien objects
        List<ThanhVien> data = new ArrayList<>();
        List<String> cbbList = new ArrayList<String>();

        for (ThanhVien tv : list) {
            String id = Integer.toString(tv.getMaTV()).substring(1, 3);
            if (!cbbList.contains(id)) {
                cbbList.add(id);

            }
        }

        if (!selectedValue.isEmpty() || selectedValue != null) {
            data.clear(); // Clear the data list if a selection is made
            list.forEach(thanhVien -> {
                String id = Integer.toString(thanhVien.getMaTV()).substring(1, 3);
                // System.out.print("id: " + id);

                if (id.equals(selectedValue)) {
                    // System.out.print("id: " + id);
                    data.add(thanhVien); // Add matching ThanhVien objects to the data list
                }
            });
        } else {
            return "thanhvien_all";
        }

        m.addAttribute("cbbList", cbbList);
        m.addAttribute("data", data);
        return "thanhvien_deleteWithCondition";
    }

    @GetMapping(value = "/thanh-vien/delete-with-condition/delete-all/{selectedValue}")
    public String deleteAllCondition(Model m, @PathVariable("selectedValue") String selectedValue) {
        Iterable<ThanhVien> list = thanhVienService.findAll();
        if (!selectedValue.isEmpty() || selectedValue != null) {
            list.forEach(thanhVien -> {
                String id = Integer.toString(thanhVien.getMaTV()).substring(1, 3);
                if (id.equals(selectedValue)) {
                    thanhVienService.deleteAllById(thanhVien.getMaTV());
                }
            });
        }
        return "redirect:/thanh-vien/all";
    }

    @GetMapping("thanh-vien/join-area")
    public String joinArea(Model m) {
        m.addAttribute("title", "Nhập mã thành viên để vào khu vực học tập");
        return "join_area";
    }

    @PostMapping("thanh-vien/join-area/{id}")
    public ResponseEntity<?> checkJoinArea(@PathVariable("id") String id) {
        Integer matv = Integer.valueOf(id);
        Optional<ThanhVien> thanhVienOptional = thanhVienService.findById(matv);

        // Check if the Optional contains a ThanhVien object
        if (thanhVienOptional.isPresent()) {
            ThanhVien thanhVien = thanhVienOptional.get();
            Map mergedObject = new HashMap<>();

            // check vi pham
            Iterable<XuLy> list = xuLyService.findAll();
            XuLy xuly = null;

            if (list != null) {
                for (XuLy item : list) {
                    if (item.getMaTV().equals(thanhVien.getMaTV()) && item.getTrangThaiXL().equals(0)) {
                        xuly = item;
                        break;
                    }
                }
                if (xuly != null) {
                    mergedObject.put("thanhVien", thanhVien); // Convert ThanhVien to a map
                    mergedObject.put("xuLy", xuly); // Convert ThanhVien to a map
                    return ResponseEntity.ok(mergedObject);
                }
            }

            LocalDateTime curDatetime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatedDateTime = curDatetime.format(formatter);

            mergedObject.put("thanhVien", thanhVien); // Convert ThanhVien to a map
            mergedObject.put("dateJoin", formatedDateTime);

            LocalDateTime localDateTime = LocalDateTime.parse(formatedDateTime, formatter);
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);

            ThongTinSD ttsd = new ThongTinSD();
            ttsd.setThanhVien(thanhVien);
            ttsd.setTGVao(date);

            thongTinSDService.add(ttsd);

            return ResponseEntity.ok(mergedObject);
        } else {
            // If the ThanhVien object does not exist, return a NOT_FOUND status
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mã thành viên không tồn tại");
        }
    }

    @GetMapping("thanh-vien/search")
    public ResponseEntity<List<ThanhVien>> searchThanhVien() {
        List<ThanhVien> results = thanhVienService.listForSearch();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/user/infor")
    public String user_infor() {
        return "user_infor";
    }

    @GetMapping("/user/infor/changePassword")
    public String changePassword() {
        return "user_changePassword";
    }

    @PostMapping("/user/update")
    public ResponseEntity<String> updateUser(@RequestBody ThanhVien data) {
        try {
            thanhVienService.update(data);

            return ResponseEntity.ok("Thông tin thành viên đã được cập nhật thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi xảy ra khi cập nhật thông tin thành viên!");
        }
    }

    @PostMapping("/user/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestBody) {
        Integer maTV = Integer.parseInt(requestBody.get("maTV"));
        String matKhauCu = requestBody.get("matKhauCu");
        String matKhauMoi = requestBody.get("matKhauMoi");

        ThanhVien userPassUpdate = thanhVienService.findByMaTV(maTV);
        try {
            // Kiểm tra xem mật khẩu cũ có trùng khớp không
            if (!userPassUpdate.getPassword().equals(matKhauCu)) {
                return ResponseEntity.badRequest().body("Mật khẩu cũ không đúng!");
            }

            // Cập nhật mật khẩu mới

            userPassUpdate.setPassword(matKhauMoi);
            thanhVienService.update(userPassUpdate);

            return ResponseEntity.ok("Mật khẩu đã được thay đổi thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi đổi mật khẩu!");
        }
    }

    @GetMapping("/api/get-userInfo")
    public ResponseEntity<ArrayList<ThanhVien>> getUserInfo(@RequestParam Integer maTV) {
        Iterable<ThanhVien> thanhvien = thanhVienService.findAll();
        ArrayList<ThanhVien> results = new ArrayList<ThanhVien>();

        for (ThanhVien tv : thanhvien) {
            if (tv.getMaTV().equals(maTV)) {
                results.add(tv);
            }
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping("/user/vipham")
    public String showViPham() {
        return "user_viewStatus";
    }

    @GetMapping("/user/dangmuon")
    public String showMuon() {
        return "user_viewItems";
    }

    @GetMapping("/api/get-userViPham")
    public ResponseEntity<ArrayList<XuLy>> getUserViPham(@RequestParam Integer maTV, @RequestParam String search) {
        Iterable<XuLy> xuly = xuLyService.findAll();
        ArrayList<XuLy> results = new ArrayList<XuLy>();

        for (XuLy xl : xuly) {
            if (xl.getMaTV().equals(maTV)) {
                if (xl.getHinhThucXL().toLowerCase().contains(search.toLowerCase()) ||
                        xl.getMaTV().toString().toLowerCase().contains(search.toLowerCase()) ||
                        xl.getMaXL().toString().toLowerCase().contains(search.toLowerCase()) ||
                        xl.getTrangThaiXL().toString().toLowerCase().contains(search.toLowerCase())) {
                    results.add(xl);
                }
            }
        }
        return ResponseEntity.ok(results);
    }

}
