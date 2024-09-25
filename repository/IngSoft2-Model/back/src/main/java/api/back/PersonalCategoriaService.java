package api.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonalCategoriaService {

    @Autowired
    private PersonalCategoriaRepository personalCategoriaRepository;

    @Autowired
    private UserRepository userRepository;

    public List<PersonalCategoria> getPersonalCategoria(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return personalCategoriaRepository.findByUser(user);
    }

    public PersonalCategoria addPersonalCategoria(String email, String nombre, String iconPath) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        PersonalCategoria categoria = new PersonalCategoria();
        categoria.setNombre(nombre);
        categoria.setIconPath(iconPath);
        categoria.setUser(user);
        return personalCategoriaRepository.save(categoria);
    }

    public void deletePersonalCategoria(Long id) {
        personalCategoriaRepository.deleteById(id);
    }

    public void findAndDeleteCategoria(String email, String nombre, String iconPath) {
        List<PersonalCategoria> categorias = getPersonalCategoria(email);
        for (PersonalCategoria item : categorias) {
            if (item.getNombre().equals(nombre)) {
                System.out.println("Found: " + item);
                deletePersonalCategoria(item.getId());
            }
        }
    }

    // dsps necesitamos agregar editar
}
