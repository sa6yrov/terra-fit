package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.visitDto.VisitDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitRequestTimeDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitResponseDto;
import kg.sabyrov.terrafit.entity.Visit;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;

import java.util.List;

public interface VisitService extends BaseService<Visit> {
    VisitResponseDto create(VisitDto visitDto) throws SubscriptionNotFoundException;
    List<VisitResponseDto> getAllVisitsBetweenTime(VisitRequestTimeDto visitRequestTimeDto);
}
