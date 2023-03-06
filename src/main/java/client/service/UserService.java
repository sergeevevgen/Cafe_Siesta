package client.service;

import client.data.repository.UserRepository;
import client.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;
    private final ValidatorUtil validatorUtil;

    public UserService(UserRepository userRepository,
                       ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.validatorUtil = validatorUtil;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
}
