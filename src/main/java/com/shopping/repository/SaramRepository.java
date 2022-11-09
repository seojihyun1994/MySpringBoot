package com.shopping.repository;

import com.shopping.entity.Saram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaramRepository extends JpaRepository<Saram, String>, QuerydslPredicateExecutor<Saram> {
    // 방법 01 : Query 메소드 사용

    // 이름 순으로 정렬
    List<Saram> findByOrderByNameAsc() ;

    // 거주지가 마포인 사람 조회
    List<Saram> findByAddressEquals(String address) ;

    // 급여가 높은 순으로 조회
    List<Saram> findByOrderBySalaryDesc() ;

    // 방법 02 : @Query 어노테이션 사용
    // 급여가 500 이상인 사람
    @Query(value = "select sa from Saram sa where sa.salary >= :salary")
    List<Saram> findBySaramSalaryDetail01(@Param("salary")Integer salary) ;

    @Query(value = "select * from sarams sa where sa.salary >= :salary", nativeQuery = true)
    List<Saram> findBySaramSalaryDetail02(@Param("salary")Integer salary) ;
}
