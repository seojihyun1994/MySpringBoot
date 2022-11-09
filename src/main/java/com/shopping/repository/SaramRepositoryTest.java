package com.shopping.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shopping.entity.QSaram;
import com.shopping.entity.Saram;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
public class SaramRepositoryTest {

    @Autowired
    SaramRepository saramRepository ;

    @Test
    @DisplayName("사람 정보 저장 테스트")
    public void createSaramTest(){
        Saram saram = new Saram() ;
        saram.setAddress("용산구 이태원동");
        saram.setSalary(12345);
        saram.setName("이순신");
        saram.setId("lee");

        Saram savedItem = saramRepository.save(saram) ;
        System.out.println(savedItem.toString());
    }

    @Test
    @DisplayName("사람 정보 여러명 추가하기 테스트")
    public void createSaramTestMany(){
        String[] id = {"kim"} ;
        String[] name = {"봄","여름","가을","겨울"} ;
        String[] address = {"마포구","서대문구","중구","용산구"} ;
        int[] salary = {111, 222, 333, 444, 555} ;

        for (int i = 0; i < 10; i++) {
            Saram saram = new Saram() ;
            //setting
            saram.setId(id[0]+i);
            saram.setName(name[i % name.length]);
            saram.setAddress(address[i % address.length]);
            saram.setSalary(salary[i % salary.length]);

            Saram savedBean = this.saramRepository.save(saram) ;
            System.out.println(savedBean.toString());
        }
   }

   @Test
   @DisplayName("사람 이름 순으로 정렬")
   public void findByOrderByNameAsc(){
        List<Saram> itemList = this.saramRepository.findByOrderByNameAsc() ;
       for(Saram saram : itemList){
           System.out.println(saram.toString());
       }
   }

    @Test
    @DisplayName("거주지가 마포인 사람 조회")
    public void findByAddressEquals(){
        String address = "마포구" ;
        List<Saram> itemList = this.saramRepository.findByAddressEquals(address) ;

        for(Saram saram : itemList){
            System.out.println(saram.toString());
        }
    }

    @Test
    @DisplayName("급여가 높은 순으로 조회")
    public void findByOrderBySalaryDesc(){

        List<Saram> itemList = this.saramRepository.findByOrderBySalaryDesc() ;

        for(Saram saram : itemList){
            System.out.println(saram.toString());
        }
    }

    @Test
    @DisplayName("@Query를 사용한 급여 조회 테스트01")
    public void findBySaramSalaryDetail01(){
        Integer salary = 500 ;
        List<Saram> itemList = this.saramRepository.findBySaramSalaryDetail01(salary) ;
        for(Saram saram : itemList){
            System.out.println(saram.toString());
        }
    }

    @Test
    @DisplayName("@Query를 사용한 급여 조회 테스트02")
    public void findBySaramSalaryDetail02(){
        Integer salary = 500 ;
        List<Saram> itemList = this.saramRepository.findBySaramSalaryDetail02(salary) ;
        System.out.println(itemList.size());
        for(Saram saram : itemList){
            System.out.println(saram.toString());
        }
    }

    @Test
    @DisplayName("사람 정보 여러명 추가하기 테스트")
    public void createSaramListNew() {
        String[] id = {"kim"};
        String[] name = {"봄", "여름", "가을", "겨울"};
        String[] address = {"중원구", "소래포구", "마포구", "중구", "용산구"};
        int[] salary = {111, 222, 333, 444, 555, 666, 777};

        for (int i = 1; i <= 30; i++) {
            Saram saram = new Saram();
            //setting
            saram.setId(id[0] + i);
            saram.setName(name[i % name.length]+i);
            saram.setAddress(address[i % address.length]);
            saram.setSalary(salary[i % salary.length]);

            Saram savedBean = this.saramRepository.save(saram);
            System.out.println(savedBean.toString());
        }
    }

    @PersistenceContext // JPA 가 동작하는 가상의 공간(space), 이 어노테이션이 em에게 의미있는 데이터를 주입(injection) 해 줍니다.
    EntityManager em ; // 엔터티 관리자

//    급여가 700미만이고, 주소에 '포'를 포함하는 회원을 조회하되, 이름의 역순으로 출력하는 queryDsl 구문을 작성하고 테스트해보세요.
    @Test
    @DisplayName("query Dsl Test01")
    public void newPersonDslTest01(){
        /*
            select * from sarams
            where address like '%포%'
            and salary < 700
            order by name desc ;
         */

        //JPAQueryFactory 는 쿼리를 만들어 내기 위한 클래스
        JPAQueryFactory queryFactory = new JPAQueryFactory(em) ;

        QSaram qbean = QSaram.saram ;

        int salary = 700 ;
        String address = "포" ;
        JPAQuery<Saram> query = queryFactory
                .selectFrom(qbean)
                .where(qbean.salary.lt(salary))
                .where(qbean.address.like("%"+address+"%"))
                .orderBy(qbean.name.desc()) ;

        List<Saram> itemList = query.fetch() ;
        for (Saram saram : itemList){
            System.out.println(saram.toString());
        }
    }

//    급여가 400미만이고, 주소에 '포'를 포함하는 회원을 조회하되, 이름의 역순으로 출력하는 queryDsl 구문을 작성하고 테스트해보세요.
//     1페이지에 3개씩 조회하되, 2페이지를 확인해 보세요.

    @Test
    @DisplayName("saram query Dsl Test02 ")
    public void newPersonDslTest02(){
        /*
            select * from sarams
            where salary < 400
            and address like '%포%'
            order by name desc ;
         */

        QSaram qbean = QSaram.saram ;

        BooleanBuilder booleanBuilder = new BooleanBuilder() ;

        String address = "포" ;
        int salary = 400 ;

        booleanBuilder.and(qbean.address.like("%"+address+"%")) ;
        booleanBuilder.and(qbean.salary.lt(salary)) ;

        int pageNumber = 2 - 1 ;
        int pageSize = 3 ;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").descending()) ;
//                                                          조건식, 페이징 객체
        Page<Saram> pageList = this.saramRepository.findAll(booleanBuilder, pageable) ;

        System.out.println("total element : " + pageList.getTotalElements());

        List<Saram> itemList = pageList.getContent() ;
        for(Saram bean : itemList){
            System.out.println(bean.toString());
        }
    }
}
