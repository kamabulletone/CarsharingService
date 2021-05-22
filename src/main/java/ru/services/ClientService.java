package ru.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.model.Car;
import ru.model.Client;
import ru.model.Role;
import ru.repositories.ClientRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClientService implements UserDetailsService {
    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    ClientRepository reps;

    @Autowired
    ClientService(ClientRepository reps, BCryptPasswordEncoder encoder) {
        this.reps = reps;
        this.encoder = encoder;
        Client admin = new Client();
        admin.setClientID(1);
        admin.setEmail("admin");
        admin.setFullName("admin");
        admin.setPassword(encoder.encode("admin"));
        Set<Role> set = new HashSet<Role>();
        set.add(new Role(1, "ROLE_USER"));
        set.add(new Role(2, "ROLE_ADMIN"));
        admin.setRoles(set);
        Client user = reps.findByEmail("admin");
        if (user == null) {
            reps.save(admin);
        }

    }

    public List<Client> getClients() {
        List<Client> ret = reps.findAll();
        ret.remove(0);
        return ret;
    }

    public void deleteById(int id) {
        reps.delete(getClient(id));
    }

    public Client getClient(int id) {
        return reps.findById(id).get();
    }

    public List<Client> getClientsWithNoOrders() {
        return reps.getClientsWithNoOrders();
    }

    public Client getClient(String email) {
        return reps.findByEmail(email);
    }

    public void signUpUser(Client user) {

        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));

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
