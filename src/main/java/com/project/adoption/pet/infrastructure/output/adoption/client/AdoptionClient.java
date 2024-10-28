package com.project.adoption.pet.infrastructure.output.adoption.client;

import com.project.adoption.pet.application.dto.AdoptionEmailRequest;
import com.project.adoption.pet.application.dto.AdoptionResponse;
import com.project.adoption.pet.application.dto.AdoptionServiceGrpc;
import com.project.adoption.pet.application.dto.MultipleEmailsRequest;
import com.project.adoption.pet.application.dto.MultipleEmailsResponse;
import com.project.adoption.pet.infrastructure.common.util.Constants;
import com.project.adoption.pet.infrastructure.exception.CouldNotCommunicateWithAdoptionException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdoptionClient {
    @GrpcClient("adoption-service")
    private AdoptionServiceGrpc.AdoptionServiceBlockingStub adoptionStub;


    public AdoptionResponse getAdoptionsByEmail(String email) {
        try {
            AdoptionEmailRequest request = AdoptionEmailRequest.newBuilder().setEmail(email).build();
            return adoptionStub.getAdoptionsByEmail(request);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new CouldNotCommunicateWithAdoptionException(Constants.MSG_ERROR_CLIENT_ADOPTION);
        }
    }

    public MultipleEmailsResponse getAdoptionsByEmails(List<String> emails) {
        try {
            List<AdoptionEmailRequest> adoptionEmailRequests = new ArrayList<>();
            emails.forEach(email->{
                AdoptionEmailRequest request = AdoptionEmailRequest.newBuilder().setEmail(email).build();
                adoptionEmailRequests.add(request);
            });
            MultipleEmailsRequest requests = MultipleEmailsRequest.newBuilder().addAllEmails(adoptionEmailRequests).build();
            return adoptionStub.getAdoptionsByEmails(requests);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new CouldNotCommunicateWithAdoptionException(Constants.MSG_ERROR_CLIENT_ADOPTION);
        }
    }
}
