package com.api.tcc;

import com.api.tcc.enums.RoleEnum;
import com.api.tcc.models.RoleModel;
import com.api.tcc.models.UserModel;
import com.api.tcc.repositories.RoleRepository;
import com.api.tcc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TccApplication implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(TccApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Register roles in the database
		RoleModel[] roles = {new RoleModel(RoleEnum.ROLE_USER), new RoleModel(RoleEnum.ROLE_ADMIN)};
		for(RoleModel role : roles){
			if(roleRepository.findByRole(role.getRole()) == null){
				roleRepository.save(role);
			}
		}
		// Register admins in the database
		Set<RoleModel> set = new HashSet<>();
		set.addAll(roleRepository.findAll());
		UserModel[] usersAdmins = {
				new UserModel(null, "eric@gmail.com", passwordEncoder.encode("1234"), "Eric",
						LocalDate.parse("2000-01-28"), true, true, true, true,
						set),
				new UserModel(null, "murilo@gmail.com", passwordEncoder.encode("1234"), "Murilo",
						LocalDate.parse("1999-01-15"), true, true, true, true,
						set),
		};
		for(UserModel userModel : usersAdmins){
			if(!userRepository.findByUsername(userModel.getUsername()).isPresent()){
				userRepository.save(userModel);
			}
		}
	}
}
