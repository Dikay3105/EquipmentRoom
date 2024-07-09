package Springweb.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "xuly")
public class XuLy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MaXL;

    private String HinhthucXL;

    private Integer Sotien;
    private Integer TrangthaiXL;
    @Temporal(TemporalType.TIMESTAMP)
    private Date NgayXL;
    
    private Integer maTV;

    public Integer getMaXL() {
        return MaXL;
    }

    public void setMaXL(Integer MaXL) {
        this.MaXL = MaXL;
    }

    public String getHinhThucXL() {
        return HinhthucXL;
    }

    public void setHinhThucXL(String HinhThucXL) {
        this.HinhthucXL = HinhThucXL;
    }

    public Integer getSoTien() {
        return Sotien;
    }

    public void setSoTien(Integer SoTien) {
        this.Sotien = SoTien;
    }

    public Date getNgayXL() {
        return NgayXL;
    }

    public void setNgayXL(Date NgayXL) {
        this.NgayXL = NgayXL;
    }

    public Integer getTrangThaiXL() {
        return TrangthaiXL;
    }

    public void setTrangThaiXL(Integer TrangThaiXL) {
        this.TrangthaiXL = TrangThaiXL;
    }

    public Integer getMaTV() {
        return maTV;
    }

    public void setMaTV(Integer MaTV) {
        this.maTV = MaTV;
    }

    
}
