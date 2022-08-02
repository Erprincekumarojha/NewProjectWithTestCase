package com.accolite.repository;

import com.accolite.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    @Query(nativeQuery = true, value = "select *from admin_user au where au.admin_user_name = :adminUserName and au.admin_password = :adminPassword")
    AdminUser getAdminUserByCredentials(@Param("adminUserName") String adminUserName, @Param("adminPassword") String adminPassword);

}
