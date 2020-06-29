package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.requestForFreezeDto.FreezeResponseDto;
import kg.sabyrov.terrafit.dto.requestForFreezeDto.RequestFreezeDto;
import kg.sabyrov.terrafit.entity.RequestFreeze;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.RequestNotFoundException;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.models.ResponseMessage;

import java.util.List;

public interface RequestFreezeService extends BaseService<RequestFreeze> {
    List<FreezeResponseDto> getAllByStatus(Status status);

    ResponseMessage create(RequestFreezeDto requestFreezeDto) throws UserNotFoundException;

    ResponseMessage approving(List<Long> idList) throws RequestNotFoundException;

    ResponseMessage cancelling(List<Long> idList);

}
