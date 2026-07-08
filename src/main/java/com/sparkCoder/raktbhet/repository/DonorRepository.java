package com.sparkCoder.raktbhet.repository;

import com.sparkCoder.raktbhet.entity.DonorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<DonorEntity, String> {

        @EntityGraph(attributePaths = "address")
        Optional<DonorEntity> findByEmail(String email);

        boolean existsByEmail(String email);

        @EntityGraph(attributePaths = "address")
        List<DonorEntity> findByBloodGrp(String bloodGrp);

        @EntityGraph(attributePaths = "address")
        List<DonorEntity> findByAddress_City(String city);

        @EntityGraph(attributePaths = "address")
        List<DonorEntity> findByAddress_CityAndAddress_StateAndAddress_Pincode(
                String city,
                String state,
                String pincode
        );

        @EntityGraph(attributePaths = "address")
        Optional<DonorEntity> findByRaktbhetId(Integer raktbhetId);

        Optional<DonorEntity> findTopByOrderByRaktbhetIdDesc();

        @Override
        @EntityGraph(attributePaths = "address")
        Page<DonorEntity> findAll(Pageable pageable);



        @EntityGraph(attributePaths = "address")
        Page<DonorEntity> findByBloodGrpAndAddress_City(
                String bloodGrp,
                String city,
                Pageable pageable
        );



}