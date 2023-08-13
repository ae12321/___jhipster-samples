package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Company;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    // JpaRepository<Company, Long> のクラスに合わせる必要はない
    // JPQLならばjoin後を格納する型ファイルを指定すればいいが、nativequeryの場合はMapのlist型で返す
    // 列名の大文字小文字は、受け取り側ではすべて小文字で返される＝＞userNameとしたければMap<String, Object>をPOJOに入れそれを返す必要あり
    @Query(
        value = """
            select
                b.id as employee_id
                , b.name as employee_name
                , b.age as employee_age
                , a.company_id as company_id
                , a.name as company_name
                , a.published_at as company_published_at
            FROM company a
            left outer join employee b
            on a.company_id = b.company_id

                        """,
        nativeQuery = true
    )
    Optional<List<Map<String, Object>>> getJoinedData();

    @Query(
        value = """
            select
                a.company_id as company_id
                , a.name as company_name
                , a.published_at as company_published_at
                , b.id as employee_id
                , b.name as employee_name
                , b.age as employee_age
            FROM company a
            left outer join employee b
            on a.company_id = b.company_id
            where a.company_id = :companyId
            """,
        nativeQuery = true
    )
    Optional<List<Map<String, Object>>> getJoinedData2(@Param("companyId") Long id);
}
