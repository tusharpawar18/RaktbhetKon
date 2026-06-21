package com.sparkCoder.raktbhet.repository;
import com.sparkCoder.raktbhet.entity.BankAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAdminRepository extends JpaRepository<BankAdminEntity, Integer> {


    }


