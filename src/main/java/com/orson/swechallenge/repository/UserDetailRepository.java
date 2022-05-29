package com.orson.swechallenge.repository;

import com.orson.swechallenge.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    @Query(value = "SELECT u.id, u.name, u.salary FROM UserDetail u where u.salary >= ?1 and u.salary <= ?2 LIMIT ?4 OFFSET ?3", nativeQuery = true)
    List<UserDetail> getUserDetailWithNoSorting(Float min, Float max, Integer offset, Integer limit);

    @Query(value = "SELECT u.id, u.name, u.salary FROM UserDetail u where u.salary >= ?1 and u.salary <= ?2 order by name ASC LIMIT ?4 OFFSET ?3"
            , nativeQuery = true)
    List<UserDetail> getUserDetailSortByName(Float min, Float max, Integer offset, Integer limit);

    @Query(value = "SELECT u.id, u.name, u.salary FROM UserDetail u where u.salary >= ?1 and u.salary <= ?2 order by salary ASC LIMIT ?4 OFFSET ?3"
            , nativeQuery = true)
    List<UserDetail> getUserDetailSortBySalary(Float min, Float max, Integer offset, Integer limit);

    UserDetail findUserDetailByNameIgnoreCase(String name);

}
