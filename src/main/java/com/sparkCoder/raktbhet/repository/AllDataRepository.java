package com.sparkCoder.raktbhet.repository;

import com.sparkCoder.raktbhet.entity.AllDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface AllDataRepository extends JpaRepository<AllDataEntity, Integer> {
        Optional<AllDataEntity> findByUserName(String username);
}
