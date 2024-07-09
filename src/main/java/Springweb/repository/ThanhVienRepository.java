/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Springweb.repository;

import Springweb.entity.ThanhVien;
import Springweb.entity.XuLy;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ThanhVienRepository extends CrudRepository<ThanhVien, Integer> {
  // @Modifying
  // @Transactional
  // @Query("DELETE FROM XuLy x WHERE x.thanhVien.id = :id")
  // void deleteXuLyByThanhVienId(@Param("id") Integer id);
  //
  // @Modifying
  // @Transactional
  // @Query("DELETE FROM ThongTinSD t WHERE t.thanhVien.id = :id")
  // void deleteThongTinSDByThanhVienId(@Param("id") Integer id);
  //
  // @Modifying
  // @Query("SELECT FROM ThanhVien t WHERE t.id = :id")
  // void deleteThanhVienById(@Param("id") Integer id);


  @Query("SELECT t FROM ThanhVien t WHERE t.MaTV = :MaTV")
  ThanhVien findByMaTV(@Param("MaTV") Integer MaTV);

}
