package istu.bacs.db.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPersonalDetailsRepository extends JpaRepository<UserPersonalDetails, User> {
    UserPersonalDetails findByUser_username(String username);
}