package Base.Ecommerce.Controller;

import Base.Ecommerce.Entity.Producto;
import Base.Ecommerce.Services.ProductoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/producto")
public class ProductoController extends BaseControllerImpl<Producto, ProductoServiceImpl>{
    //filtrar todos los productos de una categoira
    @GetMapping("/filtradoCategoria")
    public ResponseEntity<?> filtradoCategoria(@RequestParam String nombreCat){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.filtradoCategoria(nombreCat));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\":\"" + e.getMessage() + "\"}"));
        }
    }
    //Productos mas vendidos del mes
    @GetMapping("/productosMasVendidos")
    public ResponseEntity<?> filtradoCategoria(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.productosMasVendidos());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\":\"" + e.getMessage() + "\"}"));
        }
    }

}
