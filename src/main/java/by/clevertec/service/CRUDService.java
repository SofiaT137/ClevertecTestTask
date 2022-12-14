package by.clevertec.service;

import org.springframework.data.domain.Page;

public interface CRUDService<T> {

    void insert(T dto);

    T getById(Long id);

    Page<T> getAll(int pageNumber, int pageSize);

    void update(Long id, T dto);

    void delete(Long id);
}
