package com.backendLevelup.Backend.init;

import com.backendLevelup.Backend.model.Categoria;
import com.backendLevelup.Backend.repository.CategoriaRepository;
import com.backendLevelup.Backend.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = Logger.getLogger(String.valueOf(DataInitializer.class));

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public DataInitializer(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if  (categoriaRepository.count() == 0 && productoRepository.count() == 0) {
            log.info("Base de datos vacia. Sembrando datos iniciales del catalogo...");

            Categoria accesorios = new Categoria();
            accesorios.setNombre("Accesorios");
            accesorios = categoriaRepository.save(accesorios);

            Categoria computadores = new Categoria();
            computadores.setNombre("Computadores");
            computadores = categoriaRepository.save(computadores);

            Categoria consolas = new Categoria();
            consolas.setNombre("Consolas");
            consolas = categoriaRepository.save(consolas);

            Categoria Sillas_gamer = new Categoria();
            Sillas_gamer.setNombre("Sillas Gamer");
            Sillas_gamer = categoriaRepository.save(Sillas_gamer);

            Categoria Mouse_teclado = new Categoria();
            Mouse_teclado.setNombre("Mouse_teclado");
            Mouse_teclado = categoriaRepository.save(Mouse_teclado);

            Categoria Mousepads = new Categoria();
            Mousepads.setNombre("Mousepads");
            Mousepads = categoriaRepository.save(Mousepads);

            Categoria Poleras_polerones = new Categoria();
            Poleras_polerones.setNombre("Poleras Polerones");
            Poleras_polerones = categoriaRepository.save(Poleras_polerones);

            Categoria JuegosdeMesa = new Categoria();
            JuegosdeMesa.setNombre("Juegos de");
            JuegosdeMesa = categoriaRepository.save(JuegosdeMesa);
        }
    }
}
