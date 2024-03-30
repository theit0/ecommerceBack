package Base.Ecommerce.Repositories;

import Base.Ecommerce.Entity.MP;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
@Repository
public class myBaseRepository implements BaseRepository<MP, Long> {
    @Override
    public void flush() {

    }

    @Override
    public <S extends MP> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends MP> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<MP> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public MP getOne(Long aLong) {
        return null;
    }

    @Override
    public MP getById(Long aLong) {
        return null;
    }

    @Override
    public MP getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends MP> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends MP> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends MP> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends MP> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends MP> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends MP> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends MP, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends MP> S save(S entity) {
        return null;
    }

    @Override
    public <S extends MP> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<MP> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<MP> findAll() {
        return null;
    }

    @Override
    public List<MP> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(MP entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends MP> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<MP> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<MP> findAll(Pageable pageable) {
        return null;
    }
    // Implementa los métodos de la interfaz BaseRepository aquí
}