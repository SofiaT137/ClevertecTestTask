package by.clevertec.model.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity <K extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private K id;
}
