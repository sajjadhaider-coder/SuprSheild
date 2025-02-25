package com.spring3.oauth.jwt.repositories;

import com.spring3.oauth.jwt.helpers.RefreshableCRUDRepository;
import com.spring3.oauth.jwt.models.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

   public UserInfo findByUsername(String username);
   UserInfo findFirstById(Long id);

   @Query(value = "SELECT * FROM USERS WHERE user_id = :userId", nativeQuery = true)
   UserInfo findUsersByUserId(@Param("userId") int userId);

   Page<UserInfo> findAll(Pageable pageable);

   @Query(value = "SELECT * FROM USERS LIMIT :limit OFFSET :offset", nativeQuery = true)
   List<UserInfo> findWithPagination(@Param("limit") int limit, @Param("offset") int offset);

   @Query(value = "SELECT * FROM users WHERE parent_id = :parentId ORDER BY :sortBy :direction LIMIT :limit OFFSET :offset", nativeQuery = true)
   List<UserInfo> findPaginatedSubAgentsByAgentId(
           @Param("parentId") Long parentId,
           @Param("sortBy") String sortBy,
           @Param("direction") String direction,
           @Param("limit") int limit,
           @Param("offset") int offset
   );

   @Query("SELECT u FROM UserInfo u WHERE u.parentId = :parentId")
   Page<UserInfo> findPaginatedSubAgentsByParentId(@Param("parentId") String parentId, Pageable pageable);


}
