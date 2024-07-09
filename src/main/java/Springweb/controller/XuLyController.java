package Springweb.controller;

import Springweb.entity.ThanhVien;
import Springweb.entity.XuLy;
import Springweb.service.ThanhVienService;
import Springweb.service.XuLyService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class XuLyController {

    @Autowired
    private XuLyService xuLyService;
    @Autowired
    private ThanhVienService thanhVienService;
        
    @GetMapping("/xuly/all")
    public String showAllXuLy(Model model) {
        Iterable<XuLy> list = xuLyService.findAll();
        model.addAttribute("data", list);
        return "xuly_all";
    }

    
    @GetMapping("/xuly/edit/{id}")
    public String editXuLy(@PathVariable Integer id, Model model) {
        Optional<XuLy> optionalXuLy = xuLyService.findById(id);
        optionalXuLy.ifPresent(xuLy -> model.addAttribute("xuLy", xuLy));

        Iterable<ThanhVien> tvList = thanhVienService.findAll();
        model.addAttribute("thanhvienList", tvList);

        return "xuly_edit";
    }


    @GetMapping("/xuly/add")
    public String addXuLy(Model model) {
        int count = xuLyService.maxID()+1;

        List<ThanhVien> thanhVienList = (List<ThanhVien>) thanhVienService.findAll();

        model.addAttribute("thanhVienList", thanhVienList);
        model.addAttribute("count", count);
        
        Iterable<ThanhVien> tvList = thanhVienService.findAll();
        model.addAttribute("thanhvienList", tvList);

        return "xuly_add";
    }


    @PostMapping("/xuly/save")
    public ResponseEntity<String> saveXuLy(@RequestBody XuLy xuLy) {
        if (xuLy.getMaTV() == null || xuLy.getHinhThucXL() == null || xuLy.getHinhThucXL().trim().isEmpty() || xuLy.getNgayXL() == null) {
            return new ResponseEntity<>("Mã thành viên, hình thức xử lý, và ngày xử lý không được để trống!",
                    HttpStatus.BAD_REQUEST);
        }
        try {
            xuLyService.add(xuLy);
            return new ResponseEntity<>("redirect:/xuly/all", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lưu dữ liệu không thành công: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/api/xuly-data")
    @ResponseBody
    public Iterable<XuLy> getXuLyData() {
        return xuLyService.findAll();
    }
}