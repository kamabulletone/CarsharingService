package ru.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.model.Client;
import ru.repositories.ClientRepository;

@Service
public class ClientService implements UserDetailsService {
    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    private ClientRepository reps;

    public Client getClient(int id) {
        return reps.findById(id).get();
    }

    public Client getClient(String email) {
        return reps.findByEmail(email);
    }

    public void signUpUser(Client user) {

        final String encryptedPassword = encoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);

        if(reps.findByEmail(user.getEmail()) == null) {
            reps.save(user);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserDetails a = reps.findByEmail(s);
        if (a == null) {
            throw new UsernameNotFoundException("Not found user");
        }

        return a;
    }
}
