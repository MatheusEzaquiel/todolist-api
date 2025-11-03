package br.com.mbe.todolist.configurations;

import br.com.mbe.todolist.domain.auth.dto.RegisterDTO;
import br.com.mbe.todolist.domain.user.User;
import br.com.mbe.todolist.domain.user.UserRole;
import br.com.mbe.todolist.domain.user.dto.DetailUserDTO;
import br.com.mbe.todolist.repository.IUserRepository;
import br.com.mbe.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class SetupInitialAdmin implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    IUserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        UserDetails user = userRepository.findByUsername("admin");
        if(user != null) {
            RegisterDTO admin = new RegisterDTO("admin", "admin@email.com", "123", true, UserRole.ADMIN);
            DetailUserDTO adminCreated = userService.create(admin);
            if(adminCreated != null) {
                System.out.println("Usuário Admin gerado com sucesso!");
            } else {
                System.out.println("Erro ao gerar Usuário Admin.");
            }
        };
    }
}
