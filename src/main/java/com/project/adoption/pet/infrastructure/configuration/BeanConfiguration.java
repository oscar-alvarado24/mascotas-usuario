package com.project.adoption.pet.infrastructure.configuration;

import com.project.adoption.pet.application.dto.AdoptionServiceGrpc;
import com.project.adoption.pet.application.mapper.IPetMapper;
import com.project.adoption.pet.domain.api.IAdoptionServicePort;
import com.project.adoption.pet.domain.api.IPetServicePort;
import com.project.adoption.pet.domain.api.IUserServicePort;
import com.project.adoption.pet.domain.spi.IAdoptionPersistencePort;
import com.project.adoption.pet.domain.spi.IPetPersistencePort;
import com.project.adoption.pet.domain.spi.IUserPersistencePort;
import com.project.adoption.pet.domain.usecase.AdoptionUseCase;
import com.project.adoption.pet.domain.usecase.PetUseCase;
import com.project.adoption.pet.domain.usecase.UserUseCase;
import com.project.adoption.pet.infrastructure.output.adoption.adapter.AdoptionAdapter;
import com.project.adoption.pet.infrastructure.output.adoption.client.AdoptionClient;
import com.project.adoption.pet.infrastructure.output.dynamo.adapter.DynamoAdapter;
import com.project.adoption.pet.infrastructure.output.dynamo.mapper.IDynamoMapper;
import com.project.adoption.pet.infrastructure.output.dynamo.repository.DynamoRepository;
import com.project.adoption.pet.infrastructure.output.dynamo.repository.IDynamoRepository;
import com.project.adoption.pet.infrastructure.output.pet.adapter.PetAdapter;
import com.project.adoption.pet.infrastructure.output.pet.client.PetClient;
import com.project.adoption.pet.infrastructure.output.pet.mapper.IPetClientMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final PetClient petClient;
    private final IPetClientMapper petClientMapper;
    private final IDynamoMapper dynamoMapper;

    @Bean
    public IAdoptionPersistencePort adoptionPersistencePort() {
        return new AdoptionAdapter(adoptionClient());
    }

    @Bean
    public IAdoptionServicePort adoptionServicePort() {
        return new AdoptionUseCase(adoptionPersistencePort());
    }

    @Bean
    public IPetPersistencePort petPersistencePort() {
        return new PetAdapter(petClient,petClientMapper);
    }

    @Bean
    public IPetServicePort petServicePort() {
        return new PetUseCase(petPersistencePort());
    }
    @Bean
    public IDynamoRepository dynamoRepository() {
        return new DynamoRepository();
    }
    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new DynamoAdapter(dynamoRepository(),dynamoMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }

    @Bean
    public AdoptionClient adoptionClient() {
        return new AdoptionClient();
    }
}
