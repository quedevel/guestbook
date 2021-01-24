package org.zerock.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import org.zerock.guestbook.common.CommonConstant;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestbookRepository;
import org.zerock.guestbook.util.StringUtil;

import java.util.Optional;
import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO....................");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);
        repository.save(entity);
        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        Function<Guestbook, GuestbookDTO> fn = entity -> entityToDto(entity);

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);
        return result.isPresent()? entityToDto(result.get()) : null;
    }

    @Override

    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> result = repository.findById(dto.getGno());
        if(result.isPresent()){
            Guestbook entity = result.get();
            entity.setTitle(dto.getTitle());
            entity.setContent(dto.getContent());
            repository.save(entity);
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String type = StringUtil.isEmpty(requestDTO.getType())? null : requestDTO.getType().trim();
        String keyword = StringUtil.isEmpty(requestDTO.getKeyword())? null : requestDTO.getKeyword().trim();

        BooleanExpression expression = qGuestbook.gno.gt(0L);

        booleanBuilder.and(expression);

        if(StringUtil.isNotEmpty(type)){
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            if(type.contains(CommonConstant.SEARCH_TYPE_TITLE.getValue())){
                conditionBuilder.or(qGuestbook.title.contains(keyword));
            }
            if(type.contains(CommonConstant.SEARCH_TYPE_CONTENT.getValue())){
                conditionBuilder.or(qGuestbook.content.contains(keyword));
            }
            if(type.contains(CommonConstant.SEARCH_TYPE_WRITER.getValue())){
                conditionBuilder.or(qGuestbook.writer.contains(keyword));
            }
            booleanBuilder.and(conditionBuilder);
        }

        return booleanBuilder;
    }
}
