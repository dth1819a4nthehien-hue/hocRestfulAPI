package vn.hoidanit.springrestwithai.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hoidanit.springrestwithai.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
