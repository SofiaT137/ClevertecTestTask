package by.clevertec.persistence.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class AbstractEntity <K extends Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private K id;
}
