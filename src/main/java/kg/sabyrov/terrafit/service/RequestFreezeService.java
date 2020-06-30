package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.requestForFreezeDto.FreezeResponseDto;
import kg.sabyrov.terrafit.dto.requestForFreezeDto.RequestApprovingCancellingDto;
import kg.sabyrov.terrafit.dto.requestForFreezeDto.RequestFreezeDto;
import kg.sabyrov.terrafit.entity.RequestFreeze;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.RequestNotFoundException;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.models.ResponseMessage;

import javax.mail.MessagingException;
import java.util.List;

public interface RequestFreezeService extends BaseService<RequestFreeze> {
    List<FreezeResponseDto> getAllModelByStatusConsideration() throws RequestNotFoundException;
    List<RequestFreeze> getAllByStatus(Status s);
    ResponseMessage create(RequestFreezeDto requestFreezeDto) throws UserNotFoundException;

    ResponseMessage approving(RequestApprovingCancellingDto requestApprovingCancellingDto) throws RequestNotFoundException, MessagingException;

    ResponseMessage cancelling(RequestApprovingCancellingDto requestApprovingCancellingDto) throws MessagingException, RequestNotFoundException;

}
