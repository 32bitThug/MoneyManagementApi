package  com.rs.money.management.api.repository;

import com.rs.money.management.api.entities.PasswordResetOtp;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface PasswordResetOtpRepo extends JpaRepository<PasswordResetOtp,Long> {

}