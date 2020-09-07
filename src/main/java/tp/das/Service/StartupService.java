package tp.das.Service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import tp.das.DTOs.Auth.RegisterDTO;

@Component
public class StartupService implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        AuthService.createAccount(new RegisterDTO("adminadmin", "adminadmin123", "Admin"));
    }
}
