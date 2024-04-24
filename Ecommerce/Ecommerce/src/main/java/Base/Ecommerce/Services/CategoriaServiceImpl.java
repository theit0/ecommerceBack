package Base.Ecommerce.Services;

import Base.Ecommerce.Entity.Categoria;
import Base.Ecommerce.Entity.Cliente;
import Base.Ecommerce.Repositories.BaseRepository;
import Base.Ecommerce.Repositories.CategoriaRepository;
import Base.Ecommerce.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl extends BaseServiceImpl<Categoria,Long> implements CategoriaService{
    @Autowired
    CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(BaseRepository<Categoria, Long> baseRepository, CategoriaRepository categoriaRepository) {
        super(baseRepository);

    }
}
