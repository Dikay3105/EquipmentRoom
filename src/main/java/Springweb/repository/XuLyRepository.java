/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Springweb.repository;

import Springweb.entity.XuLy;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface XuLyRepository extends CrudRepository<XuLy, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM XuLy x WHERE x.maTV = :maTV")
    void deleteByMaTV(@Param("maTV") Integer maTV);
    
    @Query("SELECT x FROM XuLy x") // Truy vấn SELECT * FROM XuLy
    List<XuLy> findAll();
    
    // Thêm phương thức để lấy mã tiếp theo được tăng
    @Query(value = "SELECT MAX(maXL) FROM XuLy", nativeQuery = true)
    Integer getNextAutoIncrementValue();

}
