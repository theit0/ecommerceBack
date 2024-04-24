package Base.Ecommerce.Controller;

import Base.Ecommerce.Entity.Categoria;
import Base.Ecommerce.Repositories.CategoriaRepository;
import Base.Ecommerce.Services.CategoriaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/categoria")
public class CategoriaController extends BaseControllerImpl<Categoria, CategoriaServiceImpl>{
    @Autowired
    CategoriaRepository categoriaRepository;

}
