package com.backendLevelup.Backend.init;

import com.backendLevelup.Backend.model.Categoria;
import com.backendLevelup.Backend.model.Producto;
import com.backendLevelup.Backend.model.Rol;
import com.backendLevelup.Backend.model.Usuario;
import com.backendLevelup.Backend.repository.CategoriaRepository;
import com.backendLevelup.Backend.repository.ProductoRepository;
import com.backendLevelup.Backend.repository.RolRepository;
import com.backendLevelup.Backend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = Logger.getLogger(String.valueOf(DataInitializer.class));

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository,
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {

        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // Verificamos uno por uno. Si falta alguno, lo crea.
        if (rolRepository.findByNombre("ROLE_USER").isEmpty()) {
            rolRepository.save(new Rol("ROLE_USER"));
            log.info("Rol ROLE_USER creado exitosamente.");
        }

        if (rolRepository.findByNombre("ROLE_ADMIN").isEmpty()) {
            rolRepository.save(new Rol("ROLE_ADMIN"));
            log.info("Rol ROLE_ADMIN creado exitosamente.");
        }

        if (usuarioRepository.count() == 0) {
            log.info("Creando usuario administrador inicial...");

            Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: Rol ADMIN no encontrado"));

            Usuario admin = new Usuario();
            admin.setNombre("Admin LevelUp");
            admin.setEmail("admin@levelup.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFechaNacimiento(LocalDate.of(1990, 1, 1));
            admin.setTieneDescuentoDuoc(true);
            admin.setEnabled(true);

            if (admin.getRoles() == null) {
                admin.setRoles(new ArrayList<>());
            }
            admin.getRoles().add(rolAdmin);

            usuarioRepository.save(admin);
            log.info("Usuario admin creado exitosamente.");
        }

        if (categoriaRepository.count() == 0 && productoRepository.count() == 0) {
            log.info("Base de datos vacía. Sembrando datos iniciales del catalogo...");

            // --- Crear categorias ---
            Categoria accesorios = new Categoria();
            accesorios.setNombre("Accesorios");
            accesorios = categoriaRepository.save(accesorios);

            Categoria computadores = new Categoria();
            computadores.setNombre("Computadores");
            computadores = categoriaRepository.save(computadores);

            Categoria consolas = new Categoria();
            consolas.setNombre("Consolas");
            consolas = categoriaRepository.save(consolas);

            Categoria sillasGamer = new Categoria();
            sillasGamer.setNombre("Sillas Gamer");
            sillasGamer = categoriaRepository.save(sillasGamer);

            Categoria mouseTeclado = new Categoria();
            mouseTeclado.setNombre("Mouse y Teclado");
            mouseTeclado = categoriaRepository.save(mouseTeclado);

            Categoria mousepads = new Categoria();
            mousepads.setNombre("Mousepads");
            mousepads = categoriaRepository.save(mousepads);

            Categoria polerasPolerones = new Categoria();
            polerasPolerones.setNombre("Poleras y Polerones");
            polerasPolerones = categoriaRepository.save(polerasPolerones);

            Categoria juegosDeMesa = new Categoria();
            juegosDeMesa.setNombre("Juegos de Mesa");
            juegosDeMesa = categoriaRepository.save(juegosDeMesa);
            log.info("Categorias creadas con éxito.");

            // --- Crear productos ---
            Producto logitechG733Wireless = new Producto();
            logitechG733Wireless.setCodigo("AP001");
            logitechG733Wireless.setNombre("Logitech G733 Wireless");
            logitechG733Wireless.setDescripcion("Auriculares inalámbricos para juegos LIGHTSPEED con LIGHTSYNC RGB");
            logitechG733Wireless.setPrecio(159990);
            logitechG733Wireless.setStock(10);
            logitechG733Wireless.setCategoria(accesorios);
            logitechG733Wireless.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/logitech_g733.png");
            productoRepository.save(logitechG733Wireless);

            Producto dualsensePS5 = new Producto();
            dualsensePS5.setCodigo("AP002");
            dualsensePS5.setNombre("DualSense PS5");
            dualsensePS5.setDescripcion("Control inalámbrico DualSense para PS5");
            dualsensePS5.setPrecio(52990);
            dualsensePS5.setStock(10);
            dualsensePS5.setCategoria(accesorios);
            dualsensePS5.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/dualsense_ps5.png");
            productoRepository.save(dualsensePS5);

            Producto estucheNintendoSwitch = new Producto();
            estucheNintendoSwitch.setCodigo("AP003");
            estucheNintendoSwitch.setNombre("Estuche Nintendo Switch");
            estucheNintendoSwitch.setDescripcion("Estuche protector para Nintendo Switch OLED");
            estucheNintendoSwitch.setPrecio(19990);
            estucheNintendoSwitch.setStock(10);
            estucheNintendoSwitch.setCategoria(accesorios);
            estucheNintendoSwitch.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/nintendo_switch_case.png");
            productoRepository.save(estucheNintendoSwitch);

            Producto asusRogStrixG18 = new Producto();
            asusRogStrixG18.setCodigo("AP004");
            asusRogStrixG18.setNombre("Asus ROG Strix G18");
            asusRogStrixG18.setDescripcion("Intel Core i9-13650HX / NVIDIA GeForce RTX 4070 12GB / 32GB RAM / 1TB SSD");
            asusRogStrixG18.setPrecio(2229990);
            asusRogStrixG18.setStock(10);
            asusRogStrixG18.setCategoria(computadores);
            asusRogStrixG18.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/asus_rog_g18.png");
            productoRepository.save(asusRogStrixG18);

            Producto acerNitroAN515 = new Producto();
            acerNitroAN515.setCodigo("AP005");
            acerNitroAN515.setNombre("Acer Nitro AN515");
            acerNitroAN515.setDescripcion("Ryzen 7 8845HS/ NVIDIA® GeForce® RTX 3060 6GB / 16GB RAM / 512GB SSD / Pantalla 15,6'' Full HD @144Hz");
            acerNitroAN515.setPrecio(1099990);
            acerNitroAN515.setStock(10);
            acerNitroAN515.setCategoria(computadores);
            acerNitroAN515.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/acer_nitro.png");
            productoRepository.save(acerNitroAN515);

            Producto pcGamerR5RTX4060 = new Producto();
            pcGamerR5RTX4060.setCodigo("AP006");
            pcGamerR5RTX4060.setNombre("PC Gamer R5 RTX 4060");
            pcGamerR5RTX4060.setDescripcion("Ryzen 5 8400f / NVIDIA GeForce RTX 4060 / 16GB RAM / 512GB SSD / refrigeración líquida / gabinete ATX");
            pcGamerR5RTX4060.setPrecio(1199990);
            pcGamerR5RTX4060.setStock(10);
            pcGamerR5RTX4060.setCategoria(computadores);
            pcGamerR5RTX4060.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/pc_gamer_r5.png");
            productoRepository.save(pcGamerR5RTX4060);

            Producto playStation5 = new Producto();
            playStation5.setCodigo("AP007");
            playStation5.setNombre("PlayStation 5");
            playStation5.setDescripcion("Consola PlayStation 5 Slim 825GB");
            playStation5.setPrecio(536990);
            playStation5.setStock(10);
            playStation5.setCategoria(consolas);
            playStation5.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/ps5_slim.png");
            productoRepository.save(playStation5);

            Producto xboxSeriesS = new Producto();
            xboxSeriesS.setCodigo("AP008");
            xboxSeriesS.setNombre("Xbox Series S");
            xboxSeriesS.setDescripcion("Consola Xbox Series S 512GB");
            xboxSeriesS.setPrecio(419990);
            xboxSeriesS.setStock(10);
            xboxSeriesS.setCategoria(consolas);
            xboxSeriesS.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/xbox_series_s.png");
            productoRepository.save(xboxSeriesS);

            Producto nintendoSwitch2 = new Producto();
            nintendoSwitch2.setCodigo("AP009");
            nintendoSwitch2.setNombre("Nintendo Switch 2");
            nintendoSwitch2.setDescripcion("Consola Nintendo Switch 2 / 256GB / 12 RAM / pantalla tactil");
            nintendoSwitch2.setPrecio(589990);
            nintendoSwitch2.setStock(10);
            nintendoSwitch2.setCategoria(consolas);
            nintendoSwitch2.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/nintendo_switch2.png");
            productoRepository.save(nintendoSwitch2);

            Producto trustGXT707RResto = new Producto();
            trustGXT707RResto.setCodigo("AP010");
            trustGXT707RResto.setNombre("Trust GXT 707R Resto");
            trustGXT707RResto.setDescripcion("Silla Gamer Trust GXT 707R Resto Negro con Rojo");
            trustGXT707RResto.setPrecio(139990);
            trustGXT707RResto.setStock(10);
            trustGXT707RResto.setCategoria(sillasGamer);
            trustGXT707RResto.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/trust_gxt707.png");
            productoRepository.save(trustGXT707RResto);

            Producto kronosHunterPro = new Producto();
            kronosHunterPro.setCodigo("AP011");
            kronosHunterPro.setNombre("Kronos Hunter Pro");
            kronosHunterPro.setDescripcion("Silla Gamer Kronos Hunter Pro Negra y Azul");
            kronosHunterPro.setPrecio(119990);
            kronosHunterPro.setStock(10);
            kronosHunterPro.setCategoria(sillasGamer);
            kronosHunterPro.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/kronos_hunter_pro.png");
            productoRepository.save(kronosHunterPro);

            Producto cougarDefensor = new Producto();
            cougarDefensor.setCodigo("AP012");
            cougarDefensor.setNombre("Cougar Defensor");
            cougarDefensor.setDescripcion("Silla Gamer Cougar Defensor Negra y Naranja");
            cougarDefensor.setPrecio(189990);
            cougarDefensor.setStock(10);
            cougarDefensor.setCategoria(sillasGamer);
            cougarDefensor.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/cougar_defensor.png");
            productoRepository.save(cougarDefensor);

            Producto logitechG502Hero = new Producto();
            logitechG502Hero.setCodigo("AP013");
            logitechG502Hero.setNombre("Logitech G502 Hero");
            logitechG502Hero.setDescripcion("Mouse Gamer Logitech G502 Hero");
            logitechG502Hero.setPrecio(79990);
            logitechG502Hero.setStock(10);
            logitechG502Hero.setCategoria(mouseTeclado);
            logitechG502Hero.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/logitech_g502.png");
            productoRepository.save(logitechG502Hero);

            Producto razerDeathAdder = new Producto();
            razerDeathAdder.setCodigo("AP014");
            razerDeathAdder.setNombre("Razer Death Adder");
            razerDeathAdder.setDescripcion("Mouse Gamer Razer DeathAdder");
            razerDeathAdder.setPrecio(89990);
            razerDeathAdder.setStock(10);
            razerDeathAdder.setCategoria(mouseTeclado);
            razerDeathAdder.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/razer_deathadder_v2.png");
            productoRepository.save(razerDeathAdder);

            Producto logitechG413 = new Producto();
            logitechG413.setCodigo("AP015");
            logitechG413.setNombre("Logitech G413");
            logitechG413.setDescripcion("Teclado Gamer Logitech G413 Mechanical");
            logitechG413.setPrecio(99990);
            logitechG413.setStock(10);
            logitechG413.setCategoria(mouseTeclado);
            logitechG413.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/logitech_g413.png");
            productoRepository.save(logitechG413);

            Producto hyperXFuryS = new Producto();
            hyperXFuryS.setCodigo("AP016");
            hyperXFuryS.setNombre("Hyperx Fury S");
            hyperXFuryS.setDescripcion("Mousepad Gamer HyperX Fury S Pro XL");
            hyperXFuryS.setPrecio(29990);
            hyperXFuryS.setStock(10);
            hyperXFuryS.setCategoria(mousepads);
            hyperXFuryS.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/hyperx_fury_s.png");
            productoRepository.save(hyperXFuryS);

            Producto logitechG640 = new Producto();
            logitechG640.setCodigo("AP017");
            logitechG640.setNombre("Logitech G640");
            logitechG640.setDescripcion("Mousepad Gamer Logitech G640");
            logitechG640.setPrecio(29990);
            logitechG640.setStock(10);
            logitechG640.setCategoria(mousepads);
            logitechG640.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/logitech_g640.png");
            productoRepository.save(logitechG640);

            Producto razerGigantusV2 = new Producto();
            razerGigantusV2.setCodigo("AP018");
            razerGigantusV2.setNombre("Razer Gigantus V2");
            razerGigantusV2.setDescripcion("Mousepad Gamer Razer Gigantus V2");
            razerGigantusV2.setPrecio(29990);
            razerGigantusV2.setStock(10);
            razerGigantusV2.setCategoria(mousepads);
            razerGigantusV2.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/razer_gigantus_v2.png");
            productoRepository.save(razerGigantusV2);

            Producto poleraHALO = new Producto();
            poleraHALO.setCodigo("AP019");
            poleraHALO.setNombre("Polera HALO");
            poleraHALO.setDescripcion("Polera de HALO Talla M/L/XL");
            poleraHALO.setPrecio(19990);
            poleraHALO.setStock(10);
            poleraHALO.setCategoria(polerasPolerones);
            poleraHALO.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/polera_halo.png");
            productoRepository.save(poleraHALO);

            Producto poleraGodOfWar = new Producto();
            poleraGodOfWar.setCodigo("AP020");
            poleraGodOfWar.setNombre("Polera God Of War");
            poleraGodOfWar.setDescripcion("Polera de God Of War Talla M/L/XL");
            poleraGodOfWar.setPrecio(19990);
            poleraGodOfWar.setStock(10);
            poleraGodOfWar.setCategoria(polerasPolerones);
            poleraGodOfWar.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/polera_gow.png");
            productoRepository.save(poleraGodOfWar);

            Producto poleronLegendOfZelda = new Producto();
            poleronLegendOfZelda.setCodigo("AP021");
            poleronLegendOfZelda.setNombre("Poleron Legend of Zelda");
            poleronLegendOfZelda.setDescripcion("Poleron de Legend of Zelda Talla M/L/XL");
            poleronLegendOfZelda.setPrecio(39990);
            poleronLegendOfZelda.setStock(10);
            poleronLegendOfZelda.setCategoria(polerasPolerones);
            poleronLegendOfZelda.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/poleron_zelda.png");
            productoRepository.save(poleronLegendOfZelda);

            Producto warhammer40M = new Producto();
            warhammer40M.setCodigo("AP022");
            warhammer40M.setNombre("Warhammer 40M");
            warhammer40M.setDescripcion("Juego de mesa Warhammer 40,000");
            warhammer40M.setPrecio(89990);
            warhammer40M.setStock(10);
            warhammer40M.setCategoria(juegosDeMesa);
            warhammer40M.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/warhammer_40m.png");
            productoRepository.save(warhammer40M);

            Producto ajedrez = new Producto();
            ajedrez.setCodigo("AP023");
            ajedrez.setNombre("Ajedrez");
            ajedrez.setDescripcion("Juego de mesa Ajedrez");
            ajedrez.setPrecio(24990);
            ajedrez.setStock(10);
            ajedrez.setCategoria(juegosDeMesa);
            ajedrez.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/ajedrez.png");
            productoRepository.save(ajedrez);

            Producto shogi = new Producto();
            shogi.setCodigo("AP024");
            shogi.setNombre("Shogi");
            shogi.setDescripcion("Juego de mesa Shogi");
            shogi.setPrecio(59990);
            shogi.setStock(10);
            shogi.setCategoria(juegosDeMesa);
            shogi.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/shogi.png");
            productoRepository.save(shogi);
        }
    }
}
