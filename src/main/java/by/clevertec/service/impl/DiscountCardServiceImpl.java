package by.clevertec.service.impl;

import by.clevertec.persistence.entity.DiscountCard;
import by.clevertec.persistence.repository.DiscountCardRepository;
import by.clevertec.service.DiscountCardService;
import by.clevertec.service.dto.DiscountCardDto;
import by.clevertec.service.exception.CannotFindEntityException;
import by.clevertec.service.mapper.DiscountCardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService<DiscountCardDto> {

    private static final String CANNOT_FIND_CARD_EXCEPTION = "Cannot find the discount card with id = ";
    private final DiscountCardRepository discountCardRepository;
    private final DiscountCardMapper discountCardMapper;

    @Override
    public void insert(DiscountCardDto discountCardDto) {
        discountCardRepository
                .save(discountCardMapper
                        .mapToDiscountCard(discountCardDto));
    }

    @Override
    public DiscountCardDto getById(Long id) {
        return discountCardMapper
                .mapToDiscountCardDto(discountCardRepository
                        .findById(id)
                        .orElseThrow(() -> new CannotFindEntityException(CANNOT_FIND_CARD_EXCEPTION, id)));
    }

    @Override
    public Page<DiscountCardDto> getAll(int pageNumber, int pageSize) {
        Page<DiscountCard> discountCard = discountCardRepository
                .findAll(PageRequest.of(pageNumber, pageSize));
        return discountCard
                .map(discountCardMapper::mapToDiscountCardDto);
    }

    @Override
    public void update(Long id, DiscountCardDto discountCardDto) {
        DiscountCard discountCard = discountCardMapper
                .mapToDiscountCard(getById(id));
        discountCardMapper
                .updateDiscountCardFromDiscountCardDto(discountCardDto, discountCard);
    }

    @Override
    public void delete(Long id) {
        discountCardRepository
                .deleteById(id);
    }
}
