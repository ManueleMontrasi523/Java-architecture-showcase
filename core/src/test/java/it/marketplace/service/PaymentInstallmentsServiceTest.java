package it.marketplace.service;

import it.marketplace.common.dto.PaymentInstallmentsDto;
import it.marketplace.repository.PaymentInstallmentsRepository;
import it.marketplace.service.impl.PaymentInstallmentsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentInstallmentsServiceTest {

    @Mock
    private PaymentInstallmentsRepository repository;

    @InjectMocks
    private PaymentInstallmentsServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllByCode_WhenInstallmentsExist_ThenArrangeActAssert() {
        // Arrange
        String orderCode = "ORD123";
        PaymentInstallmentsDto entity = new PaymentInstallmentsDto();
        when(repository.findByOrderCode(orderCode)).thenReturn(List.of(entity));
        // Act
        List<PaymentInstallmentsDto> result = service.findAllByCode(orderCode);
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findByOrderCode(orderCode);
    }
}

