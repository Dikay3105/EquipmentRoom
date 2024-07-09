/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Springweb.entity;

import javax.persistence.*;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "thietbi") // should equal to table name in db
public class ThietBi {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MaTB;

    private String TenTB;
    private String MotaTB;

    public Integer getMaTB() {
        return MaTB;
    }

    public void setMaTB(Integer MaTB) {
        this.MaTB = MaTB;
    }

    public String getTenTB() {
        return TenTB;
    }

    public void setTenTB(String TenTB) {
        this.TenTB = TenTB;
    }

    public String getMoTaTB() {
        return MotaTB;
    }

    public void setMoTaTB(String MoTaTB) {
        this.MotaTB = MoTaTB;
    }

}
