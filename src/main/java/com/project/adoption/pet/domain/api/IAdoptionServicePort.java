package com.project.adoption.pet.domain.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IAdoptionServicePort {
    Map<Long, LocalDate> getPetsId(String userEmail);
    Map<String,Map<Long, LocalDate>> getPetsIdByEmails (List<String> emails);
}
