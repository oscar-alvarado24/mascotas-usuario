package com.project.adoption.pet.infrastructure.output.adoption.adapter;

import com.google.protobuf.Timestamp;
import com.project.adoption.pet.application.dto.AdoptionResponse;
import com.project.adoption.pet.application.dto.Adoptions;
import com.project.adoption.pet.application.dto.MultipleEmailsResponse;
import com.project.adoption.pet.domain.spi.IAdoptionPersistencePort;
import com.project.adoption.pet.infrastructure.output.adoption.client.AdoptionClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AdoptionAdapter implements IAdoptionPersistencePort {

    private final AdoptionClient adoptionClient;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * @param email of user to search adoptions
     * @return map of pets id belonging to the user
     */
    @Override
    public Map<Long, LocalDate> getPetsId(String email) {
        AdoptionResponse response = adoptionClient.getAdoptionsByEmail(email);

        return response.getAdoptionsList().stream()
                .collect(Collectors.toMap(
                        Adoptions::getId,
                        adoption -> timestampToLocalDate(adoption.getDateAdoption())
                ));
    }

    /**
     * @param emails
     * @return
     */
    @Override
    public Map<String, Map<Long, LocalDate>> getPetsIdByEmails(List<String> emails) {
        MultipleEmailsResponse response = adoptionClient.getAdoptionsByEmails(emails);
        Map<String, Map<Long, LocalDate>> petsIdByEmail = new HashMap<>();
        response.getAdoptionResponsesList().forEach(adoptionResponse -> {
            Map<Long, LocalDate> petsId = adoptionResponse.getAdoptionsList().stream()
                    .collect(Collectors.toMap(
                            Adoptions::getId,
                            adoption -> timestampToLocalDate(adoption.getDateAdoption())
                    ));
            petsIdByEmail.put(adoptionResponse.getEmail(),petsId);
        });
        return petsIdByEmail;
    }
    private LocalDate timestampToLocalDate(Timestamp timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        LocalDate time = instant.atZone(ZoneId.of("America/Bogota"))
                .toLocalDate();
        return time.plusDays(1L);
    }
}
