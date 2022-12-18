package by.clevertec.service.impl;

import by.clevertec.persistence.repository.DiscountCardRepository;
import by.clevertec.service.DiscountCardService;
import by.clevertec.service.dto.DiscountCardDto;
import by.clevertec.service.mapper.DiscountCardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService<DiscountCardDto> {

    private final DiscountCardRepository discountCardRepository;

    private final DiscountCardMapper discountCardMapper;

    @Override
    public void insert(DiscountCardDto discountCardDto) {
        discountCardRepository.save(discountCardMapper.mapToDiscountCard(discountCardDto));
    }

    @Override
    public DiscountCardDto getById(Long id) {
        return discountCardMapper.mapToDiscountCardDto(discountCardRepository.findById(id).get());
    }

    @Override
    public Page<DiscountCardDto> getAll(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public void update(Long id, DiscountCardDto dto) {

    }

    @Override
    public void delete(Long id) {
        discountCardRepository.deleteById(id);
    }
}
