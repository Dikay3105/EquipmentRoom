/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
*/
package Springweb.entity;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "thongtinsd")
public class ThongTinSD {
    @OneToOne
    @JoinColumn(name = "MaTV")
    private ThanhVien thanhVien;

    @OneToOne
    @JoinColumn(name = "MaTB")
    private ThietBi thietBi;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MaTT;

    @Temporal(TemporalType.TIMESTAMP)
    private Date TGVao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date TGMuon;

    @Temporal(TemporalType.TIMESTAMP)
    private Date TGTra;

    @Temporal(TemporalType.TIMESTAMP)
    private Date TGDatcho;

    @Column(name = "maTV", insertable = false, updatable = false)
    private Integer maTV;
    @Column(name = "maTB", insertable = false, updatable = false)
    private Integer maTB;

    public Integer getMaTT() {
        return MaTT;
    }

    public void setMaTT(Integer MaTT) {
        this.MaTT = MaTT;
    }

    public Date getTGVao() {
        return TGVao;
    }

    public void setTGVao(Date TGVao) {
        this.TGVao = TGVao;
    }

    public Date getTGMuon() {
        return TGMuon;
    }

    public void setTGMuon(Date TGMuon) {
        this.TGMuon = TGMuon;
    }

    public Date getTGTra() {
        return TGTra;
    }

    public void setTGTra(Date TGTra) {
        this.TGTra = TGTra;
    }

    public Date getTGDatcho() {
        return TGDatcho;
    }

    public void setTGDatcho(Date TGDatcho) {
        this.TGDatcho = TGDatcho;
    }

    public Integer getMaTV() {
        return maTV;
    }

    public void setMaTV(Integer MaTV) {
        this.maTV = MaTV;
    }

    public Integer getMaTB() {
        return maTB;
    }

    public void setMaTB(Integer MaTB) {
        this.maTB = MaTB;
    }

    public ThanhVien getThanhVien() {
        return thanhVien;
    }

    public void setThanhVien(ThanhVien thanhVien) {
        this.thanhVien = thanhVien;
    }

    public ThietBi getThietBi() {
        return thietBi;
    }

    public void setThietBi(ThietBi thietBi) {
        this.thietBi = thietBi;
    }
}
