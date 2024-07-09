/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Springweb.repository;

import Springweb.entity.ThietBi;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ThietBiRepository extends CrudRepository<ThietBi,Integer>{
    @Modifying
    @Transactional
    
    // Thêm phương thức để lấy mã tiếp theo được tăng
    @Query(value = "SELECT MAX(maXL) FROM ThietBi", nativeQuery = true)
    Integer getNextAutoIncrementValue();
    
}
