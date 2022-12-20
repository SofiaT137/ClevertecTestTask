package by.clevertec.service;

import org.springframework.data.domain.Page;

/**
 * "CRDService" interface features CRUD Service functionality
 * @param <T> The entity object
 */
public interface CRUDService<T> {

    /**
     * Method provides service layer functionality for inserting the entity object
     * @param dto The "Dto" object
     */
    void insert(T dto);

    /**
     * Method provides service layer functionality for searching the entity object by its identifier
     * @param id Long id
     * @return The entity object
     */
    T getById(Long id);

    /**
     * Method provides service layer functionality for searching all the entity objects
     * @param pageNumber int number of the page
     * @param pageSize int current page size
     * @return Page of the entity objects
     */
    Page<T> getAll(int pageNumber, int pageSize);

    /**
     * Method provides service layer functionality for updating the entity object by its identifier
     * @param id Object id(Long value)
     * @param dto The "Dto" object
     */
    void update(Long id, T dto);

    /**
     * Method provides service layer  functionality for removing the entity object by its identifier
     * @param id Long id
     */
    void delete(Long id);
}
