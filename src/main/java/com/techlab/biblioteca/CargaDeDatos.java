package com.techlab.biblioteca;

import com.techlab.biblioteca.model.Libro;
import com.techlab.biblioteca.model.Usuario;
import com.techlab.biblioteca.repository.LibroRepository;
import com.techlab.biblioteca.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class CargaDeDatos {

    @Bean
    CommandLineRunner initDatabase(LibroRepository libroRepo, UsuarioRepository usuarioRepo) {
        return args -> {
            if (libroRepo.count() == 0) {
                // 1. Libro popular (Mucho stock, se puede vender y alquilar)
                Libro l1 = new Libro();
                l1.setTitulo("Harry Potter y la Piedra Filosofal");
                l1.setAutor("J.K. Rowling");
                l1.setCategoria("Fantasía");
                l1.setStock(20);
                l1.setCostoAlquiler(new BigDecimal("600.00"));
                l1.setPrecioVenta(new BigDecimal("18000.00"));
                l1.setImagenUrl("https://images-na.ssl-images-amazon.com/images/I/81iqZ2HHD-L.jpg");
                libroRepo.save(l1);

                // 2. Libro escaso (Solo 4 unidades -> NO SE PUEDE VENDER, SOLO ALQUILAR)
                Libro l2 = new Libro();
                l2.setTitulo("Clean Code");
                l2.setAutor("Robert C. Martin");
                l2.setCategoria("Tecnología");
                l2.setStock(4);
                l2.setCostoAlquiler(new BigDecimal("1200.00"));
                l2.setPrecioVenta(new BigDecimal("45000.00"));
                l2.setImagenUrl("https://images-na.ssl-images-amazon.com/images/I/41jEbK-jG+L._SX258_BO1,204,203,200_.jpg");
                libroRepo.save(l2);

                // 3. Clásico (Stock medio)
                Libro l3 = new Libro();
                l3.setTitulo("Don Quijote de la Mancha");
                l3.setAutor("Miguel de Cervantes");
                l3.setCategoria("Clásico");
                l3.setStock(6); // 6 - 1 = 5. (Permite vender 1 copia exacto)
                l3.setCostoAlquiler(new BigDecimal("300.00"));
                l3.setPrecioVenta(new BigDecimal("10000.00"));
                l3.setImagenUrl("https://imagessl9.casadellibro.com/a/l/t5/49/9788437604949.jpg");
                libroRepo.save(l3);

                // 4. Novedad
                Libro l4 = new Libro();
                l4.setTitulo("Hábitos Atómicos");
                l4.setAutor("James Clear");
                l4.setCategoria("Autoayuda");
                l4.setStock(15);
                l4.setCostoAlquiler(new BigDecimal("800.00"));
                l4.setPrecioVenta(new BigDecimal("22000.00"));
                l4.setImagenUrl("https://images-na.ssl-images-amazon.com/images/I/91bYsX41DVL.jpg");
                libroRepo.save(l4);
            }

            if (usuarioRepo.count() == 0) {
                Usuario u1 = new Usuario();
                u1.setNombre("Admin Bibliotecario");
                u1.setEmail("admin@biblioteca.com");
                u1.setPassword("admin123");
                usuarioRepo.save(u1);
            }
        };
    }
}