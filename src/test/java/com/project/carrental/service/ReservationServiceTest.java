package com.project.carrental.service;

import com.project.carrental.dto.request.CarRequest;
import com.project.carrental.dto.request.ReservationRequest;
import com.project.carrental.dto.request.UpdateReservationRequest;
import com.project.carrental.dto.response.CarDetailResponse;
import com.project.carrental.dto.response.CarResponse;
import com.project.carrental.dto.response.ReservationResponse;
import com.project.carrental.mapper.ReservationMapper;
import com.project.carrental.model.*;
import com.project.carrental.repository.CarRepository;
import com.project.carrental.repository.PriceRepository;
import com.project.carrental.repository.ReservationRepository;
import com.project.carrental.service.impl.ReservationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    void getReservationByIdShouldReturnReservationResponse () {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationMapper.reservationToReservationResponse(any(Reservation.class))).thenReturn(new ReservationResponse());

        ReservationResponse reservationResponse = reservationService.getReservationById(reservationId);

        assertThat(reservationResponse).isNotNull();
        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationMapper, times(1)).reservationToReservationResponse(reservation);
    }

    @Test
    void getReservationByIdShouldThrowEntityNotFoundException () {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            ReservationResponse reservationResponse = reservationService.getReservationById(reservationId);
        }).isInstanceOf(EntityNotFoundException.class);

        verify(reservationRepository, times(1)).findById(reservationId);
        verifyNoInteractions(reservationMapper);
    }

    @Test
    void getReservationsShouldReturnReservationResponses() {
        List<Reservation> reservations = List.of(new Reservation(), new Reservation());
        when(reservationRepository.findAll()).thenReturn(reservations);
        when(reservationMapper.reservationToReservationResponse(any(Reservation.class))).thenReturn(new ReservationResponse());

        List<ReservationResponse> reservationResponses = reservationService.getReservations();

        assertThat(reservationResponses).isNotEmpty();
        assertThat(reservationResponses.size()).isEqualTo(reservations.size());
        verify(reservationRepository, times(1)).findAll();
        verify(reservationMapper, times(reservations.size())).reservationToReservationResponse(any(Reservation.class));
    }

    @Test
    void createReservationShouldReturnReservationResponse() {
        ReservationRequest reservationRequest = createSampleReservationRequest();
        Long carId = 1L;
        Car car = Car.builder().price(createSamplePrice()).build();
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(reservationMapper.reservationToReservationResponse(any(Reservation.class))).thenReturn(new ReservationResponse());

        ReservationResponse reservationResponse = reservationService.createReservation(carId, reservationRequest);

        assertThat(reservationResponse).isNotNull();
        verify(carRepository, times(1)).findById(carId);
        verify(reservationMapper, times(1)).reservationToReservationResponse(any(Reservation.class));
    }

    @Test
    void createReservationShouldThrowEntityNotFoundExceptionWhenWrongCarId() {
        ReservationRequest reservationRequest = createSampleReservationRequest();
        Long carId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            ReservationResponse reservationResponse = reservationService.createReservation(carId, reservationRequest);
        }).isInstanceOf(EntityNotFoundException.class);

        verify(carRepository, times(1)).findById(carId);
        verifyNoInteractions(reservationRepository);
        verifyNoInteractions(reservationMapper);
    }

    @Test
    void updateReservationShouldReturnReservationResponse() {
        Long reservationId = 1L;
        UpdateReservationRequest updateReservationRequest = createSampleUpdateReservationRequest();
        Reservation reservation = createSampleReservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationMapper.reservationToReservationResponse(any(Reservation.class))).thenReturn(new ReservationResponse());

        ReservationResponse reservationResponse = reservationService.updateReservation(reservationId, updateReservationRequest);

        assertThat(reservationResponse).isNotNull();
        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(reservationMapper, times(1)).reservationToReservationResponse(any(Reservation.class));
    }

    @Test
    void updateReservationShouldThrowEntityNotFoundExceptionWhenWrongReservationId() {
        Long reservationId = 1L;
        UpdateReservationRequest updateReservationRequest = createSampleUpdateReservationRequest();
        Reservation reservation = createSampleReservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            ReservationResponse reservationResponse = reservationService.updateReservation(reservationId, updateReservationRequest);
        }).isInstanceOf(EntityNotFoundException.class);

        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationRepository, never()).save(any(Reservation.class));
        verifyNoInteractions(reservationMapper);
    }

    @Test
    void deleteReservationShouldDeleteReservation() {
        Long reservationId = 1L;
        Reservation reservation = createSampleReservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        assertThatNoException().isThrownBy(() -> {
            reservationService.deleteReservation(reservationId);
        });

        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationRepository, times(1)).delete(reservation);
    }

    @Test
    void deleteReservationShouldThrowEntityNotFoundExceptionWhenWrongReservationId() {
        Long reservationId = 1L;
        Reservation reservation = createSampleReservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            reservationService.deleteReservation(reservationId);
        }).isInstanceOf(EntityNotFoundException.class);

        verify(reservationRepository, times(1)).findById(reservationId);
        verify(reservationRepository, never()).delete(any(Reservation.class));
    }

    @Test
    void calculateCostShouldReturnCostForDay() {
        Price price = createSamplePrice();

        BigDecimal cost = reservationService.calculateCost(price, LocalDate.now(), LocalDate.now());

        assertThat(cost).isNotNull();
        assertThat(cost).isEqualTo(price.getForDay());
    }

    @Test
    void calculateCostShouldReturnCostForFourDays() {
        Price price = createSamplePrice();

        BigDecimal cost = reservationService.calculateCost(price, LocalDate.now(), LocalDate.now().plusDays(3));

        assertThat(cost).isNotNull();
        assertThat(cost).isEqualTo(price.getForTwoToFourDays().multiply(BigDecimal.valueOf(4)));
    }

    @Test
    void calculateCostShouldReturnCostForFiveDays() {
        Price price = createSamplePrice();

        BigDecimal cost = reservationService.calculateCost(price, LocalDate.now(), LocalDate.now().plusDays(4));

        assertThat(cost).isNotNull();
        assertThat(cost).isEqualTo(price.getForWeek().multiply(BigDecimal.valueOf(5)));
    }

    private ReservationRequest createSampleReservationRequest() {
        return new ReservationRequest(LocalDate.now(), LocalDate.now().plusDays(2), "John", "Doe", "john@doe.com", "123456789");
    }

    private UpdateReservationRequest createSampleUpdateReservationRequest() {
        return new UpdateReservationRequest(LocalDate.now(), LocalDate.now().plusDays(2), "John", "Doe", "john@doe.com", "123456789", BigDecimal.valueOf(200));
    }

    private Reservation createSampleReservation() {
        Driver driver = Driver.builder()
                .firstName("John")
                .lastName("Doe")
                .email("John@doe.com")
                .phoneNumber("123456789")
                .build();

        return Reservation.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2))
                .cost(BigDecimal.valueOf(200))
                .driver(driver)
                .build();
    }

    private Price createSamplePrice() {
        return Price.builder()
                .forDay(BigDecimal.valueOf(50))
                .forTwoToFourDays(BigDecimal.valueOf(20))
                .forWeek(BigDecimal.TEN)
                .build();
    }
}