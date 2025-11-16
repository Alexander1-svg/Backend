package com.backendLevelup.Backend.init;

import com.backendLevelup.Backend.model.Categoria;
import com.backendLevelup.Backend.model.Producto;
import com.backendLevelup.Backend.repository.CategoriaRepository;
import com.backendLevelup.Backend.repository.ProductoRepository;
import com.backendLevelup.Backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.logging.Logger;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = Logger.getLogger(String.valueOf(DataInitializer.class));

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public DataInitializer(CategoriaRepository categoriaRepository, ProductoRepository productoRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if  (categoriaRepository.count() == 0 && productoRepository.count() == 0) {
            log.info("Base de datos vacia. Sembrando datos iniciales del catalogo...");

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

            Categoria Sillas_gamer = new Categoria();
            Sillas_gamer.setNombre("Sillas_Gamer");
            Sillas_gamer = categoriaRepository.save(Sillas_gamer);

            Categoria Mouse_teclado = new Categoria();
            Mouse_teclado.setNombre("Mouse_teclado");
            Mouse_teclado = categoriaRepository.save(Mouse_teclado);

            Categoria Mousepads = new Categoria();
            Mousepads.setNombre("Mousepads");
            Mousepads = categoriaRepository.save(Mousepads);

            Categoria Poleras_polerones = new Categoria();
            Poleras_polerones.setNombre("Poleras_Polerones");
            Poleras_polerones = categoriaRepository.save(Poleras_polerones);

            Categoria JuegosdeMesa = new Categoria();
            JuegosdeMesa.setNombre("Juegos_de_Mesa");
            JuegosdeMesa = categoriaRepository.save(JuegosdeMesa);
            log.info("Categorias creadas con éxito.");

            // --- Crear productos ---
            //Accesorios y Periféricos Gaming Esports
            Producto Logitech_G733_Wireless  = new Producto();
            Logitech_G733_Wireless.setCodigo("AP001");
            Logitech_G733_Wireless.setNombre("Logitech G733 Wireless");
            Logitech_G733_Wireless.setDescripcion("Auriculares inalámbricos para juegos LIGHTSPEED con LIGHTSYNC RGB");
            Logitech_G733_Wireless.setPrecio(new BigDecimal("159990.00"));
            Logitech_G733_Wireless.setStock(10);
            Logitech_G733_Wireless.setCategoria(accesorios);
            Logitech_G733_Wireless.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/logitech_g733.png");
            productoRepository.save(Logitech_G733_Wireless);

            Producto DualSense_PS5 = new Producto();
            DualSense_PS5.setCodigo("AP002");
            DualSense_PS5.setNombre("DualSense PS5");
            DualSense_PS5.setDescripcion("Control inalámbrico DualSense para PS5");
            DualSense_PS5.setPrecio(new BigDecimal("52.990"));
            DualSense_PS5.setStock(10);
            DualSense_PS5.setCategoria(accesorios);
            DualSense_PS5.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/dualsense_ps5.png");
            productoRepository.save(DualSense_PS5);

            Producto Estuche_Nintendo_Switch = new Producto();
            Estuche_Nintendo_Switch.setCodigo("AP003");
            Estuche_Nintendo_Switch.setNombre("Estuche Nintendo Switch");
            Estuche_Nintendo_Switch.setDescripcion("Estuche protector para Nintendo Switch OLED");
            Estuche_Nintendo_Switch.setPrecio(new BigDecimal("19.990"));
            Estuche_Nintendo_Switch.setStock(10);
            Estuche_Nintendo_Switch.setCategoria(accesorios);
            Estuche_Nintendo_Switch.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/nintendo_switch_case.png");
            productoRepository.save(Estuche_Nintendo_Switch);

            //Computadores y Laptops Gaming

            Producto Asus_ROG_Strix_G18 = new Producto();
            Asus_ROG_Strix_G18.setCodigo("AP004");
            Asus_ROG_Strix_G18.setNombre("Asus ROG Strix G18");
            Asus_ROG_Strix_G18.setDescripcion("Intel Core i9-13650HX / NVIDIA GeForce RTX 4070 12GB / 32GB RAM\n" +
                    "                / 1TB SSD");
            Asus_ROG_Strix_G18.setPrecio(new BigDecimal("2.229.990"));
            Asus_ROG_Strix_G18.setStock(10);
            Asus_ROG_Strix_G18.setCategoria(computadores);
            Asus_ROG_Strix_G18.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/asus_rog_g18.png");
            productoRepository.save(Asus_ROG_Strix_G18);

            Producto Acer_Nitro_AN515 = new Producto();
            Acer_Nitro_AN515.setCodigo("AP005");
            Acer_Nitro_AN515.setNombre("Acer Nitro AN515");
            Acer_Nitro_AN515.setDescripcion("Ryzen 7 8845HS/ NVIDIA® GeForce® RTX 3060 6GB / 16GB RAM / 512GB\n" +
                    "                SSD / Pantalla 15,6'' Full HD @144Hz");
            Acer_Nitro_AN515.setPrecio(new BigDecimal("1.099.990"));
            Acer_Nitro_AN515.setStock(10);
            Acer_Nitro_AN515.setCategoria(computadores);
            Asus_ROG_Strix_G18.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/acer_nitro.png");
            productoRepository.save(Asus_ROG_Strix_G18);

            Producto PC_Gamer_R5_RTX_4060 = new Producto();
            PC_Gamer_R5_RTX_4060.setCodigo("AP006");
            PC_Gamer_R5_RTX_4060.setNombre("PC Gamer R5 RTX 4060");
            PC_Gamer_R5_RTX_4060.setDescripcion("Ryzen 5 8400f / NVIDIA GeForce RTX 4060 / 16GB RAM / 512GB SSD / refrigeración líquida / gabinete ATX");
            PC_Gamer_R5_RTX_4060.setPrecio(new BigDecimal("1.199.990"));
            PC_Gamer_R5_RTX_4060.setStock(10);
            PC_Gamer_R5_RTX_4060.setCategoria(computadores);
            PC_Gamer_R5_RTX_4060.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/pc_gamer_r5.png");
            productoRepository.save(PC_Gamer_R5_RTX_4060);

            //Consolas
            Producto PlayStation_5 = new  Producto();
            PlayStation_5.setCodigo("AP007");
            PlayStation_5.setNombre("PlayStation 5");
            PlayStation_5.setDescripcion("Consola PlayStation 5 Slim 825GB");
            PlayStation_5.setPrecio(new BigDecimal("536.990"));
            PlayStation_5.setStock(10);
            PlayStation_5.setCategoria(consolas);
            PC_Gamer_R5_RTX_4060.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/ps5_slim.png");
            productoRepository.save(PlayStation_5);

            Producto Xbox_Series_S = new Producto();
            Xbox_Series_S.setCodigo("AP008");
            Xbox_Series_S.setNombre("Xbox Series S");
            Xbox_Series_S.setDescripcion("Consola Xbox Series S 512GB");
            Xbox_Series_S.setPrecio(new BigDecimal("419.990"));
            Xbox_Series_S.setStock(10);
            Xbox_Series_S.setCategoria(consolas);
            Xbox_Series_S.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/xbox_series_s.png");
            productoRepository.save(Xbox_Series_S);

            Producto Nintendo_Switch_2 = new Producto();
            Nintendo_Switch_2.setCodigo("AP009");
            Nintendo_Switch_2.setNombre("Nintendo Switch 2");
            Nintendo_Switch_2.setDescripcion("Consola Nintendo Switch 2 / 256GB / 12 RAM / pantalla tactil");
            Nintendo_Switch_2.setPrecio(new BigDecimal("589.990"));
            Nintendo_Switch_2.setStock(10);
            Nintendo_Switch_2.setCategoria(consolas);
            Nintendo_Switch_2.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/nintendo_switch2.png");
            productoRepository.save(Nintendo_Switch_2);

            //Sillas Gamer

            Producto Trust_GXT_707R_Resto = new Producto();
            Trust_GXT_707R_Resto.setCodigo("AP010");
            Trust_GXT_707R_Resto.setNombre("Trust GXT 707R Resto");
            Trust_GXT_707R_Resto.setDescripcion("Silla Gamer Trust GXT 707R Resto Negro con Rojo");
            Trust_GXT_707R_Resto.setPrecio(new BigDecimal("139.990"));
            Trust_GXT_707R_Resto.setStock(10);
            Trust_GXT_707R_Resto.setCategoria(Sillas_gamer);
            Trust_GXT_707R_Resto.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/trust_gxt707.png");
            productoRepository.save(Trust_GXT_707R_Resto);

            Producto Kronos_Hunter_Pro = new Producto();
            Kronos_Hunter_Pro.setCodigo("AP011");
            Kronos_Hunter_Pro.setNombre("Kronos Hunter Pro");
            Trust_GXT_707R_Resto.setDescripcion("Silla Gamer Kronos Hunter Pro Negra y Azul");
            Trust_GXT_707R_Resto.setPrecio(new BigDecimal("119.990"));
            Trust_GXT_707R_Resto.setStock(10);
            Trust_GXT_707R_Resto.setCategoria(Sillas_gamer);
            Trust_GXT_707R_Resto.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/kronos_hunter_pro.png");
            productoRepository.save(Trust_GXT_707R_Resto);

            Producto Cougar_Defensor = new Producto();
            Cougar_Defensor.setCodigo("AP012");
            Cougar_Defensor.setNombre("Cougar Defensor");
            Cougar_Defensor.setDescripcion("Silla Gamer Cougar Defensor Negra y Naranja");
            Cougar_Defensor.setPrecio(new BigDecimal("189.990"));
            Cougar_Defensor.setStock(10);
            Cougar_Defensor.setCategoria(Sillas_gamer);
            Cougar_Defensor.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/cougar_defensor.png");
            productoRepository.save(Cougar_Defensor);

            //Mouse y teclados Gamer
            Producto Logitech_G502_Hero = new Producto();
            Logitech_G502_Hero.setCodigo("AP013");
            Logitech_G502_Hero.setNombre("Logitech G502 Hero");
            Logitech_G502_Hero.setDescripcion("Mouse Gamer Logitech G502 Hero");
            Logitech_G502_Hero.setPrecio(new BigDecimal("79.990"));
            Logitech_G502_Hero.setStock(10);
            Logitech_G502_Hero.setCategoria(Mouse_teclado);
            Logitech_G502_Hero.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/logitech_g502.png");
            productoRepository.save(Logitech_G502_Hero);

            Producto Razer_DeathAdder = new Producto();
            Razer_DeathAdder.setCodigo("AP014");
            Razer_DeathAdder.setNombre("Razer Death Adder");
            Razer_DeathAdder.setDescripcion("Mouse Gamer Razer DeathAdder");
            Razer_DeathAdder.setPrecio(new BigDecimal("89.990"));
            Razer_DeathAdder.setStock(10);
            Razer_DeathAdder.setCategoria(Mouse_teclado);
            Razer_DeathAdder.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/razer_deathadder_v2.png");
            productoRepository.save(Razer_DeathAdder);

            Producto Logitech_G413 = new Producto();
            Logitech_G413.setCodigo("AP015");
            Razer_DeathAdder.setNombre("Logitech G413");
            Razer_DeathAdder.setDescripcion("Teclado Gamer Logitech G413 Mechanical");
            Razer_DeathAdder.setPrecio(new BigDecimal("99.990"));
            Razer_DeathAdder.setStock(10);
            Razer_DeathAdder.setCategoria(Mouse_teclado);
            Razer_DeathAdder.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/logitech_g413.png");
            productoRepository.save(Razer_DeathAdder);

            //Mousepads
            Producto HyperX_Fury_S = new Producto();
            HyperX_Fury_S.setCodigo("AP016");
            HyperX_Fury_S.setNombre("Hyperx Fury S");
            HyperX_Fury_S.setDescripcion("Mousepad Gamer HyperX Fury S Pro XL");
            HyperX_Fury_S.setPrecio(new BigDecimal("29.990"));
            HyperX_Fury_S.setStock(10);
            HyperX_Fury_S.setCategoria(Mousepads);
            HyperX_Fury_S.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/hyperx_fury_s.png");
            productoRepository.save(HyperX_Fury_S);

            Producto Logitech_G640 = new Producto();
            Logitech_G640.setCodigo("AP017");
            Logitech_G640.setNombre("Logitech G640");
            Logitech_G640.setDescripcion("Mousepad Gamer Logitech G640");
            Logitech_G640.setPrecio(new BigDecimal("29.990"));
            Logitech_G640.setStock(10);
            Logitech_G640.setCategoria(Mousepads);
            Logitech_G640.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/logitech_g640.png");
            productoRepository.save(Logitech_G640);

            Producto Razer_Gigantus_V2 = new Producto();
            Razer_Gigantus_V2.setCodigo("AP018");
            Razer_Gigantus_V2.setNombre("Razer Gigantus V2");
            Razer_Gigantus_V2.setDescripcion("Mousepad Gamer Razer Gigantus V2");
            Razer_Gigantus_V2.setPrecio(new BigDecimal("29.990"));
            Razer_Gigantus_V2.setStock(10);
            Razer_Gigantus_V2.setCategoria(Mousepads);
            Razer_Gigantus_V2.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/razer_gigantus_v2.png");
            productoRepository.save(Razer_Gigantus_V2);

            //Poleras y polerones personalizados
            Producto Polera_HALO = new Producto();
            Polera_HALO.setCodigo("AP019");
            Polera_HALO.setNombre("Polera HALO");
            Polera_HALO.setDescripcion("Polera de HALO Talla M/L/XL");
            Polera_HALO.setPrecio(new BigDecimal("19.990"));
            Polera_HALO.setStock(10);
            Polera_HALO.setCategoria(Poleras_polerones);
            Polera_HALO.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/polera_halo.png");
            productoRepository.save(Polera_HALO);

            Producto Polera_God_Of_War = new Producto();
            Polera_God_Of_War.setCodigo("AP020");
            Polera_God_Of_War.setNombre("Polera God Of War");
            Polera_God_Of_War.setDescripcion("Polera de God Of War Talla M/L/XL");
            Polera_God_Of_War.setPrecio(new BigDecimal("19.990"));
            Polera_God_Of_War.setStock(10);
            Polera_God_Of_War.setCategoria(Poleras_polerones);
            Polera_God_Of_War.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/polera_gow.png");
            productoRepository.save(Polera_God_Of_War);

            Producto Poleron_Legend_of_Zelda = new Producto();
            Poleron_Legend_of_Zelda.setCodigo("AP021");
            Poleron_Legend_of_Zelda.setNombre("Poleron Legend of Zelda");
            Poleron_Legend_of_Zelda.setDescripcion("Poleron de Legend of Zelda Talla M/L/XL");
            Poleron_Legend_of_Zelda.setPrecio(new BigDecimal("39.990"));
            Poleron_Legend_of_Zelda.setStock(10);
            Poleron_Legend_of_Zelda.setCategoria(Poleras_polerones);
            Poleron_Legend_of_Zelda.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/poleron_zelda.png");
            productoRepository.save(Poleron_Legend_of_Zelda);

            //Juegos de mesa
            Producto Warhammer_40M = new Producto();
            Warhammer_40M.setCodigo("AP022");
            Warhammer_40M.setNombre("Warhammer 40M");
            Warhammer_40M.setDescripcion("Juego de mesa Warhammer 40,000");
            Warhammer_40M.setPrecio(new BigDecimal("89.990"));
            Warhammer_40M.setStock(10);
            Warhammer_40M.setCategoria(JuegosdeMesa);
            Warhammer_40M.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/warhammer_40m.png");
            productoRepository.save(Warhammer_40M);

            Producto Ajedrez = new Producto();
            Warhammer_40M.setCodigo("AP023");
            Warhammer_40M.setNombre("Ajedrez");
            Warhammer_40M.setDescripcion("Juego de mesa Ajedrez");
            Warhammer_40M.setPrecio(new BigDecimal("24.990"));
            Warhammer_40M.setStock(10);
            Warhammer_40M.setCategoria(JuegosdeMesa);
            Warhammer_40M.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/ajedrez.png");
            productoRepository.save(Ajedrez);

            Producto Shogi = new Producto();
            Warhammer_40M.setCodigo("AP024");
            Warhammer_40M.setNombre("Shogi");
            Warhammer_40M.setDescripcion("Juego de mesa Shogi");
            Warhammer_40M.setPrecio(new BigDecimal("59.990"));
            Warhammer_40M.setStock(10);
            Warhammer_40M.setCategoria(JuegosdeMesa);
            Warhammer_40M.setImagenUrl("https://levelupgamer-img.s3.us-east-1.amazonaws.com/shogi.png");
            productoRepository.save(Shogi);
        }
    }
}
